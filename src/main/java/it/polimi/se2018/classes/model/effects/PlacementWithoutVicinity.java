package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.MatchHandlerModel;

import java.io.Serializable;

public class PlacementWithoutVicinity implements ToolCardsEffectsInterface,Serializable {

        private MatchHandlerModel model;

    public PlacementWithoutVicinity(){

    }

    public void useEffect(PlaceDiceEvent placeDiceEvent, int currentPlayer){
        if(model.checkCorrectNinthToolCardPlacement(placeDiceEvent, currentPlayer)){
            model.placeDice(placeDiceEvent, currentPlayer);
        }
        else{
            //messaggio errore
        }

        //usata prima di piazzare
        //aggiorna interfaccia
    }
}
