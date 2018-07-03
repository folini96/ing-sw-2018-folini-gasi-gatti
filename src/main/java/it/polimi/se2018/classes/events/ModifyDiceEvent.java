package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the parameters for a draft dice modification
 */
public class ModifyDiceEvent implements Serializable,ViewControllerEvent {
    private int draftDice;
    private int upOrDown;

    /**
     * constructor
     * @param dice the index of the dice in the draft
     * @param upOrDown boolean to know the type of modification
     */
    public ModifyDiceEvent(int dice, int upOrDown){
        this.draftDice=dice;
        this.upOrDown=upOrDown;
    }

    /**
     * @return the draft index
     */
    public int getDraftDice() {
        return draftDice;
    }

    /**
     * @return return value of upOrDown
     */
    public int getUpOrDown() {
        return upOrDown;
    }

    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
