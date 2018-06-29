package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Round;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

public class ModifiedRoundTrack implements ModelViewEvent,Serializable {
    private Round[] roundTrack;
    public ModifiedRoundTrack(Round[] roundTrack){
        this.roundTrack=roundTrack;
    }
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }

    public Round[] getRoundTrack() {
        return roundTrack;
    }
}
