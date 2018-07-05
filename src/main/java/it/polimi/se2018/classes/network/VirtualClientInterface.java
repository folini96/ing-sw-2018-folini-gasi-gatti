package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;

public interface VirtualClientInterface {
    /**
     * @return the lobby of this client
    */
    int getLobbyNumber();

    /**
     * set the lobby number of this client
     * @param number number of the lobby
     */
    void setLobbyNumber(int number);

    /**
     * @return the username of this client
     */
    String getUsername();

    /**
     * send through the network the windows for the player
     * @param windowToChoseEvent contains the 4 windows for the player
     */
    void sendWindowToChose(WindowToChoseEvent windowToChoseEvent);

    /**
     * send the event for the player
     * @param modelViewEvent the event from the model
     * @throws Exception caused by a connection error
     */
    void sendToClient(ModelViewEvent modelViewEvent) throws Exception;

    /**
     * send a notification that the timer for the turn ended
     * @param player current player that will be suspended
     * @throws Exception caused by a connection error
     */
    void endByTime(String player) throws Exception;

    /**
     * use to catch a disconnection of the client
     * @throws Exception caused by a connection error
     */
    void ping() throws Exception;

    /**
     * notify that another player got disconnected
     * @param otherPlayerDisconnectedEvent disconnected player
     * @throws Exception caused by a connection error
     */
    void otherPlayerDisconnected(OtherPlayerDisconnectedEvent otherPlayerDisconnectedEvent)throws Exception;

    /**
     * notify that the client is the only player left in the lobby
     * @throws Exception caused by a connection error
     */
    void lastPlayerLeft() throws Exception;

    /**
     * notify that the match ended if this client was still suspended
     * @throws Exception caused by a connection error
     */
    void notifyEndMatchToDisconnected() throws Exception;
}
