package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.model.*;
import java.util.ArrayList;

public interface VirtualClientInterface {
    public String getUsername();
    public void notValideMoveMessage (Message message);
    public void sendPublicObjCard(PublicObjCard publicObjCard);
    public void sendPrivateObjCard(PrivateObjCard privateObjCard);
    public void sendToolCard(ToolCard toolCard);
    public void sendWindow(WindowSide window);
    public void sendDice(Dice dice);
    public void sendRound(Round round);
    public void removeFavorToken(int removedToken);
}
