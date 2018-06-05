package it.polimi.se2018.classes.model;

/**
 * @author Leonard Gasi
 */
public class Box {
    private Color color;
    private int value;
    private Dice dice;

    /**
     * constructor
     * @param color the color of the box
     * @param value the value of the box
     */
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
