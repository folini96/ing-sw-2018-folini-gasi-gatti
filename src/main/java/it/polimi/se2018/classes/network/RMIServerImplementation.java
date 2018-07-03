package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Andrea Folini
 * get the remote method calls from the rmi client
 */
public class RMIServerImplementation extends UnicastRemoteObject implements RMIRemoteServerInterface {
    private Server server;

    /**
     * constructor
     * @param server reference of the main server
     * @throws RemoteException caused by an error creating the rmi server
     */
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
