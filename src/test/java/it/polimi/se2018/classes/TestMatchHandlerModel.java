package it.polimi.se2018.classes;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.model.effects.*;
import it.polimi.se2018.classes.network.Server;
import it.polimi.se2018.classes.view.VirtualView;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Alessandro Gatti
 */
public class TestMatchHandlerModel {
    private Server serverTest = new Server();
    private VirtualView viewTest = new VirtualView(serverTest, 1);
    private MatchHandlerModel modelTest;

    private PublicObjCard[] publicObjDeck = {new PublicObjCardColoriDiversiColonna(), new PublicObjCardColoriDiversiRiga(),
            new PublicObjCardSfumatureChiare()};
    private PrivateObjCard[] privateObjDeck = {new PrivateObjCard(Color.ROSSO), new PrivateObjCard(Color.BLU)};
    private ToolCardsEffectsInterface effect1= new Move(EffectType.NOCOLORBOUND);
    private ToolCardsEffectsInterface effect2= new Move(EffectType.NOVALUEBOUND);
    private ToolCardsEffectsInterface effect3= new Move(EffectType.MOVETWODICESELECTEDCOLOR);
    private ToolCardsEffectsInterface effect4= new Modify(EffectType.UPORDOWNVALUEMODIFY);
    private ToolCardsEffectsInterface effect5= new Modify(EffectType.NEWRANDOMVALUEMODIFY);
    private ToolCardsEffectsInterface effect6= new Modify(EffectType.ROTATEDICEMODIFY);
    private ToolCardsEffectsInterface effect7= new Exchange(EffectType.DRAFTPOOLROUNDTRACKEXCHANGE);
    private ToolCard[] toolDeck = {new ToolCard("Pennello per Eglomise", 2, 3, Color.ROSSO, effect1, false),
            new ToolCard("Alesatore per lamina di rame", 3, 3, Color.ROSSO, effect2, false),
            new ToolCard("Taglierina Manuale", 12, 3, Color.ROSSO, effect3, false)};
    private ToolCard[] toolDeck2 = {new ToolCard("Pinza Sgrossatrice", 1, 3, Color.ROSSO, effect4, true),
            new ToolCard("Pennello per Pasta Salda", 6, 3, Color.ROSSO, effect5, true),
            new ToolCard("Tampone Diamantato", 10, 3, Color.ROSSO, effect6, true)};
    private ToolCard[] toolDeck3 = {new ToolCard("Taglierina circolare", 5, 3, Color.ROSSO, effect7, true),
            new ToolCard("test", 0, 3, Color.ROSSO, effect1, true),
            new ToolCard("test", 0, 3, Color.ROSSO, effect1, true)};

    private ArrayList<String> playerNames = new ArrayList<String>();
    private ArrayList<Dice> leftDicesTest;
    int playerNumber = 2;
    private ArrayList<Player> players = new ArrayList<>();
    private WindowSide windowTest;
    private Box[][] boxSchemeTest = new Box[4][5];
    private Dice dice;
    private Dice dice1;
    private Dice dice2;
    private Dice dice3;
    private Dice dice4;
    private Dice dice5;
    private PlaceDiceEvent placeDiceEvent;
    private MoveDiceEvent moveDiceEvent;
    private ModifyDiceEvent modifyDiceEvent;
    private ExchangeEvent exchangeEvent;
    private SetValueEvent setValueEvent;

    public void prepareTest() {
        playerNames.clear();
        playerNames.add("prova1");
        playerNames.add("prova2");
        modelTest.prepareMatch(playerNumber, playerNames);
    }

    /**
     * to verify that the first placement of the dice is handled right
     */
    @Test
    public void checkCorrectFirstMoveTrue() {
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.ROSSO);
        modelTest.getDraftPool().add(dice);
        placeDiceEvent = new PlaceDiceEvent(0, 0, 4);

        for (i = 0; i <= 3; i++) {
            for (j = 0; j <= 4; j++) {
                boxSchemeTest[i][j] = new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertTrue(modelTest.checkCorrectPlacement(placeDiceEvent, 0, false));
    }

    /**
     * to verify that the first dice can't be placed in an incorrect position
     */
    @Test
    public void checkCorrectFirstMoveFalse() {
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.ROSSO);
        modelTest.getDraftPool().add(dice);
        placeDiceEvent = new PlaceDiceEvent(0, 1, 3);

        for (i = 0; i <= 3; i++) {
            for (j = 0; j <= 4; j++) {
                boxSchemeTest[i][j] = new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectPlacement(placeDiceEvent, 0, false));
    }

    /**
     * to verify that the placement of the dice is handled right
     */
    @Test
    public void checkCorrectPlacementTrue() {
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);
        modelTest.getDraftPool().add(dice4);
        placeDiceEvent = new PlaceDiceEvent(0, 1, 3);

        for (i = 0; i <= 3; i++) {
            for (j = 0; j <= 4; j++) {
                boxSchemeTest[i][j] = new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[1][4] = new Box(null, 3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertTrue(modelTest.checkCorrectPlacement(placeDiceEvent, 0, false));
    }

    /**
     * to verify that the dice can't be placed in a color box with a different color
     */
    @Test
    public void checkCorrectPlacementColorFalse() {
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.ROSSO);
        dice4.setValue(6);
        modelTest.getDraftPool().add(dice4);
        placeDiceEvent = new PlaceDiceEvent(0, 1, 3);

        for (i = 0; i <= 3; i++) {
            for (j = 0; j <= 4; j++) {
                boxSchemeTest[i][j] = new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[0][4] = new Box(null, 3);
        boxSchemeTest[0][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectPlacement(placeDiceEvent, 0, false));
    }

    /**
     * to verify that the dice can't be placed in a box that already contains a dice
     */
    @Test
    public void checkCorrectPlacementNotEmptyBoxFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.VERDE);
        dice4.setValue(2);
        modelTest.getDraftPool().add(dice4);
        placeDiceEvent = new PlaceDiceEvent(0, 0, 3);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[0][4] = new Box(null,3);
        boxSchemeTest[0][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectPlacement(placeDiceEvent, 0, false));
    }

    /**
     * to verify that the dice can't be placed near a dice of the same color
     */
    @Test
    public void checkCorrectPlacementColorVicinityFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.VERDE);
        dice4.setValue(6);
        modelTest.getDraftPool().add(dice4);
        placeDiceEvent = new PlaceDiceEvent(0, 1, 3);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[0][4] = new Box(null,3);
        boxSchemeTest[0][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.VERDE, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectPlacement(placeDiceEvent, 0, false));
    }

    /**
     * to verify that the dice can't be placed separated from the other dices
     */
    @Test
    public void checkCorrectPlacementDiceVicinityFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);
        modelTest.getDraftPool().add(dice4);
        placeDiceEvent = new PlaceDiceEvent(0, 2, 2);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[0][4] = new Box(null,3);
        boxSchemeTest[0][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectPlacement(placeDiceEvent, 0, false));
    }

    @Test
    public void checkCorrectNoColorBoundMove(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);
        moveDiceEvent = new MoveDiceEvent(1, 4, 1,2,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[1][2] = new Box(Color.VERDE, 0);
        boxSchemeTest[3][1] = new Box(null, 4);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertTrue(modelTest.checkCorrectMove(moveDiceEvent, 0, 0));
    }

    @Test
    public void checkCorrectNoValueBoundMove(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);
        moveDiceEvent = new MoveDiceEvent(1, 4, 1,2,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[1][2] = new Box(null, 1);
        boxSchemeTest[3][1] = new Box(null, 4);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertTrue(modelTest.checkCorrectMove(moveDiceEvent, 0, 1));
    }

    @Test
    public void checkCorrectEmptyMove(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);
        moveDiceEvent = new MoveDiceEvent(1, 4, 0,2,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[1][2] = new Box(null, 1);
        boxSchemeTest[3][1] = new Box(null, 4);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertTrue(modelTest.checkCorrectMove(moveDiceEvent, 0, 1));
    }

    @Test
    public void checkCorrectEmptyMoveFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);
        moveDiceEvent = new MoveDiceEvent(1, 4, 1,2,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[1][2] = new Box(null, 1);
        boxSchemeTest[3][1] = new Box(null, 4);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectMove(moveDiceEvent, 0, 1));
    }

    @Test
    public void checkCorrectNoValueBoundMoveFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);
        moveDiceEvent = new MoveDiceEvent(1, 4, 1,2,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[1][2] = new Box(Color.VERDE, 0);
        boxSchemeTest[3][1] = new Box(null, 4);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectMove(moveDiceEvent, 0, 1));
    }

    @Test
    public void checkCorrectNotEmptyBoxMoveFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.GIALLO);
        dice4.setValue(6);
        moveDiceEvent = new MoveDiceEvent(1, 4, 1,2,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[1][2] = new Box(Color.GIALLO, 0);
        boxSchemeTest[1][2].setDice(dice4);
        boxSchemeTest[3][1] = new Box(null, 4);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectMove(moveDiceEvent, 0, 1));
    }

    @Test
    public void checkCorrectColorVicinityMoveFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.GIALLO);
        dice4.setValue(6);
        moveDiceEvent = new MoveDiceEvent(1, 4, 1,2,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[0][2] = new Box(Color.GIALLO, 0);
        boxSchemeTest[0][2].setDice(dice4);
        boxSchemeTest[3][1] = new Box(null, 4);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectMove(moveDiceEvent, 0, 1));
    }

    @Test
    public void checkCorrectValueVicinityMoveFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(3);
        moveDiceEvent = new MoveDiceEvent(1, 4, 1,2,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[0][2] = new Box(null, 6);
        boxSchemeTest[0][2].setDice(dice4);
        boxSchemeTest[3][1] = new Box(null, 4);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectMove(moveDiceEvent, 0, 1));
    }

    @Test
    public void checkCorrectDiceVicinityMoveFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);
        moveDiceEvent = new MoveDiceEvent(1, 4, 3,3,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[1][2] = new Box(null, 1);
        boxSchemeTest[3][3] = new Box(Color.GIALLO, 0);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        assertFalse(modelTest.checkCorrectMove(moveDiceEvent, 0, 1));
    }

    @Test
    public void checkCorrectTwoDiceSameColorMoveFalse(){
        int i, j;
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);
        prepareTest();
        players = modelTest.getPlayers();
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);
        dice5 = new Dice(Color.ROSSO);
        dice5.setValue(6);
        moveDiceEvent = new MoveDiceEvent(1, 4, 1,2,0,0);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[0][3] = new Box(Color.VERDE, 0);
        boxSchemeTest[0][3].setDice(dice2);
        boxSchemeTest[1][4] = new Box(null,3);
        boxSchemeTest[1][4].setDice(dice3);
        boxSchemeTest[1][3] = new Box(Color.BLU, 0);
        boxSchemeTest[1][2] = new Box(null, 3);
        boxSchemeTest[3][3] = new Box(Color.GIALLO, 0);
        boxSchemeTest[3][2] = new Box(Color.BLU, 0);

        windowTest = new WindowSide("test", 4, boxSchemeTest);
        players.get(0).setWindow(windowTest);

        leftDicesTest=new ArrayList<>();
        leftDicesTest.add(dice5);
        modelTest.getRoundTrack()[0]=new Round();
        modelTest.getRoundTrack()[0].setLeftDices(leftDicesTest);

        assertFalse(modelTest.checkCorrectMove(moveDiceEvent, 0, 2));
    }

    @Test
    public void testUpOrDownValueUp(){
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck2);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.VERDE);
        dice.setValue(3);
        modelTest.getDraftPool().add(dice);
        modifyDiceEvent = new ModifyDiceEvent(0, 1);
        modelTest.modifyDice(modifyDiceEvent, 0);
        assertEquals(modelTest.getDraftPool().get(0).getValue(), 4);
    }

    @Test
    public void testUpOrDownValueDown(){
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck2);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.VERDE);
        dice.setValue(3);
        modelTest.getDraftPool().add(dice);
        modifyDiceEvent = new ModifyDiceEvent(0, 0);
        modelTest.modifyDice(modifyDiceEvent, 0);
        assertEquals(modelTest.getDraftPool().get(0).getValue(), 2);
    }

    @Test
    public void testUpOrDownValueFalse(){
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck2);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.VERDE);
        dice.setValue(3);
        modelTest.getDraftPool().add(dice);
        modifyDiceEvent = new ModifyDiceEvent(0, 1);
        modelTest.modifyDice(modifyDiceEvent, 0);
        assertNotEquals(modelTest.getDraftPool().get(0).getValue(), 1);
    }

    @Test
    public void checkCorrectUpOrDownValueFalse(){
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck2);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.VERDE);
        dice.setValue(6);
        modelTest.getDraftPool().add(dice);
        modifyDiceEvent = new ModifyDiceEvent(0, 1);
        assertFalse(modelTest.modifyDice(modifyDiceEvent, 0));
    }

    @Test
    public void testNewRandomValue(){
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck2);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.VERDE);
        dice.setValue(3);
        modelTest.getDraftPool().add(dice);
        modifyDiceEvent = new ModifyDiceEvent(0, 0);
        modelTest.modifyDice(modifyDiceEvent, 1);
        assertEquals(modelTest.getDraftPool().get(0).getColor(), Color.VERDE);
    }

    @Test
    public void testRotateDice(){
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck2);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.VERDE);
        dice.setValue(2);
        modelTest.getDraftPool().add(dice);
        modifyDiceEvent = new ModifyDiceEvent(0, 0);
        modelTest.modifyDice(modifyDiceEvent, 2);
        assertEquals(modelTest.getDraftPool().get(0).getValue(), 5);
    }

    @Test
    public void testRotateDiceFalse(){
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck2);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.VERDE);
        dice.setValue(2);
        modelTest.getDraftPool().add(dice);
        modifyDiceEvent = new ModifyDiceEvent(0, 0);
        modelTest.modifyDice(modifyDiceEvent, 2);
        assertNotEquals(modelTest.getDraftPool().get(0).getValue(), 3);
    }

    @Test
    public void testSetValueDiceFromBag(){
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck2);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.VERDE);
        dice.setValue(2);
        modelTest.getDraftPool().add(dice);
        setValueEvent = new SetValueEvent(3);
        modelTest.setValueDiceFromBag(setValueEvent, 0);
        assertEquals(modelTest.getDraftPool().get(0).getValue(), 3);
    }

    @Test
    public void testExchangeDraftPoolRoundTrack(){
        modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck3);
        prepareTest();
        players = modelTest.getPlayers();
        dice = new Dice(Color.VERDE);
        dice.setValue(2);
        modelTest.getDraftPool().add(dice);
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(5);
        leftDicesTest=new ArrayList<>();
        leftDicesTest.add(dice1);
        modelTest.getRoundTrack()[0]=new Round();
        modelTest.getRoundTrack()[0].setLeftDices(leftDicesTest);

        exchangeEvent = new ExchangeEvent(0, 0, 0);
        modelTest.exchange(0, exchangeEvent, 0);

        assertEquals(modelTest.getDraftPool().get(0).getValue(), 5);
        assertEquals(modelTest.getDraftPool().get(0).getColor(), Color.ROSSO);
        assertEquals(modelTest.getRoundTrack()[0].getLeftDices().get(0).getValue(), 2);
        assertEquals(modelTest.getRoundTrack()[0].getLeftDices().get(0).getColor(), Color.VERDE);
    }

}
