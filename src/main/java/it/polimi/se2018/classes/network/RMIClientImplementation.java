package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.Events.Message;
import it.polimi.se2018.classes.model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIClientImplementation implements RMIRemoteClientInterface {


    public void sendMessage(Message message) throws RemoteException {
        System.out.println(message.getMessage());
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
