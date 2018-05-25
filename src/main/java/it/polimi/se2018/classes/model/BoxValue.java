package it.polimi.se2018.classes.model;

/**
 * @author Leonard Gasi
 */
public class BoxValue extends Box{

    private int value;

    /**
     * Constructor
     * @param value the value of the box
     */
    public BoxValue(int value){

        this.value=value;
    }

    /**
     * @return the value of the box
     */
    @Override
    public int getValue(){
        return value;

    }

    @Override
    public Color getColor(){
        return null;
    }

    @Override
    public boolean isBlank(){
        return false;
    }

    @Override
    public boolean isColor(){
        return false;
    }

    @Override
    public boolean isValue(){
        return true;
    }


}
