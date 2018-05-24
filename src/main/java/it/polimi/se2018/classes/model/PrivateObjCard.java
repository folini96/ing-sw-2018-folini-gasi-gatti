package it.polimi.se2018.classes.model;

import java.io.Serializable;

/**
 * @author Alessandro Gatti
 */
public class PrivateObjCard implements Cloneable, Serializable {

    private Color color;

    /**
     *Costruttore della classe
     * @param color the color of the card
     */
    public PrivateObjCard(Color color){

        this.color = color;
    }

    /**
     * @return the color of the card
     */
    public Color getColor(){

        return this.color;
    }

    /**
     * @param window the window from which the score will be calculated
     * @return the score gained according to the private objective
     */
    public int getScore(WindowSide window){
        int i, j, score=0;
        Box[][] boxScheme;
        boxScheme = window.getBoxScheme();

        for(i=0; i<=3; i++){
            for(j=0; j<=4; j++){

                if(boxScheme[i][j].getDice() != null){

                    if(boxScheme[i][j].getDice().getColor() == this.color){

                        score = score + boxScheme[i][j].getDice().getValue();
                    }

                }
            }
        }
        return score;

    }

}
