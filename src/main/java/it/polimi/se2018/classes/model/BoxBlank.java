package it.polimi.se2018.classes.model;

public class BoxBlank extends Box {

    @Override
    public Color getColor(){
        return null;
    }

    @Override
    public int getValue(){
        return 0;
    }

    @Override
    public boolean isBlank(){
        return true;
    }

    @Override
    public boolean isColor(){
        return false;
    }

    @Override
    public boolean isValue(){
        return false;
    }
}
