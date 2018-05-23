package it.polimi.se2018.classes.model;

/**
 * @author Leonard Gasi
 */
public class WindowSide {

    private String name;
    private int difficult;
    private Box [][] boxScheme;

    /**
     * costruttore della classe
     * @param name the name of the window
     * @param difficult the difficulty associated with the window
     */
    public WindowSide( String name, int difficult){
        int i,j;
        this.name = name;
        this.difficult = difficult;
        this.boxScheme = new Box[4][5];
        for (i=0;i<=3;i++){
            for (j=0;j<=4;j++){
                this.boxScheme[i][j]=new Box();
            }
        }

    }

    /**
     * @return the name of the window
     */
    public String getName(){
        return name;

    }

    /**
     * @return the value of the difficulty of the window
     */
    public int getDifficult(){
        return difficult;

    }

    /**
     * @return the scheme of the boxes that form the window
     */
    public Box [][] getBoxScheme(){
        return boxScheme;

    }


}
