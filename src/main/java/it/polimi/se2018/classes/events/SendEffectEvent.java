package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.effects.EffectType;
import it.polimi.se2018.classes.model.effects.ToolCardsEffectsInterface;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import javax.tools.Tool;
import java.io.Serializable;

public class SendEffectEvent implements Serializable,ModelViewEvent {
    private ToolCardsEffectsInterface effect;
    private String player;
    public SendEffectEvent(ToolCardsEffectsInterface effect,String player){
        this.effect=effect;
        this.player=player;
    }

    public String getPlayer() {
        return player;
    }

    public ToolCardsEffectsInterface getEffect() {
        return effect;
    }

    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
