package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Andrea Folini
 */
public interface RMIRemoteServerInterface extends Remote {
     /**
     * take the request to add a client to the main server and check that the username is accepted
     * @param client the remote reference of the client that wants to join the server
     * @param firstUsername the name that the new client wants to use
     * @throws RemoteException caused by an rmi connection error
     */
     void addClient (RMIRemoteClientInterface client,String firstUsername) throws RemoteException;

     /**
     * send the event from the client to the main server
     * @param viewControllerEvent the event that will be send
     * @param lobbyNumber the lobby number of the client that sended the event
     * @throws RemoteException caused by an rmi connection error
     */
     void sendToServer(ViewControllerEvent viewControllerEvent, int lobbyNumber)throws RemoteException;

     /**
     * empty method used to catch connection error
     * @throws RemoteException caused by an rmi connection error
     */
     void ping()throws  RemoteException;

    /**
     * send to main server the reconnection request from the client
     * @param username name of the clietn
     * @param lobbyNumber lobby number of the client
     * @throws RemoteException caused by an rmi connection error
     */
     void reconnect(String username,int lobbyNumber) throws RemoteException;
}
