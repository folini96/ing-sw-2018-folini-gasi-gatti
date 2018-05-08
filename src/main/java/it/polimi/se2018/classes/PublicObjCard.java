package it.polimi.se2018.classes;

public abstract class PublicObjCard {

    private int name;
    private int value;

    public int getName(){
        return name;

    }

    public int getValue(){
        return value;

    }

    public abstract int getScore(WindowSide window);






}
