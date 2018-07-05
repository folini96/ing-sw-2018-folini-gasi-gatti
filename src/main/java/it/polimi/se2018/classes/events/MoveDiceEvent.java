package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the parameters for a dice movement
 */
public class MoveDiceEvent implements ViewControllerEvent,Serializable {
    private int diceRow;
    private int diceColumn;
    private int newRow;
    private int newColumn;
    private int round;
    private int diceInRound;

    /**
     * constructor
     * @param diceRow the current row of the dice
     * @param diceColumn the current column of the dice
     * @param newRow the new row of the dice
     * @param newColumn the new column of the dice
     * @param round the round number of the round track dice selected (use only when the dice moved needs to be of the same color of a selected round track dice)
     * @param diceInRound the index in the round of the round track dice selected (use only when the dice moved needs to be of the same color of a selected round track dice)
     */
    public MoveDiceEvent(int diceRow, int diceColumn, int newRow, int newColumn, int round, int diceInRound){
        this.diceRow=diceRow;
        this.diceColumn=diceColumn;
        this.newRow=newRow;
        this.newColumn=newColumn;
        this.round=round;
        this.diceInRound=diceInRound;
    }

    /**
     * @return the current row
     */
    public int getDiceRow(){
        return diceRow;
    }

    /**
     * @return the current column
     */
    public int getDiceColumn(){
        return diceColumn;
    }

    /**
     * @return the new row
     */
    public int getNewRow(){
        return  newRow;
    }

    /**
     * @return the new column
     */
    public int getNewColumn(){
        return newColumn;
    }

    /**
     * @return the round number
     */
    public int getRound() {
        return round;
    }

    /**
     * @return the index in the round
     */
    public int getDiceInRound() {
        return diceInRound;
    }

    /**
     * @param visitor the visitor that called the method
     */
    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}