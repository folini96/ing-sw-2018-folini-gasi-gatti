package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.WindowSide;

import java.io.Serializable;

/**
 * contains the windows that a player can choose
 * @author Andrea Folini
 */
public class WindowToChoseEvent implements Serializable {
    private WindowSide[] windows;
    private int timer;

    /**
     * constructor
     * @param windows the 4 windows for the choice
     * @param timer the time that every player has to take the decision
     */
    public WindowToChoseEvent(WindowSide[] windows, int timer){
        this.windows=windows;
        this.timer = timer;
    }

    /**
     * @return the windows
     */
    public WindowSide[] getWindows() {
        return windows;
    }

    /**
     * @return the time
     */
    public int getTimer() {
        return timer;
    }
}
