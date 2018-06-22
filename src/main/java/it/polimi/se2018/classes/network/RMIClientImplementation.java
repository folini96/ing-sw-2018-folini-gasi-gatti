package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIClientImplementation implements RMIRemoteClientInterface {
    private RMIClient client;
    public  RMIClientImplementation(RMIClient client){
        this.client=client;
    }
    public void askUsername() throws RemoteException{
        client.askUsername();
    }

    public void sendMessage(Message message) throws RemoteException {

    }

    public void setLobbyNumber(int number){
        client.setLobbyNumber(number);
    }
    public void okUsername(String username)throws RemoteException{
        client.okUsername(username);
    }

    public void sendWindowToChose (WindowToChoseEvent windowToChoseEvent)throws  RemoteException{
        client.sendWindowToChose(windowToChoseEvent);
    }
    public void sendToClient(ModelViewEvent modelViewEvent)throws  RemoteException{
        client.sendToClient(modelViewEvent);
    }
    public void endByTime()throws RemoteException{
        client.endByTime();
    }
    public void ping()throws  RemoteException{
        //EMPTY METHOD USED TO CATCH DISCONNECTION DURING THE LOBBY SETUP
    }
}
