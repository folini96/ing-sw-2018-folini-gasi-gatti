package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.Events.Message;
import it.polimi.se2018.classes.Events.SelectedCoordinate;
import it.polimi.se2018.classes.Events.SelectedRoundTrackDice;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIClient implements ClientInterface {
   private RMIRemoteServerInterface server;
   public void main(){

      try {
         server = (RMIRemoteServerInterface)Naming.lookup("//localhost/MyServer");
         RMIClientImplementation client= new RMIClientImplementation();
         RMIRemoteClientInterface remoteRef = (RMIRemoteClientInterface) UnicastRemoteObject.exportObject(client, 0);
         server.addClient(remoteRef);

      }catch (MalformedURLException e) {
         System.err.println("URL non trovato!");
      }catch (RemoteException e) {
         System.err.println("Errore di connessione: " + e.getMessage() + "!");
      }catch (NotBoundException e) {
         System.err.println("Il riferimento passato non è associato a nulla!");
      }
   }
   public void placeDiceFromDraft(int draftDice, SelectedCoordinate coordinate){
       try{
           server.placeDiceFromDraft(draftDice, coordinate);
       }catch (RemoteException e){
           System.out.println("Errore nella comunicazione con il server");
       }

   }
    public void useToolCard(int toolCard) throws RemoteException{
        try{
            server.useToolCard(toolCard);
        }catch (RemoteException e){
            System.out.println("Errore nella comunicazione con il server");
        }
    }


    public void switchDraftDiceRoundTrackDice(int draftDice, SelectedRoundTrackDice roundTrackDice) throws RemoteException{
        try{
            server.switchDraftDiceRoundTrackDice(draftDice, roundTrackDice);
        }catch (RemoteException e){
            System.out.println("Errore nella comunicazione con il server");
        }
    }


    public void moveWindowDice(SelectedCoordinate currentPosition, SelectedCoordinate newPosition) throws RemoteException{
        try{
            server.moveWindowDice(currentPosition, newPosition);
        }catch (RemoteException e){
            System.out.println("Errore nella comunicazione con il server");
        }

    }


    public void endTurn() throws RemoteException{
        try{
            server.endTurn();
        }catch (RemoteException e){
            System.out.println("Errore nella comunicazione con il server");
        }
    }
    public void notValideMoveMessage (Message message){

    }
    public void sendPublicObjCard(){

    }
    public void sendPrivateObjCard(){

    }
    public void sendToolCard(){

    }
    public void sendWindow(){

    }
    public void sendDraftPool(){

    }
    public void sendRoundTrack(){

    }
    public void removeFavorToken(){

    }

}
