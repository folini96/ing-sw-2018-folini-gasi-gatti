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
    Server server;
    public VirtualView(Server server){
        this.server=server;

    }



    @Override
    public void update(Observable model, Object arg) {
        ModelViewEvent event=(ModelViewEvent)arg;
        event.accept(this);
    }

    public void visit (Message message){
        server.notValideMoveMessage(message);
    }

    public void sendToServer(ViewControllerEvent viewControllerEvent){
        setChanged();
        notifyObservers(viewControllerEvent);
    }
    public void visit(StartMatchEvent startMatchEvent){
        server.sendStartMatchEvent(startMatchEvent);
    }
    public void visit (StartRoundEvent startRoundEvent){
        server.sendStartRoundEvent(startRoundEvent);
    }
    public void visit (StartTurnEvent startTurnEvent){
        server.sendStartTurnEvent(startTurnEvent);
    }
    public void visit (EndRoundEvent endRoundEvent){
        server.sendEndRoundEvent(endRoundEvent);
    }
    /*public void visit(int removedToken){
        server.removeFavorToken(removedToken);
    }*/
    public void windowToChose(WindowSide[] windows){
        server.sendWindowToChose(windows);
    }
}
