package it.polimi.se2018.classes.model;

//import it.polimi.se2018.classes.events.SelectedDraftPoolDice;
import it.polimi.se2018.classes.effects.ModificationType;
import it.polimi.se2018.classes.model.Color;
import it.polimi.se2018.classes.effects.ToolCardsEffectsInterface;
import it.polimi.se2018.classes.view.VirtualView;

import java.io.Serializable;

public class ToolCard implements Serializable {

    private String name;
    private int number;
    private int token;
    private Color color;
    private ToolCardsEffectsInterface effect;
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
    private boolean setValue;
    private ModificationType modification;
    private boolean newRandomValue;
    private boolean rotateDice;



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
