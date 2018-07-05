package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.events.ExchangeFromRoundTrackEvent;
import it.polimi.se2018.classes.events.SelectedDraftPoolDice;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.view.MainScreenController;

import java.io.Serializable;

/**
 * @author Alessandro Gatti
 */
public class Exchange implements ToolCardsEffectsInterface,Serializable {
    private EffectType effectType;

    /**
     * constructor
     * @param effectType the type of exchange effect of the card
     */
    public Exchange(EffectType effectType){
        this.effectType=effectType;
    }

    /**
     * @return the type of effect
     */
    public EffectType getEffectType() {
        return effectType;
    }

    /**
     * @param visitor the visitor from the view
     */
    public void accept(MainScreenController visitor){
        visitor.visit(this);
    }

    /**
     * @return the name of the effect
     */
    @Override
    public String toString() {
        return "Exchange";
    }
}
