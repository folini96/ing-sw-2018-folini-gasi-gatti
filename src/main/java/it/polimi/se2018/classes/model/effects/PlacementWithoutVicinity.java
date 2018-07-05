package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.view.MainScreenController;

import java.io.Serializable;

public class PlacementWithoutVicinity implements ToolCardsEffectsInterface,Serializable {
    private EffectType effectType;

    /**
     * constructor
     * @param effectType the type of placement effect of the card
     */
    public PlacementWithoutVicinity(EffectType effectType){
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
    public String toString() {
        return "PlacementWithoutVicinity";
    }
}
