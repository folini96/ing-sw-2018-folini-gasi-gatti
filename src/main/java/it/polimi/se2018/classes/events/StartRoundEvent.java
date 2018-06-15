package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.Round;
import it.polimi.se2018.classes.view.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

public class StartRoundEvent implements ModelViewEvent, Serializable {
    private int round;
    private String firstPlayer;
    private ArrayList<Dice> draftPool;
    public StartRoundEvent(int round, String firstPlayer,ArrayList<Dice> draftPool){
        this.round=round;
        this.firstPlayer=firstPlayer;
        this.draftPool=draftPool;
    }

    public int getRound() {
        return round;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }


    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }
    public void accept(ModelViewEventVisitor visitor){
        visitor.visit(this);
    }
}
