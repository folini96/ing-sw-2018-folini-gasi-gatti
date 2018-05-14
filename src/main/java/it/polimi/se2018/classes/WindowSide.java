package it.polimi.se2018.classes;

public class WindowSide {

    private String name;
    private int difficult;
    private Box [][] boxScheme;

    public WindowSide( String name, int difficult){
        int i, j;
        this.name = name;
        this.difficult = difficult;
        this.boxScheme = new Box[4][5];
        for(i=0;i<=3;i++){
            for(j=0;j<=4;j++){
                this.boxScheme[i][j]=new Box();
            }
        }
    }

    public String getName(){
        return name;

    }

    public int getDifficult(){
        return difficult;

    }

    public Box [][] getBoxScheme(){
        return boxScheme;

    }

}
