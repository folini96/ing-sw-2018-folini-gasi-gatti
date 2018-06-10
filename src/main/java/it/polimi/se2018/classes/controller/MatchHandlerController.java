package it.polimi.se2018.classes.controller;
import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.view.VirtualView;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MatchHandlerController {

    private MatchHandlerModel matchHandlerModel;
    private VirtualView view;
    public MatchHandlerController(VirtualView view){
        this.view =view;
    }

    public void handleStartMatch(String[] usernames){
        int playerNumber=0;
        for(String name:usernames){
            playerNumber++;
        }
        matchHandlerModel=new MatchHandlerModel(view);
        //matchHandlerModel.prepareMatch(playerNumber, usernames,createPublicObjDeck(),createPrivateObjDeck(),createToolCardDeck(),handleWindowChoice());
    }
    /*public PublicObjCard[] createPublicObjDeck(){

    }
    public PrivateObjCard[] createPrivateObjDeck(){

    }
    public ToolCard[] createToolCardDeck(){

    }*/
    public void handleWindowChoice(){
        view.choseWindow((matchHandlerModel.parseWindowSide()));
    }

    public void  handleStartRound(){

    }

    public void handleEndRound(){

    }

    public void handleEndMatch(){

    }

    public void handlePlaceDice(Dice dice, SelectedCoordinate coordinate){
        if(matchHandlerModel.checkCorrectMove(dice, coordinate)){
            //place dice;
        }
        else{
           //messaggio errore
        }

    }

    public void handleToolCardSelection(){

    }

}
