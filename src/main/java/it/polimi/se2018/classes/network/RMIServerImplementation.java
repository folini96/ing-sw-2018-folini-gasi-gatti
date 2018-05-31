package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.events.SelectedRoundTrackDice;
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

    @Override
    public void placeDiceFromDraft(int draftDice, SelectedCoordinate coordinate) throws RemoteException{
        server.placeDiceFromDraft(draftDice,coordinate);
    }

    @Override
    public void useToolCard(int toolCard) throws RemoteException{
        server.useToolCard(toolCard);
    }

    @Override
    public void switchDraftDiceRoundTrackDice(int draftDice, SelectedRoundTrackDice roundTrackDice) throws RemoteException{
        server.switchDraftDiceRoundTrackDice(draftDice, roundTrackDice);
    }

    @Override
    public void moveWindowDice(SelectedCoordinate currentPosition, SelectedCoordinate newPosition) throws RemoteException{
        server.moveWindowDice(currentPosition, newPosition);
    }

    @Override
    public void endTurn() throws RemoteException{
        server.endTurn();
    }



}
