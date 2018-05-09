package it.polimi.se2018.classes;

public class PrivateObjCard {

    private Color color;

    //costruttore della classe
    public PrivateObjCard(Color color){

        this.color = color;
    }

    public Color getColor(){

        return this.color;
    }

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
