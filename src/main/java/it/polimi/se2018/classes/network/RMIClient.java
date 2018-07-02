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
    public void startDisconnectionTimer(){
        TimerTask disconnectionTask=new TimerTask() {
            @Override
            public void run() {
               try{
                   client.ping();
               }catch (RemoteException e){
                   interfaceHandler.connectionToServerLost();
               }
            }
        };
        disconnectionTimer.schedule(disconnectionTask,1000,1000);
    }

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
    public void okUsername(String username){
       interfaceHandler.okUsername(username);
    }
    public void askUsername(){
       interfaceHandler.askUsername();
    }


    public void sendWindowToChose(WindowToChoseEvent windowToChoseEvent){
        interfaceHandler.windowToChose(windowToChoseEvent);
    }
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
    public void playerDisconnected(String player){
        interfaceHandler.disconnectedPlayer(player);
    }
    public void lastPlayerLeft(){
        interfaceHandler.lastPlayerLeft();
    }
    public void deleteAfterMatch(){
        disconnectionTimer.cancel();

    }
}
