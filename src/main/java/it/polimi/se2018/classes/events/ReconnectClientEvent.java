package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

/**
 * @author Andrea Folini
 * request from a player to reconnect; used from the server to notify the controller of the reconnection
 */
public class ReconnectClientEvent implements ViewControllerEvent{
    private String player;

    /**
     * constructor
     * @param player the reconnected player
     */
    public ReconnectClientEvent(String player){
        this.player=player;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * @return the reconnected player
     */
    public String getPlayer() {
        return player;
    }
}
