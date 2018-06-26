package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.model.MatchHandlerModel;

import java.io.Serializable;

public class RerollDraftPool implements ToolCardsEffectsInterface,Serializable {

    private MatchHandlerModel model;

    public RerollDraftPool(){

    }

    public void useEffect(){
        model.rerollDraftPool();

        //solo durante il secondo turno, prima di piazzare un dado
        //aggiorna interfaccia
    }
}
