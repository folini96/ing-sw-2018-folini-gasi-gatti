package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.view.VirtualView;

public interface ViewControllerEvent {
    void accept(MatchHandlerController visitor);
}
