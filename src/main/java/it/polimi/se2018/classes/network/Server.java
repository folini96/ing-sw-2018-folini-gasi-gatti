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
    private static int PORT = 1099; // porta di default
    private RMIServerImplementation rmiHandler;
    private ArrayList<VirtualClientInterface> clients = new ArrayList<VirtualClientInterface>();
    public void main(){
        rmiMain();
        socketMain();
    }

    public void rmiMain() {

        try {

            LocateRegistry.createRegistry(PORT);

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
    public static void socketMain(){

    }
    public void addClient(VirtualClientInterface newClient){

        clients.add(newClient);
        System.out.println("Il client "+ newClient.getUsername() + " è connesso!");
    }
    public void placeDiceFromDraft(int draftDice, SelectedCoordinate coordinate) throws RemoteException{

    }

    public void useToolCard(int toolCard) throws RemoteException{

    }

    public void switchDraftDiceRoundTrackDice(int draftDice, SelectedRoundTrackDice roundTrackDice) throws RemoteException{

    }

    public void moveWindowDice(SelectedCoordinate currentPosition, SelectedCoordinate newPosition) throws RemoteException{

    }

    public void endTurn() throws RemoteException{

    }
    public void notValideMoveMessage(Message message, int player) throws RemoteException {
        clients.get(player).notValideMoveMessage(message);
    }
    public void sendPublicObjDeck (PublicObjCard[] publicObjDeck) throws RemoteException{
        for (VirtualClientInterface client: clients){
            client.sendPublicObjDeck(publicObjDeck);
        }
    }
    public void sendPrivateObjCard (PrivateObjCard privateObjCard, int player) throws RemoteException{
        clients.get(player).sendPrivateObjCard(privateObjCard);
    }
    public void sendToolCardDeck (ToolCard[] toolDeck) throws RemoteException{
        for (VirtualClientInterface client: clients){
            client.sendToolCardDeck(toolDeck);
        }
    }
    public void sendWindowDeck (WindowSide[] windowSideDeck, int player){
        int i;
        for (i=0;i<4;i++){
            clients.get(player).sendWindow(windowSideDeck[i]);
        }
    }
    public void sendWindowSide (WindowSide windowSide) throws RemoteException{
        for (VirtualClientInterface client: clients){
            client.sendWindow(windowSide);
        }
    }
    public void sendDraftPool (ArrayList<Dice> draftPool)throws RemoteException{
        for (VirtualClientInterface client: clients){
            client.sendDraftPool(draftPool);
        }
    }
    public void sendRoundTrack(Round[] roundTrack) throws RemoteException{
        for (VirtualClientInterface client: clients){
            client.sendRoundTrack(roundTrack);
        }
    }
    public void removeFavorToken(int removedFavorToken) throws RemoteException{
        for (VirtualClientInterface client: clients){
            client.removeFavorToken(removedFavorToken);
        }
    }

}
