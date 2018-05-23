package it.polimi.se2018.classes.model;

/**
 * @author Leonard Gasi
 */
public class BoxValue extends Box{

    private int value;

    /**
     * costruttore della classe
     * @param value the value of the box
     */
    public BoxValue(int value){

        this.value=value;
    }

    /**
     * @return the value of the box
     */
    public int getValue(){
        return value;

    }


}
