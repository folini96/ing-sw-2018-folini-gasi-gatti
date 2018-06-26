package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.view.VirtualView;
import sun.reflect.annotation.ExceptionProxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;

public class Server {
    private static int RMIPORT = 1099; // porta di default RMI
    private static int SOCKETPORT = 5463; //porta di default SOCKET
    private Timer lobbyTimer = new Timer();
    HashMap<Integer,Timer> playTimer = new HashMap<>();
    HashMap<Integer,Timer> windowTimer = new HashMap<>();
    private static int LOBBYTIME=5;
    private int passedTime;
    private int lobbyNumber=0;
    private RMIServerImplementation rmiHandler;
    private ArrayList<VirtualView> proxyViews;
    private ArrayList<MatchHandlerController> controllers;
    private ArrayList<VirtualClientInterface> removedClients = new ArrayList<>();
    private ArrayList<VirtualClientInterface> clients = new ArrayList<VirtualClientInterface>();
    private ArrayList<VirtualClientInterface> disconnectedClients = new ArrayList<>();
    public void main() {
        proxyViews=new ArrayList<>();
        controllers =new ArrayList<>();
        rmiMain();
        socketMain();

    }

    public void rmiMain() {

        try {

            LocateRegistry.createRegistry(RMIPORT);

        } catch (RemoteException e) {

            System.out.println("Registry già presente!");
        }

        try {

            rmiHandler = new RMIServerImplementation(this);
            Naming.rebind("//localhost/MyServer", rmiHandler);

        } catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

    }

    public void socketMain() {
        (new SocketClientGatherer(this, SOCKETPORT)).start();

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

    public synchronized void disconnectPlayer(VirtualClientInterface player){

    }
    public void startLobby() {
        passedTime=0;
        TimerTask startMatchTask = new TimerTask() {
            public void run() {
                passedTime++;
                if (passedTime==LOBBYTIME){
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
        proxyViews.add(new VirtualView(this,lobbyNumber));
        controllers.add(new MatchHandlerController(proxyViews.get(lobbyNumber)));
        proxyViews.get(lobbyNumber).addObserver(controllers.get(lobbyNumber));
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber() == lobbyNumber){
                usernames.add(client.getUsername());
            }
        }

        controllers.get(lobbyNumber).handleStartMatch(usernames);

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



    public synchronized void sendToServer(ViewControllerEvent viewControllerEvent,int clientLobbyNumber){
       proxyViews.get(clientLobbyNumber).sendToServer(viewControllerEvent);
    }

    public void notValideMoveMessage(Message message, int matchNumber) {

        for (VirtualClientInterface client : clients) {
            if ((client.getUsername().equals(message.getPlayer()))&&(client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))) {

                try{
                    client.sendToClient(message);
                }catch (Exception e){
                    disconnectedClients.add(client);
                }
            }
        }
    }

    public void sendStartMatchEvent(StartMatchEvent startMatchEvent, int matchNumber) {
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber()==matchNumber && (!disconnectedClients.contains(client))){
                try{

                    client.sendToClient(startMatchEvent);
                }catch (Exception e){
                   disconnectedClients.add(client);
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
                    disconnectedClients.add(client);
                }
            }

        }

    }

    public void sendStartTurnEvent(StartTurnEvent startTurnEvent, int matchNumber) {
        for (VirtualClientInterface client : clients) {

            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(startTurnEvent);
                    }catch (Exception e){
                        disconnectedClients.add(client);
                    }
            }

        }
        class PlayTask extends TimerTask{
            int lobbyNumber;
            public PlayTask(int lobbyNumber){
                this.lobbyNumber=lobbyNumber;
            };
            public void run() {
                endByTime(lobbyNumber);
                playTimer.remove(lobbyNumber);
                sendToServer(new EndTurnEvent(),lobbyNumber);
            }
        }
        playTimer.put(matchNumber,new Timer());
        playTimer.get(matchNumber).schedule(new PlayTask(matchNumber),200000);

    }

    public void sendEndRoundEvent(EndRoundEvent endRoundEvent, int matchNumber) {
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(endRoundEvent);
                }catch (Exception e){
                    disconnectedClients.add(client);
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
                client.sendWindowToChose(new WindowToChoseEvent(windowToSend));
            }
        }
        //startWindowTimer(matchNumber);
    }
    public void startWindowTimer(int lobbyNumber){
        TimerTask windowTask=new TimerTask(){
            public void run() {
                for (VirtualClientInterface client:clients){
                    //client.ping()
                }
            }
        };
        windowTimer.put(lobbyNumber,new Timer());
        windowTimer.get(lobbyNumber).schedule(windowTask,1000,1000);

    }
    public void sendModifiedWindow(ModifiedWindowEvent modifiedWindowEvent, int matchNumber){
        for (VirtualClientInterface client : clients) {
            if ((client.getLobbyNumber()==matchNumber)&& (!disconnectedClients.contains(client))){
                try{
                    client.sendToClient(modifiedWindowEvent);
                }catch (Exception e){
                    disconnectedClients.add(client);
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
                    disconnectedClients.add(client);
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
                    disconnectedClients.add(client);
                }
            }
        }
    }
    public void endByTime(int lobbyNumber){
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber()==lobbyNumber){
                try{
                    client.endByTime();
                    disconnectedClients.add(client);
                }catch (Exception e){
                    disconnectedClients.add(client);
                }
            }
        }
    }
    public void cancelTimer(int matcNumber){
        if (playTimer.containsKey(matcNumber)){
            playTimer.get(matcNumber).cancel();
            playTimer.remove(matcNumber);
        }
    }
    public void reconnectRMIClient(String username, int lobbyNumber){
        
    }
    public void reconnectClient(VirtualClientInterface reconnectingClient){

    }
    /*public void removeFavorToken(int removedFavorToken) {
        for (VirtualClientInterface client : clients) {
                    }
    }*/
}
