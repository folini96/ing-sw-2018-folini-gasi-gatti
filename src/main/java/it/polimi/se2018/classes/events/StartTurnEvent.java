package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the name of the current player; used every time a new turn starts
 */
public class StartTurnEvent implements ModelViewEvent, Serializable {
    String player;

    /**
     * constructor
     * @param player the name of player that will play now
     */
    public StartTurnEvent(String player){
        this.player=player;
    }

    /**
     * @return the name of the player
     */
    public String getPlayer(){
        return player;
    }

    /**
     * @param visitor the visitor that called the method
     */
    public void accept(ModelViewEventVisitor visitor){
        visitor.visit(this);
    }
}
