package it.polimi.se2018.classes.model;

import java.io.Serializable;

/**
 * @author Leonard Gasi
 */
public class Box implements Serializable {
    private Color color;
    private int value;
    private Dice dice;
    public Box(Color color, int value){
        this.color=color;
        this.value=value;
    }
    /**
     * @param dice the dice the player is going to place
     */
    public void setDice(Dice dice){
        this.dice=dice;
    }

    /**
     * @return the dice that is placed in the box
     */
    public Dice getDice(){
        return dice;

    }


    public Color getColor(){
        return color;
    }

    public int getValue(){
        return value;
    }

}
