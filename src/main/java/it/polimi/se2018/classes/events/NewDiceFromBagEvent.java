package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import java.io.Serializable;

public class NewDiceFromBagEvent implements ModelViewEvent,Serializable {
    private Dice extractedDice;
    private String player;
    public NewDiceFromBagEvent(Dice dice, String player){
        extractedDice=dice;
        this.player=player;
    }

    public Dice getExtractedDice() {
        return extractedDice;
    }

    public String getPlayer() {
        return player;
    }

    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
