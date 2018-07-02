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
    public void main(int rmiPort,int socketPort,int lobbyTime,int playTime) {
        proxyViews=new CopyOnWriteArrayList<>();
        this.rmiPort=rmiPort;
        this.socketPort=socketPort;
        this.lobbyTime=lobbyTime;
        this.playTime=playTime*1000;
        rmiMain();
        socketMain();

    }

    public void rmiMain() {

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

    public void socketMain() {
        (new SocketClientGatherer(this, socketPort)).start();

    }



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


    public void startLobby() {
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

    public void startMatch() {
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

    public Boolean checkUsername(String username) {
        for (VirtualClientInterface client : clients) {

            if ((lobbyNumber==client.getLobbyNumber())&&(client.getUsername().equals(username))) {
                return false;
            }
        }
        return true;
    }



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

    public synchronized void sendStartMatchEvent(StartMatchEvent startMatchEvent, int matchNumber) {
        if (checkNoPlayersLeft(matchNumber)){
            lockCommunication.put(matchNumber,true);
            cancelTimer(matchNumber);
            for (VirtualClientInterface client : clients) {
                if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))) {
                    try{
                        client.lastPlayerLeft();
                    }catch (Exception e){
                        System.out.println("No players left in lobby matchNumber");
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
                        System.out.println("No players left in lobby matchNumber");
                    }
                }
            }
            deleteEndedMatch(lobbyNumber);
        }
    }
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
    public void cancelTimer(int matcNumber){
        if (playTimer.containsKey(matcNumber)){
            playTimer.get(matcNumber).cancel();
            playTimer.remove(matcNumber);
        }
    }
    public void reconnectRMIClient(String username, int lobbyNumber){
        VirtualClientInterface reconnectingClient=null;
        for (VirtualClientInterface disconnectedClient:disconnectedClients){
            if ((disconnectedClient.getUsername().equals(username))&&disconnectedClient.getLobbyNumber()==lobbyNumber){
                reconnectingClient=disconnectedClient;
            }
        }
        reconnectClient(reconnectingClient);
    }
    public void reconnectClient(VirtualClientInterface reconnectingClient){
        disconnectedClients.remove(reconnectingClient);
        for (VirtualView proxy:proxyViews){
            if (reconnectingClient.getLobbyNumber()==proxy.getMatchNumber()){
                proxy.reconnect(new ReconnectClientEvent(reconnectingClient.getUsername()));
            }
        }

    }
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
                        System.out.println("No players left in lobby matchNumber");
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
