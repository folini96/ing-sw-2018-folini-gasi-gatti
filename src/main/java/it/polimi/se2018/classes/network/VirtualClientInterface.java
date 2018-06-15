package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;
import java.util.ArrayList;

public interface VirtualClientInterface {
    String getUsername();
    void sendWindowToChose(WindowToChoseEvent windowToChoseEvent);
    void sendToClient(ModelViewEvent modelViewEvent);

}
