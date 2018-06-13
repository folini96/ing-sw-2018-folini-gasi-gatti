package it.polimi.se2018.classes;

import it.polimi.se2018.classes.model.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Alessandro Gatti
 */
public class TestWindowSide {
    private WindowSide windowTest;
    private Box[][] boxSchemeTest = new Box[4][5];
    private Dice dice;

    /**
     * to verify that a window completely full does not lose any points
     */
    @Test
    public void testWindowFullLostPoints(){
        int i, j;
        dice = new Dice(Color.ROSSO);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null,0);
            }
        }
        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j].setDice(dice);
            }
        }

        windowTest = new WindowSide("test", 4, boxSchemeTest);

        assertEquals(0,windowTest.getLostPoints());
    }

    /**
     *to verify the the lost points are calculated right
     */
    @Test
    public void testRightLostPoints(){
        int i, j;
        dice= new Dice(Color.ROSSO);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }
        boxSchemeTest[1][2].setDice(dice);
        boxSchemeTest[2][2].setDice(dice);
        boxSchemeTest[2][3].setDice(dice);
        boxSchemeTest[3][2].setDice(dice);
        boxSchemeTest[3][4].setDice(dice);

        windowTest = new WindowSide("test", 4, boxSchemeTest);

        assertEquals(15,windowTest.getLostPoints());
    }

    /**
     * to verify that a empty window is recognized as such
     */
    @Test
    public void testIsReallyEmpty(){
        int i, j;

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }

        windowTest = new WindowSide("test", 4, boxSchemeTest);

        assertTrue(windowTest.isEmpty());
    }

    /**
     * to verify that a window who has some dices placed in it isn't recognized as empty
     */
    @Test
    public void testIsNotEmpty(){
        int i, j;
        dice= new Dice(Color.ROSSO);

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxSchemeTest[i][j]= new Box(null, 0);
            }
        }

        boxSchemeTest[0][2].setDice(dice);
        boxSchemeTest[2][2].setDice(dice);
        boxSchemeTest[3][3].setDice(dice);

        windowTest = new WindowSide("test", 4, boxSchemeTest);

        assertFalse(windowTest.isEmpty());
    }

}
