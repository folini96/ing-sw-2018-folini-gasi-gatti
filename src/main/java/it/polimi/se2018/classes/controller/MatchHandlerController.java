package it.polimi.se2018.classes.controller;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.view.VirtualView;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class MatchHandlerController implements Observer{
    private int playerNumber=0;
    private ArrayList<ChoseWindowEvent> chosenWindow=new ArrayList<>();
    private String[] playerNames;
    private MatchHandlerModel matchHandlerModel;
    private VirtualView view;
    private int round;
    private int firstPlayer;
    private int currentPlayer;
    private int turnPassed;
    public MatchHandlerController(VirtualView view){

        this.view =view;
    }

    public void handleStartMatch(String[] usernames){
        for(String name:usernames){
            playerNumber++;
        }
        round=0;
        Random random = new Random();
        int randomInt = random.nextInt(playerNumber-1);
        firstPlayer = randomInt;
        playerNames=usernames;
        matchHandlerModel=new MatchHandlerModel(view);
        matchHandlerModel.prepareMatch(playerNumber,playerNames);
        handleWindowCreation();
    }

    public void handleWindowCreation(){
        view.choseWindow((matchHandlerModel.parseWindowSide()));
    }

    public void  handleStartRound(){
        currentPlayer=firstPlayer;
        turnPassed=0;
        handleStartTurn();
        matchHandlerModel.startRound(round, firstPlayer);

    }
    public void handleStartTurn(){
        matchHandlerModel.startTurn(currentPlayer);
    }
    public void handleEndTurn(){
        turnPassed++;
        if (turnPassed==(playerNumber*2)) {
            handleEndMatch();
        }else {
            if (turnPassed<playerNumber){
                if (currentPlayer==(playerNumber-1)){
                    currentPlayer=0;
                }else{
                    currentPlayer++;
                }
            }else if (turnPassed>playerNumber){
                if (currentPlayer==0){
                    currentPlayer=(playerNumber-1);
                }else{
                    currentPlayer--;
                }
            }
            handleStartTurn();
        }

    }
    public void handleEndRound(){
        matchHandlerModel.endRound(round);
        if (round==9){
            handleEndMatch();
        }else{
            if (firstPlayer==(playerNumber-1)){
                firstPlayer=0;
            }else{
                firstPlayer++;
            }
            round++;
            handleStartRound();
        }
    }

    public void handleEndMatch(){

    }

    public void handlePlaceDice(PlaceDiceEvent placeDiceEvent){
        if(matchHandlerModel.checkCorrectMove(placeDiceEvent, currentPlayer)){
            //place dice;
        }
        else{
           //messaggio errore
        }

    }
    public void handleWindowSelection(ChoseWindowEvent window){
        chosenWindow.add(window);
        if (chosenWindow.size()==playerNumber){
            matchHandlerModel.windowSelection(chosenWindow);
            matchHandlerModel.startMatch();
            handleStartRound();
        }
    }
    public void handleToolCardSelection(){

    }
    public void update(Observable view, Object arg) {
        ViewControllerEvent event=(ViewControllerEvent) arg;
        event.accept(this);
    }
    public void visit(ChoseWindowEvent window){
        handleWindowSelection(window);
    }
    public void visit(PlaceDiceEvent placeDiceEvent){

    }
    public void visit(UseToolCardEvent toolCard){

    }
    public void visit(EndTurnEvent endTurnEvent){
        handleEndRound();
    }


}
