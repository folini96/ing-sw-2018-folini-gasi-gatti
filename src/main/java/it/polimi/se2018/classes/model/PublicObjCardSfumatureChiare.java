package it.polimi.se2018.classes.model;

/**
 * @author Andrea Folini
 */
public class PublicObjCardSfumatureChiare extends PublicObjCard {

    public PublicObjCardSfumatureChiare(){
        super("SfumatureChiare", 2);
    }


    @Override
    public int getScore(WindowSide window){
        int i, j, one, two;
        Box[][] boxScheme;
        boxScheme =window.getBoxScheme();
        one=0;
        two=0;
        for (i=0; i<=3;i++) {

            for (j=0; j<=4; j++){
                if (boxScheme[i][j].getDice()!=null){
                    switch (boxScheme[i][j].getDice().getValue()){
                        case 1:
                            one++;
                            break;
                        case 2:
                            two++;
                            break;
                        default:
                            break;
                    }
                }

            }



        }
        if (one<two){
            return one*this.getValue();
        }else{
            return two*this.getValue();
        }

    }


}
