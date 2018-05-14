package it.polimi.se2018.classes;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Alessandro Gatti
 */
public class TestPrivateObjCard {

    private PrivateObjCard privateCardTest;
    private Box [][] boxSchemeTest = new Box[4][5];
    private WindowSide windowTest;
    private Dice dice1;
    private Dice dice2;
    private Dice dice3;
    private Dice dice4;
    private Dice dice5;
    private Dice dice6;

    /**
     * to verify that no score is associated with a blank window card
     */
    @Test
    public void testZeroScore() {

        privateCardTest= new PrivateObjCard(Color.ROSSO);
        windowTest = new WindowSide("test", 4);

        assertEquals(0, privateCardTest.getScore(windowTest));
    }

    /**
     * to verify that the calculated score is right
     */
    @Test
    public void testRightScore() {

        privateCardTest= new PrivateObjCard(Color.ROSSO);
        windowTest = new WindowSide("test", 4);

        dice1 = new Dice(Color.ROSSO);
        dice1.setValue(4);
        dice2 = new Dice(Color.ROSSO);
        dice2.setValue(3);
        dice3 = new Dice(Color.BLU);
        dice3.setValue(4);
        dice4 = new Dice(Color.VERDE);
        dice4.setValue(4);
        dice5 = new Dice(Color.ROSSO);
        dice5.setValue(1);
        dice6 = new Dice(Color.ROSSO);
        dice6.setValue(6);

        boxSchemeTest = windowTest.getBoxScheme();

        boxSchemeTest[0][4] = new BoxColor(Color.ROSSO);
        boxSchemeTest[0][4].setDice(dice1);
        boxSchemeTest[1][1] = new BoxColor(Color.ROSSO);
        boxSchemeTest[1][1].setDice(dice2);
        boxSchemeTest[1][2] = new BoxColor(Color.BLU);
        boxSchemeTest[1][2].setDice(dice3);
        boxSchemeTest[1][3] = new BoxColor(Color.VERDE);
        boxSchemeTest[1][3].setDice(dice4);
        boxSchemeTest[2][2] = new BoxColor(Color.ROSSO);
        boxSchemeTest[2][2].setDice(dice5);
        boxSchemeTest[3][1].setDice(dice6);

        assertEquals(14, privateCardTest.getScore(windowTest));

    }

}