package it.polimi.se2018.classes.model;

/**
 * @author Leonard Gasi
 */
public abstract class Box {

    private Dice dice;

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

    public abstract boolean isBlank();
    public abstract boolean isColor();
    public abstract boolean isValue();
    public abstract Color getColor();
    public abstract int getValue();

}
