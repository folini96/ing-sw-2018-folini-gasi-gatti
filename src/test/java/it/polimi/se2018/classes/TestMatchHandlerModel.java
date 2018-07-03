package it.polimi.se2018.classes;

import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.model.effects.EffectType;
import it.polimi.se2018.classes.model.effects.ToolCardsEffectsInterface;
import it.polimi.se2018.classes.network.Server;
import it.polimi.se2018.classes.view.VirtualView;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Alessandro Gatti
 */
public class TestMatchHandlerModel {
    private ToolCardsEffectsInterface interfaceTest;
    Server serverTest = new Server();
    private VirtualView viewTest = new VirtualView(serverTest, 1);
    private PublicObjCard[] publicObjDeck = {new PublicObjCardColoriDiversiColonna(), new PublicObjCardColoriDiversiRiga(),
            new PublicObjCardSfumatureChiare()};
    private PrivateObjCard[] privateObjDeck = {new PrivateObjCard(Color.ROSSO), new PrivateObjCard(Color.BLU)};
    private ToolCard[] toolDeck = {new ToolCard("toolTest1", 1, 1, Color.ROSSO, interfaceTest, true),
            new ToolCard("toolTest2", 1, 1, Color.ROSSO, interfaceTest, true),
            new ToolCard("toolTest3", 1, 1, Color.ROSSO, interfaceTest, true)};
    private ArrayList<String> playerNames = new ArrayList<String>();
    private DiceBag diceBag;
    private ArrayList<Dice> draftPool;
    int playerNumber = 2;
    private MatchHandlerModel modelTest = new MatchHandlerModel(viewTest, publicObjDeck, privateObjDeck, toolDeck);

    private ArrayList<Player> players = new ArrayList<>();
    private WindowSide windowTest;
    private Box[][] boxSchemeTest = new Box[4][5];
    private Dice dice;
    private Dice dice1;
    private Dice dice2;
    private Dice dice3;
    private Dice dice4;
    private PlaceDiceEvent placeDiceEvent;
    private PrivateObjCard privateObjCardTest = new PrivateObjCard(Color.ROSSO);

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

}
