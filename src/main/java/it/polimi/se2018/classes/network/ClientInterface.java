package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.view.GUIHandler;


public interface ClientInterface {
     void main(String username, GUIHandler interfaceHandler);
     void sendToServer(ViewControllerEvent viewControllerVisitor);
     void newUsername(String username);
     void reconnect(String username);
}
