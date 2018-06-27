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
    private boolean allDices;
    private boolean twoTurnsInOne;
    private boolean takeFromDraftPool;
    private boolean selectFromWindow;
    private boolean takeFromRoundTrack;
    private boolean takeFromDiceBag;
    private boolean blockedAfterPlacement;
    private boolean blockedFirstTurn;
    private boolean vicinityBound;
    private ToolCardsEffectsInterface effect;

    public ToolCard(String name, int number, int token, Color color, ToolCardsEffectsInterface effect,
                    boolean allDices, boolean twoTurnsInOne, boolean takeFromDraftPool,
                    boolean selectFromWindow, boolean takeFromRoundTrack, boolean takeFromDiceBag,
                    boolean blockedAfterPlacement, boolean blockedFirstTurn, boolean vicinityBound){
        this.name=name;
        this.number=number;
        this.token=token;
        this.color=color;
        this.effect=effect;
        this.allDices=allDices;
        this.twoTurnsInOne=twoTurnsInOne;
        this.takeFromDraftPool=takeFromDraftPool;
        this.selectFromWindow=selectFromWindow;
        this.takeFromRoundTrack=takeFromRoundTrack;
        this.takeFromDiceBag=takeFromDiceBag;
        this.blockedAfterPlacement=blockedAfterPlacement;
        this.blockedFirstTurn=blockedFirstTurn;
        this.vicinityBound=vicinityBound;
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

    public boolean isTakeFromRoundTrack(){
        return takeFromRoundTrack;
    }
}
