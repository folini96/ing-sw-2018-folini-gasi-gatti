package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.effects.ToolCardsEffectsInterface;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import java.io.Serializable;

/**
 * @author Andrea Folini
 * contains the effect of the tool card used by a player; used after a player decides to use a tool card and the controller approves it
 */
public class SendEffectEvent implements Serializable,ModelViewEvent {
    private ToolCardsEffectsInterface effect;
    private String player;

    /**
     * constructor
     * @param effect the effect of the used tool card
     * @param player the name player that used the tool card
     */
    public SendEffectEvent(ToolCardsEffectsInterface effect,String player){
        this.effect=effect;
        this.player=player;
    }

    /**
     * @return the name of the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * @return the effect of the tool card
     */
    public ToolCardsEffectsInterface getEffect() {
        return effect;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
