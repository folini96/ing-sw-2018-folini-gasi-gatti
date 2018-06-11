package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.events.SelectedRoundTrackDice;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIRemoteServerInterface extends Remote {
        void addClient (RMIRemoteClientInterface client,String firstUsername) throws RemoteException;
        void placeDiceFromDraft (PlaceDiceEvent placeDiceEvent) throws RemoteException;
        void useToolCard (int toolCard) throws RemoteException;
        void switchDraftDiceRoundTrackDice (int draftDice, SelectedRoundTrackDice roundTrackDice) throws RemoteException;
        void endTurn() throws RemoteException;


}
