package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.model.*;
import java.util.ArrayList;

public interface VirtualClientInterface {
    public String getUsername();
    public void notValideMoveMessage (Message message);
    public void sendPublicObjDeck(PublicObjCard[] publicObjDeck);
    public void sendPrivateObjCard(PrivateObjCard privateObjCard);
    public void sendToolCardDeck(ToolCard[] toolDeck);
    public void sendWindow(WindowSide window);
    public void sendDraftPool(ArrayList<Dice> draftPool);
    public void sendRoundTrack(Round[] roundTrack);
    public void removeFavorToken(int removedToken);
}
