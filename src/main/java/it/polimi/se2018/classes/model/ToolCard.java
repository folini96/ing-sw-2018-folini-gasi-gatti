package it.polimi.se2018.classes.model;

//import it.polimi.se2018.classes.events.SelectedDraftPoolDice;
import it.polimi.se2018.classes.model.effects.EffectType;
import it.polimi.se2018.classes.model.effects.ToolCardsEffectsInterface;

import java.io.Serializable;

/**
 * @author Alessandro Gatti
 */
public class ToolCard implements Serializable {

    private String name;
    private int number;
    private int token;
    private Color color;
    private boolean blockedAfterPlacement;
    private ToolCardsEffectsInterface effect;

    /**
     * constructor
     * @param name the name of the tool card
     * @param number the number of the tool card
     * @param token the number of the initial tokens of the card
     * @param color the color of the card
     * @param effect the effect of the card
     * @param blockedAfterPlacement used to indicate if the card can or cannot be used in the same turn a player place a dice
     */
    public ToolCard(String name, int number, int token, Color color, ToolCardsEffectsInterface effect, boolean blockedAfterPlacement){
        this.name=name;
        this.number=number;
        this.token=token;
        this.color=color;
        this.effect=effect;
        this.blockedAfterPlacement=blockedAfterPlacement;
     }

    /**
     * @param token the number of the tokens the card has left
     */
    public void setToken(int token){
        this.token=token;

    }

    /**
     * @return the number of the tokens
     */
    public int getToken(){
        return token;

    }

    /**
     * @return the flag used to indicate when the effect can be used
     */
    public boolean getBlockedAfterPlacement(){
        return blockedAfterPlacement;
    }

    /**
     * @return the number of the tool card
     */
    public int getNumber(){
        return number;
    }

    /**
     * @return the name of the card
     */
    public String getName(){
        return name;

    }

    /**
     * @return the color of the card
     */
    public Color getColor(){
        return color;

    }

    /**
     * @return the effect of the card
     */
    public ToolCardsEffectsInterface getEffect(){
        return effect;
    }

}
