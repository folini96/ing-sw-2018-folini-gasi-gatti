package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * request from a player to roll again every dice in the draft
 */
public class RerollDraftEvent implements Serializable,ViewControllerEvent {
    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
