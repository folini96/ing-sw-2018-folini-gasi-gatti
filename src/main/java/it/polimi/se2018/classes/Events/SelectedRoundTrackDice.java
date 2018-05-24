package it.polimi.se2018.classes.Events;

import java.io.Serializable;

public class SelectedRoundTrackDice implements Serializable {
    private int round;
    private int numDice;
    public SelectedRoundTrackDice(int round, int numDice){
        this.round=round;
        this.numDice=numDice;
    }
    public int getRound(){
        return round;
    }
    public int getNumDice(){
        return numDice;
    }
}
