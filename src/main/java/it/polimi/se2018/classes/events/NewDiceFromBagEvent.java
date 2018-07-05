package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains a new dice extracted from the dice bag and the name of the player that requested it
 */
public class NewDiceFromBagEvent implements ModelViewEvent,Serializable {
    private Dice extractedDice;
    private String player;

    /**
     * constructor
     * @param dice the new dice
     * @param player the name of the player
     */
    public NewDiceFromBagEvent(Dice dice, String player){
        extractedDice=dice;
        this.player=player;
    }

    /**
     * @return the dice
     */
    public Dice getExtractedDice() {
        return extractedDice;
    }

    /**
     * @return the name of the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
