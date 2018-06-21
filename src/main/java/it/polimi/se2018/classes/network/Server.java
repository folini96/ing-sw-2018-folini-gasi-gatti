package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.view.VirtualView;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    private static int RMIPORT = 1099; // porta di default RMI
    private static int SOCKETPORT = 9000; //porta di default SOCKET
    private Timer lobbyTimer = new Timer();
    private Timer playTimer = new Timer();
    private static int LOBBYTIME=10;
    private int passedTime;
    private int lobbyNumber=0;
    private RMIServerImplementation rmiHandler;
    private ArrayList<VirtualView> proxyViews;
    private ArrayList<MatchHandlerController> controllers;
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
            //notifica i client che una nuova partita inizierà tra poco

    }

    public synchronized void removePlayer (VirtualClientInterface player){

    }
    public void startLobby() {
        passedTime=0;
        TimerTask startMatchTask = new TimerTask() {
            public void run() {
                passedTime++;
                if (passedTime==LOBBYTIME){
                    lobbyTimer.cancel();
                    startMatch();
                }
                for (VirtualClientInterface client:clients){
                    //client.ping()
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

            if (client.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }



    public synchronized void sendToServer(ViewControllerEvent viewControllerEvent,int clientLobbyNumber){
        //playTimer.cancel();
        proxyViews.get(clientLobbyNumber).sendToServer(viewControllerEvent);
    }

    public void notValideMoveMessage(Message message, int matchNumber) {

        for (VirtualClientInterface client : clients) {
            if ((client.getUsername().equals(message.getPlayer()))&&(client.getLobbyNumber()==matchNumber)) {
                client.sendToClient(message);
            }
        }
    }

    public void sendStartMatchEvent(StartMatchEvent startMatchEvent, int matchNumber) {
        System.out.println(matchNumber);
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber()==matchNumber){
                client.sendToClient(startMatchEvent);
            }

        }
    }

    public void sendStartRoundEvent(StartRoundEvent startRoundEvent, int matchNumber) {
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber()==matchNumber){
                client.sendToClient(startRoundEvent);
            }
        }

    }

    public void sendStartTurnEvent(StartTurnEvent startTurnEvent, int matchNumber) {
        for (VirtualClientInterface client : clients) {

            if (client.getLobbyNumber()==matchNumber){
                client.sendToClient(startTurnEvent);
            }

        }

        /*TimerTask playTask = new TimerTask() {
            public void run() {
                sendToServer(new EndTurnEvent());
            }
        };
        //playTimer=new Timer();
        //playTimer.schedule(playTask,30000);*/

    }

    public void sendEndRoundEvent(EndRoundEvent endRoundEvent, int matchNumber) {
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber()==matchNumber){
                client.sendToClient(endRoundEvent);
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
    }
    public void sendModifiedWindow(ModifiedWindowEvent modifiedWindowEvent, int matchNumber){
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber()==matchNumber){
                client.sendToClient(modifiedWindowEvent);
            }
        }
    }
    public void sendModifiedDraft(ModifiedDraftEvent modifiedDraftEvent, int matchNumber){
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber()==matchNumber){
                client.sendToClient(modifiedDraftEvent);
            }
        }
    }
    public void sendEndMatchEvent(EndMatchEvent endMatchEvent, int matchNumber){
        for (VirtualClientInterface client : clients) {
            if (client.getLobbyNumber()==matchNumber){
                client.sendToClient(endMatchEvent);
            }
        }
    }
    /*public void removeFavorToken(int removedFavorToken) {
        for (VirtualClientInterface client : clients) {
                    }
    }*/
}
