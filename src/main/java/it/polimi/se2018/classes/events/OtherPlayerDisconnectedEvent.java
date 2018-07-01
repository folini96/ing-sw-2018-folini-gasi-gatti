package it.polimi.se2018.classes.events;

import java.io.Serializable;

public class OtherPlayerDisconnectedEvent implements Serializable {
    private String player;
    public OtherPlayerDisconnectedEvent(String player){
        this.player=player;
    }

    public String getPlayer() {
        return player;
    }
}
