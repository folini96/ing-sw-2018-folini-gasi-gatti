package it.polimi.se2018.classes;

public class PublicObjCardColoriDiversiColonna extends PublicObjCard {

    public PublicObjCardColoriDiversiColonna(){
        super("ColoriDiversiColonna", 5);
    }

    @Override
    public int getScore(WindowSide window){
        // restituisce il punteggio per un certo obiettivo pubblico ottenuto nella scheda in ingresso
        int i, j, completeColumn=0, red, yellow, green, blue, purple, nullcheck;
        Color[] columnColors= new Color[4];
        Box[][] boxScheme;
        boxScheme =window.getBoxScheme();
        for (i=0; i<=4;i++) {
            red=0;
            yellow=0;
            green=0;
            blue=0;
            purple=0;
            nullcheck=0;
            for (j=0; j<=3; j++){

               if (boxScheme[j][i].getDice()!=null){
                   columnColors[j]= boxScheme[j][i].getDice().getColor();
               }
            }
            j=0;
            while ((j<4) && (nullcheck==0)){
                if (columnColors[j]!=null){
                    switch (columnColors[j]){
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
               completeColumn++;
            }

        }
        return completeColumn*this.getValue();
    }

}
