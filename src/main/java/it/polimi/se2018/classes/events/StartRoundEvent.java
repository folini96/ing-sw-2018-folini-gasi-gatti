package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.model.Dice;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Andrea Folini
 * contains the round number, the name of the player that will play first in the round and the new draft pool; used at the start of every round
 */
public class StartRoundEvent implements ModelViewEvent, Serializable {
    private int round;
    private String firstPlayer;
    private ArrayList<Dice> draftPool;

    /**
     * constructor
     * @param round the number of the round
     * @param firstPlayer the name of the first player
     * @param draftPool the new draft pool
     */
    public StartRoundEvent(int round, String firstPlayer,ArrayList<Dice> draftPool){
        this.round=round;
        this.firstPlayer=firstPlayer;
        this.draftPool=draftPool;
    }

    /**
     * @return the round number
     */
    public int getRound() {
        return round;
    }

    /**
     * @return the first player name
     */
    public String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * @return the draft pool
     */
    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }

    /**
     * @param visitor the visitor that called the method
     */
    public void accept(ModelViewEventVisitor visitor){
        visitor.visit(this);
    }
}
