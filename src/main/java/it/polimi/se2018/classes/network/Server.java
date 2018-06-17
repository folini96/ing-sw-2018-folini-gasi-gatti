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
    public static int LOBBYTIME=10;
    public int passedTime;
    private RMIServerImplementation rmiHandler;
    private VirtualView proxyView;
    private MatchHandlerController controller;
    private ArrayList<VirtualClientInterface> clients = new ArrayList<VirtualClientInterface>();
    private ArrayList<VirtualClientInterface> disconnectedClients = new ArrayList<>();
    public void main() {
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

    private TimerTask startMatchTask = new TimerTask() {
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

    public synchronized void addClient(VirtualClientInterface newClient) {
        if (clients.size() > 3) {
            //inizia partita e blocca l'accesso a nuovi giocatori
            System.out.println("server pieno");
        } else {
            clients.add(newClient);
            System.out.println("Il client " + newClient.getUsername() + " è connesso!");
            if (clients.size() == 2) {
                startLobby();
            }
            if (clients.size() == 4) {
                lobbyTimer.cancel();
                startMatch();

            }
            //notifica i client che una nuova partita inizierà tra poco

        }
    }
    public synchronized void removePlayer (VirtualClientInterface player){

    }
    public void startLobby() {
        lobbyTimer.schedule(startMatchTask,1000, 1000);

    }

    public void startMatch() {
        ArrayList<String> usernames = new ArrayList<>();
        proxyView = new VirtualView(this);
        controller = new MatchHandlerController(proxyView);
        proxyView.addObserver(controller);
        for (VirtualClientInterface client : clients) {
            usernames.add (client.getUsername());
        }

        controller.handleStartMatch(usernames);
    }

    public Boolean checkUsername(String username) {
        for (VirtualClientInterface client : clients) {

            if (client.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }



    public void sendToServer(ViewControllerEvent viewControllerEvent){
        //playTimer.cancel();
        proxyView.sendToServer(viewControllerEvent);
    }

    public void notValideMoveMessage(Message message, int player) {

    }

    public void sendStartMatchEvent(StartMatchEvent startMatchEvent) {
        for (VirtualClientInterface client : clients) {
            client.sendToClient(startMatchEvent);
        }
    }

    public void sendStartRoundEvent(StartRoundEvent startRoundEvent) {
        for (VirtualClientInterface client : clients) {
            client.sendToClient(startRoundEvent);
        }

    }

    public void sendStartTurnEvent(StartTurnEvent startTurnEvent) {
        for (VirtualClientInterface client : clients) {
            if (client.getUsername().equals(startTurnEvent.getPlayer())) {
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

    public void sendEndRoundEvent(EndRoundEvent endRoundEvent) {
        for (VirtualClientInterface client : clients) {
            client.sendToClient(endRoundEvent);
        }
    }

    public void sendWindowToChose(WindowSide[] windows) {
        for (VirtualClientInterface client : clients) {
            int windowPosition=clients.indexOf(client)*4;
            WindowSide[] windowToSend=Arrays.copyOfRange(windows,windowPosition,windowPosition+4);
            client.sendWindowToChose(new WindowToChoseEvent(windowToSend));
        }
    }


    /*public void removeFavorToken(int removedFavorToken) {
        for (VirtualClientInterface client : clients) {
                    }
    }*/
}
