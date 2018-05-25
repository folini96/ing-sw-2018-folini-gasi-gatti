package it.polimi.se2018.classes.model;

/**
 * @author Leonard Gasi
 */
public class BoxColor extends Box {

    private Color color;

    /**
     * Constructor
     * @param color the color of the box
     */
    public BoxColor(Color color){

        this.color=color;
    }

    /**
     * @return the color of the box
     */
    @Override
    public Color getColor(){
        return color;

    }

    @Override
    public int getValue(){
        return 0;
    }

    @Override
    public boolean isBlank(){
        return false;
    }

    @Override
    public boolean isColor(){
        return true;
    }

    @Override
    public boolean isValue(){
        return false;
    }


}
