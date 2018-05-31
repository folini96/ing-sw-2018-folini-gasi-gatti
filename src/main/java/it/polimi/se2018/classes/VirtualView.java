package it.polimi.se2018.classes;

import it.polimi.se2018.classes.controller.MatchHandlerController;

import java.util.Observable;
import java.util.Observer;

public class VirtualView implements Observer {
    MatchHandlerController controller;
    public VirtualView(MatchHandlerController controller){
        this.controller=controller;
    }

    

    @Override
    public void update(Observable o, Object arg) {
        //model and virtual view data sharing still needs to be implemented
    }
}
