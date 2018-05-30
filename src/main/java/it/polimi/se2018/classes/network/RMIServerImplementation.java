package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.events.SelectedRoundTrackDice;
import it.polimi.se2018.classes.model.PrivateObjCard;
import it.polimi.se2018.classes.model.ToolCard;
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
    public void addClient(RMIRemoteClientInterface client, String playerName) throws RemoteException {
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



}
