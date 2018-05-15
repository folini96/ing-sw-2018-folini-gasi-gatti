package it.polimi.se2018.classes;

/**
 * @author Leonard Gasi
 */
public class BoxColor extends Box {

    private Color color;

    /**
     * costruttore della classe
     * @param color the color of the box
     */
    public BoxColor(Color color){

        this.color=color;
    }

    /**
     * @return the color of the box
     */
    public Color getColor(){
        return color;

    }


}
