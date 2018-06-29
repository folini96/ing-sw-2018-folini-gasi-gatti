package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

public class RerollDraftEvent implements Serializable,ViewControllerEvent {
    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
