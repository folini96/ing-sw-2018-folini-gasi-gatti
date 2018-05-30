package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIClient implements ClientInterface {
   public void main(String playerName){
      RMIRemoteServerInterface server;
      try {
         server = (RMIRemoteServerInterface)Naming.lookup("//localhost/MyServer");
         RMIClientImplementation client= new RMIClientImplementation();
         RMIRemoteClientInterface remoteRef = (RMIRemoteClientInterface) UnicastRemoteObject.exportObject(client, 0);
         server.addClient(remoteRef, playerName);

      }catch (MalformedURLException e) {
         System.err.println("URL non trovato!");
      }catch (RemoteException e) {
         System.err.println("Errore di connessione: " + e.getMessage() + "!");
      }catch (NotBoundException e) {
         System.err.println("Il riferimento passato non è associato a nulla!");
      }
   }
}
