package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;


public class MoveDiceEvent implements ViewControllerEvent,Serializable {
    private int diceRow;
    private int diceColumn;
    private int newRow;
    private int newColumn;
    private int round;
    private int diceInRound;
    public MoveDiceEvent(int diceRow, int diceColumn, int newRow, int newColumn, int round, int diceInRound){
        this.diceRow=diceRow;
        this.diceColumn=diceColumn;
        this.newRow=newRow;
        this.newColumn=newColumn;
        this.round=round;
        this.diceInRound=diceInRound;
    }

    public int getDiceRow(){
        return diceRow;
    }
    public int getDiceColumn(){
        return diceColumn;
    }
    public int getNewRow(){
        return  newRow;
    }
    public int getNewColumn(){
        return newColumn;
    }

    public int getRound() {
        return round;
    }

    public int getDiceInRound() {
        return diceInRound;
    }

    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}