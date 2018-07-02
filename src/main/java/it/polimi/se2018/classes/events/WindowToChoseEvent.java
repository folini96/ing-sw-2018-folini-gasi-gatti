package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.WindowSide;

import java.io.Serializable;

public class WindowToChoseEvent implements Serializable {
    private WindowSide[] windows;
    private int timer;
    public WindowToChoseEvent(WindowSide[] windows, int timer){
        this.windows=windows;
        this.timer = timer;
    }

    public WindowSide[] getWindows() {
        return windows;
    }


    public int getTimer() {
        return timer;
    }
}
