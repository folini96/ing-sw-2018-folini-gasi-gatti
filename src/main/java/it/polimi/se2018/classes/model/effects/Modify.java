package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.model.Color;
import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.view.MainScreenController;

import java.io.Serializable;

public class Modify implements ToolCardsEffectsInterface,Serializable {

    private MatchHandlerModel model;
    private EffectType effectType;
    public Modify(EffectType effectType){
        this.effectType=effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }


    public void accept(MainScreenController visitor){
        visitor.visit(this);
    }
}
