package it.polimi.se2018.classes.visitor;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.effects.Modify;

public interface ViewControllerVisitor {
    void visit(ChoseWindowEvent window);
    void visit(PlaceDiceEvent placeDiceEvent);
    void visit(UseToolCardEvent toolCard);
    void visit(EndTurnEvent endTurnEvent);
    void visit(MoveDiceEvent moveDiceEvent);
    void visit(ModifyDiceEvent modifyDiceEvent);
    void visit(RerollDraftEvent rerollDraftEvent);
    void visit (ExchangeEvent exchangeEvent);
    void visit (SetValueEvent setValueEvent);
    void visit (ReconnectClientEvent reconnectClientEvent);
    void visit (ConnectionErrorEvent connectionErrorEvent);
}
