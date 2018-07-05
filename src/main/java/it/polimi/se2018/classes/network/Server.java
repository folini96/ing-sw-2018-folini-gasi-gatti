package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.view.VirtualView;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Andrea Folini
 * main server of the application
 * intantiate rmi and socket servers
 * handle message exchange between the clients and the proxy view that communicates with the model and the controller
 */
public class Server {
    private  int rmiPort;
    private int socketPort ;
    private Timer lobbyTimer = new Timer();
    private ConcurrentHashMap<Integer,Timer> playTimer = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,Timer> windowTimer = new ConcurrentHashMap<>();
    private int lobbyTime;
    private int playTime;
    private int passedTime;
    private int lobbyNumber=0;
    private ConcurrentHashMap<Integer,Boolean> lockCommunication=new ConcurrentHashMap<>();
    private RMIServerImplementation rmiHandler;
    private CopyOnWriteArrayList<VirtualView> proxyViews;
    private ArrayList<VirtualClientInterface> removedClients = new ArrayList<>();
    private CopyOnWriteArrayList<VirtualClientInterface> clients = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<VirtualClientInterface> disconnectedClients = new CopyOnWriteArrayList<>();

    /**
     * set the configurations parameters and instantiate rmi and socket servers
     * @param rmiPort port of rmi server
     * @param socketPort port of socket server
     * @param lobbyTime the max waiting time for a lobby
     * @param playTime the max time of every turn
     */
    public void main(int rmiPort,int socketPort,int lobbyTime,int playTime) {
        proxyViews=new CopyOnWriteArrayList<>();
        this.rmiPort=rmiPort;
        this.socketPort=socketPort;
        this.lobbyTime=lobbyTime;
        this.playTime=playTime*1000;
        rmiMain();
        socketMain();

    }

    /**
     * bind rmi server on register
     */
    private void rmiMain() {

        try {

            LocateRegistry.createRegistry(rmiPort);

        } catch (RemoteException e) {

            System.out.println("Registry già presente!");
        }

        try {

            rmiHandler = new RMIServerImplementation(this);
            Naming.rebind("//0.0.0.0/MyServer", rmiHandler);

        } catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

    }

    /**
     * launch socket client gatherer
     */
    private void socketMain() {
        (new SocketClientGatherer(this, socketPort)).start();

    }


    /**
     * add a client to the existing lobby or to a no one
     * @param newClient the client that will be add
     */
    public synchronized void addClient(VirtualClientInterface newClient) {
      int clientsInCurrentLobby;
      newClient.setLobbyNumber(lobbyNumber);
      clients.add(newClient);
      System.out.println("Il client " + newClient.getUsername() + " è connesso nella lobby numero "+lobbyNumber);
      clientsInCurrentLobby=0;
      for(VirtualClientInterface client:clients){
          if (client.getLobbyNumber()==lobbyNumber){
              clientsInCurrentLobby++;
          }
      }
      if (clientsInCurrentLobby == 2) {
          startLobby();
      }
      if (clientsInCurrentLobby == 4) {
          lobbyTimer.cancel();
          startMatch();
      }


    }

    /**
     * start a new game lobby
     */
    private void startLobby() {
        passedTime=0;
        TimerTask startMatchTask = new TimerTask() {
            public void run() {
                passedTime++;
                if (passedTime==lobbyTime){
                    lobbyTimer.cancel();
                    startMatch();
                }else{
                    int playersInLobby=0;
                    for (VirtualClientInterface client:clients){
                        try{
                            client.ping();
                        }catch (Exception e){
                            removedClients.add(client);

                            System.out.println("il client " + client.getUsername() + " si è disconnesso dalla lobby " + client.getLobbyNumber());                    }
                    }
                    for (VirtualClientInterface removedClient: removedClients){
                        if (clients.contains(removedClient)){
                            clients.remove(removedClient);
                        }
                    }
                    for (VirtualClientInterface clients:clients){
                        if (clients.getLobbyNumber()==lobbyNumber){
                            playersInLobby++;

                        }
                    }
                    if (playersInLobby<2){
                        lobbyTimer.cancel();
                    }
                    removedClients.clear();
                }

            }
        };
        lobbyTimer=new Timer();
        lobbyTimer.schedule(startMatchTask,1000, 1000);

    }

    /**
     * start a new match after the lobby reached max waiting time or the max player limit
     * instantiate a new proxy view and a new controller to handle the match
     */
    private void startMatch() {
        ArrayList<String> usernames = new ArrayList<>();
        MatchHandlerController controller=new MatchHandlerController();
        proxyViews.add(new VirtualView(this,lobbyNumber));
        for (VirtualView proxy:proxyViews){
            if (proxy.getMatchNumber()==lobbyNumber){
                controller.setView(proxy);
                proxy.addObserver(controller);
            }
        }

        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber() == lobbyNumber){
                usernames.add(client.getUsername());
            }
        }
        controller.handleStartMatch(usernames);
        lockCommunication.put(lobbyNumber,false);
        lobbyNumber++;
    }

    /**
     * check if a username is alredy used in the current lobby
     * @param username the username that needs to be checked
     * @return true if the username if free, false otherwise
     */
    public Boolean checkUsername(String username) {
        for (VirtualClientInterface client : clients) {

            if ((lobbyNumber==client.getLobbyNumber())&&(client.getUsername().equals(username))) {
                return false;
            }
        }
        return true;
    }


    /**
     * send the event got from the network to the right proxy view
     * @param viewControllerEvent the event that will be send
     * @param clientLobbyNumber the lobby number of the client that sent the event
     * @param endForDisconnection true if the event is caused by the turn number that expired or a disconnection, false if it is from a client
     */
    public synchronized void sendToServer(ViewControllerEvent viewControllerEvent,int clientLobbyNumber, boolean endForDisconnection){
        if (lockCommunication!=null){

            if ((lockCommunication.containsKey(clientLobbyNumber))&&(!lockCommunication.get(clientLobbyNumber))||(endForDisconnection)){
                VirtualView proxyView=new VirtualView(this,clientLobbyNumber);
                for (VirtualView proxy:proxyViews){
                    if (clientLobbyNumber==proxy.getMatchNumber()){
                        proxyView=proxy;
                    }
                }
                proxyView.sendToServer(viewControllerEvent);

            }

        }


    }

    /**
     * send a message to notify a wrong move
     * @param message the message for the client
     * @param matchNumber the lobby number of the client
     */
    public void notValideMoveMessage(Message message, int matchNumber) {

        for (VirtualClientInterface client : clients) {
            if ((client.getUsername().equals(message.getPlayer()))&&(client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))) {

                try{
                    client.sendToClient(message);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }
        }
    }

    /**
     * send to every player of the right lobby the match data
     * @param startMatchEvent contains the parameters of the new match
     * @param matchNumber the lobby number of the match
     */
    public synchronized void sendStartMatchEvent(StartMatchEvent startMatchEvent, int matchNumber) {
        if (checkNoPlayersLeft(matchNumber)){
            lockCommunication.put(matchNumber,true);
            cancelTimer(matchNumber);
            for (VirtualClientInterface client : clients) {
                if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))) {
                    try{
                        client.lastPlayerLeft();
                    }catch (Exception e){
                        System.out.println("non è rimasto nessun giocatore nella lobby"+ matchNumber);
                    }
                }
            }
            deleteEndedMatch(matchNumber);
        }else {

            for (VirtualClientInterface client : clients) {
                if (client.getLobbyNumber() == matchNumber && (!disconnectedClients.contains(client))) {
                    try {

                        client.sendToClient(startMatchEvent);
                        for (VirtualClientInterface disconnectedClient:disconnectedClients){
                            client.otherPlayerDisconnected(new OtherPlayerDisconnectedEvent(disconnectedClient.getUsername()));
                        }
                    } catch (Exception e) {
                        connectionError(client, matchNumber);
                    }
                }
            }
        }
    }

    /**
     * send a notification to every client of the right lobby that a new round started
     * @param startRoundEvent contains the parameters for the new round
     * @param matchNumber the lobby number of the match
     */
    public void sendStartRoundEvent(StartRoundEvent startRoundEvent, int matchNumber) {
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(startRoundEvent);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }

        }

    }

    /**
     * send a notification to every client of the right lobby that a new turn started and start the timer to check player inactivity.
     * the turn get skipped if the player that needs to play is disconnected or suspended
     * @param startTurnEvent contains the parameters of the new turn
     * @param matchNumber the lobby number of the match
     */
    public void sendStartTurnEvent(StartTurnEvent startTurnEvent, int matchNumber) {
        lockCommunication.put(matchNumber,false);
        boolean skipTurn=false;
        for (VirtualClientInterface player:disconnectedClients){
            if ((player.getLobbyNumber()==matchNumber)&&(player.getUsername().equals(startTurnEvent.getPlayer()))){
                skipTurn=true;
            }
        }
        if (!skipTurn){
            for (VirtualClientInterface client : clients) {
                if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                    try{
                        client.sendToClient(startTurnEvent);
                    }catch (Exception e){
                        connectionError(client,matchNumber);
                        if (client.getUsername().equals(startTurnEvent.getPlayer())){
                            return;
                        }

                    }
                }

            }
            class PlayTask extends TimerTask{
                private int lobby;
                private PlayTask(int lobbyNumber){
                    this.lobby=lobbyNumber;
                };
                public void run() {
                    lockCommunication.put(lobby,true);
                    endByTime(lobby,startTurnEvent.getPlayer());
                    playTimer.remove(lobby);
                    sendToServer(new EndTurnEvent(),lobby,true);
                }
            }
            playTimer.put(matchNumber,new Timer());
            playTimer.get(matchNumber).schedule(new PlayTask(matchNumber),playTime);
        }else{
            sendToServer(new EndTurnEvent(),matchNumber,false);
        }


    }

    /**
     * send a notification to every player in the right lobby that the round ended
     * @param endRoundEvent contains the parameters of the ended round
     * @param matchNumber the lobby number of the match
     */
    public void sendEndRoundEvent(EndRoundEvent endRoundEvent, int matchNumber) {
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(endRoundEvent);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }

            }
        }
    }

    /**
     * send the window to chose to every player in the right lobby
     * @param windows contains the windows for every player
     * @param matchNumber the lobby number of the match
     */
    public void sendWindowToChose(WindowSide[] windows, int matchNumber) {
        int firstPlayerInLobby=-1;
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber()==matchNumber){
                if (firstPlayerInLobby<0){
                    firstPlayerInLobby=clients.indexOf(client);
                }
                int windowPosition=(clients.indexOf(client)-firstPlayerInLobby)*4;
                WindowSide[] windowToSend=Arrays.copyOfRange(windows,windowPosition,windowPosition+4);
                client.sendWindowToChose(new WindowToChoseEvent(windowToSend,playTime));
            }
        }
        startWindowTimer(matchNumber);
    }

    /**
     * start a timer to check the disconnection during the lobby selection
     * @param lobbyNumber the lobby number of the match
     */
    public void startWindowTimer(int lobbyNumber){
        TimerTask windowTask=new TimerTask(){
            public void run() {
                for (VirtualClientInterface client:clients){
                    try{
                        client.ping();
                    }catch (Exception e){
                        windowConnectionError(client,lobbyNumber);
                    }
                }
            }
        };
        windowTimer.put(lobbyNumber,new Timer());
        windowTimer.get(lobbyNumber).schedule(windowTask,1000,1000);

    }

    /**
     * handle a connection error during the window selection
     * @param disconnectedClient the client that lost connection
     * @param matchNumber the lobby number of the mathch
     */
    private void windowConnectionError(VirtualClientInterface disconnectedClient, int matchNumber){
        disconnectedClients.add(disconnectedClient);
        Random random = new Random();
        int randomInt = random.nextInt(4);
        for (VirtualView proxy:proxyViews){
            if (matchNumber==proxy.getMatchNumber()){
                proxy.sendToServer(new ChoseWindowEvent(randomInt,disconnectedClient.getUsername()));
            }
        }

    }

    /**
     * send a notification to every player in the right lobby that a window has been modified
     * @param modifiedWindowEvent contains the modified window
     * @param matchNumber the lobby number of the match
     */
    public void sendModifiedWindow(ModifiedWindowEvent modifiedWindowEvent, int matchNumber){
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(modifiedWindowEvent);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }
        }
    }
    /**
     * send a notification to every player in the right lobby that the draft has been modified
     * @param modifiedDraftEvent contains the modified draft
     * @param matchNumber the lobby number of the match
     */
    public void sendModifiedDraft(ModifiedDraftEvent modifiedDraftEvent, int matchNumber){
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(modifiedDraftEvent);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }
        }
    }
    /**
     * send a notification to every player in the right lobby that the round track has been modified
     * @param modifiedRoundTrack contains the modified round track
     * @param matchNumber the lobby number of the match
     */
    public void sendModifiedRoundTrack(ModifiedRoundTrack modifiedRoundTrack, int matchNumber){
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(modifiedRoundTrack);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }
        }
    }
    /**
     * send a notification to every player in the right lobby that the match ended
     * @param endMatchEvent contains the points of every player in the match
     * @param matchNumber the lobby number of the match
     */
    public void sendEndMatchEvent(EndMatchEvent endMatchEvent, int matchNumber){
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(endMatchEvent);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }
        }
        deleteEndedMatch(matchNumber);
    }

    /**
     * send to the right player the new dice extracted from the dice bag
     * @param newDiceFromBagEvent contains the new dice
     * @param matchNumber the lobby number of the match
     */
    public void sendNewDiceFromBag(NewDiceFromBagEvent newDiceFromBagEvent, int matchNumber){
        for (VirtualClientInterface client : clients) {
            if ((client.getUsername().equals(newDiceFromBagEvent.getPlayer()))&&(client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))) {

                try{
                    client.sendToClient(newDiceFromBagEvent);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }
        }
    }

    /**
     * send to the right player the effect of the tool card selected
     * @param effectEvent the effect of the card
     * @param matchNumber the lobby number of the match
     */
    public void sendEffect(SendEffectEvent effectEvent,int matchNumber){
        for (VirtualClientInterface client : clients) {
            if ((client.getUsername().equals(effectEvent.getPlayer()))&&(client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))) {

                try{
                    client.sendToClient(effectEvent);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }
        }
    }

    /**
     * send a notification to every player in the right lobby that the favor tokens of a player and a tool card has been modified
     * @param modifiedTokenEvent contains the new token values
     * @param matchNumber the lobby number of the match
     */
    public void sendModifiedToken(ModifiedTokenEvent modifiedTokenEvent,int matchNumber){
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(modifiedTokenEvent);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }
        }
    }

    /**
     * send a notification to every player in the right lobby that the time for the turn expired
     * @param lobbyNumber the lobby number of the match
     * @param player the player that will be suspended for inactivity
     */
    public void endByTime(int lobbyNumber,String player){
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==lobbyNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.endByTime(player);
                    if (client.getUsername().equals(player)){
                        disconnectedClients.add(client);
                    }

                }catch (Exception e){
                    connectionError(client,lobbyNumber);
                }
            }
        }
        if (checkNoPlayersLeft(lobbyNumber)){
            lockCommunication.put(lobbyNumber,true);
            cancelTimer(lobbyNumber);
            for (VirtualClientInterface client : clients) {
                if ((client.getLobbyNumber()==lobbyNumber)&& (!disconnectedClients.contains(client))) {
                    try{
                        client.lastPlayerLeft();
                    }catch (Exception e){
                        System.out.println("non è rimasto nessun giocatore nella lobby"+ lobbyNumber);
                    }
                }
            }
            deleteEndedMatch(lobbyNumber);
        }
    }

    /**
     * check if there are less than two players left in a lobby
     * @param matchNumber the lobby number of the match
     * @return true if there are less than two players, false otherwise
     */
    public boolean checkNoPlayersLeft(int matchNumber){
        int disconnected=0;
        int connected=0;
        for (VirtualClientInterface client:clients){
            if (client.getLobbyNumber()==matchNumber){
                connected++;
            }
        }
        for (VirtualClientInterface client:disconnectedClients){
            if (client.getLobbyNumber()==matchNumber){
                disconnected++;
            }
        }
        if ((connected-disconnected)<2){
            return true;
        }else{
            return false;
        }
    }

    /**
     * cancel a turn timer when the current player end his turn before the time expires
     * @param matcNumber
     */
    public void cancelTimer(int matcNumber){
        if (playTimer.containsKey(matcNumber)){
            playTimer.get(matcNumber).cancel();
            playTimer.remove(matcNumber);
        }
    }

    /**
     * reconnect an rmi client that was suspended
     * @param username the name of the client that needs to be reconnected
     * @param lobbyNumber the lobby number of the client that needs to be reconnected
     */
    public void reconnectRMIClient(String username, int lobbyNumber){
        VirtualClientInterface reconnectingClient=null;
        for (VirtualClientInterface disconnectedClient:disconnectedClients){
            if ((disconnectedClient.getUsername().equals(username))&&disconnectedClient.getLobbyNumber()==lobbyNumber){
                reconnectingClient=disconnectedClient;
            }
        }
        reconnectClient(reconnectingClient);
    }

    /**
     * reconnect a suspended client
     * @param reconnectingClient the client that needs to be reconnected
     */
    public void reconnectClient(VirtualClientInterface reconnectingClient){
        disconnectedClients.remove(reconnectingClient);
        for (VirtualView proxy:proxyViews){
            if (reconnectingClient.getLobbyNumber()==proxy.getMatchNumber()){
                proxy.reconnect(new ReconnectClientEvent(reconnectingClient.getUsername()));
            }
        }

    }

    /**
     * send a notification to every player in the right lobby that a player has been reconnected, and send an update of every object of the match
     * @param updateReconnectedClientEvent contains the updated objects of the match
     * @param matchNumber the lobby number of the match
     */
    public void sendReconnectionUpdate(UpdateReconnectedClientEvent updateReconnectedClientEvent,int matchNumber){
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))) {

                try{
                    client.sendToClient(updateReconnectedClientEvent);
                }catch (Exception e){
                    connectionError(client,matchNumber);
                }
            }
        }
    }

    /**
     * handle a connection error from a client
     * @param disconnectedClient the client that lost connection
     * @param matchNumber the lobby number of the client
     */
    private void connectionError(VirtualClientInterface disconnectedClient, int matchNumber){
        disconnectedClients.add(disconnectedClient);
        if (checkNoPlayersLeft(matchNumber)){
            lockCommunication.put(matchNumber,true);
            cancelTimer(matchNumber);
            for (VirtualClientInterface client : clients) {
                if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))) {
                    try{
                        client.lastPlayerLeft();
                    }catch (Exception e){
                        System.out.println("non è rimasto nessun giocatore nella lobby"+ matchNumber);
                    }
                }
            }
            deleteEndedMatch(matchNumber);
        }else{
            for (VirtualClientInterface client : clients) {
                if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                    try{
                        client.otherPlayerDisconnected(new OtherPlayerDisconnectedEvent(disconnectedClient.getUsername()));
                    }catch (Exception e){
                        connectionError(client,matchNumber);
                    }
                }
            }
            for (VirtualView proxy:proxyViews){
                if (matchNumber==proxy.getMatchNumber()){
                    proxy.connectionError(new ConnectionErrorEvent(disconnectedClient.getUsername()));
                }
            }
        }



    }

    /**
     * delete the resources used for an ended match
     * @param matchNumber lobby number of the match that will be deleted
     */
    private synchronized void deleteEndedMatch(int matchNumber){
        ArrayList<VirtualClientInterface> remove=new ArrayList<>();
        VirtualView removeView=null;
        for (VirtualClientInterface client:clients){
            if (client.getLobbyNumber()==matchNumber){
                remove.add(client);
            }
        }
        for (VirtualClientInterface client:remove){
            if (clients.contains(client)){
                clients.remove(client);
            }
        }
        remove.clear();
        for (VirtualClientInterface client:disconnectedClients){
            if (client.getLobbyNumber()==matchNumber){
                try{
                    client.notifyEndMatchToDisconnected();
                }catch (Exception e){
                    //the client was disconnected by a connection error and doesn't need to be notify of the ended match
                }
                remove.add(client);
            }
        }
        for (VirtualClientInterface client:remove){
            if (disconnectedClients.contains(client)){
                disconnectedClients.remove(client);
            }
        }
        remove.clear();
        for (VirtualView view:proxyViews){
            if (view.getMatchNumber()==matchNumber){
                removeView=view;
            }
        }
        lockCommunication.remove(matchNumber);
        proxyViews.remove(removeView);
    }

}
