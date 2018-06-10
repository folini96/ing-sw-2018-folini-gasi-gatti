package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.events.SelectedRoundTrackDice;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.view.VirtualView;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Server  {
    private static int RMIPORT = 1099; // porta di default RMI
    private static int SOCKETPORT = 9000; //porta di default SOCKET
    private Timer lobbyTimer=new Timer();
    private RMIServerImplementation rmiHandler;
    private VirtualView proxyView;
    private MatchHandlerController controller;
    private ArrayList<VirtualClientInterface> clients = new ArrayList<VirtualClientInterface>();
    public void main(){
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

        }catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        }
         catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

    }
    public void socketMain(){
        (new SocketClientGatherer(this, SOCKETPORT)).start();

    }
    TimerTask startMatchTask = new TimerTask(){
        public void run()
        {
            startMatch();
        }
    };
    public synchronized void addClient(VirtualClientInterface newClient){
        if (clients.size()>3){
            //inizia partita e blocca l'accesso a nuovi giocatori
            System.out.println("server pieno");
        }else{
            clients.add(newClient);
            System.out.println("Il client "+ newClient.getUsername() + " è connesso!");
            if (clients.size()==2){
                startLobby();
            }
            if (clients.size()==4){
                lobbyTimer.cancel();
                startMatch();

            }
            //notifica i client che una nuova partita inizierà tra poco

        }

    }
    public void startLobby(){
        lobbyTimer.schedule(startMatchTask, 60000);
    }
    public void startMatch(){
        String[] usernames=new String[4];
        proxyView=new VirtualView();
        controller=new MatchHandlerController(proxyView);
        for (VirtualClientInterface client:clients){
            usernames[clients.indexOf(client)]=client.getUsername();
        }
        controller.handleStartMatch(usernames);
    }
    public Boolean checkUsername(String username){
        for(VirtualClientInterface client:clients){

            if (client.getUsername().equals(username)){
                return false;
            }
        }
        return  true;
    }
    public void placeDiceFromDraft(int draftDice, SelectedCoordinate coordinate){

    }

    public void useToolCard(int toolCard){

    }

    public void switchDraftDiceRoundTrackDice(int draftDice, SelectedRoundTrackDice roundTrackDice){

    }

    public void moveWindowDice(SelectedCoordinate currentPosition, SelectedCoordinate newPosition){

    }

    public void endTurn() throws RemoteException{

    }
    public void notValideMoveMessage(Message message, int player) {
        clients.get(player).notValideMoveMessage(message);
    }
    public void sendPublicObjCard (PublicObjCard publicObjCard) {
        for (VirtualClientInterface client: clients){
            client.sendPublicObjCard(publicObjCard);
        }
    }
    public void sendPrivateObjCard (PrivateObjCard privateObjCard, String player) {

        for (VirtualClientInterface client:clients){
            if (client.getUsername()==player){
                client.sendPrivateObjCard(privateObjCard);
            }
        }

    }
    public void sendToolCard (ToolCard toolCard) {
        for (VirtualClientInterface client: clients){
            client.sendToolCard(toolCard);
        }
    }
    public void sendWindowToChose (WindowSide[] windows){
        for (VirtualClientInterface client:clients){

            client.sendWindow(windows[clients.indexOf(client)]);
            //DA MODIFICARE (DIVIDERE I METODI PER L'INVIO DI WINDOW DA SCEGLIERE E DA MOSTRARE)

        }
    }
    public void sendWindowSide (WindowSide windowSide) {
        for (VirtualClientInterface client: clients){
            client.sendWindow(windowSide);
        }
    }
    public void sendDice (Dice dice){
        for (VirtualClientInterface client: clients){
            client.sendDice(dice);
        }
    }
    public void sendRound(Round round){
        for (VirtualClientInterface client: clients){
            client.sendRound(round);
        }
    }
    public void removeFavorToken(int removedFavorToken){
        for (VirtualClientInterface client: clients){
            client.removeFavorToken(removedFavorToken);
        }
    }

}
