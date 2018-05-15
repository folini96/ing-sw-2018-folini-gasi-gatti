package it.polimi.se2018.classes;
/**
 * @author Andrea Folini
 */


public class PublicObjCardDiagonaliColorate extends PublicObjCard {

    public PublicObjCardDiagonaliColorate(){
        super("DiagonaliColorate", 0);
    }


    @Override
    public int getScore(WindowSide window){
        int i, j, streak, redStreak=0, yellowStreak=0, greenStreak=0, blueStreak=0, purpleStreak=0, score;
        Color[][] colorScheme= new Color[4][5];
        Color currentColor;
        Box[][] boxScheme;
        boxScheme =window.getBoxScheme();
        for (i=0; i<=3; i++){
            for (j=0; j<=4; j++){
                if (boxScheme[i][j].getDice()!=null){
                    colorScheme[i][j]=boxScheme[i][j].getDice().getColor();
                }

            }

        }
        for (i=0; i<=3; i++){
            for (j=0; j<=4; j++){
                if (colorScheme[i][j]!=null){
                    currentColor=colorScheme[i][j];
                    streak=calculateStreak(colorScheme, i, j, currentColor);
                    if (streak>1){
                        switch (currentColor){
                            case ROSSO:
                                redStreak= redStreak+streak;
                                break;
                            case GIALLO:
                                yellowStreak=yellowStreak+streak;
                                break;
                            case VERDE:
                                greenStreak=greenStreak+streak;
                                break;
                            case BLU:
                                blueStreak=blueStreak+streak;
                                break;
                            case VIOLA:
                                purpleStreak= purpleStreak+streak;
                                break;
                        }
                    }

                }
            }

        }
        score=redStreak+yellowStreak+greenStreak+blueStreak+purpleStreak;
        return score;

    }

    private int calculateStreak(Color[][] colorScheme, int row, int column, Color currentColor){
        int streak=1;
        colorScheme[row][column]= null;
        if (((row>0) && (column>0))&&(colorScheme[row-1][column-1]==currentColor)){
            streak= streak+calculateStreak(colorScheme, row-1, column-1, currentColor);

        }
        if (((row>0) && (column<4)) && (colorScheme[row-1][column+1]==currentColor)){
            streak= streak+calculateStreak(colorScheme, row-1, column+1, currentColor);

        }
        if (((row<3) && (column>0)) && (colorScheme[row+1][column-1]==currentColor)){
            streak= streak+calculateStreak(colorScheme, row+1, column-1, currentColor);

        }
        if (((row<3) && (column<4)) && (colorScheme[row+1][column+1]==currentColor)){
            streak= streak+calculateStreak(colorScheme, row+1, column+1, currentColor);

        }

        return streak;
    }

}
