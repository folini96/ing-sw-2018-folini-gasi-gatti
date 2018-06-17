package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIRemoteServerInterface extends Remote {
        void addClient (RMIRemoteClientInterface client,String firstUsername) throws RemoteException;
        void sendToServer(ViewControllerEvent viewControllerEvent)throws RemoteException;
        void ping()throws  RemoteException;
}
