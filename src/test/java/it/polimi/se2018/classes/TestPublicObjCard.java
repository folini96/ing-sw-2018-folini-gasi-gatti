package it.polimi.se2018.classes;
import it.polimi.se2018.classes.model.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Andrea Folini
 */
public class TestPublicObjCard {

    private Box[][] boxScheme= new Box[4][5];
    PublicObjCardColoriDiversiColonna coloriDiversiColonna=new PublicObjCardColoriDiversiColonna();
    PublicObjCardVarietaDiColore varietaDiColore=new PublicObjCardVarietaDiColore();
    PublicObjCardSfumatureDiverse sfumatureDiverse=new PublicObjCardSfumatureDiverse();
    PublicObjCardSfumatureDiverseRiga sfumatureDiverseRiga=new PublicObjCardSfumatureDiverseRiga();
    PublicObjCardSfumatureDiverseColonna sfumatureDiverseColonna=new PublicObjCardSfumatureDiverseColonna();
    PublicObjCardSfumatureChiare sfumatureChiare=new PublicObjCardSfumatureChiare();
    PublicObjCardDiagonaliColorate diagonaliColorate=new PublicObjCardDiagonaliColorate();
    PublicObjCardColoriDiversiRiga coloriDiversiRiga=new PublicObjCardColoriDiversiRiga();
    PublicObjCardSfumatureMedie sfumatureMedie=new PublicObjCardSfumatureMedie();
    PublicObjCardSfumatureScure sfumatureScure=new PublicObjCardSfumatureScure();

    /**
     *ensure that a with a blank window every card return 0 points
     */
    @Test
    public void testZeroPoint() {
        int i, j;

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxScheme[i][j]= new Box(null, 0);
            }
        }

        WindowSide window=new WindowSide("test",5, boxScheme);
        assertEquals(0,coloriDiversiColonna.getScore(window));
        assertEquals(0,varietaDiColore.getScore(window));
        assertEquals(0,sfumatureDiverse.getScore(window));
        assertEquals(0,sfumatureDiverseRiga.getScore(window));
        assertEquals(0,sfumatureDiverseColonna.getScore(window));
        assertEquals(0,sfumatureChiare.getScore(window));
        assertEquals(0,diagonaliColorate.getScore(window));
        assertEquals(0,coloriDiversiRiga.getScore(window));
        assertEquals(0,sfumatureMedie.getScore(window));
        assertEquals(0,sfumatureScure.getScore(window));
     }

    /**
     * ensure that the calculated points on a not blank window are correct
     */
    @Test
    public void testRealWindow(){
        int i, j;

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){
                boxScheme[i][j]= new Box(null, 0);
            }
        }

        WindowSide window=new WindowSide("test",4, boxScheme);
        Dice dice00, dice01, dice02, dice03, dice04, dice10, dice11, dice12, dice13, dice14, dice21, dice22, dice23, dice30, dice31, dice32, dice33, dice34;
        dice00=new Dice(Color.VIOLA);
        dice00.setValue(3);
        dice01=new Dice(Color.ROSSO);
        dice01.setValue(2);
        dice02=new Dice(Color.GIALLO);
        dice02.setValue(5);
        dice03=new Dice(Color.VERDE);
        dice03.setValue(6);
        dice04=new Dice(Color.BLU);
        dice04.setValue(1);
        dice10=new Dice(Color.ROSSO);
        dice10.setValue(5);
        dice11=new Dice(Color.GIALLO);
        dice11.setValue(4);
        dice12=new Dice(Color.VERDE);
        dice12.setValue(3);
        dice13=new Dice(Color.BLU);
        dice13.setValue(2);
        dice14=new Dice(Color.VERDE);
        dice14.setValue(6);
        dice21=new Dice(Color.VERDE);
        dice21.setValue(1);
        dice22=new Dice(Color.BLU);
        dice22.setValue(6);
        dice23=new Dice(Color.VIOLA);
        dice23.setValue(4);
        dice30=new Dice(Color.VIOLA);
        dice30.setValue(2);
        dice31=new Dice(Color.BLU);
        dice31.setValue(4);
        dice32=new Dice(Color.ROSSO);
        dice32.setValue(5);
        dice33=new Dice(Color.VERDE);
        dice33.setValue(3);
        dice34=new Dice(Color.GIALLO);
        dice34.setValue(1);

        boxScheme[0][0].setDice(dice00);
        boxScheme[0][1].setDice(dice01);
        boxScheme[0][2].setDice(dice02);
        boxScheme[0][3].setDice(dice03);
        boxScheme[0][4].setDice(dice04);
        boxScheme[1][0].setDice(dice10);
        boxScheme[1][1].setDice(dice11);
        boxScheme[1][2].setDice(dice12);
        boxScheme[1][3].setDice(dice13);
        boxScheme[1][4].setDice(dice14);
        boxScheme[2][1].setDice(dice21);
        boxScheme[2][2].setDice(dice22);
        boxScheme[2][3].setDice(dice23);
        boxScheme[3][0].setDice(dice30);
        boxScheme[3][1].setDice(dice31);
        boxScheme[3][2].setDice(dice32);
        boxScheme[3][3].setDice(dice33);
        boxScheme[3][4].setDice(dice34);

        assertEquals(10,coloriDiversiColonna.getScore(window));
        assertEquals(12,varietaDiColore.getScore(window));
        assertEquals(15,sfumatureDiverse.getScore(window));
        assertEquals(15,sfumatureDiverseRiga.getScore(window));
        assertEquals(4,sfumatureDiverseColonna.getScore(window));
        assertEquals(6,sfumatureChiare.getScore(window));
        assertEquals(12,diagonaliColorate.getScore(window));
        assertEquals(12,coloriDiversiRiga.getScore(window));
        assertEquals(6,sfumatureMedie.getScore(window));
        assertEquals(6,sfumatureScure.getScore(window));
     }

}
