package it.polimi.se2018.classes.Events;

import java.io.Serializable;

public class SelectedCoordinate implements Serializable {
    private int row;
    private int column;
    public SelectedCoordinate(int row, int column){
        this.row=row;
        this.column=column;
    }
    public int getRow(){
        return row;
    }
    public int getColumn(){
        return column;
    }
}
