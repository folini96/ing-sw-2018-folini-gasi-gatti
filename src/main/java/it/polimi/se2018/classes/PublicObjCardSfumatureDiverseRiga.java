package it.polimi.se2018.classes;

public class PublicObjCardSfumatureDiverseRiga extends PublicObjCard {

    @Override
    public int getScore(WindowSide window){
        // restituisce il punteggio per un certo obiettivo pubblico ottenuto nella scheda in ingresso
        int i, j, completeRow=0, one, two, three, four, five, six, nullcheck;
        int[] rowNumbers= new int[5];
        Box[][] boxScheme;

        for (i=0; i<=3;i++) {
            one=0;
            two=0;
            three=0;
            four=0;
            five=0;
            six=0;
            nullcheck=0;
            for (j=0; j<=4; j++){
                boxScheme =window.getBoxScheme();
                if (boxScheme[i][j].getDice()!=null){
                    rowNumbers[j]= boxScheme[i][j].getDice().getValue();
                }

            }
            j=0;
            while ((j<5) && (nullcheck==0)){
                if (rowNumbers[j]!=null){
                    switch (rowNumbers[j]){
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
                completeRow++;
            }


        }
        return completeRow*this.getValue();


    }

}
