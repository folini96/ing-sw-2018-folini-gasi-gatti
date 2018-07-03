package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIRemoteClientInterface extends Remote {
     void setLobbyNumber(int number) throws RemoteException;
     void askUsername() throws RemoteException;
     void sendToClient(ModelViewEvent modelViewEvent) throws  RemoteException;
     void sendWindowToChose (WindowToChoseEvent windowToChoseEvent) throws RemoteException;
     void okUsername(String username)throws  RemoteException;
     void endByTime(String player) throws RemoteException;
     void ping() throws RemoteException;
     void disconnectedPlayer(String player) throws RemoteException;
     void lastPlayerLeft() throws RemoteException;
     void gameEnded() throws RemoteException;
}
