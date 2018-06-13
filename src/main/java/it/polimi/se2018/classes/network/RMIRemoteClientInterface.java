package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIRemoteClientInterface extends Remote {
     public String askUsername() throws RemoteException;
     public void sendMessage(Message message) throws RemoteException;
     public void sendWindowSide (WindowSide windowSide) throws RemoteException;
     public void removeFavorToken(int removedFavorToken) throws RemoteException;
     public void sendStartMatchEvent(StartMatchEvent startMatchEvent) throws RemoteException;
     public void sendStartRoundEvent(StartRoundEvent startRoundEvent) throws RemoteException;
     public void sendStartTurnEvent(StartTurnEvent startTurnEvent) throws RemoteException;
     public void sendEndRoundEvent(EndRoundEvent endRoundEvent) throws RemoteException;
}
