package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.view.VirtualView;
import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

public interface ViewControllerEvent {
    void accept(ViewControllerVisitor visitor);
}
