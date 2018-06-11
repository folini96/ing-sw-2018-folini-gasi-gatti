package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.controller.MatchHandlerController;

import java.io.Serializable;

public class PlaceDiceEvent implements ViewControllerEvent,Serializable {
    private int draftDice;
    private int row;
    private int column;
    public PlaceDiceEvent(int draftDice,int row, int column){
        this.draftDice=draftDice;
        this.row=row;
        this.column=column;
    }
    public int getDraftDice(){return draftDice;}
    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }
    public void accept(MatchHandlerController visitor){
        
    }
}
