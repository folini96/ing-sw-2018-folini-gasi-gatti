package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.view.GUIHandler;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIClient implements ClientInterface,ModelViewEventVisitor {
    GUIHandler interfaceHandler;
    RMIRemoteClientInterface remoteRef;
    RMIClientImplementation client;
    private RMIRemoteServerInterface server;
    private int lobbyNumber;

    public void main(String username, GUIHandler interfaceHandler){
       this.interfaceHandler=interfaceHandler;
      try {
         server = (RMIRemoteServerInterface)Naming.lookup("//localhost/MyServer");
         client= new RMIClientImplementation(this);
         remoteRef = (RMIRemoteClientInterface) UnicastRemoteObject.exportObject(client, 0);
         server.addClient(remoteRef, username);

      }catch (MalformedURLException e) {
         System.err.println("URL non trovato!");
      }catch (RemoteException e) {
         System.err.println("Errore di connessione: " + e.getMessage() + "!");
      }catch (NotBoundException e) {
         System.err.println("Il riferimento passato non è associato a nulla!");
      }
   }
    public void setLobbyNumber(int number){
        lobbyNumber=number;
    }
   public void newUsername(String username){
       try {

           server.addClient(remoteRef, username);
       }catch (RemoteException e){
           System.err.println("Errore di connessione: " + e.getMessage() + "!");
       }

   }
   public void sendToServer (ViewControllerEvent viewControllerEvent){
       try{
           server.sendToServer(viewControllerEvent,lobbyNumber);
       }catch (RemoteException e){
           System.out.println("Errore nella comunicazione con il server");
       }
   }
    public void okUsername(String username){
       interfaceHandler.okUsername(username);
    }
    public void askUsername(){
       interfaceHandler.askUsername();
    }

    public void notValideMoveMessage (Message message){

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
    public void endByTime(){
        interfaceHandler.endByTime();
    }
}
