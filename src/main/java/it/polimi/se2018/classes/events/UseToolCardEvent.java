package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.controller.MatchHandlerController;

public class UseToolCardEvent implements ViewControllerEvent{
    private int toolCard;
    public UseToolCardEvent(int toolCard){
        this.toolCard=toolCard;
    }

    public int getToolCard() {
        return toolCard;
    }
    public void accept(MatchHandlerController visitor){
        visitor.visit(this);
    }
}
