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
    public void addClient(RMIRemoteClientInterface client, String firstUsername) throws RemoteException {
        String username=firstUsername;

        while (!server.checkUsername(username)){
            System.out.println("The username "+username+" is already used. Waiting for another username");
            try{
                username=client.askUsername();
            }catch(RemoteException e){
                System.out.println("Errore di comunicazione con il client RMI ");
            }

        }
        server.addClient(new RMIVirtualClient(client, username));

    }

   public void sendToServer(ViewControllerEvent viewControllerEvent){
        server.sendToServer(viewControllerEvent);
   }
}
