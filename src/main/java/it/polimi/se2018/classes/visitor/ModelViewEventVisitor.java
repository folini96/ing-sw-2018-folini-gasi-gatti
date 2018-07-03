package it.polimi.se2018.classes.visitor;

import it.polimi.se2018.classes.events.*;

/**
 * @author Andrea Folini
 * pattern visitor used to recognize the events passed with observer pattern
 */
public interface ModelViewEventVisitor {
    /**
     @param message the event to visit
     */
    void visit (Message message);

    /**
     * @param startMatchEvent the event to visit
     */
    void visit(StartMatchEvent startMatchEvent);

    /**
     * @param startRoundEvent the event to visit
     */
    void visit (StartRoundEvent startRoundEvent);
    /**
     * @param startTurnEvent the event to visit
     */
    void visit (StartTurnEvent startTurnEvent);
    /**
     * @param endRoundEvent the event to visit
     */
    void visit (EndRoundEvent endRoundEvent);
    /**
     * @param modifiedWindowEvent the event to visit
     */
    void visit (ModifiedWindowEvent modifiedWindowEvent);
    /**
     * @param modifiedDraftEvent the event to visit
     */
    void visit (ModifiedDraftEvent modifiedDraftEvent);
    /**
     * @param endMatchEvent the event to visit
     */
    void visit (EndMatchEvent endMatchEvent);
    /**
     * @param effectEvent the event to visit
     */
    void visit (SendEffectEvent effectEvent);
    /**
     * @param modifiedRoundTrack the event to visit
     */
    void visit (ModifiedRoundTrack modifiedRoundTrack);
    /**
     * @param newDiceFromBagEvent the event to visit
     */
    void visit (NewDiceFromBagEvent newDiceFromBagEvent);
    /**
     * @param modifiedTokenEvent the event to visit
     */
    void visit (ModifiedTokenEvent modifiedTokenEvent);
    /**
     * @param updateReconnectedClientEvent the event to visit
     */
    void visit (UpdateReconnectedClientEvent updateReconnectedClientEvent);
}
