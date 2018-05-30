package it.polimi.se2018.classes.model;

import it.polimi.se2018.classes.eventi.SelectedCoordinate;

import java.util.ArrayList;

/**
 * @author Alessandro Gatti
 */
public class MatchHandlerModel {

    private ArrayList <Player> players;
    private int playerNumber;
    private int currentPlayer;
    private ToolCard[] toolDeck;
    private PublicObjCard[] publicObjDeck;
    //private PrivateObjCard[] privateObjDeck;
    private DiceBag diceBag;
    private Round[] roundTrack;
    private int round;
    private int firstPlayer;
    private ArrayList <Dice> draftPool;


    public void startRound(){


    }

    public void endRound(){


    }

    /**
     * @param dice the dice that is to set
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @param player the player making the move
     * @return true if the placement is allowed, false if not
     */
    public boolean checkCorrectMove(Dice dice, SelectedCoordinate coordinate, Player player){

        int i, j;
        int countDice=0;
        Box[][] boxScheme;

        WindowSide window = player.getSide();
        boxScheme = window.getBoxScheme();

        //checking if window is empty
        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++) {
                if(boxScheme[i][j].getDice() != null){
                    countDice++;
                }
            }
        }
        if(countDice ==0){
            if(!checkCorrectFirstMove(coordinate)) return false;
            if(!checkCorrectColorMatching(dice, coordinate, player)) return false;
            if(!checkCorrectValueMatching(dice, coordinate, player)) return false;
            return true;
        }
        else{
            if(!checkCorrectColorMatching(dice, coordinate, player)) return false;
            if(!checkCorrectValueMatching(dice, coordinate, player)) return false;
            if(!checkBoxNotEmpty(coordinate, player)) return false;
            if(!checkColorOrValueVicinity(dice, coordinate, player)) return false;
            if(!checkDiceVicinity(coordinate, player)) return false;
            return true;
        }
    }

    /**
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @return true if the coordinates of the first dice are allowed, false if not
     */
    private boolean checkCorrectFirstMove(SelectedCoordinate coordinate){
        int row=coordinate.getRow();
        int column=coordinate.getColumn();

        //checking that the first dice is placed on the edge of the window
        if(row ==1 || row == 2){
            return false;
        }
        if(column ==1 || column == 2 || column ==3){
            return false;
        }

        return true;
    }

    /**
     * @param dice the dice that is to set
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @param player the player making the move
     * @return true if the color of the box is compatible, false if not
     */
    private boolean checkCorrectColorMatching(Dice dice, SelectedCoordinate coordinate, Player player){
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = player.getSide();
        boxScheme = window.getBoxScheme();

        //checking that a dice is set in a box that matches color or value
        if(!boxScheme[row][column].isBlank()){
            if(boxScheme[row][column].isColor()  && dice.getColor() != boxScheme[row][column].getColor()){
                return false;
            }
        }

        return true;
    }

    /**
     * @param dice the dice that is to set
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @param player the player making the move
     * @return true if the value of the box is compatible, false if not
     */
    private boolean checkCorrectValueMatching(Dice dice, SelectedCoordinate coordinate, Player player){
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = player.getSide();
        boxScheme = window.getBoxScheme();

        //checking that a dice is set in a box that matches color or value
        if(!boxScheme[row][column].isBlank()){
            if(boxScheme[row][column].isValue()  && dice.getValue() != boxScheme[row][column].getValue()){
                return false;
            }
        }

        return true;
    }

    /**
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @param player the player making the move
     * @return true if the box is empty, false if not
     */
    private boolean checkBoxNotEmpty(SelectedCoordinate coordinate, Player player){
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = player.getSide();
        boxScheme = window.getBoxScheme();

        //checking that a dice isn't placed in a box that already has a dice
        if(boxScheme[row][column].getDice() != null){
            return false;
        }

        return true;
    }

    /**
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @param player the player making the move
     * @return true if the dice is near another dice, false if not
     */
    private boolean checkDiceVicinity(SelectedCoordinate coordinate, Player player){
        int i, j, vicinityCheck=0;
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = player.getSide();
        boxScheme = window.getBoxScheme();

        //checking that a dice is placed near another dice
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
     * @param dice the dice that is to set
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @param player the player making the move
     * @return true if the dice isn't adjacent to a dice of same color or value, false if not
     */
    private boolean checkColorOrValueVicinity(Dice dice, SelectedCoordinate coordinate, Player player){
        int i, j;
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = player.getSide();
        boxScheme = window.getBoxScheme();

        //checking that a dice isn't placed adjacent to a dice of the same color or value
        for(i=0; i<=3; i++) {
            for (j = 0; j <= 4; j++) {
                if(i==row){
                    if(j==column-1 || j==column+1){
                        if(boxScheme[i][j].getDice().getColor() == dice.getColor() ||
                                boxScheme[i][j].getDice().getValue() == dice.getValue()){
                            return false;
                        }
                    }
                }
                if(j==column){
                    if(i==row-1 || i==row+1){
                        if(boxScheme[i][j].getDice().getColor() == dice.getColor() ||
                                boxScheme[i][j].getDice().getValue() == dice.getValue()){
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
            publicObjCardPoints = publicObjCardPoints + publicObjDeck[i].getScore(player.getSide());
        }

        privateObjCardPoints = player.getPrivateObj().getScore(player.getSide());
        lostPoints = player.getSide().getLostPoints();

        playerScore = publicObjCardPoints + privateObjCardPoints + player.getToken() - lostPoints;

        return playerScore;
    }


}
