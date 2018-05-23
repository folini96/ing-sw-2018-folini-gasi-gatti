package it.polimi.se2018.classes.model;

/**
 * @author Andrea Folini
 */
public class PublicObjCardSfumatureScure extends PublicObjCard {

    public PublicObjCardSfumatureScure(){
        super("SfumatureScure", 2);
    }

    @Override
    public int getScore(WindowSide window){
        int i, j, five, six;
        Box[][] boxScheme;
        boxScheme =window.getBoxScheme();
        five=0;
        six=0;
        for (i=0; i<=3;i++) {

            for (j=0; j<=4; j++){
                if (boxScheme[i][j].getDice()!=null){
                    switch (boxScheme[i][j].getDice().getValue()){
                        case 1:
                            five++;
                            break;
                        case 2:
                            six++;
                            break;
                        default:
                            break;
                    }
                }

            }



        }
        if (five<six){
            return five*this.getValue();
        }else{
            return six*this.getValue();
        }

    }


}
