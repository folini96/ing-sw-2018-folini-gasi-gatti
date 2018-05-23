package it.polimi.se2018.classes.model;

import it.polimi.se2018.classes.model.Dice;

import java.util.ArrayList;
import java.util.List;

/**
 *@author Alessandro Gatti
 */
public class Round {

    private ArrayList<Dice> leftDices;

    /**
     * @return the dices that were not used and were placed on the Round Track
     */
    public List<Dice> getLeftDices(){

        return leftDices;

    }


}
