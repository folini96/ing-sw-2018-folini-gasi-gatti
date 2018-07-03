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
    private Server server;
    public RMIVirtualClient(RMIRemoteClientInterface client, String username, Server server){
        rmiClient=client;
        this.username=username;
        this.server=server;
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
    public void endByTime(String player) throws RemoteException{
        rmiClient.endByTime(player);

    }
    public void ping() throws RemoteException{
        rmiClient.ping();
    }
    public void otherPlayerDisconnected(OtherPlayerDisconnectedEvent otherPlayerDisconnectedEvent) throws RemoteException{
        rmiClient.disconnectedPlayer(otherPlayerDisconnectedEvent.getPlayer());
    }
    public void deleteAfterMatch(){
        //void method that needed to be implemented for interface
    }
    public void lastPlayerLeft()throws RemoteException{
        rmiClient.lastPlayerLeft();

    }
    public void notifyEndMatchToDisconnected()throws RemoteException{
        rmiClient.gameEnded();
    }
}
