package it.polimi.se2018.classes.controller;
import it.polimi.se2018.classes.eventi.SelectedCoordinate;
import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.model.Player;

public class MatchHadlerController {

    private MatchHandlerModel model;
    //private ViewHandler view;

    public void handleStartMatch(){

    }

    public void  handleStartRound(){

    }

    public void handleEndRound(){

    }

    public void handleEndMatch(){

    }

    public void handlePlaceDice(Dice dice, SelectedCoordinate coordinate, Player player){
        if(model.checkCorrectMove(dice, coordinate, player)){
            //place dice;
        }
        else{
           //messaggio errore
        }

    }

    public void handleToolCardSelection(){

    }

}
