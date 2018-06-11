package it.polimi.se2018.classes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Alessandro Gatti
 */
public class DiceBag {
    private Dice[] diceSet;

    /**
     * Constructor
     */
    public DiceBag(){
        int i;

        for(i=0; i<=89; i++){

            if(i<18){
                diceSet[i] = new Dice(Color.ROSSO);
            }

            if(i>17 && i<36){
                diceSet[i] = new Dice(Color.GIALLO);
            }

            if(i>35 && i<54){
                diceSet[i] = new Dice(Color.VERDE);
            }

            if(i>53 && i<72){
                diceSet[i] = new Dice(Color.BLU);
            }

            if(i>71 && i<90){
                diceSet[i] = new Dice(Color.VIOLA);
            }

        }
    }

    /**
     * @param number the number of dices to extract form the bag
     * @return the extracted dices, each with a color and a random value
     */
    public ArrayList<Dice> extractDice(int number){

        int i, randomInt;
        ArrayList<Dice> extractedDices = new ArrayList<Dice>();

        for(i=0; i<number; i++){

            do{
                Random random = new Random();
                randomInt = random.nextInt(89);

            } while(diceSet[randomInt] == null);

            diceSet[randomInt].getRandomValue();
            extractedDices.add(diceSet[randomInt]);
            diceSet[randomInt] = null;

        }
        return extractedDices;
    }

}
