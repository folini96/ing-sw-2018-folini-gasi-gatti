package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.WindowSide;

import java.io.Serializable;

public class WindowToChoseEvent implements Serializable {
    private WindowSide[] windows;
    public WindowToChoseEvent(WindowSide[] windows){
        this.windows=windows;
    }

    public WindowSide[] getWindows() {
        return windows;
    }
}
