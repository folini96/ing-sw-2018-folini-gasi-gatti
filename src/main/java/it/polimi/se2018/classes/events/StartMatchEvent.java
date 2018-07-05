package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.model.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Andrea Folini
 * contains every object initialized at the start of the match; used at the start of the match
 */
public class StartMatchEvent implements ModelViewEvent, Serializable{
    private ArrayList<Player> players;
    private PublicObjCard[] publicObjCards;
    private ToolCard[] toolCards;

    /**
     * constructor
     * @param players every player in the match
     * @param publicObjCards the public objective cards extracted for the match
     * @param toolCards the tool cards extracted for the match
     */
    public StartMatchEvent(ArrayList<Player> players, PublicObjCard[] publicObjCards, ToolCard[] toolCards){
        this.players=players;
        this.publicObjCards=publicObjCards;
        this.toolCards=toolCards;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return the public objective cards deck
     */
    public PublicObjCard[] getPublicObjCards() {
        return publicObjCards;
    }

    /**
     * @return the tool cards deck
     */
    public ToolCard[] getToolCards() {
        return toolCards;
    }
}
