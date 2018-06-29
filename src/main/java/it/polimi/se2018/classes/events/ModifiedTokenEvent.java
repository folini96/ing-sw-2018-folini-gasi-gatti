package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

public class ModifiedTokenEvent implements ModelViewEvent,Serializable {
    String player;
    private int playerToken;
    int toolCard;
    private int toolCardToken;
    public ModifiedTokenEvent(String player,int playerToken,int toolCard, int toolCardToken){
        this.player=player;
        this.playerToken=playerToken;
        this.toolCard=toolCard;
        this.toolCardToken=toolCardToken;
    }

    public String getPlayer() {
        return player;
    }

    public int getPlayerToken() {
        return playerToken;
    }

    public int getToolCard() {
        return toolCard;
    }

    public int getToolCardToken() {
        return toolCardToken;
    }

    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
