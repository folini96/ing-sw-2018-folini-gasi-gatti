package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.view.VirtualView;

import java.io.Serializable;
import java.util.ArrayList;

public class StartMatchEvent implements ModelViewEvent, Serializable{
    private ArrayList<Player> players;
    private PublicObjCard[] publicObjCards;
    private ToolCard[] toolCards;
    public StartMatchEvent(ArrayList<Player> players, PublicObjCard[] publicObjCards, ToolCard[] toolCards){
        this.players=players;
        this.publicObjCards=publicObjCards;
        this.toolCards=toolCards;
    }
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public PublicObjCard[] getPublicObjCards() {
        return publicObjCards;
    }

    public ToolCard[] getToolCards() {
        return toolCards;
    }
}
