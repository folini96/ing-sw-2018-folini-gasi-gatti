package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import java.rmi.RemoteException;

/**
 * get the remote method calls from the rmi server
 */
public class RMIClientImplementation implements RMIRemoteClientInterface {
    private RMIClient client;

    /**
     * constructor
     * @param client the reference of the intermediary with the interface handler
     */
    public  RMIClientImplementation(RMIClient client){
        this.client=client;
    }
    public void askUsername() throws RemoteException{
        client.askUsername();
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
    public void endByTime(String player)throws RemoteException{
        client.endByTime(player);
    }
    public void ping()throws  RemoteException{
        //EMPTY METHOD USED TO CATCH DISCONNECTION DURING THE LOBBY SETUP
    }
    public void disconnectedPlayer(String player) throws RemoteException{
        client.playerDisconnected(player);
    }
    public void lastPlayerLeft(){
        client.lastPlayerLeft();
    }
    public void gameEnded(){
        client.gameEnded();
    }
}
