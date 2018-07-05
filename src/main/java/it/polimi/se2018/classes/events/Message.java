package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * used to send a message to the player
 */
public class Message implements Serializable,ModelViewEvent{
    private String message;
    private String player;

    /**
     * constructor
     * @param message the message to send
     * @param player the name of the player recipient of the message
     */
    public Message (String message,String player){
        this.message=message;
        this.player=player;
    }

    /**
     * @return the message
     */
    public String getMessage(){
        return message;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * @return the name of the player
     */
    public String getPlayer() {
        return player;
    }
}
