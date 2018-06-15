package it.polimi.se2018.classes.visitor;

import it.polimi.se2018.classes.events.EndRoundEvent;
import it.polimi.se2018.classes.events.StartMatchEvent;
import it.polimi.se2018.classes.events.StartRoundEvent;
import it.polimi.se2018.classes.events.StartTurnEvent;

public interface ModelViewEventVisitor {

        void visit(StartMatchEvent startMatchEvent);
        void visit (StartRoundEvent startRoundEvent);
        void visit (StartTurnEvent startTurnEvent);
        void visit (EndRoundEvent endRoundEvent);



}
