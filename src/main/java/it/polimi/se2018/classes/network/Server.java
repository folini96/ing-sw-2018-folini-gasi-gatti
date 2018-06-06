package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.events.SelectedRoundTrackDice;
import it.polimi.se2018.classes.model.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server  {
    private static int RMIPORT = 1099; // porta di default RMI
    private static int SOCKETPORT = 9000; //porta di default SOCKET
    private RMIServerImplementation rmiHandler;
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
    public void addClient(VirtualClientInterface newClient){

        clients.add(newClient);
        System.out.println("Il client "+ newClient.getUsername() + " è connesso!");
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
    public void sendWindowToChose (WindowSide window, String player){
        for (VirtualClientInterface client:clients){
            if (client.getUsername()==player){
                client.sendWindow(window);
            }
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
