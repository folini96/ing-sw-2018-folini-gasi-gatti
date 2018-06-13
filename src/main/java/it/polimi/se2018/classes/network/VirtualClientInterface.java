package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;
import java.util.ArrayList;

public interface VirtualClientInterface {
    public String getUsername();
    public void notValideMoveMessage (Message message);
    public void sendStartMatchEvent (StartMatchEvent startMatchEvent);
    public void sendStartRoundEvent(StartRoundEvent startRoundEvent);
    public void sendStartTurnEvent (StartTurnEvent startTurnEvent);
    public void sendEndRoundEvent (EndRoundEvent endRoundEvent);
    public void sendWindow(WindowSide window);
    public void removeFavorToken(int removedToken);

}
