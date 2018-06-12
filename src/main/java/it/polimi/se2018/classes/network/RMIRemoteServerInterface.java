package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIRemoteServerInterface extends Remote {
        void addClient (RMIRemoteClientInterface client,String firstUsername) throws RemoteException;
        public void placeDiceFromDraft(PlaceDiceEvent placeDiceEvent) throws RemoteException;
        public void choseWindow(ChoseWindowEvent choseWindowEvent) throws RemoteException;
        public void endTurn (EndTurnEvent endTurnEvent) throws RemoteException;
        public void useToolCard(UseToolCardEvent useToolCardEvent) throws RemoteException;

}
