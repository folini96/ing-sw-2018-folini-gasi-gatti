package it.polimi.se2018.classes.visitor;

import it.polimi.se2018.classes.events.*;

/**
 * @author Andrea Folini
 * pattern visitor used to recognize the events passed with observer pattern
 */
public interface ViewControllerVisitor {
    /**
     * @param window the event to visit
     */
    void visit(ChoseWindowEvent window);

    /**
     * @param placeDiceEvent the event to visit
     */
    void visit(PlaceDiceEvent placeDiceEvent);
    /**
     * @param toolCard the event to visit
     */
    void visit(UseToolCardEvent toolCard);
    /**
     * @param endTurnEvent the event to visit
     */
    void visit(EndTurnEvent endTurnEvent);
    /**
     * @param moveDiceEvent the event to visit
     */
    void visit(MoveDiceEvent moveDiceEvent);
    /**
     * @param modifyDiceEvent the event to visit
     */
    void visit(ModifyDiceEvent modifyDiceEvent);
    /**
     * @param rerollDraftEvent the event to visit
     */
    void visit(RerollDraftEvent rerollDraftEvent);
    /**
     * @param exchangeEvent the event to visit
     */
    void visit (ExchangeEvent exchangeEvent);
    /**
     * @param setValueEvent the event to visit
     */
    void visit (SetValueEvent setValueEvent);
    /**
     * @param reconnectClientEvent the event to visit
     */
    void visit (ReconnectClientEvent reconnectClientEvent);
    /**
     * @param connectionErrorEvent the event to visit
     */
    void visit (ConnectionErrorEvent connectionErrorEvent);
}
