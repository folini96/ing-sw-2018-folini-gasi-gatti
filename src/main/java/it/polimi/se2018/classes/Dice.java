package it.polimi.se2018.classes;

import java.util.Random;

public class Dice {

    private Color color;
    private int usedValue;

    //costruttore della classe
    public Dice(Color color){

        this.color = color;
    }


    public void setValue( int value){
        usedValue=value;

    }

    public void getRandomValue(){
        Random random = new Random();
        int randomInt = random.nextInt(5)+1;
        this.setValue(randomInt);

    }

    public Color getColor(){
        return color;

    }

    public int getValue(){
        return usedValue;

    }

}
