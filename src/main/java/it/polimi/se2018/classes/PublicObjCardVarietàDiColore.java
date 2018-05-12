package it.polimi.se2018.classes;

public class PublicObjCardVarietàDiColore extends PublicObjCard {

    public PublicObjCardVarietàDiColore(){
        super("VarietàdiColore", 4);
    }

    @Override
    public int getScore(WindowSide window){
        int i, j,differentColor=0;
        int[] colors= new int[5];
        Box[][] boxScheme;
        boxScheme =window.getBoxScheme();
        for (i=0; i<=5; i++){
            colors[i]=0;
        }
        for (i=0; i<=3;i++) {

            for (j=0; j<=4; j++){

                if (boxScheme[i][j].getDice()!=null){
                    switch (boxScheme[i][j].getDice().getColor()){
                        case ROSSO:
                            colors[0]++;
                            break;
                        case GIALLO:
                            colors[1]++;
                            break;
                        case VERDE:
                            colors[2]++;
                            break;
                        case BLU:
                            colors[3]++;
                            break;
                        case VIOLA:
                            colors[4]++;
                            break;

                    }
                }

            }


        }
        differentColor=colors[0];
        for (i=1; i<=5; i++){
            if (colors[i]<differentColor){
                differentColor=colors[i];
            }

        }
        return differentColor*getValue();



    }

}
