package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.events.MoveDiceEvent;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.model.ToolCard;

import java.io.Serializable;

public class MoveDoubleDice implements ToolCardsEffectsInterface,Serializable {

    private MatchHandlerModel model;
    private ToolCard[] toolDeck;

    public MoveDoubleDice(){

    }

    public void useEffect(MoveDiceEvent moveDice1, MoveDiceEvent moveDice2, int currentPlayer){

        if(toolDeck[model.getActiveToolCard()].isTakeFromRoundTrack()){
            if(model.checkSameColorMove(moveDice1, moveDice2, currentPlayer)){
                if(model.checkCorrectMove(moveDice1, currentPlayer)){
                    if(model.checkCorrectMove(moveDice2, currentPlayer)){
                        model.moveDice(moveDice1, currentPlayer);
                        model.moveDice(moveDice2, currentPlayer);
                    }
                    else{
                        //errore
                    }
                }
                else{
                    //errore
                }
            }
            else{
                //messaggio errore
            }

        }
        else {
            if(model.checkCorrectMove(moveDice1, currentPlayer)){
                if(model.checkCorrectMove(moveDice2, currentPlayer)){
                    model.moveDice(moveDice1, currentPlayer);
                    model.moveDice(moveDice2, currentPlayer);
                }
                else{
                    //errore
                }
            }
            else{
                //errore
            }
        }

        //aggiorna interfaccia

    }
}
