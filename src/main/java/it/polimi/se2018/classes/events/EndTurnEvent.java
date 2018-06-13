package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.controller.MatchHandlerController;

import java.io.Serializable;

public class EndTurnEvent implements ViewControllerEvent, Serializable {
    public void accept(MatchHandlerController visitor){
        visitor.visit(this);
    }
}
