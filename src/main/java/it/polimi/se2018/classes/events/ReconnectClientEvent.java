package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

public class ReconnectClientEvent implements ViewControllerEvent{
    private String player;
    public ReconnectClientEvent(String player){
        this.player=player;
    }
    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }

    public String getPlayer() {
        return player;
    }
}
