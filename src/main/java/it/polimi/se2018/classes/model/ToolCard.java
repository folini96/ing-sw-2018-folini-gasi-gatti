package it.polimi.se2018.classes.model;

//import it.polimi.se2018.classes.events.SelectedDraftPoolDice;
import it.polimi.se2018.classes.effects.Effect;
import it.polimi.se2018.classes.effects.EffectType;
import it.polimi.se2018.classes.model.Color;
import it.polimi.se2018.classes.effects.ToolCardsEffectsInterface;
import it.polimi.se2018.classes.view.VirtualView;

import java.io.Serializable;

public class ToolCard implements Serializable {

    private String name;
    private int number;
    private int token;
    private Color color;
    private Effect effect;
    private boolean doubleDice;
    private boolean allDices;
    private boolean twoTurnsInOne;
    private boolean takeFromDraftPool;
    private boolean selectFromWindow;
    private boolean takeFromRoundTrack;
    private boolean takeFromDiceBag;
    private boolean blockedAfterPlacement;
    private boolean blockedFirstTurn;
    private boolean colorBound;
    private boolean valueBound;
    private boolean vicinityBound;
    private EffectType type;

    public ToolCard(String name, int number, int token, Color color, Effect effect,
                    boolean doubleDice, boolean allDices, boolean twoTurnsInOne, boolean takeFromDraftPool,
                    boolean selectFromWindow, boolean takeFromRoundTrack, boolean takeFromDiceBag,
                    boolean blockedAfterPlacement, boolean blockedFirstTurn, boolean colorBound,
                    boolean valueBound, boolean vicinityBound, EffectType type){
        this.name=name;
        this.number=number;
        this.token=token;
        this.color=color;
        this.effect=effect;
        this.doubleDice=doubleDice;
        this.allDices=allDices;
        this.twoTurnsInOne=twoTurnsInOne;
        this.takeFromDraftPool=takeFromDraftPool;
        this.selectFromWindow=selectFromWindow;
        this.takeFromRoundTrack=takeFromRoundTrack;
        this.takeFromDiceBag=takeFromDiceBag;
        this.blockedAfterPlacement=blockedAfterPlacement;
        this.blockedFirstTurn=blockedFirstTurn;
        this.colorBound=colorBound;
        this.valueBound=valueBound;
        this.vicinityBound=vicinityBound;
        this.type=type;
    }

    public void setToken(int token){
        this.token=token;

    }

    public int getToken(){
        return token;

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

    /*public void usePinzaSgrossatrice(SelectedDraftPoolDice selectedDraftPoolDice){
        int value;
        Dice dice;

        dice = selectedDraftPoolDice.getDice();
        value = dice.getValue();


        dice.setValue(value);

    }*/

}
