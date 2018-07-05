package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.WindowSide;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains an update window of a player
 */
public class ModifiedWindowEvent implements ModelViewEvent,Serializable {
    String player;
    WindowSide window;

    /**
     * constructor
     * @param player the name of the player that modified his window
     * @param window the updated window
     */
    public ModifiedWindowEvent(String player, WindowSide window){
        this.player=player;
        this.window=window;
    }

    /**
     * @return the name of the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * @return the window
     */
    public WindowSide getWindow() {
        return window;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
