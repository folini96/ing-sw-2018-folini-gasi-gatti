package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the number in the tool deck of the tool card chosen by the player
 */
public class UseToolCardEvent implements ViewControllerEvent, Serializable {
    private int toolCard;

    /**
     * constructor
     * @param toolCard the tool card chosen
     */
    public UseToolCardEvent(int toolCard){
        this.toolCard=toolCard;
    }

    /**
     * @return the tool card
     */
    public int getToolCard() {
        return toolCard;
    }
    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}
