package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.Player;
import it.polimi.se2018.classes.model.Round;
import it.polimi.se2018.classes.model.ToolCard;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Andrea Folini
 * contains every object of the match to update a player that was disconnected; used every time a player reconnects to the match
 */
public class UpdateReconnectedClientEvent implements ModelViewEvent,Serializable {
    private String player;
    private ArrayList<Player> players;
    private Round[] roundTrack;
    private ToolCard[] toolCards;
    private ArrayList<Dice> draftPool;

    /**
     * constructor
     * @param player the name of the reconnected player
     * @param players the players in the match
     * @param roundTrack the updated round track
     * @param toolCards the updated tool cards
     * @param draftPool the updated draft pool
     */
    public UpdateReconnectedClientEvent(String player, ArrayList<Player> players, Round[] roundTrack, ToolCard[] toolCards,ArrayList<Dice>draftPool){
        this.player=player;
        this.players=players;
        this.roundTrack=roundTrack;
        this.toolCards=toolCards;
        this.draftPool=draftPool;
    }

    /**
     * @return the name of the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return the round track
     */
    public Round[] getRoundTrack() {
        return roundTrack;
    }

    /**
     * @return the tool cards
     */
    public ToolCard[] getToolCards() {
        return toolCards;
    }

    /**
     * @return the draft pool
     */
    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}

