package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.rmi.ConnectException;
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
           server.addClient(new RMIVirtualClient(client, username));
       }


    }

   public void sendToServer(ViewControllerEvent viewControllerEvent, int lobbyNumber){
        server.sendToServer(viewControllerEvent, lobbyNumber);
   }
   public void ping()throws  RemoteException{
      //METODO VUOTO USATO SOLO PER CONTROLLARE CHE IL CLIENT SIA ANCORA CONNESSO
   }
}
