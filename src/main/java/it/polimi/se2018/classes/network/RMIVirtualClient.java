package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIVirtualClient implements VirtualClientInterface {
    private RMIRemoteClientInterface rmiClient;
    private String username;
    private int lobbyNumber;
    public RMIVirtualClient(RMIRemoteClientInterface client, String username){
        rmiClient=client;
        this.username=username;
    }
    public String getUsername(){
        return username;
    }



    public void setLobbyNumber(int number){
        this.lobbyNumber=number;
        try{
            rmiClient.setLobbyNumber(number);
        }catch(RemoteException e){
            e.printStackTrace();
        }

    }
    public int getLobbyNumber(){
        return lobbyNumber;
    }
    public void sendWindowToChose(WindowToChoseEvent windowToChoseEvent){
        try{
            rmiClient.sendWindowToChose(windowToChoseEvent);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
    public void sendToClient(ModelViewEvent modelViewEvent) throws RemoteException{
       rmiClient.sendToClient(modelViewEvent);

    }
    public void endByTime() throws RemoteException{
        rmiClient.endByTime();

    }
    public void ping() throws RemoteException{
        rmiClient.ping();
    }
}
