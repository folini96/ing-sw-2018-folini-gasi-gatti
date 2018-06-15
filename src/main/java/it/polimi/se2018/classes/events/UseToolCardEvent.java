package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

public class UseToolCardEvent implements ViewControllerEvent, Serializable {
    private int toolCard;
    public UseToolCardEvent(int toolCard){
        this.toolCard=toolCard;
    }

    public int getToolCard() {
        return toolCard;
    }
    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}
