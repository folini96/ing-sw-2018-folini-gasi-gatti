package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.model.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIRemoteClientInterface extends Remote {
     public String askUsername() throws RemoteException;
     public void sendMessage(Message message) throws RemoteException;
     public void sendPublicObjCard (PublicObjCard publicObjCard) throws RemoteException;
     public void sendPrivateObjCard (PrivateObjCard privateObjCard) throws RemoteException;
     public void sendToolCard (ToolCard toolCard) throws RemoteException;
     public void sendWindowSide (WindowSide windowSide) throws RemoteException;
     public void sendDice (Dice dice)throws RemoteException;
     public void sendRound(Round round) throws RemoteException;
     public void removeFavorToken(int removedFavorToken) throws RemoteException;
}
