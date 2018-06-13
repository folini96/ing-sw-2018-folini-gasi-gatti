package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIClient implements ClientInterface {
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

   public void placeDiceFromDraft(PlaceDiceEvent placeDiceEvent){
       try{
           server.placeDiceFromDraft(placeDiceEvent);
       }catch (RemoteException e){
           System.out.println("Errore nella comunicazione con il server");
       }

   }

    @Override
    public void choseWindow(ChoseWindowEvent choseWindowEvent) {
        try{
            server.choseWindow(choseWindowEvent);
        }catch (RemoteException e){
            System.out.println("Errore nella comunicazione con il server");
        }
    }

    public void useToolCard(UseToolCardEvent useToolCardEvent){
        try{
            server.useToolCard(useToolCardEvent);
        }catch (RemoteException e){
            System.out.println("Errore nella comunicazione con il server");
        }
    }

    public void endTurn(EndTurnEvent endTurnEvent){
        try{
            server.endTurn(endTurnEvent);
        }catch (RemoteException e){
            System.out.println("Errore nella comunicazione con il server");
        }
    }
    public String askUsername(){
       return "Not implemented yet";
    }

    public void notValideMoveMessage (Message message){

    }

    public void removeFavorToken(){

    }
    public void sendStartMatchEvent(StartMatchEvent startMatchEvent){

    }
    public void sendStartRoundEvent(StartRoundEvent startRoundEvent){

    }
    public void sendStartTurnEvent(StartTurnEvent startTurnEvent){

    }
    public void sendEndRoundEvent(EndRoundEvent endRoundEvent){

    }
}
