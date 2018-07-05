package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the parameters for a dice placement
 */
public class PlaceDiceEvent implements ViewControllerEvent,Serializable {
    private int draftDice;
    private int row;
    private int column;

    /**
     * constructor
     * @param draftDice the index of the dice in the draft
     * @param row the row of the placement coordinates
     * @param column the column of the placement coordinates
     */
    public PlaceDiceEvent(int draftDice,int row, int column){
        this.draftDice=draftDice;
        this.row=row;
        this.column=column;
    }

    /**
     * @return the index of the dice
     */
    public int getDraftDice(){return draftDice;}

    /**
     * @return the row of the coordinates
     */
    public int getRow(){
        return row;
    }

    /**
     * @return the column of the coordinates
     */
    public int getColumn(){
        return column;
    }

    /**
     * @param visitor the visitor that called the method
     */
    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}
