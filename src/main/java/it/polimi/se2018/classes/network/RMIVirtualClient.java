package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIVirtualClient implements VirtualClientInterface {
    private RMIRemoteClientInterface rmiClient;
    private String username;
    public RMIVirtualClient(RMIRemoteClientInterface client, String username){
        rmiClient=client;
        this.username=username;
    }
    public String getUsername(){
        return username;
    }
    public void notValideMoveMessage (Message message){
        try{
            rmiClient.sendMessage(message);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
    public void sendPublicObjCard(PublicObjCard publicObjCard){
        try{
            rmiClient.sendPublicObjCard(publicObjCard);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }

    }
    public void sendPrivateObjCard(PrivateObjCard privateObjCard){
        try{
            rmiClient.sendPrivateObjCard(privateObjCard);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
    public void sendToolCard(ToolCard toolCard){
        try{
            rmiClient.sendToolCard(toolCard);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
    public void sendWindow(WindowSide window){
        try{
            rmiClient.sendWindowSide(window);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
    public void sendDice(Dice dice){
        try{
            rmiClient.sendDice(dice);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
    public void sendRound(Round round){
        try{
            rmiClient.sendRound(round);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
    public void removeFavorToken(int removedToken){
        try{
            rmiClient.removeFavorToken(removedToken);
        }catch(RemoteException e){
            System.out.println("Errore di comunicazione con il client RMI ");
        }
    }
}
