package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.events.SelectedRoundTrackDice;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient implements ClientInterface{
    private static final int PORT = 9000;
    private static final String HOST = "localhost";
    public void main(String username) {

    }

    @Override
    public void placeDiceFromDraft(int draftDice, SelectedCoordinate coordinate) {

    }
    public void useToolCard (int toolCard) throws RemoteException{

    }
    public void switchDraftDiceRoundTrackDice (int draftDice, SelectedRoundTrackDice roundTrackDice) throws RemoteException{

    }
    public void moveWindowDice (SelectedCoordinate currentPosition, SelectedCoordinate newPosition) throws RemoteException{

    }
    public void endTurn() throws RemoteException{

    }
}
