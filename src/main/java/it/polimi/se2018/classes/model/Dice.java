package it.polimi.se2018.classes.model;

import java.util.Random;

/**
 * @author Leonard Gasi
 */
public class Dice {

    private Color color;
    private int usedValue;

    /**
     * costruttore della classe
     * @param color the color of the dice
     */
    public Dice(Color color){

        this.color = color;
    }

    /**
     * @param value the value we want to assign to the dice
     */
    public void setValue( int value){
        usedValue=value;

    }

    /**
     * to set a random value for the dice
     */
    public void getRandomValue(){
        Random random = new Random();
        int randomInt = random.nextInt(5)+1;
        this.setValue(randomInt);

    }


    /**
     * @return the color of the dice
     */
    public Color getColor(){
        return color;

    }

    /**
     * @return the value of the dice
     */
    public int getValue(){
        return usedValue;

    }

}
