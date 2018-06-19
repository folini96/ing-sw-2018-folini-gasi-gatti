package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class ModifiedDraftEvent implements ModelViewEvent,Serializable{
    private ArrayList<Dice> draftPool;
    public ModifiedDraftEvent(ArrayList<Dice> draftPool){
        this.draftPool=draftPool;
    }

    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }

    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
