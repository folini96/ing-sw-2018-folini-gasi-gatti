package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

public class ExchangeEvent implements ViewControllerEvent,Serializable {
    private int draftDice;
    private int roundNumber;
    private int diceInRound;
    public ExchangeEvent(int draftDice, int roundNumber, int diceInRound){
        this.draftDice=draftDice;
        this.roundNumber=roundNumber;
        this.diceInRound=diceInRound;
    }

    public int getDiceInRound() {
        return diceInRound;
    }

    public int getDraftDice() {
        return draftDice;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
