package it.polimi.se2018.classes;

public class PublicObjCardColoriDiversiRiga extends PublicObjCard {

    public PublicObjCardColoriDiversiRiga(){
        super("ColoriDiversiRiga", 6);
    }

    @Override
    public int getScore(WindowSide window){
        // restituisce il punteggio per un certo obiettivo pubblico ottenuto nella scheda in ingresso
        int i, j, completeRow=0, red, yellow, green, blue, purple, nullcheck;
        Color[] rowColors= new Color[5];
        Box[][] boxScheme;
        boxScheme =window.getBoxScheme();
        for (i=0; i<=3;i++) {
            red=0;
            yellow=0;
            green=0;
            blue=0;
            purple=0;
            nullcheck=0;
            for (j=0; j<=4; j++){

                if (boxScheme[i][j].getDice()!=null){
                    rowColors[j]= boxScheme[i][j].getDice().getColor();
                }

            }
            j=0;
            while ((j<5) && (nullcheck==0)){
                if (rowColors[j]!=null){
                    switch (rowColors[j]){
                        case ROSSO:
                            red++;
                            break;
                        case GIALLO:
                            yellow++;
                            break;
                        case VERDE:
                            green++;
                            break;
                        case BLU:
                            blue++;
                            break;
                        case VIOLA:
                            purple++;
                            break;

                    }
                }else{
                    nullcheck=1;
                }
                j++;

            }
            if ((red<2)&&(yellow<2)&&(green<2)&&(blue<2)&&(purple<2)&&(nullcheck==0)){
                completeRow++;
            }


        }
        return completeRow*this.getValue();

    }

}
