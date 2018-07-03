package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.view.GUIHandler;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Andrea Folini
 * intermediary between the client implementation and the interface handler
 */
public class RMIClient implements ClientInterface,ModelViewEventVisitor {
    private GUIHandler interfaceHandler;
    private RMIRemoteClientInterface remoteRef;
    private RMIClientImplementation client;
    private RMIRemoteServerInterface server;
    private int lobbyNumber;
    private Timer disconnectionTimer=new Timer();
    public void main(String username, GUIHandler interfaceHandler,String serverIp,int serverPort){
      this.interfaceHandler=interfaceHandler;
      try {
         server = (RMIRemoteServerInterface)Naming.lookup("//" + serverIp+"/MyServer");
         client= new RMIClientImplementation(this);
         remoteRef = (RMIRemoteClientInterface) UnicastRemoteObject.exportObject(client, 0);
         server.addClient(remoteRef, username);
         startDisconnectionTimer();
      }catch (MalformedURLException|RemoteException|NotBoundException e) {
         interfaceHandler.createClientError();
      }
   }

    /**
     * start a timer that will ping the server every second to catch a connection error
     */
    public void startDisconnectionTimer(){
        TimerTask disconnectionTask=new TimerTask() {
            @Override
            public void run() {
               try{
                   server.ping();

               }catch (RemoteException e){
                   disconnectionTimer.cancel();
                   interfaceHandler.connectionToServerLost();
               }
            }
        };
        disconnectionTimer.schedule(disconnectionTask,1000,1000);
    }

    /**
     * @param number set the lobby number of this client
     */
    public void setLobbyNumber(int number){
        lobbyNumber=number;
    }

   public void sendToServer (ViewControllerEvent viewControllerEvent){
       try{
           server.sendToServer(viewControllerEvent,lobbyNumber);
       }catch (RemoteException e){
           interfaceHandler.connectionToServerLost();
       }
   }

    /**
     * notify to the interface handler that the chosen username has been accepted
     * @param username the chosen username
     */
    public void okUsername(String username){
       interfaceHandler.okUsername(username);
    }

    /**
     * notify to the interface handler that the chosen username has been rejected and ask for a new one
     */
    public void askUsername(){
       interfaceHandler.askUsername();
    }

    /**
     * send the windows to the player
     * @param windowToChoseEvent contains the windows for the player
     */
    public void sendWindowToChose(WindowToChoseEvent windowToChoseEvent){
        interfaceHandler.windowToChose(windowToChoseEvent);
    }

    /**
     * chose the right method  with the pattern visitor to notify the interface handler with the right event
     * @param modelViewEvent the event from the network
     */
    public void sendToClient(ModelViewEvent modelViewEvent){
       modelViewEvent.accept(this);
    }
    public void visit (Message message){
       interfaceHandler.showMessage(message);
    }
    public void visit(StartMatchEvent startMatchEvent){
        interfaceHandler.startMatch(startMatchEvent);
    }
    public void visit (StartRoundEvent startRoundEvent){
        interfaceHandler.startRound(startRoundEvent);

    }
    public void visit (StartTurnEvent startTurnEvent){
       interfaceHandler.startTurn(startTurnEvent);

    }
    public void visit (EndRoundEvent endRoundEvent){
        interfaceHandler.endRound(endRoundEvent);
    }
    public void visit (ModifiedWindowEvent modifiedWindowEvent){
        interfaceHandler.modifiedWindow(modifiedWindowEvent);
    }
    public void visit (ModifiedDraftEvent modifiedDraftEvent){
       interfaceHandler.modifiedDraft(modifiedDraftEvent);
    }
    public void visit (EndMatchEvent endMatchEvent){
       interfaceHandler.endMatch(endMatchEvent);
    }
    public void visit (SendEffectEvent effectEvent){
        interfaceHandler.sendEffect(effectEvent);
    }
    public void visit (ModifiedRoundTrack modifiedRoundTrack){
        interfaceHandler.modifiedRoundTrack(modifiedRoundTrack);
    }
    public void visit (NewDiceFromBagEvent newDiceFromBagEvent){
        interfaceHandler.askNewDiceValue(newDiceFromBagEvent);
    }
    public void visit (UpdateReconnectedClientEvent updateReconnectedClientEvent){
        interfaceHandler.updateReconnectedPlayer(updateReconnectedClientEvent);
    }
    public void visit (ModifiedTokenEvent modifiedTokenEvent){
        interfaceHandler.modifiedToken(modifiedTokenEvent);
    }

    /**
     * notify the interface handler that a turn ended because the time expired and a client will be suspended
     * @param player the player that will be suspended
     */
    public void endByTime(String player){
        interfaceHandler.endByTime(player);
    }
    public void reconnect(String username){
        try{
            server.reconnect(username,lobbyNumber);
        }catch (RemoteException e){
            interfaceHandler.connectionToServerLost();
        }

    }

    /**
     * notify the interface handler that another player got disconnected
     * @param player the disconnected player
     */
    public void playerDisconnected(String player){
        interfaceHandler.disconnectedPlayer(player);
    }

    /**
     * notify the interface handler that there are no other player in the game
     */
    public void lastPlayerLeft(){
        interfaceHandler.lastPlayerLeft();
    }
    public void deleteAfterMatch(){
        disconnectionTimer.cancel();

    }
    /**
     * notify the interface handler that a game ended, in the case that the player is still suspended
     */
    public void gameEnded(){
        deleteAfterMatch();
        interfaceHandler.gameEnded();
    }
}
