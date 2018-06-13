package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.view.VirtualView;

import java.io.Serializable;

public class StartTurnEvent implements ModelViewEvent, Serializable {
    String player;
    public StartTurnEvent(String player){
        this.player=player;
    }
    public String getPlayer(){
        return player;
    }
    public void accept(VirtualView visitor){
        visitor.visit(this);
    }
}