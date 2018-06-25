package it.polimi.se2018.classes.visitor;

import it.polimi.se2018.classes.events.*;

public interface ViewControllerVisitor {
    void visit(ChoseWindowEvent window);
    void visit(PlaceDiceEvent placeDiceEvent);
    void visit(UseToolCardEvent toolCard);
    void visit(EndTurnEvent endTurnEvent);
    void visit(MoveDiceEvent moveDiceEvent);
}
