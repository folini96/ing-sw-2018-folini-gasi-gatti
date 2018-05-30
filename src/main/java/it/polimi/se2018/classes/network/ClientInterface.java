package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.Events.Message;
import it.polimi.se2018.classes.Events.SelectedCoordinate;
import it.polimi.se2018.classes.Events.SelectedRoundTrackDice;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public interface ClientInterface {
     void main();
     void placeDiceFromDraft(int draftDice, SelectedCoordinate coordinate);
     void useToolCard (int toolCard) throws RemoteException;
     void switchDraftDiceRoundTrackDice (int draftDice, SelectedRoundTrackDice roundTrackDice) throws RemoteException;
     void moveWindowDice (SelectedCoordinate currentPosition, SelectedCoordinate newPosition) throws RemoteException;
     void endTurn() throws RemoteException;
}
