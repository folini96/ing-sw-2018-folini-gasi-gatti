package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

/**
 * contains the updated tokens of the used tool card and of the player that used it
 */
public class ModifiedTokenEvent implements ModelViewEvent,Serializable {
    String player;
    private int playerToken;
    int toolCard;
    private int toolCardToken;

    /**
     * constructor
     * @param player the name of the player that used the tool card
     * @param playerToken the new value for the player token
     * @param toolCard the used tool card
     * @param toolCardToken the new value for the tool card token
     */
    public ModifiedTokenEvent(String player,int playerToken,int toolCard, int toolCardToken){
        this.player=player;
        this.playerToken=playerToken;
        this.toolCard=toolCard;
        this.toolCardToken=toolCardToken;
    }

    /**
     * @return the name of the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * @return the token of the player
     */
    public int getPlayerToken() {
        return playerToken;
    }

    /**
     * @return the index of the used tool card
     */
    public int getToolCard() {
        return toolCard;
    }

    /**
     * @return the token of the tool card
     */
    public int getToolCardToken() {
        return toolCardToken;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
