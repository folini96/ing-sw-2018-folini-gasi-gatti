package it.polimi.se2018.classes.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.view.VirtualView;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;


/**
 * @author Alessandro Gatti
 */
public class MatchHandlerModel extends Observable {

    private ArrayList <Player> players= new ArrayList<>();
    private int playerNumber;
    private ToolCard[] toolDeck;
    private PublicObjCard[] publicObjDeck;
    private PrivateObjCard[] privateObjDeck;
    private DiceBag diceBag;
    private Round[] roundTrack;
    private ArrayList <Dice> draftPool;

    public MatchHandlerModel(VirtualView view, PublicObjCard[] publicObjDeck, PrivateObjCard[] privateObjDeck){
        addObserver(view);
        diceBag= new DiceBag();
        roundTrack=new Round[10];
        draftPool=new ArrayList<>();
        this.publicObjDeck= publicObjDeck;
        this.privateObjDeck=privateObjDeck;

    }





    public void addPlayer(Player player){
        players.add(player);
    }
    public void prepareMatch(int playerNumber, ArrayList<String> playerNames){
        this.playerNumber=playerNumber;
        for (String name:playerNames){
            addPlayer(new Player(name,privateObjDeck[playerNames.indexOf(name)]));
        }
    }
    public void startMatch(){
        setChanged();
        notifyObservers(new StartMatchEvent(players, publicObjDeck, toolDeck));
    }

    public void windowSelection(ArrayList<ChoseWindowEvent> choseWindowEvent, WindowSide[] windowSides){
        int eventPlayer;
        int i;
        for (i=0;i<playerNumber;i++){
            for (Player player:players){
                if (player.getName().equals(choseWindowEvent.get(i).getUsername())){
                    eventPlayer=players.indexOf(player);
                    player.setWindow(windowSides[((choseWindowEvent.get(i).getChosenWindow()+1)*(eventPlayer+1)-1)]);
                }
            }
        }

    }
    public void startRound(int round, int firstPlayer){
       draftPool=diceBag.extractDice(playerNumber*2+1);
        setChanged();
       notifyObservers(new StartRoundEvent(round,players.get(firstPlayer).getName(), draftPool));
    }
    public void startTurn(int currentPlayer){
        setChanged();
        notifyObservers(new StartTurnEvent(players.get(currentPlayer).getName()));
    }
    public void endRound(int round){
        setChanged();
        roundTrack[round].setLeftDices(draftPool);
        notifyObservers(new EndRoundEvent(roundTrack));
    }
    /**
     * @param placeDiceEvent the number of the dice of the draft and the coordinate of the box the player wants to put the dice into
     * @param currentPlayer the column of the box the player wants to put the dice into
     * @return true if the placement is allowed, false if not
     */
    public boolean checkCorrectMove(PlaceDiceEvent placeDiceEvent, int currentPlayer){

        if(players.get(currentPlayer).getWindow().isEmpty()){
            if(!checkCorrectFirstMove(placeDiceEvent.getRow(),placeDiceEvent.getColumn())) return false;
            if(!checkCorrectColorMatching(placeDiceEvent.getDraftDice(), placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkCorrectValueMatching(placeDiceEvent.getDraftDice(), placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            return true;
        }
        else{
            if(!checkCorrectColorMatching(placeDiceEvent.getDraftDice(), placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkCorrectValueMatching(placeDiceEvent.getDraftDice(), placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkBoxNotEmpty(placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkColorVicinity(placeDiceEvent.getDraftDice(), placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkValueVicinity(placeDiceEvent.getDraftDice(), placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkDiceVicinity(placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            return true;
        }
    }

    /**
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @return true if the coordinates of the first dice are allowed, false if not
     */
    private boolean checkCorrectFirstMove(int selectedRow, int selectedColumn){
        int row= selectedRow ;
        int column=selectedColumn;

        if((row ==1 || row == 2) && (column ==1 || column == 2 || column ==3) ){
            return false;
        }

        return true;
    }

    /**
     * @param draftDice the dice that is to set
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the column of the box the player wants to put the dice into
     * @return true if the color of the box is compatible, false if not
     */
    private boolean checkCorrectColorMatching(int draftDice, int selectedRow, int selectedColumn, int currentPlayer){
        int row=selectedRow;
        int column=selectedColumn;
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        if(boxScheme[row][column].getColor()!=null) {
            if(draftPool.get(draftDice).getColor() != boxScheme[row][column].getColor()){
                return false;
            }
        }

        return true;
    }

    /**
     * @param draftDice the dice that is to set
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the column of the box the player wants to put the dice into
     * @return true if the value of the box is compatible, false if not
     */
    private boolean checkCorrectValueMatching(int draftDice, int selectedRow, int selectedColumn, int currentPlayer){
        int row=selectedRow;
        int column=selectedColumn;
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        if(boxScheme[row][column].getValue()!=0){
            if(draftPool.get(draftDice).getValue() != boxScheme[row][column].getValue()){
                return false;
            }
        }

        return true;
    }

    /**
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the column of the box the player wants to put the dice into
     * @return true if the box is empty, false if not
     */
    private boolean checkBoxNotEmpty(int selectedRow, int selectedColumn,int currentPlayer){
        int row=selectedRow;
        int column=selectedColumn;
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        if(boxScheme[row][column].getDice() != null){
            return false;
        }

        return true;
    }
    public void placeDice(PlaceDiceEvent placeDiceEvent, int currentPlayer){
        
    }
    /**
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the column of the box the player wants to put the dice into
     * @return true if the dice is near another dice, false if not
     */
    private boolean checkDiceVicinity(int selectedRow, int selectedColumn, int currentPlayer){
        int i, j, vicinityCheck=0;
        int row=selectedRow;
        int column=selectedColumn;
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                if(i==row-1 || i==row || i==row+1){
                    if(j==column-1 || j==column || j==column+1){
                        if(boxScheme[i][j].getDice()!=null){
                            vicinityCheck++;
                        }
                    }
                }
            }
        }
        if(vicinityCheck==0){
            return false;
        }

        return true;
    }

    /**
     * @param draftDice the dice that is to set
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the column of the box the player wants to put the dice into
     * @return true if the dice isn't adjacent to a dice of same color, false if not
     */
    private boolean checkColorVicinity(int draftDice, int selectedRow, int selectedColumn, int currentPlayer){
        int i, j;
        int row=selectedRow;
        int column=selectedColumn;
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        for(i=0; i<=3; i++) {
            for (j = 0; j <= 4; j++) {
                if(i==row){
                    if(j==column-1 || j==column+1){
                        if(boxScheme[i][j].getDice() != null && boxScheme[i][j].getDice().getColor() == draftPool.get(draftDice).getColor()){
                            return false;
                        }
                    }
                }
                if(j==column){
                    if(i==row-1 || i==row+1){
                        if(boxScheme[i][j].getDice() != null && boxScheme[i][j].getDice().getColor() == draftPool.get(draftDice).getColor()){
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * @param draftDice the dice that is to set
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the column of the box the player wants to put the dice into
     * @return true if the dice isn't adjacent to a dice of same value, false if not
     */
    private boolean checkValueVicinity(int draftDice, int selectedRow, int selectedColumn, int currentPlayer){
        int i, j;
        int row= selectedRow;
        int column= selectedColumn;
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        for(i=0; i<=3; i++) {
            for (j = 0; j <= 4; j++) {
                if(i==row){
                    if(j==column-1 || j==column+1){
                        if(boxScheme[i][j].getDice() != null && boxScheme[i][j].getDice().getValue() == draftPool.get(draftDice).getValue()){
                            return false;
                        }
                    }
                }
                if(j==column){
                    if(i==row-1 || i==row+1){
                        if(boxScheme[i][j].getDice() != null && boxScheme[i][j].getDice().getValue() == draftPool.get(draftDice).getValue()){
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * @param player the player the score is calculated for
     * @return the score
     */
    public int calculateScore(Player player){

        int i, publicObjCardPoints=0;
        int privateObjCardPoints, lostPoints, playerScore;

        for(i=0; i<=2; i++){
            publicObjCardPoints = publicObjCardPoints + publicObjDeck[i].getScore(player.getWindow());
        }

        privateObjCardPoints = player.getPrivateObj().getScore(player.getWindow());
        lostPoints = player.getWindow().getLostPoints();

        playerScore = publicObjCardPoints + privateObjCardPoints + player.getToken() - lostPoints;

        return playerScore;
    }


}
