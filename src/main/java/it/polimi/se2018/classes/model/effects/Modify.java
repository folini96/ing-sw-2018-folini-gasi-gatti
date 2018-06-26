package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.model.Color;
import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.MatchHandlerModel;

import java.io.Serializable;

public class Modify implements ToolCardsEffectsInterface,Serializable {

    private MatchHandlerModel model;

    public Modify(){
    }

    public void useEffect(EffectType type, Dice dice){
        switch(type){
            case UPORDOWNVALUEMODIFY:
                model.upOrDownValue(dice);
                break;
            case ROTATEDICEMODIFY:
                model.rotateDice(dice);
                break;
            case NEWRANDOMVALUEMODIFY:
                model.getNewRandomValue(dice);
                break;
        }

        //aggiorna interfaccia
    }

}
