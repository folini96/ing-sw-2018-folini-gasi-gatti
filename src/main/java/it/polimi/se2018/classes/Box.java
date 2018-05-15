package it.polimi.se2018.classes;

/**
 * @author Leonard Gasi
 */
public class Box {

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

}
