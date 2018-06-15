package it.polimi.se2018.classes.visitor;

import it.polimi.se2018.classes.events.ChoseWindowEvent;
import it.polimi.se2018.classes.events.EndTurnEvent;
import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.events.UseToolCardEvent;

public interface ViewControllerVisitor {
    void visit(ChoseWindowEvent window);
    void visit(PlaceDiceEvent placeDiceEvent);
    void visit(UseToolCardEvent toolCard);
    void visit(EndTurnEvent endTurnEvent);
}
