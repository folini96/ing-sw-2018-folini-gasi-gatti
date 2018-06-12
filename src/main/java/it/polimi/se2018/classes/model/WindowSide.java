package it.polimi.se2018.classes.model;

import it.polimi.se2018.classes.view.VirtualView;

import java.io.Serializable;

/**
 * @author Leonard Gasi
 * @author Alessandro Gatti
 */
public class WindowSide implements Serializable {

    private String name;
    private int difficult;
    private Box [][] boxScheme;

    /**
     * Constructor
     * @param name the name of the window
     * @param difficult the difficulty associated to the window
     */
    public WindowSide( String name, int difficult, Box[][] boxScheme){
        int i,j;
        this.name = name;
        this.difficult = difficult;
        this.boxScheme = boxScheme;

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

    /**
     * @return the points lost for every open space left on the window
     */
    public int getLostPoints(){
        int lostPoints=0;
        int i, j;

        for (i=0;i<=3;i++){
            for (j=0;j<=4;j++){
                if(this.boxScheme[i][j].getDice() == null){
                    lostPoints++;
                }
            }
        }

        return lostPoints;
    }

    /**
     * @return true if the window is empty, false if not
     */
    public boolean isEmpty(){
        int i, j;
        int countDice=0;

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++) {
                if(this.boxScheme[i][j].getDice() != null){
                    countDice++;
                }
            }
        }

        if(countDice == 0) return true;
        else return false;
    }


}

