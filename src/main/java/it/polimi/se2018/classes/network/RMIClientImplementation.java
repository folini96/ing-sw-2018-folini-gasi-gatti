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

    public void sendWindowSide (WindowSide windowSide) throws RemoteException{

    }

    public void removeFavorToken(int removedFavorToken) throws RemoteException{

    }
    public void sendStartMatchEvent(StartMatchEvent startMatchEvent){
        client.sendStartMatchEvent(startMatchEvent);
    }
    public void sendStartRoundEvent(StartRoundEvent startRoundEvent){
        client.sendStartRoundEvent(startRoundEvent);
    }
    public void sendStartTurnEvent(StartTurnEvent startTurnEvent){
        client.sendStartTurnEvent(startTurnEvent);
    }
    public void sendEndRoundEvent(EndRoundEvent endRoundEvent){
        client.sendEndRoundEvent(endRoundEvent);
    }
}
