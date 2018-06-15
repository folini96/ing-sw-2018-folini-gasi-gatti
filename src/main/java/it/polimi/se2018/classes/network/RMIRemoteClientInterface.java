package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIRemoteClientInterface extends Remote {
     String askUsername() throws RemoteException;
     void sendToClient(ModelViewEvent modelViewEvent) throws  RemoteException;
     void sendWindowToChose (WindowToChoseEvent windowToChoseEvent) throws RemoteException;

}
