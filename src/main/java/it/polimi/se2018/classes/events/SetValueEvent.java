package it.polimi.se2018.classes.events;


import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

/**
 * contains the value that the player chose for the new dice extracted from the dice bag
 */
public class SetValueEvent implements ViewControllerEvent,Serializable{
    private int newValue;

    /**
     * @param newValue the value for the new dice
     */
    public SetValueEvent(int newValue){
        this.newValue=newValue;
    }

    /**
     * @return the value for the new dice
     */
    public int getNewValue() {
        return newValue;
    }

    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
