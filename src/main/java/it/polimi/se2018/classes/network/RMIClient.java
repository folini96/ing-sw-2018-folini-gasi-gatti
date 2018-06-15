package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIClient implements ClientInterface,ModelViewEventVisitor {
   private RMIRemoteServerInterface server;
   public void main(String username){

      try {
         server = (RMIRemoteServerInterface)Naming.lookup("//localhost/MyServer");
         RMIClientImplementation client= new RMIClientImplementation(this);
         RMIRemoteClientInterface remoteRef = (RMIRemoteClientInterface) UnicastRemoteObject.exportObject(client, 0);
         server.addClient(remoteRef, username);

      }catch (MalformedURLException e) {
         System.err.println("URL non trovato!");
      }catch (RemoteException e) {
         System.err.println("Errore di connessione: " + e.getMessage() + "!");
      }catch (NotBoundException e) {
         System.err.println("Il riferimento passato non è associato a nulla!");
      }
   }
   public void sendToServer (ViewControllerEvent viewControllerEvent){
       try{
           server.sendToServer(viewControllerEvent);
       }catch (RemoteException e){
           System.out.println("Errore nella comunicazione con il server");
       }
   }

    public String askUsername(){
       return "Not implemented yet";
    }

    public void notValideMoveMessage (Message message){

    }
    public void sendWindowToChose(WindowToChoseEvent windowToChoseEvent){

    }
    public void sendToClient(ModelViewEvent modelViewEvent){
       modelViewEvent.accept(this);
    }
    public void visit(StartMatchEvent startMatchEvent){

    }
    public void visit (StartRoundEvent startRoundEvent){


    }
    public void visit (StartTurnEvent startTurnEvent){

    }
    public void visit (EndRoundEvent endRoundEvent){

    }




}
