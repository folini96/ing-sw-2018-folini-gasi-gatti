package it.polimi.se2018.classes.model;

/**
 * @author Andrea Folini
 */
public class PublicObjCardSfumatureDiverse extends PublicObjCard {

    public PublicObjCardSfumatureDiverse(){
        super("SfumatureDiverse", 5);
    }


    @Override
    public int getScore(WindowSide window){
        int i, j,differentShade=0;
        int[] shades= new int[6];
        Box[][] boxScheme;
        boxScheme =window.getBoxScheme();
        for (i=0; i<=5; i++){
            shades[i]=0;
        }
        for (i=0; i<=3;i++) {

            for (j=0; j<=4; j++){

                if (boxScheme[i][j].getDice()!=null){
                    shades[boxScheme[i][j].getDice().getValue()-1]++;
                }

            }


        }
        differentShade=shades[0];
        for (i=1; i<=5; i++){
            if (shades[i]<differentShade){
                differentShade=shades[i];
            }

        }
        return differentShade*getValue();

    }


}
