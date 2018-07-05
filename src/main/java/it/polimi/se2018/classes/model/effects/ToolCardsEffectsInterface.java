package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.view.MainScreenController;
import javafx.scene.effect.Effect;

/**
 * implemented by the classes that represent the tool cards' effects
 * @author Alessandro Gatti
 */
public interface ToolCardsEffectsInterface {

    /**
     * @param visitor the visitor from the view
     */
    void accept(MainScreenController visitor);

    /**
     * @return the type of the effect
     */
    EffectType getEffectType();

    /**
     * @return the name of the effect
     */
    String toString();
}
