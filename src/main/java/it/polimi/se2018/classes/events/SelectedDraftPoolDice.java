package it.polimi.se2018.classes.events;
import it.polimi.se2018.classes.model.Dice;

import java.io.Serializable;

public class SelectedDraftPoolDice implements Serializable {
     private int dice;

     public SelectedDraftPoolDice(int dice){
         this.dice=dice;
     }

     public int getDice(){
         return dice;
     }
}
