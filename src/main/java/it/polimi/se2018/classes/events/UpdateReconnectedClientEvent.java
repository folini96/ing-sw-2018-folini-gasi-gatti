package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.Player;
import it.polimi.se2018.classes.model.Round;
import it.polimi.se2018.classes.model.ToolCard;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;
import java.util.ArrayList;

public class UpdateReconnectedClientEvent implements ModelViewEvent,Serializable {
    private String player;
    private ArrayList<Player> players;
    private Round[] roundTrack;
    private ToolCard[] toolCards;
    private ArrayList<Dice> draftPool;
    public UpdateReconnectedClientEvent(String player, ArrayList<Player> players, Round[] roundTrack, ToolCard[] toolCards,ArrayList<Dice>draftPool){
        this.player=player;
        this.players=players;
        this.roundTrack=roundTrack;
        this.toolCards=toolCards;
        this.draftPool=draftPool;
    }

    public String getPlayer() {
        return player;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Round[] getRoundTrack() {
        return roundTrack;
    }

    public ToolCard[] getToolCards() {
        return toolCards;
    }

    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }

    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}

