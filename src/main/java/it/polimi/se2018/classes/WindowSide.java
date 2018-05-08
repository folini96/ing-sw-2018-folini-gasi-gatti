package it.polimi.se2018.classes;

public class WindowSide {

    private String name;
    private int difficult;
    private Box [][] boxScheme;

    public WindowSide( String name, int difficult, Box[][] boxScheme){
        this.name = name;
        this.difficult = difficult;
        this.boxScheme = boxScheme;
    }

    public String getName(){
        return name;

    }

    public int getDifficult(){
        return difficult;

    }

    public Box [][] getBoxScheme(){
        return boxScheme;

    }

}
