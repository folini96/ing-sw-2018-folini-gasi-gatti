package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the parameters of the window selection from a player
 */
public class ChoseWindowEvent implements ViewControllerEvent,Serializable {
    private int chosenWindow;
    private String username;

    /**
     * constructor
     * @param chosenWindow the index of the chosen window
     * @param username the name of the player that made the selection
     */
    public ChoseWindowEvent(int chosenWindow, String username){
        this.chosenWindow=chosenWindow;
        this.username=username;
    }

    /**
     * @return the index of the window
     */
    public int getChosenWindow() {
        return chosenWindow;
    }

    /**
     * @return the name of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param visitor the visitor that called the method
     */
    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}
