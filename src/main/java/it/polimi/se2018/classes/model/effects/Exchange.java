package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.events.ExchangeFromRoundTrackEvent;
import it.polimi.se2018.classes.events.SelectedDraftPoolDice;
import it.polimi.se2018.classes.model.MatchHandlerModel;

public class Exchange implements ToolCardsEffectsInterface {

    private MatchHandlerModel model;

    public Exchange(){

    }

    public void useEffect(EffectType type, ExchangeFromRoundTrackEvent exchangeFromRoundTrackEvent){
        if(type==EffectType.DRAFTPOOLROUNDTRACKEXCHANGE){
            if(model.checkDraftPoolRoundTrackDices(exchangeFromRoundTrackEvent)){
                model.exchangeDraftPoolRoundTrack(exchangeFromRoundTrackEvent);
            }
            else{
                //messaggio errore
            }
        }
        else{
            //messaggio errore
        }

        //aggiorna interfaccia
    }

    public void useEffect(EffectType type, SelectedDraftPoolDice selectedDraftPoolDice){
        if(type==EffectType.DRAFTPOOLBAGEXCHANGE){
            if(model.checkDraftPoolDiceBagDices(selectedDraftPoolDice)){
                model.exchangeDraftPoolDiceBag(selectedDraftPoolDice);
            }
            else{
                //messaggio errore
            }
        }
        else{
            //errore
        }


        //aggiorna interfaccia
    }


}
