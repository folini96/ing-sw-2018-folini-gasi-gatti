package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIVirtualClient implements VirtualClientInterface {
    private RMIRemoteClientInterface rmiClient;
    private String username;
    public RMIVirtualClient(RMIRemoteClientInterface client, String username){
        rmiClient=client;
        this.username=username;
    }
    public String getUsername(){
        return username;
    }





    public void sendWindowToChose(WindowToChoseEvent windowToChoseEvent){
        try{
            rmiClient.sendWindowToChose(windowToChoseEvent);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
    public void sendToClient(ModelViewEvent modelViewEvent){
        try{
            rmiClient.sendToClient(modelViewEvent);
        }catch (RemoteException e){
            e.printStackTrace();
        }

    }

}
