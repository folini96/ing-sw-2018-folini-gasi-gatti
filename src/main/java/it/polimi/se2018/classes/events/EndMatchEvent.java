package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Andrea Folini
 * contains the points of every player; send once a game is ended
 */
public class EndMatchEvent implements Serializable,ModelViewEvent {
    private ArrayList<String> players;
    private ArrayList<Integer> points;

    /**
     * constructor
     * @param players the names of the players in the match
     * @param points the points of every player
     */
    public EndMatchEvent(ArrayList<String> players,ArrayList<Integer> points){
        this.players=players;
        this.points=points;
    }

    /**
     * @return the names of the players
     */
    public ArrayList<String> getPlayers() {
        return players;
    }

    /**
     * @return the points of the players
     */
    public ArrayList<Integer> getPoints() {
        return points;
    }

    /**
     * @param visitor the visitor that called the method
     */
    public void accept (ModelViewEventVisitor visitor){
        visitor.visit(this);
    }
}
