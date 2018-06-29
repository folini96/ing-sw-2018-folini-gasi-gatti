package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.network.Server;

import javax.swing.text.html.parser.Entity;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class VirtualView extends Observable implements Observer,ModelViewEventVisitor {
    private Server server;
    private int matchNumber;
    public VirtualView(Server server, int matchNumber){
        this.server=server;
        this.matchNumber=matchNumber;
    }



    @Override
    public void update(Observable model, Object arg) {
        ModelViewEvent event=(ModelViewEvent)arg;
        event.accept(this);
    }

    public void visit (Message message){
        server.notValideMoveMessage(message, matchNumber);
    }

    public void sendToServer(ViewControllerEvent viewControllerEvent){
        setChanged();
        notifyObservers(viewControllerEvent);
    }
    public void visit (ModifiedWindowEvent modifiedWindowEvent){
        server.sendModifiedWindow(modifiedWindowEvent, matchNumber);
    }
    public void visit(StartMatchEvent startMatchEvent){
        server.sendStartMatchEvent(startMatchEvent, matchNumber);
    }
    public void visit (StartRoundEvent startRoundEvent){
        server.sendStartRoundEvent(startRoundEvent, matchNumber);
    }
    public void visit (StartTurnEvent startTurnEvent){
        server.sendStartTurnEvent(startTurnEvent, matchNumber);
    }
    public void visit (EndRoundEvent endRoundEvent){
        server.sendEndRoundEvent(endRoundEvent, matchNumber);
    }
    public void visit (ModifiedDraftEvent modifiedDraftEvent){server.sendModifiedDraft(modifiedDraftEvent, matchNumber);}
    public void visit (EndMatchEvent endMatchEvent){
        server.sendEndMatchEvent(endMatchEvent, matchNumber);
    }
    public void visit (SendEffectEvent effectEvent){
        server.sendEffect(effectEvent,matchNumber);
    }
    public void visit (ModifiedRoundTrack modifiedRoundTrack){
        server.sendModifiedRoundTrack(modifiedRoundTrack,matchNumber);
    }
    public void visit (NewDiceFromBagEvent newDiceFromBagEvent){
        server.sendNewDiceFromBag(newDiceFromBagEvent,matchNumber);
    }
    public void visit (ModifiedTokenEvent modifiedTokenEvent){
        server.sendModifiedToken(modifiedTokenEvent,matchNumber);
    }
    public void windowToChose(WindowSide[] windows){
        server.sendWindowToChose(windows, matchNumber);
    }
    public void cancelTimer(){
        server.cancelTimer(matchNumber);
    }

}
