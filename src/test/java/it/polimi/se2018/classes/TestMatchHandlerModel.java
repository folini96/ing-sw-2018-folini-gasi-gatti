package it.polimi.se2018.classes;

import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.model.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Alessandro Gatti
 */
public class TestMatchHandlerModel {
    private MatchHandlerModel modelTest = new MatchHandlerModel();
    private WindowSide windowTest;
    private Box[][] boxSchemeTest = new Box[4][5];
    private Dice dice;
    private Dice dice1;
    private Dice dice2;
    private Dice dice3;
    private Dice dice4;
    private SelectedCoordinate coordinateTest;
    private PrivateObjCard privateObjCardTest = new PrivateObjCard(Color.ROSSO);
    private Player player;

    /**
     * to verify that the first placement of the dice is handled right
     */
    @Test
    public void checkCorrectFirstMoveTrue(){
        int i, j;
        coordinateTest = new SelectedCoordinate(0, 4);
        dice = new Dice(Color.ROSSO);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        windowTest = new WindowSide("test", 4, boxSchemeTest);

        modelTest.addPlayer(new Player("test", privateObjCardTest,windowTest));

        assertTrue(modelTest.checkCorrectMove(dice, coordinateTest));
    }

    /**
     * to verify that the first dice can't be placed in an incorrect position
     */
    @Test
    public void checkCorrectFirstMoveFalse(){
        int i, j;
        coordinateTest = new SelectedCoordinate(1, 3);
        dice = new Dice(Color.ROSSO);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[0][4] = new Box(Color.ROSSO, 0);
        windowTest = new WindowSide("test", 4, boxSchemeTest);

        modelTest.addPlayer(new Player("test", privateObjCardTest,windowTest));

        assertFalse(modelTest.checkCorrectMove(dice, coordinateTest));
    }

    /**
     * to verify that the placement of the dice is handled right
     */
    @Test
    public void checkCorrectMoveTrue(){
        int i, j;
        coordinateTest = new SelectedCoordinate(1, 3);
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);

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

        modelTest.addPlayer(new Player("test", privateObjCardTest,windowTest));

        assertTrue(modelTest.checkCorrectMove(dice4, coordinateTest));
    }

    /**
     * to verify that the dice can't be placed in a color box with a different color
     */
    @Test
    public void checkCorrectMoveColorFalse(){
        int i, j;
        coordinateTest = new SelectedCoordinate(1, 3);
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.ROSSO);
        dice4.setValue(6);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
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

        modelTest.addPlayer(new Player("test", privateObjCardTest,windowTest));

        assertFalse(modelTest.checkCorrectMove(dice4, coordinateTest));
    }

    /**
     * to verify that the dice can't be placed in a box that already contains a dice
     */
    @Test
    public void checkCorrectMoveNotEmptyBoxFalse(){
        int i, j;
        coordinateTest = new SelectedCoordinate(0, 3);
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.VERDE);
        dice4.setValue(2);

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

        modelTest.addPlayer(new Player("test", privateObjCardTest,windowTest));

        assertFalse(modelTest.checkCorrectMove(dice4, coordinateTest));
    }

    /**
     * to verify that the dice can't be placed near a dice of the same color
     */
    @Test
    public void checkCorrectMoveColorVicinityFalse(){
        int i, j;
        coordinateTest = new SelectedCoordinate(1, 3);
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.VERDE);
        dice4.setValue(6);

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

        modelTest.addPlayer(new Player("test", privateObjCardTest,windowTest));

        assertFalse(modelTest.checkCorrectMove(dice4, coordinateTest));
    }

    /**
     * to verify that the dice can't be placed separated from the other dices
     */
    @Test
    public void checkCorrectMoveDiceVicinityFalse(){
        int i, j;
        coordinateTest = new SelectedCoordinate(2, 2);
        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.VERDE);
        dice2.setValue(2);
        dice3 = new Dice(Color.GIALLO);
        dice3.setValue(3);
        dice4 = new Dice(Color.BLU);
        dice4.setValue(6);

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

        modelTest.addPlayer(new Player("test", privateObjCardTest,windowTest));

        assertFalse(modelTest.checkCorrectMove(dice4, coordinateTest));
    }

}
