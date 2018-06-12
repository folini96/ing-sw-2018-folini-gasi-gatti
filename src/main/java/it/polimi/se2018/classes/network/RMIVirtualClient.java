package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

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
    public void notValideMoveMessage (Message message){
        try{
            rmiClient.sendMessage(message);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
    public void sendStartMatchEvent (StartMatchEvent startMatchEvent){

    }
    public void sendStartRoundEvent(StartRoundEvent startRoundEvent){

    }
    public void sendStartTurnEvent (StartTurnEvent startTurnEvent){

    }
    public void sendEndRoundEvent (EndRoundEvent endRoundEvent){

    }
    public void sendWindow(WindowSide window){
        try{
            rmiClient.sendWindowSide(window);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }

    public void removeFavorToken(int removedToken){
        try{
            rmiClient.removeFavorToken(removedToken);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
}
