package it.polimi.se2018.classes.events;

import java.io.Serializable;

public class ExchangeFromRoundTrackEvent implements Serializable {

    private int draftPoolDice;
    private int round;
    private int roundTrackDice;

    public ExchangeFromRoundTrackEvent(int draftPoolDice, int round, int roundTrackDice){
        this.draftPoolDice=draftPoolDice;
        this.round=round;
        this.roundTrackDice=roundTrackDice;
    }

    public int getDraftPoolDice(){
        return draftPoolDice;
    }

    public int getRound(){
        return round;
    }

    public int getRoundTrackDice(){
        return roundTrackDice;
    }
}
