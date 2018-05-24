package it.polimi.se2018.classes.model;

import it.polimi.se2018.classes.model.Color;

import java.io.Serializable;

public abstract class ToolCard implements Cloneable, Serializable {

    private String name;
    private int token;
    private Color color;

    public void setToken(int token){
        this.token=token;

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
