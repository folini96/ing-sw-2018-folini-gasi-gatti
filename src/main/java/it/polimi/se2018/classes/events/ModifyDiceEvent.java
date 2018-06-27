package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

public class ModifyDiceEvent implements Serializable,ViewControllerEvent {
    private int draftDice;
    private int upOrDown;
    public ModifyDiceEvent(int dice, int upOrDown){
        this.draftDice=dice;
        this.upOrDown=upOrDown;
    }

    public int getDraftDice() {
        return draftDice;
    }

    public int getUpOrDown() {
        return upOrDown;
    }

    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
