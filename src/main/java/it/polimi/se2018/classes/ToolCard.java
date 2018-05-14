package it.polimi.se2018.classes;

public abstract class ToolCard {

    private String name;
    private int token;
    private Color color;

    public void setToken(){


    }

    public int getToken(){

        return token;

    }

    public String getName(){

        return name;

    }

    public Color getColor(){

        return color;

    }

    public abstract void useTool();


}
