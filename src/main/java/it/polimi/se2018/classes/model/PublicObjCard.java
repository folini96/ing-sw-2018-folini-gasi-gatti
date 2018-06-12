package it.polimi.se2018.classes.model;

import it.polimi.se2018.classes.view.VirtualView;

import java.io.Serializable;

/**
 * @author Andrea Folini
 */
public abstract class PublicObjCard implements Serializable {

    private String name;
    private int value;

    /**
     *
     * @param name the name of the card
     * @param value the value to calculate the points
     */
    public PublicObjCard(String name,int value){
        this.name=name;
        this.value=value;
    }

    /**
     *
     * @return the name of the card
     */
    public String getName(){
        return name;

    }

    /**
     *
     * @return the value of the card
     */
    public int getValue(){
        return value;

    }

    /**
     *
     * @param window the window from which the score is calculated
     * @return the points calculated
     */
    public abstract int getScore(WindowSide window);


}
