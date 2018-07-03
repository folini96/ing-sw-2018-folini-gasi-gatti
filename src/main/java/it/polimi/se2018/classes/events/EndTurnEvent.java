package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * used from the client to notify that he ended his turn, or from the server in the case the turn time expired or the client lost the connection
 */
public class EndTurnEvent implements ViewControllerEvent, Serializable {
    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}
