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
    public String askUsername() throws RemoteException{
        return client.askUsername();
    }

    public void sendMessage(Message message) throws RemoteException {

    }


    public void sendWindowToChose (WindowToChoseEvent windowToChoseEvent)throws  RemoteException{
        client.sendWindowToChose(windowToChoseEvent);
    }
    public void sendToClient(ModelViewEvent modelViewEvent)throws  RemoteException{
        client.sendToClient(modelViewEvent);
    }
}
