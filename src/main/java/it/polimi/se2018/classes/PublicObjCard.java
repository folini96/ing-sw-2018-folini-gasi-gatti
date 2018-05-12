package it.polimi.se2018.classes;

public abstract class PublicObjCard {

    private String name;
    private int value;

    public PublicObjCard(String name,int value){
        this.name=name;
        this.value=value;
    }
    public String getName(){
        return name;

    }

    public int getValue(){
        return value;

    }

    public abstract int getScore(WindowSide window);






}
