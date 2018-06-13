package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.Round;
import it.polimi.se2018.classes.view.VirtualView;

import java.util.ArrayList;

public class StartRoundEvent implements ModelViewEvent {
    private int round;
    private String firstPlayer;
    private Round[] roundTrack;
    private ArrayList<Dice> draftPool;
    public StartRoundEvent(int round, String firstPlayer,Round[] roundTrack, ArrayList<Dice> draftPool){
        this.round=round;
        this.firstPlayer=firstPlayer;
        this.roundTrack=roundTrack;
        this.draftPool=draftPool;
    }

    public int getRound() {
        return round;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public Round[] getRoundTrack() {
        return roundTrack;
    }

    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }
    public void accept(VirtualView visitor){
        visitor.visit(this);
    }
}
