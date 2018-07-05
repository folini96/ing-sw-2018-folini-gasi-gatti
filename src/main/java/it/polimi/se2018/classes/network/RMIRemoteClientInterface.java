package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.events.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Andrea Folini
 */
public interface RMIRemoteClientInterface extends Remote {
    /**
     * @param number the client lobby number
     * @throws RemoteException caused by an rmi connection error
     */
     void setLobbyNumber(int number) throws RemoteException;

    /**
     * notify that the chosen username has been rejected
     * @throws RemoteException caused by an rmi connection error
     */
     void askUsername() throws RemoteException;

    /**
     * send the event from the network to the intermediary
     * @param modelViewEvent the event got from the remote call
     * @throws RemoteException caused by an rmi connection error
     */
     void sendToClient(ModelViewEvent modelViewEvent) throws  RemoteException;

    /**
     * send the windows for the player
     * @param windowToChoseEvent the windows of the player
     * @throws RemoteException caused by an rmi connection error
     */
     void sendWindowToChose (WindowToChoseEvent windowToChoseEvent) throws RemoteException;

    /**
     * notify that the chosen username has been accepted
     * @param username the accepted username
     * @throws RemoteException caused by an rmi connection error
     */
     void okUsername(String username)throws  RemoteException;

    /**
     * notify that the time for the turn has expired
     * @param player the player that will be suspended
     * @throws RemoteException caused by an rmi connection error
     */
     void endByTime(String player) throws RemoteException;

    /**
     * empty method used to catch a connection error
     * @throws RemoteException caused by an rmi connection error
     */
     void ping() throws RemoteException;

    /**
     * notify that another player has been disconnected
     * @param player the disconnected player
     * @throws RemoteException caused by an rmi connection error
     */
     void disconnectedPlayer(String player) throws RemoteException;

    /**
     * notify that there are no player left in the lobby
     * @throws RemoteException caused by an rmi connection error
     */
     void lastPlayerLeft() throws RemoteException;

    /**
     * notify that a game ended, in the case that the client is still suspended
     * @throws RemoteException caused by an rmi
     */
     void gameEnded() throws RemoteException;
}
