package it.polimi.se2018.classes.model.effects;

import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.view.MainScreenController;
import javafx.scene.effect.Effect;

public interface ToolCardsEffectsInterface {

    default void useEffect() {

    }
    void accept(MainScreenController visitor);
    EffectType getEffectType();
}
