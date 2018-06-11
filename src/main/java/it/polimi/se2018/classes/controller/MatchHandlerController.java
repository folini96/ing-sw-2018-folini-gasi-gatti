package it.polimi.se2018.classes.controller;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.view.VirtualView;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Observable;
import java.util.Observer;

public class MatchHandlerController implements Observer{
    private int playerNumber;
    private String[] playerNames;
    private MatchHandlerModel matchHandlerModel;
    private VirtualView view;
    public MatchHandlerController(VirtualView view){

        this.view =view;
    }

    public void handleStartMatch(String[] usernames){
        for(String name:usernames){
            playerNumber++;
        }
        playerNames=usernames;
        matchHandlerModel=new MatchHandlerModel(view);
        matchHandlerModel.prepareMatch(playerNumber);
        handleWindowChoice();
    }

    public void handleWindowChoice(){
        view.choseWindow((matchHandlerModel.parseWindowSide()));
    }

    public void  handleStartRound(){

    }

    public void handleEndRound(){

    }

    public void handleEndMatch(){

    }

    public void handlePlaceDice(PlaceDiceEvent placeDiceEvent){
        if(matchHandlerModel.checkCorrectMove(placeDiceEvent)){
            //place dice;
        }
        else{
           //messaggio errore
        }

    }

    public void handleToolCardSelection(){

    }
    public void update(Observable view, Object arg) {
        ViewControllerEvent event=(ViewControllerEvent) arg;
        event.accept(this);
    }
    public void visit(ChoseWindowEvent window){

    }
    public void visit(PlaceDiceEvent placeDiceEvent){

    }
    public void visit(UseToolCardEvent toolCard){

    }
    public void visit(EndTurnEvent endTurnEvent){

    }


}
