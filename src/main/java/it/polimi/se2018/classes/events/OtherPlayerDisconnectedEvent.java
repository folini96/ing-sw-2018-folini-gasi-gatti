package it.polimi.se2018.classes.events;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the player that lost connection with the server
 */
public class OtherPlayerDisconnectedEvent implements Serializable {
    private String player;

    /**
     * constructor
     * @param player the name disconnected player
     */
    public OtherPlayerDisconnectedEvent(String player){
        this.player=player;
    }

    /**
     * @return the name disconnected player
     */
    public String getPlayer() {
        return player;
    }
}
