package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.view.MainScreenController;

import java.io.Serializable;

public class PlacementWithoutVicinity implements ToolCardsEffectsInterface,Serializable {

    private MatchHandlerModel model;
    private EffectType effectType;
    public PlacementWithoutVicinity(EffectType effectType){
        this.effectType=effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }



    public void accept(MainScreenController visitor){
        visitor.visit(this);
    }
}
