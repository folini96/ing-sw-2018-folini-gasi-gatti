package it.polimi.se2018.classes.events;


import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

public class SetValueEvent implements ViewControllerEvent,Serializable{
    private int newValue;
    public SetValueEvent(int newValue){
        this.newValue=newValue;
    }

    public int getNewValue() {
        return newValue;
    }

    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
