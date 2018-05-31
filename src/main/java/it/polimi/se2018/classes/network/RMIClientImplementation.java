package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIClientImplementation implements RMIRemoteClientInterface {
    private RMIClient client;
    public  RMIClientImplementation(RMIClient client){
        this.client=client;
    }
    public String askUsername() throws RemoteException{
        return client.askUsername();
    }

    public void sendMessage(Message message) throws RemoteException {

    }
    public void sendPublicObjDeck (PublicObjCard[] publicObjDeck) throws RemoteException{

    }
    public void sendPrivateObjCard (PrivateObjCard privateObjCard) throws RemoteException{

    }
    public void sendToolCardDeck (ToolCard[] toolDeck) throws RemoteException{

    }
    public void sendWindowSide (WindowSide windowSide) throws RemoteException{

    }
    public void sendDraftPool (ArrayList<Dice> draftPool)throws RemoteException{

    }
    public void sendRoundTrack(Round[] roundTrack) throws RemoteException{

    }
    public void removeFavorToken(int removedFavorToken) throws RemoteException{

    }
}
