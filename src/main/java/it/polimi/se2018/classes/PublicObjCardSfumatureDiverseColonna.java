package it.polimi.se2018.classes;
/**
 * @author Andrea Folini
 */
public class PublicObjCardSfumatureDiverseColonna extends PublicObjCard {

    public PublicObjCardSfumatureDiverseColonna(){
        super("SfumatureDiverseColonna", 4);
    }

    @Override
    public int getScore(WindowSide window){
        // restituisce il punteggio per un certo obiettivo pubblico ottenuto nella scheda in ingresso
        int i, j, completeColumn=0, one, two, three, four, five, six, nullcheck;
        int[] columnNumbers= new int[4];
        Box[][] boxScheme;
        boxScheme =window.getBoxScheme();
        for (i=0; i<=4;i++) {
            for (j=0; j<=3; j++){
                columnNumbers[j]=0;
            }
            one=0;
            two=0;
            three=0;
            four=0;
            five=0;
            six=0;
            nullcheck=0;
            for (j=0; j<=3; j++){

                if (boxScheme[j][i].getDice()!=null){
                    columnNumbers[j]= boxScheme[j][i].getDice().getValue();
                }

            }
            j=0;
            while ((j<4) && (nullcheck==0)){
                if (columnNumbers[j]!=0){
                    switch (columnNumbers[j]){
                        case 1:
                            one++;
                            break;
                        case 2:
                            two++;
                            break;
                        case 3:
                            three++;
                            break;
                        case 4:
                            four++;
                            break;
                        case 5:
                            five++;
                            break;
                        case 6:
                            six++;
                            break;
                    }
                }else{
                    nullcheck=1;
                }
                j++;

            }
            if ((one<2)&&(two<2)&&(three<2)&&(four<2)&&(five<2) && (six<2) &&(nullcheck==0)){
                completeColumn++;
            }


        }
        return completeColumn*this.getValue();

    }

}
