package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.events.ModifiedWindowEvent;
import it.polimi.se2018.classes.events.MoveDiceEvent;
import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.model.ToolCard;

import java.io.Serializable;

public class Move implements ToolCardsEffectsInterface,Serializable {

    private MatchHandlerModel model;

    public Move(){

    }

    public void useEffect(EffectType type, MoveDiceEvent moveDice1, int currentPlayer){

        if(type==EffectType.NOCOLORBOUND){
            if(model.checkCorrectSecondToolCardMove(moveDice1, currentPlayer)){
                model.moveDice(moveDice1, currentPlayer);
            }
            else{
                //messaggio errore
            }
        }
        if(type==EffectType.NOVALUEBOUND){
            if(model.checkCorrectThirdToolCardMove(moveDice1, currentPlayer)){
                model.moveDice(moveDice1, currentPlayer);
            }
            else {
                //messaggio errore
            }
        }

        //aggiorna intefaccia
    }
}
