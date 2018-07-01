package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class RMIServerImplementation extends UnicastRemoteObject implements RMIRemoteServerInterface {
    private Server server;
    public RMIServerImplementation(Server server) throws RemoteException{
        super(0);
        this.server=server;
    }

    @Override
    public void addClient(RMIRemoteClientInterface client, String username) throws RemoteException {


       if(!server.checkUsername(username)){

            try{
                client.askUsername();
            }catch(RemoteException e){
                System.out.println("Errore di comunicazione con il client RMI ");
            }

       }else{
           client.okUsername(username);
           server.addClient(new RMIVirtualClient(client, username, server));
       }


    }

   public void sendToServer(ViewControllerEvent viewControllerEvent, int lobbyNumber){
        server.sendToServer(viewControllerEvent, lobbyNumber,false);
   }
   public void ping()throws  RemoteException{
      //EMPTY METHOD USED TO CATCH DISCONNECTION CLIENT SIDE;
   }
   public void reconnect(String username,int lobbyNumber) throws RemoteException{
        server.reconnectRMIClient(username,lobbyNumber);
   }


}
