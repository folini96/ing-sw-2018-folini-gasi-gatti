package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.events.SelectedRoundTrackDice;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public interface ClientInterface {
     void main(String username);
     void placeDiceFromDraft(PlaceDiceEvent placeDiceEvent);
     void useToolCard (int toolCard) throws RemoteException;
     void switchDraftDiceRoundTrackDice (int draftDice, SelectedRoundTrackDice roundTrackDice) throws RemoteException;
     //void moveWindowDice (SelectedCoordinate currentPosition, SelectedCoordinate newPosition) throws RemoteException;
     void endTurn() throws RemoteException;
}
