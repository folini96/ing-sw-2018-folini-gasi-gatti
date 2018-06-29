package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.view.MainScreenController;

import java.io.Serializable;

public class RerollDraftPool implements ToolCardsEffectsInterface,Serializable {

    private EffectType effectType;
    public RerollDraftPool(EffectType effectType){
        this.effectType=effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }


    public void accept(MainScreenController visitor){
        visitor.visit(this);
    }
    public String toString() {
        return "Reroll";
    }
}
