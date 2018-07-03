package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import java.rmi.RemoteException;

/**
 * @author Andrea Folini
 * calls the remote methods to send the events to the rmi client
 */
public class RMIVirtualClient implements VirtualClientInterface {
    private RMIRemoteClientInterface rmiClient;
    private String username;
    private int lobbyNumber;
    private Server server;

    /**
     * constructor
     * @param client the remote reference to the rmi client
     * @param username name of the client
     * @param server the reference to the server
     */
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

    public void lastPlayerLeft()throws RemoteException{
        rmiClient.lastPlayerLeft();

    }
    public void notifyEndMatchToDisconnected()throws RemoteException{
        rmiClient.gameEnded();
    }
}
