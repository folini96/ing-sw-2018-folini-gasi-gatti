package it.polimi.se2018.classes.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.effects.EffectType;
import it.polimi.se2018.classes.model.effects.Exchange;
import it.polimi.se2018.classes.view.VirtualView;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;


/**
 * @author Alessandro Gatti
 * @author Andrea Folini
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

    /**
     * Constructor
     * @param view the view connected to the model of the game
     * @param publicObjDeck the public objective cards used in the game
     * @param privateObjDeck the private objective cards used in the game
     */
    public MatchHandlerModel(VirtualView view, PublicObjCard[] publicObjDeck, PrivateObjCard[] privateObjDeck, ToolCard[] toolDeck){
        addObserver(view);
        diceBag= new DiceBag();
        roundTrack=new Round[10];
        draftPool=new ArrayList<>();
        this.publicObjDeck= publicObjDeck;
        this.privateObjDeck=privateObjDeck;
        this.toolDeck=toolDeck;

    }

    /**
     * @return the tool cards in the game
     */
    public ToolCard[] getToolDeck(){
        return toolDeck;
    }

    /**
     * @return the list of the players
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * @param player the player that is to be added
     */
    public void addPlayer(Player player){
        players.add(player);
    }

    /**
     * @param playerNumber the number of the connected players
     * @param playerNames the list of the names of the players
     */
    public void prepareMatch(int playerNumber, ArrayList<String> playerNames){
        this.playerNumber=playerNumber;
        for (String name:playerNames){
            addPlayer(new Player(name,privateObjDeck[playerNames.indexOf(name)]));
        }
    }

    /**
     * the method that notifies the start of a new match
     */
    public void startMatch(){
        setChanged();
        notifyObservers(new StartMatchEvent(players, publicObjDeck, toolDeck));
    }

    /**
     * @param choseWindowEvent the event that represents the choice of a window card
     * @param windowSides the list of the window cards that are available
     */
    public void windowSelection(ArrayList<ChoseWindowEvent> choseWindowEvent, WindowSide[] windowSides){
        int eventPlayer;
        int i;
        for (i=0;i<playerNumber;i++){
            for (Player player:players){
                if (player.getName().equals(choseWindowEvent.get(i).getUsername())){
                    eventPlayer=players.indexOf(player);
                    player.setWindow(windowSides[(eventPlayer*4)+choseWindowEvent.get(i).getChosenWindow()]);
                }
            }
        }
    }

    /**
     * @param round the number of the round that is going to start
     * @param firstPlayer the number that represents the first player
     */
    public void startRound(int round, int firstPlayer){
        draftPool=diceBag.extractDice(playerNumber*2+1);
        setChanged();
        notifyObservers(new StartRoundEvent(round,players.get(firstPlayer).getName(), draftPool));
    }

    /**
     * @param currentPlayer the number of the player currently in control
     */
    public void startTurn(int currentPlayer){
        setChanged();
        notifyObservers(new StartTurnEvent(players.get(currentPlayer).getName()));
    }

    /**
     * @param round the number of the ending round
     */
    public void endRound(int round){
        setChanged();
        roundTrack[round]=new Round();
        roundTrack[round].setLeftDices(draftPool);
        notifyObservers(new EndRoundEvent(roundTrack));
    }

    /**
     * the method that notifies the end of the game
     */
    public void endMatch(){
        ArrayList<String> names=new ArrayList<>();
        ArrayList<Integer> points=new ArrayList<>();
        for (Player player:players){
            names.add(player.getName());
            points.add(calculateScore(player));
        }
        setChanged();
        notifyObservers(new EndMatchEvent(names,points));
    }

    /**
     * @param placeDiceEvent the event that represents the placement of a dice
     * @param currentPlayer the number of the player currently in control
     */
    public void placeDice(PlaceDiceEvent placeDiceEvent, int currentPlayer){
        int row=placeDiceEvent.getRow();
        int column=placeDiceEvent.getColumn();
        int draftDice=placeDiceEvent.getDraftDice();
        players.get(currentPlayer).getWindow().getBoxScheme()[row][column].setDice(draftPool.get(draftDice));
        draftPool.remove(draftDice);
        setChanged();
        notifyObservers(new ModifiedDraftEvent(draftPool));
        setChanged();
        notifyObservers(new ModifiedWindowEvent(players.get(currentPlayer).getName(), players.get(currentPlayer).getWindow()));
    }
    public boolean checkUseSecondTurn(int toolCard){
        if (toolDeck[toolCard].getName().equals("Martelletto")){
            return true;
        }
        return false;
    }
    public boolean checkUseFirstTurn(int toolCard){
        if (toolDeck[toolCard].getName().equals("Tenaglia a Rotelle")){
            return true;
        }
        return false;
    }
    public boolean checkBeforePlacing(int toolCard){
        return toolDeck[toolCard].getBlockedAfterPlacement();
    }
    public boolean checkNotUseWithEmptyDiceBag(int toolCard){
        if (diceBag.getDiceSet().size()==0){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkNotUseWithEmptyWindow(int toolCard, int currentPlayer){
        if ((players.get(currentPlayer).getWindow().isEmpty())&&(toolDeck[toolCard].getEffect().toString().equals("Move"))){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkNotUseWithEmptyRoundTrack(int toolCard){
        if (((toolDeck[toolCard].getName().equals("Taglierina circolare"))||(toolDeck[toolCard].getName().equals("Taglierina Manuale")))&&(isEmptyRoundTrack())){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkNoVicinityBound(int toolCard){
        if ((toolDeck[toolCard].getName()).equals("Riga in Sughero")){
            return true;
        }else{
            return false;
        }
    }
    private boolean isEmptyRoundTrack(){
        if (roundTrack[0]==null){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkEnoughToken(int toolCard, int currentPlayer){
        int currentToken=players.get(currentPlayer).getToken();
        int toolCardToken=toolDeck[toolCard].getToken();
        if (toolCardToken>0){
            if (currentToken<2){
                return false;
            }else{
                players.get(currentPlayer).setToken(currentToken-2);
                toolDeck[toolCard].setToken(toolCardToken+2);
                return true;
            }
        }else {
            if (currentToken<1){
                return false;
            }else{
                players.get(currentPlayer).setToken(currentToken-1);
                toolDeck[toolCard].setToken(toolCardToken+1);
                return true;
            }
        }
    }
    public void sendEffect(int toolCard,int currenPlayer){
        setChanged();
        notifyObservers(new SendEffectEvent(toolDeck[toolCard].getEffect(),players.get(currenPlayer).getName()));
        setChanged();
        notifyObservers(new ModifiedTokenEvent(players.get(currenPlayer).getName(),players.get(currenPlayer).getToken(),toolCard,toolDeck[toolCard].getToken()));
    }
    /**
     * @param placeDiceEvent the number of the dice of the draft and the coordinate of the box the player wants to put the dice into
     * @param currentPlayer the number of the player currently in control
     * @return true if the placement is allowed, false if not
     */
    public boolean checkCorrectPlacement(PlaceDiceEvent placeDiceEvent, int currentPlayer,boolean noVicinityBound){
        Dice dice=draftPool.get(placeDiceEvent.getDraftDice());

        if(players.get(currentPlayer).getWindow().isEmpty()){
            if(!checkCorrectFirstMove(placeDiceEvent.getRow(),placeDiceEvent.getColumn())) return false;
            if(!checkCorrectColorMatching(dice, placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkCorrectValueMatching(dice, placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            return true;
        }
        else{
            if(!checkCorrectColorMatching(dice, placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkCorrectValueMatching(dice, placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkBoxNotEmpty(placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkColorVicinity(dice, placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkValueVicinity(dice, placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if (!noVicinityBound){
                if(!checkDiceVicinity(placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            }

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
     * @param dice the dice that is to be set
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the number of the player currently in control
     * @return true if the color of the box is compatible, false if not
     */
    private boolean checkCorrectColorMatching(Dice dice, int selectedRow, int selectedColumn, int currentPlayer){
        int row=selectedRow;
        int column=selectedColumn;
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
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the number of the player currently in control
     * @return true if the value of the box is compatible, false if not
     */
    private boolean checkCorrectValueMatching(Dice dice, int selectedRow, int selectedColumn, int currentPlayer){
        int row=selectedRow;
        int column=selectedColumn;
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
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the number of the player currently in control
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

    /**
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the number of the player currently in control
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
     * @param dice the dice that is to set
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the number of the player currently in control
     * @return true if the dice isn't adjacent to a dice of same color, false if not
     */
    private boolean checkColorVicinity(Dice dice, int selectedRow, int selectedColumn, int currentPlayer){
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
     * @param selectedRow the row of the box the player wants to put the dice into
     * @param selectedColumn the column of the box the player wants to put the dice into
     * @param currentPlayer the number of the player currently in control
     * @return true if the dice isn't adjacent to a dice of same value, false if not
     */
    private boolean checkValueVicinity(Dice dice, int selectedRow, int selectedColumn, int currentPlayer){
        int i, j;
        int row= selectedRow;
        int column= selectedColumn;
        Box[][] boxScheme;

        WindowSide window = players.get(currentPlayer).getWindow();
        boxScheme = window.getBoxScheme();

        for(i=0; i<=3; i++) {
            for (j=0; j<=4; j++) {
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
     * @param moveDiceEvent the event that represents the move of a dice according to a tool card
     * @param currentPlayer the number of the player currently in control
     * @return true if the move is allowed by the second tool card rules, false if not
     */
    public boolean checkCorrectSecondToolCardMove(MoveDiceEvent moveDiceEvent, int currentPlayer){
        Dice dice=players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].getDice();
        if(dice==null) return false;
        if(!checkCorrectValueMatching(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        if(!checkBoxNotEmpty(moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        if(!checkColorVicinity(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        if(!checkValueVicinity(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        if(!checkDiceVicinity(moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        return true;
    }

    /**
     * @param moveDiceEvent the event that represents the move of a dice according to a tool card
     * @param currentPlayer the number of the player currently in control
     * @return true if the move is allowed by the third tool card rules, false if not
     */
    public boolean checkCorrectThirdToolCardMove(MoveDiceEvent moveDiceEvent, int currentPlayer){
        Dice dice=players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].getDice();
        if(dice==null) return false;
        if(!checkCorrectColorMatching(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        if(!checkBoxNotEmpty(moveDiceEvent.getNewRow(),moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        if(!checkColorVicinity(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        if(!checkValueVicinity(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        if(!checkDiceVicinity(moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) return false;
        return true;
    }

    /**
     * @param moveDiceEvent the event that represents the move of a dice according to a tool card
     * @param currentPlayer the number of the player currently in control
     * @return true if the move is allowed, false if not
     */
    public boolean checkCorrectMove(MoveDiceEvent moveDiceEvent, int currentPlayer, int toolCard){
        Dice dice=players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].getDice();
        players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(null);
        if ((toolDeck[toolCard].getEffect().getEffectType()==EffectType.MOVETWODICESELECTEDCOLOR)&&(dice.getColor()!=roundTrack[moveDiceEvent.getRound()].getLeftDices().get(moveDiceEvent.getDiceInRound()).getColor())){
            players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(dice);
            return false;
        }

        if(dice==null) return false;
        if (toolDeck[toolCard].getEffect().getEffectType()!=EffectType.NOCOLORBOUND){
            if(!checkCorrectColorMatching(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) {
                players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(dice);
                return false;
            }
        }
        if (toolDeck[toolCard].getEffect().getEffectType()!=EffectType.NOVALUEBOUND){
            if(!checkCorrectValueMatching(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) {
                players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(dice);
                return false;
            }
        }
        if(players.get(currentPlayer).getWindow().isEmpty()){
            if(!checkCorrectFirstMove(moveDiceEvent.getNewRow(),moveDiceEvent.getNewColumn())) {
                players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(dice);
                return false;
            }
        }else{
            if(!checkBoxNotEmpty(moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) {
                players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(dice);
                return false;
            }
            if(!checkColorVicinity(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) {
                players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(dice);
                return false;
            }
            if(!checkValueVicinity(dice, moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) {
                players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(dice);
                return false;
            }
            if(!checkDiceVicinity(moveDiceEvent.getNewRow(), moveDiceEvent.getNewColumn(), currentPlayer)) {
                players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(dice);
                return false;
            }
        }
        players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent.getDiceRow()][moveDiceEvent.getDiceColumn()].setDice(dice);
        return true;
    }

    public boolean checkSameColorMove(MoveDiceEvent moveDiceEvent1, MoveDiceEvent moveDiceEvent2, int currentPlayer){
        int i, j, c=0;
        ArrayList<Dice> leftDices;
        Dice dice1=players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent1.getDiceRow()][moveDiceEvent1.getDiceColumn()].getDice();
        Dice dice2=players.get(currentPlayer).getWindow().getBoxScheme()[moveDiceEvent2.getDiceRow()][moveDiceEvent2.getDiceColumn()].getDice();
        if(dice1.getColor()!=dice2.getColor()) return false;

        for(i=0; i<10; i++){
            leftDices=roundTrack[i].getLeftDices();
            for(j=0; j<leftDices.size(); j++){
                if(leftDices.get(j).getColor() == dice1.getColor()){
                    c++;
                }
            }
        }
        if(c==0)return false;
        return true;
    }

    public boolean checkNotDiceVicinity(int selectedRow, int selectedColumn, int currentPlayer){
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
        if(vicinityCheck>0){
            return false;
        }

        return true;
    }


    public boolean checkCorrectNinthToolCardPlacement(PlaceDiceEvent placeDiceEvent, int currentPlayer){
        Dice dice=draftPool.get(placeDiceEvent.getDraftDice());

        if(players.get(currentPlayer).getWindow().isEmpty()){
            if(!checkCorrectFirstMove(placeDiceEvent.getRow(),placeDiceEvent.getColumn())) return false;
            if(!checkCorrectColorMatching(dice, placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            if(!checkCorrectValueMatching(dice, placeDiceEvent.getRow(),placeDiceEvent.getColumn(), currentPlayer)) return false;
            return true;
        }
        else {
            if (!checkCorrectColorMatching(dice, placeDiceEvent.getRow(), placeDiceEvent.getColumn(), currentPlayer)) return false;
            if (!checkCorrectValueMatching(dice, placeDiceEvent.getRow(), placeDiceEvent.getColumn(), currentPlayer)) return false;
            if (!checkBoxNotEmpty(placeDiceEvent.getRow(), placeDiceEvent.getColumn(), currentPlayer)) return false;
            if (!checkNotDiceVicinity(placeDiceEvent.getRow(), placeDiceEvent.getColumn(), currentPlayer)) return false;
            return true;
        }
    }
    public boolean modifyDice (ModifyDiceEvent modifyDiceEvent,int toolCard){
        switch (toolDeck[toolCard].getEffect().getEffectType()){
            case UPORDOWNVALUEMODIFY:
                if(!upOrDownValue(draftPool.get(modifyDiceEvent.getDraftDice()),modifyDiceEvent.getUpOrDown())){
                    return false;
                }
                break;
            case NEWRANDOMVALUEMODIFY:
                getNewRandomValue(draftPool.get(modifyDiceEvent.getDraftDice()));
                break;
            case ROTATEDICEMODIFY:
                rotateDice(draftPool.get(modifyDiceEvent.getDraftDice()));
        }
        setChanged();
        notifyObservers(new ModifiedDraftEvent(draftPool));
        return true;
    }

    public void rotateDice(Dice dice){
        switch (dice.getValue()){
            case 1:
                dice.setValue(6);
                break;
            case 2:
                dice.setValue(5);
                break;
            case 3:
                dice.setValue(4);
                break;
            case 4:
                dice.setValue(3);
                break;
            case 5:
                dice.setValue(2);
                break;
            case 6:
                dice.setValue(1);
                break;
        }
    }

    public void getNewRandomValue(Dice dice){
        dice.getRandomValue();
    }

    public boolean upOrDownValue(Dice dice, int upOrDown){
        if(upOrDown==1){
            System.out.println(upOrDown);
            System.out.println(dice.getValue());
            if(dice.getValue() < 6){
                dice.setValue(dice.getValue()+1);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            System.out.println(dice.getValue());
            if(dice.getValue()>1){
                dice.setValue(dice.getValue()-1);
                return true;
            }
            else{
                return false;
            }
        }

    }

    public void moveDice(MoveDiceEvent moveDice, int currentPlayer){
        Dice dice = players.get(currentPlayer).getWindow().getBoxScheme()[moveDice.getDiceRow()][moveDice.getDiceColumn()].getDice();
        players.get(currentPlayer).getWindow().getBoxScheme()[moveDice.getNewRow()][moveDice.getNewColumn()].setDice(dice);
        players.get(currentPlayer).getWindow().getBoxScheme()[moveDice.getDiceRow()][moveDice.getDiceColumn()].setDice(null);
        setChanged();
        notifyObservers(new ModifiedWindowEvent(players.get(currentPlayer).getName(), players.get(currentPlayer).getWindow()));
    }

    public void rerollDraftPool(){
        for(Dice dice:draftPool){
            dice.getRandomValue();
        }
        setChanged();
        notifyObservers(new ModifiedDraftEvent(draftPool));
    }



    public void exchange(int toolCard,ExchangeEvent exchangeEvent, int currentPlayer){
        if (toolDeck[toolCard].getEffect().getEffectType().equals(EffectType.DRAFTPOOLBAGEXCHANGE)){
            exchangeDraftPoolDiceBag(exchangeEvent,currentPlayer);
        }else{
            exchangeDraftPoolRoundTrack(exchangeEvent);
        }
    }

    private void exchangeDraftPoolRoundTrack(ExchangeEvent exchangeEvent){
        Color draftDiceColor=draftPool.get(exchangeEvent.getDraftDice()).getColor();
        int draftDiceValue=draftPool.get(exchangeEvent.getDraftDice()).getValue();
        Dice roundTrackDice=roundTrack[exchangeEvent.getRoundNumber()].getLeftDices().get(exchangeEvent.getDiceInRound());
        draftPool.get(exchangeEvent.getDraftDice()).setValue(roundTrackDice.getValue());
        draftPool.get(exchangeEvent.getDraftDice()).setColor(roundTrackDice.getColor());
        roundTrackDice.setColor(draftDiceColor);
        roundTrackDice.setValue(draftDiceValue);
        setChanged();
        notifyObservers(new ModifiedRoundTrack(roundTrack));
        setChanged();
        notifyObservers(new ModifiedDraftEvent(draftPool));
    }
    private void exchangeDraftPoolDiceBag(ExchangeEvent exchangeEvent,int currentPlayer){
        Dice draftDice=draftPool.get(exchangeEvent.getDraftDice());
        Dice newDice;
        diceBag.getDiceSet().add(new Dice(draftDice.getColor()));
        newDice=diceBag.extractDice(1).get(0);
        draftDice.setColor(newDice.getColor());
        setChanged();
        notifyObservers(new NewDiceFromBagEvent(newDice,players.get(currentPlayer).getName()));
    }
    public void setValueDiceFromBag(SetValueEvent setValueEvent, int modifiedDraftDice){
        draftPool.get(modifiedDraftDice).setValue(setValueEvent.getNewValue());
        setChanged();
        notifyObservers(new ModifiedDraftEvent(draftPool));
    }
    public void sendReconnectionUpdate(String reconnectingClient){
        setChanged();
        notifyObservers(new UpdateReconnectedClientEvent(reconnectingClient,players,roundTrack,toolDeck,draftPool));
    }
    /**
     * @param player the player the score is calculated for
     * @return the score
     */
    private int calculateScore(Player player){
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
