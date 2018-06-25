package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;


public class MoveDiceEvent implements ViewControllerEvent,Serializable {
    private int diceRow;
    private int diceColumn;
    private int newRow;
    private int newColumn;

    public MoveDiceEvent(int diceRow, int diceColumn, int newRow, int newColumn){
        this.diceRow=diceRow;
        this.diceColumn=diceColumn;
        this.newRow=newRow;
        this.newColumn=newColumn;
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
    public void accept(ViewControllerVisitor visitor){
        visitor.visit(this);
    }
}