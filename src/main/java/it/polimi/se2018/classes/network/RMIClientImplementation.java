package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIClientImplementation implements RMIRemoteClientInterface {
    @Override

    public void sendMessage(Message message) throws RemoteException {

    }
    public void sendPublicObjDeck (PublicObjCard[] publicObjCardDeck) throws RemoteException{

    }
    public void sendPrivateObjCard (PrivateObjCard privateObjCard) throws RemoteException{

    }
    public void sendToolCard (ToolCard[] toolCardDeck) throws RemoteException{

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
