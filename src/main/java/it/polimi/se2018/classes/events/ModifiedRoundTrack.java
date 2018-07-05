package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Round;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains an updated round track; used when the round track gets modified by the players
 */
public class ModifiedRoundTrack implements ModelViewEvent,Serializable {
    private Round[] roundTrack;

    /**
     * constructor
     * @param roundTrack the updated round track
     */
    public ModifiedRoundTrack(Round[] roundTrack){
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
