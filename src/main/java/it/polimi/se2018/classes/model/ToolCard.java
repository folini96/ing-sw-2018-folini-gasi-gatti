package it.polimi.se2018.classes.model;

import it.polimi.se2018.classes.model.Color;
import it.polimi.se2018.classes.view.VirtualView;

import java.io.Serializable;

public class ToolCard implements Serializable {

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

}
