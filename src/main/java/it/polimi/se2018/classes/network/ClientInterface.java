package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.view.GUIHandler;

/**
 * @author Andrea Folini
 */
public interface ClientInterface {
     /**
      * constructor
      * handle the creation of a new client
      * @param username name of the client
      * @param interfaceHandler reference to the interface handler
      * @param serverIp ip of the server
      * @param serverPort port of the server
      */
     void main(String username, GUIHandler interfaceHandler,String serverIp,int serverPort);

    /**
     * send the event to the client implementation that will send it to the server
     * @param viewControllerVisitor the event from the user interface
     */
     void sendToServer(ViewControllerEvent viewControllerVisitor);

    /**
     * send to the client implementation a request of reconnection
     * @param username username of this player
     */
     void reconnect(String username);

    /**
     * delete the connection after the game ended
     */
    void deleteAfterMatch();
}
