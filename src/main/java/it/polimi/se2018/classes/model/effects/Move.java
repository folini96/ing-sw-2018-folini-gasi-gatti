package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.events.ModifiedWindowEvent;
import it.polimi.se2018.classes.events.MoveDiceEvent;
import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.model.ToolCard;
import it.polimi.se2018.classes.view.MainScreenController;

import java.io.Serializable;

public class Move implements ToolCardsEffectsInterface,Serializable {

    private EffectType effectType;
    public Move(EffectType effectType){
        this.effectType=effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }
    public void accept(MainScreenController visitor){
        visitor.visit(this);
    }
    public String toString() {
        return "Move";
    }
}
