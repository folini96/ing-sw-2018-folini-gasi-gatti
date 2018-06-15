package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.view.VirtualView;
import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

public class ChoseWindowEvent implements ViewControllerEvent,Serializable {
    private int chosenWindow;
    private String username;
    public ChoseWindowEvent(int chosenWindow, String username){
        this.chosenWindow=chosenWindow;
        this.username=username;
    }

    public int getChosenWindow() {
        return chosenWindow;
    }

    public String getUsername() {
        return username;
    }

    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}
