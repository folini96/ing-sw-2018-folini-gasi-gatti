package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

public class EndTurnEvent implements ViewControllerEvent, Serializable {
    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}
