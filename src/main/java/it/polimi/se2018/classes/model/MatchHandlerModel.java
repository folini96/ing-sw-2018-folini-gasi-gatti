package it.polimi.se2018.classes.model;

import it.polimi.se2018.classes.Events.SelectedCoordinate;

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
     * @return if the placement is allowed or not
     */
    public boolean checkCorrectMove(Dice dice, SelectedCoordinate coordinate, Player player){

        int i, j, prosimityCheck=0;
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = player.getSide();
        boxScheme = window.getBoxScheme();

        //checking that a dice is set in a box that matches color or value
        if(boxScheme[row][column].isBlank() == false){
            if(boxScheme[row][column].isColor()  && dice.getColor() != boxScheme[row][column].getColor()){
                return false;
            }
            if(boxScheme[row][column].isValue()  && dice.getValue() != boxScheme[row][column].getValue()){
                return false;
            }
        }

        //checking that a dice is placed near another dice
        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                if(i==row-1 || i==row || i==row+1){
                    if(j==column-1 || j==column || j==column+1){
                        if(boxScheme[i][j].getDice()!=null){
                            prosimityCheck++;
                        }
                    }
                }
            }
        }
        if(prosimityCheck==0){
            return false;
        }

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
        int privateObjCardPoints, tokenPoints, lostPoints, playerScore;

        for(i=0; i<=2; i++){
            publicObjCardPoints = publicObjCardPoints + publicObjDeck[i].getScore(player.getSide());
        }

        privateObjCardPoints = player.getPrivateObj().getScore(player.getSide());
        tokenPoints = player.getToken();
        lostPoints = player.getSide().getLostPoints();

        playerScore = publicObjCardPoints + privateObjCardPoints + tokenPoints - lostPoints;

        return playerScore;
    }


}
