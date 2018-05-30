package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.events.SelectedRoundTrackDice;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIRemoteServerInterface extends Remote {
        void addClient (RMIRemoteClientInterface client, String username) throws RemoteException;
        void placeDiceFromDraft (int draftDice, SelectedCoordinate coordinate) throws RemoteException;
        void useToolCard (int toolCard) throws RemoteException;
        void switchDraftDiceRoundTrackDice (int draftDice, SelectedRoundTrackDice roundTrackDice) throws RemoteException;
        void moveWindowDice (SelectedCoordinate currentPosition, SelectedCoordinate newPosition) throws RemoteException;
        void endTurn() throws RemoteException;


}
