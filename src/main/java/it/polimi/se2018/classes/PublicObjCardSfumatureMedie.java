package it.polimi.se2018.classes;
/**
 * @author Andrea Folini
 */
public class PublicObjCardSfumatureMedie extends PublicObjCard {

    public PublicObjCardSfumatureMedie(){
        super("SfumatureMedie", 2);
    }

    @Override
    public int getScore(WindowSide window){
        int i, j, three, four;
        Box[][] boxScheme;
        boxScheme =window.getBoxScheme();
        three=0;
        four=0;
        for (i=0; i<=3;i++) {

            for (j=0; j<=4; j++){
                if (boxScheme[i][j].getDice()!=null){
                    switch (boxScheme[i][j].getDice().getValue()){
                        case 3:
                            three++;
                            break;
                        case 4:
                            four++;
                            break;
                        default:
                            break;
                    }
                }

            }



        }
        if (three<four){
            return three*this.getValue();
        }else{
            return four*this.getValue();
        }

    }

}
