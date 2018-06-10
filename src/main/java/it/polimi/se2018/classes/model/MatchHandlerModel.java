package it.polimi.se2018.classes.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.se2018.classes.events.SelectedCoordinate;
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
    private int currentPlayer=0;
    private ToolCard[] toolDeck;
    private PublicObjCard[] publicObjDeck;
    //private PrivateObjCard[] privateObjDeck;
    private DiceBag diceBag;
    private Round[] roundTrack;
    private int round;
    private int firstPlayer;
    private ArrayList <Dice> draftPool;

    public MatchHandlerModel(VirtualView view){
        addObserver(view);
    }

    public MatchHandlerModel(){

    }
    public WindowSide[] parseWindowSide(){
        int i;
        int j;
        int randomInt;
        WindowSide[] windows = new WindowSide[24];
        WindowSide[] chosenWindows = new WindowSide[playerNumber*4];
        ArrayList<Integer> windowNotAvailable=new ArrayList<>();
        JsonParser windowParser=new JsonParser();
        try{
            JsonArray sides=(JsonArray) windowParser.parse (new FileReader("sagrada\\JSON\\Windows.json"));
            int currentWindow=0;
            for (Object o: sides){
                JsonObject side = (JsonObject) o;
                String name = side.get("name").getAsString();
                int difficult = side.get("difficult").getAsInt();
                JsonArray scheme = side.get("boxScheme").getAsJsonArray();
                Box[][] boxScheme = new Box[4][5];
                for (Object b: scheme){
                    JsonObject box = (JsonObject) b;
                    String color = box.get("color").getAsString();
                    int value = box.get("value").getAsInt();
                    boxScheme[box.get("row").getAsInt()][box.get("column").getAsInt()]= new Box(color,value);
                }
                windows[currentWindow]=new WindowSide(name,difficult,boxScheme);
            }
        }catch(FileNotFoundException e){
            System.out.println("File JSON non trovato");
        }
        for (i=0; i<playerNumber;i++){
            for(j=0;j<2;j++){
                do{
                    Random random = new Random();
                    randomInt = random.nextInt(11);
                }while (windowNotAvailable.contains(randomInt));
                windowNotAvailable.add(randomInt);
                chosenWindows[(i*4)+(j*2)]=windows[randomInt*2];
                chosenWindows[((i*4)+(j*2))+1]=windows[(randomInt*2)+1];
            }
        }
        return chosenWindows;
    }
    public void addPlayer(Player player){
        players.add(player);
    }
    public void prepareMatch(int playerNumber, String[] playerNames, PublicObjCard[] publicObjCards, ToolCard[] toolCards, PrivateObjCard[] privateObjCards, WindowSide[] playerWindows){
        int i;
        this.playerNumber=playerNumber;
        diceBag= new DiceBag();
        roundTrack=new Round[10];
        round=0;
        draftPool=new ArrayList<>();

        for (i=0; i<this.playerNumber;i++){
           addPlayer(new Player(playerNames[i], privateObjCards[i], playerWindows[i]));
        }
        publicObjDeck=publicObjCards;
        toolDeck=toolCards;
        for (PublicObjCard publicObjCard:publicObjDeck){
            notifyObservers(publicObjCard);
        }
        for (ToolCard toolCard:toolDeck){
            notifyObservers(toolCard);
        }
        for (Player player:players){
            notifyObservers(player);
        }

    }

    public void startRound(){

    }

    public void endRound(){


    }

    /**
     * @param dice the dice that is to set
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @return true if the placement is allowed, false if not
     */
    public boolean checkCorrectMove(Dice dice, SelectedCoordinate coordinate){

        if(players.get(currentPlayer).getWindow().isEmpty()){
            if(!checkCorrectFirstMove(coordinate)) return false;
            if(!checkCorrectColorMatching(dice, coordinate)) return false;
            if(!checkCorrectValueMatching(dice, coordinate)) return false;
            return true;
        }
        else{
            if(!checkCorrectColorMatching(dice, coordinate)) return false;
            if(!checkCorrectValueMatching(dice, coordinate)) return false;
            if(!checkBoxNotEmpty(coordinate)) return false;
            if(!checkColorVicinity(dice, coordinate)) return false;
            if(!checkValueVicinity(dice, coordinate)) return false;
            if(!checkDiceVicinity(coordinate)) return false;
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

        if((row ==1 || row == 2) && (column ==1 || column == 2 || column ==3) ){
            return false;
        }

        return true;
    }

    /**
     * @param dice the dice that is to set
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @return true if the color of the box is compatible, false if not
     */
    private boolean checkCorrectColorMatching(Dice dice, SelectedCoordinate coordinate){
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        if(boxScheme[row][column].getColor()!=null) {
            if(dice.getColor() != boxScheme[row][column].getColor()){
                return false;
            }
        }

        return true;
    }

    /**
     * @param dice the dice that is to set
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @return true if the value of the box is compatible, false if not
     */
    private boolean checkCorrectValueMatching(Dice dice, SelectedCoordinate coordinate){
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        if(boxScheme[row][column].getValue()!=0){
            if(dice.getValue() != boxScheme[row][column].getValue()){
                return false;
            }
        }

        return true;
    }

    /**
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @return true if the box is empty, false if not
     */
    private boolean checkBoxNotEmpty(SelectedCoordinate coordinate){
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        if(boxScheme[row][column].getDice() != null){
            return false;
        }

        return true;
    }

    /**
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @return true if the dice is near another dice, false if not
     */
    private boolean checkDiceVicinity(SelectedCoordinate coordinate){
        int i, j, vicinityCheck=0;
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
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
     * @param dice the dice that is to set
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @return true if the dice isn't adjacent to a dice of same color, false if not
     */
    private boolean checkColorVicinity(Dice dice, SelectedCoordinate coordinate){
        int i, j;
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        for(i=0; i<=3; i++) {
            for (j = 0; j <= 4; j++) {
                if(i==row){
                    if(j==column-1 || j==column+1){
                        if(boxScheme[i][j].getDice() != null && boxScheme[i][j].getDice().getColor() == dice.getColor()){
                            return false;
                        }
                    }
                }
                if(j==column){
                    if(i==row-1 || i==row+1){
                        if(boxScheme[i][j].getDice() != null && boxScheme[i][j].getDice().getColor() == dice.getColor()){
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * @param dice the dice that is to set
     * @param coordinate the coordinate of the box the player wants to put the dice into
     * @return true if the dice isn't adjacent to a dice of same value, false if not
     */
    private boolean checkValueVicinity(Dice dice, SelectedCoordinate coordinate){
        int i, j;
        int row=coordinate.getRow();
        int column=coordinate.getColumn();
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        for(i=0; i<=3; i++) {
            for (j = 0; j <= 4; j++) {
                if(i==row){
                    if(j==column-1 || j==column+1){
                        if(boxScheme[i][j].getDice() != null && boxScheme[i][j].getDice().getValue() == dice.getValue()){
                            return false;
                        }
                    }
                }
                if(j==column){
                    if(i==row-1 || i==row+1){
                        if(boxScheme[i][j].getDice() != null && boxScheme[i][j].getDice().getValue() == dice.getValue()){
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
