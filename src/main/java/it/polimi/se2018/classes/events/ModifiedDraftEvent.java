package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * contains an updated draft pool; used when the draft gets modified by players
 */
public class ModifiedDraftEvent implements ModelViewEvent,Serializable{
    private ArrayList<Dice> draftPool;

    /**
     * constructor
     * @param draftPool the updated draft pool
     */
    public ModifiedDraftEvent(ArrayList<Dice> draftPool){
        this.draftPool=draftPool;
    }

    /**
     * @return the draft pool
     */
    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
