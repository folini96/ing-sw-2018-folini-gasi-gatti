package it.polimi.se2018.classes.model;

//import it.polimi.se2018.classes.events.SelectedDraftPoolDice;
import it.polimi.se2018.classes.model.effects.EffectType;
import it.polimi.se2018.classes.model.effects.ToolCardsEffectsInterface;

import java.io.Serializable;

public class ToolCard implements Serializable {

    private String name;
    private int number;
    private int token;
    private Color color;
    private boolean blockedAfterPlacement;
    private ToolCardsEffectsInterface effect;

    public ToolCard(String name, int number, int token, Color color, ToolCardsEffectsInterface effect,boolean blockedAfterPlacement){
        this.name=name;
        this.number=number;
        this.token=token;
        this.color=color;
        this.effect=effect;
        this.blockedAfterPlacement=blockedAfterPlacement;
     }

    public void setToken(int token){
        this.token=token;

    }

    public int getToken(){
        return token;

    }
    public boolean getBlockedAfterPlacement(){
        return blockedAfterPlacement;
    }
    public int getNumber(){
        return number;
    }

    public String getName(){
        return name;

    }

    public Color getColor(){
        return color;

    }

    public ToolCardsEffectsInterface getEffect(){
        return effect;
    }

}
