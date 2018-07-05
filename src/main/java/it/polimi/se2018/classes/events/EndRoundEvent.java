package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.model.Round;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the round track updated; send after the end of every round
 */
public class EndRoundEvent implements ModelViewEvent, Serializable {
    private Round[] roundTrack;

    /**
     * constructor
     * @param roundTrack the updated round track
     */
    public EndRoundEvent(Round[] roundTrack){
        this.roundTrack=roundTrack;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * @return the round track
     */
    public Round[] getRoundTrack() {
        return roundTrack;
    }
}
