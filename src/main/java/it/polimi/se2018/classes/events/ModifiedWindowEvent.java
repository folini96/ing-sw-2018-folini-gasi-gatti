package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.model.WindowSide;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

public class ModifiedWindowEvent implements ModelViewEvent,Serializable {
    String player;
    WindowSide window;
    public ModifiedWindowEvent(String player, WindowSide window){
        this.player=player;
        this.window=window;
    }

    public String getPlayer() {
        return player;
    }

    public WindowSide getWindow() {
        return window;
    }

    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }
}
