package it.polimi.se2018.classes;

public class WindowCard {

    private int name;
    private WindowSide front;
    private WindowSide back;

    public WindowCard(int name, WindowSide front, WindowSide back){
        this.name = name;
        this.front = front;
        this.back = back;
    }
    public int getName(){
        return name;

    }

    public WindowSide getFront(){
        return front;

    }

    public WindowSide getBack(){
        return back;

    }


}
