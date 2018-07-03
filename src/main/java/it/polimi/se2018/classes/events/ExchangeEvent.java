package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the parameters for a draft dice exchange with the dice bag or the round track
 */
public class ExchangeEvent implements ViewControllerEvent,Serializable {
    private int draftDice;
    private int roundNumber;
    private int diceInRound;

    /**
     * constructor
     * @param draftDice index of the dice in the draft
     * @param roundNumber the round number of the round track dice selected (use only when the dice is exchanged with one in the round track)
     * @param diceInRound the index in the round of the round track dice selected (use only when the dice is exchanged with one in the round track)
     */
    public ExchangeEvent(int draftDice, int roundNumber, int diceInRound){
        this.draftDice=draftDice;
        this.roundNumber=roundNumber;
        this.diceInRound=diceInRound;
    }

    /**
     * @return the index of the dice
     */
    public int getDiceInRound() {
        return diceInRound;
    }

    /**
     * @return the number of the round where the dice is
     */
    public int getDraftDice() {
        return draftDice;
    }

    /**
     * @return the index of the dice in the round
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
