package it.polimi.se2018.classes.model;

import java.io.Serializable;

/**
 * @author Leonard Gasi
 */
public class Box implements Serializable {
    private Color color;
    private int value;
    private Dice dice;

    /**
     * constructor
     * @param color the color of the box
     * @param value the value of the box
     */
    public Box(String color, int value){
        switch (color){
            case "rosso":
                this.color=Color.ROSSO;
                break;
            case "giallo":
                this.color=Color.GIALLO;
                break;
            case "blu":
                this.color=Color.BLU;
                break;
            case "verde":
                this.color=Color.VERDE;
                break;
            case "viola":
                this.color=Color.VIOLA;
                break;
            case "null":
                this.color=null;
                break;
        }

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

    /**
     * @return the color of the box
     */
    public Color getColor(){
        return color;
    }

    /**
     * @return the value of the box
     */
    public int getValue(){
        return value;
    }

}
