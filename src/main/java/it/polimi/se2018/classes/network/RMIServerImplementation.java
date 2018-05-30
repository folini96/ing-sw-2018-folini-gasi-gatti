package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.events.SelectedRoundTrackDice;
import it.polimi.se2018.classes.model.*;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class RMIServerImplementation extends UnicastRemoteObject implements RMIRemoteServerInterface {
    private ArrayList<RMIRemoteClientInterface> rmiClients = new ArrayList<RMIRemoteClientInterface>();
    protected RMIServerImplementation() throws RemoteException{
        super(0);
    }

    @Override
    public void addClient(RMIRemoteClientInterface client) throws RemoteException {
        rmiClients.add(client);
        System.out.println("Il client "+(rmiClients.indexOf(client)+1) + " connesso!");
    }

    @Override
    public void placeDiceFromDraft(int draftDice, SelectedCoordinate coordinate) throws RemoteException{

    }

    @Override
    public void useToolCard(int toolCard) throws RemoteException{

    }

    @Override
    public void switchDraftDiceRoundTrackDice(int draftDice, SelectedRoundTrackDice roundTrackDice) throws RemoteException{

    }

    @Override
    public void moveWindowDice(SelectedCoordinate currentPosition, SelectedCoordinate newPosition) throws RemoteException{

    }

    @Override
    public void endTurn() throws RemoteException{

    }
    public void notValideMoveMessage (Message message){

    }
    public void sendPublicObjDeck(PublicObjCard[] publicObjDeck){
        for (RMIRemoteClientInterface client:rmiClients){
            try{
                client.sendPublicObjDeck(publicObjDeck);
            }catch(RemoteException e){
                System.out.println("Errore di comunicazione con il client RMI "+ rmiClients.indexOf(client)+1);
            }

        }
    }
    public void sendPrivateObjCard(PrivateObjCard privateObjCard){

    }
    public void sendToolCardDeck(ToolCard[] toolDeck){
        for (RMIRemoteClientInterface client:rmiClients){
            try{
                client.sendToolCardDeck(toolDeck);
            }catch(RemoteException e){
                System.out.println("Errore di comunicazione con il client RMI "+ rmiClients.indexOf(client)+1);
            }

        }
    }
    public void sendWindow(WindowSide window){
        for (RMIRemoteClientInterface client:rmiClients){
            try{
                client.sendWindowSide(window);
            }catch(RemoteException e){
                System.out.println("Errore di comunicazione con il client RMI "+ rmiClients.indexOf(client)+1);
            }

        }
    }
    public void sendDraftPool(ArrayList<Dice> draftPool){
        for (RMIRemoteClientInterface client:rmiClients){
            try{
                client.sendDraftPool(draftPool);
            }catch(RemoteException e){
                System.out.println("Errore di comunicazione con il client RMI "+ rmiClients.indexOf(client)+1);
            }

        }
    }
    public void sendRoundTrack(Round[] roundTrack){
        for (RMIRemoteClientInterface client:rmiClients){
            try{
                client.sendRoundTrack(roundTrack);
            }catch(RemoteException e){
                System.out.println("Errore di comunicazione con il client RMI "+ rmiClients.indexOf(client)+1);
            }

        }
    }
    public void removeFavorToken(int removedToken){
        for (RMIRemoteClientInterface client:rmiClients){
            try{
                client.removeFavorToken(removedToken);
            }catch(RemoteException e){
                System.out.println("Errore di comunicazione con il client RMI "+ rmiClients.indexOf(client)+1);
            }

        }
    }


}
