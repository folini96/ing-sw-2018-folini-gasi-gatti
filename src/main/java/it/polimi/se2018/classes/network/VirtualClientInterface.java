package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;
import java.util.ArrayList;

public interface VirtualClientInterface {
    int getLobbyNumber();
    void setLobbyNumber(int number);
    String getUsername();
    void sendWindowToChose(WindowToChoseEvent windowToChoseEvent);
    void sendToClient(ModelViewEvent modelViewEvent);

}
