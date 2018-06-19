package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class EndMatchEvent implements Serializable,ModelViewEvent {
    private ArrayList<String> players;
    private ArrayList<Integer> points;
    public EndMatchEvent(ArrayList<String> players,ArrayList<Integer> points){
        this.players=players;
        this.points=points;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }
    public void accept (ModelViewEventVisitor visitor){
        visitor.visit(this);
    }
}
