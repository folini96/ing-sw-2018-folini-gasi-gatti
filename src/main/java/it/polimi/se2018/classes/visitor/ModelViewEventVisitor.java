package it.polimi.se2018.classes.visitor;

import it.polimi.se2018.classes.events.*;


public interface ModelViewEventVisitor {
        void visit (Message message);
        void visit(StartMatchEvent startMatchEvent);
        void visit (StartRoundEvent startRoundEvent);
        void visit (StartTurnEvent startTurnEvent);
        void visit (EndRoundEvent endRoundEvent);
        void visit (ModifiedWindowEvent modifiedWindowEvent);
        void visit (ModifiedDraftEvent modifiedDraftEvent);
        void visit (EndMatchEvent endMatchEvent);
        void visit (SendEffectEvent effectEvent);
        void visit (ModifiedRoundTrack modifiedRoundTrack);
        void visit (NewDiceFromBagEvent newDiceFromBagEvent);
        void visit (ModifiedTokenEvent modifiedTokenEvent);
}
