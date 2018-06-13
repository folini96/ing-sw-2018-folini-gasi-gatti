package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Round;
import it.polimi.se2018.classes.view.VirtualView;

import java.io.Serializable;

public class EndRoundEvent implements ModelViewEvent, Serializable {
    private Round[] roundTrack;
    public EndRoundEvent(Round[] roundTrack){
        this.roundTrack=roundTrack;
    }
    @Override
    public void accept(VirtualView visitor) {
        visitor.visit(this);
    }

    public Round[] getRoundTrack() {
        return roundTrack;
    }
}
