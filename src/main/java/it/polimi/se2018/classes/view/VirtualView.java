package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.network.Server;

import java.util.Observable;
import java.util.Observer;

/**
 * class used as intermediary between the network and the model and the controller of the match
 * @author Andrea Folini
 */
public class VirtualView extends Observable implements Observer,ModelViewEventVisitor {
    private Server server;
    private int matchNumber;

    /**
     * constructor
     * @param server is the server of the game
     * @param matchNumber is the number used by the server to recognize this virtual
     */
    public VirtualView(Server server, int matchNumber){
        this.server=server;
        this.matchNumber=matchNumber;
    }

    /**
     * @return the number of the match
     */
    public int getMatchNumber() {
        return matchNumber;
    }

    /**
     * update used by the observable to notify changes
     * @param model the observable the send the notification
     * @param arg the object of the notification
     */
    @Override
    public void update(Observable model, Object arg) {
        ModelViewEvent event=(ModelViewEvent)arg;
        event.accept(this);
    }


    public void visit (Message message){
        server.notValideMoveMessage(message, matchNumber);
    }

    /**
     * send a notification to the controller with the event that happened in the interface
     * @param viewControllerEvent the object that will be send
     */
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
    public void visit (UpdateReconnectedClientEvent updateReconnectedClientEvent){
        server.sendReconnectionUpdate(updateReconnectedClientEvent,matchNumber);
    }

    /**
     * send to the server the windows that the players need to chose
     * @param windows array containing all the windows
     */
    public void windowToChose(WindowSide[] windows){
        server.sendWindowToChose(windows, matchNumber);
    }

    /**
     * cancel the server timer used for the turns; used every time that a player end his turn
     */
    public void cancelTimer(){
        server.cancelTimer(matchNumber);
    }

    /**
     * notify to the controller that a player riconnected after suspension
     * @param reconnectClientEvent
     */
    public void reconnect(ReconnectClientEvent reconnectClientEvent){
        setChanged();
        notifyObservers(reconnectClientEvent);
    }

    /**
     * notify that a player has been disconnected to the controller, so it can end his turn if the player was the one currently playing
     * @param connectionErrorEvent
     */
    public void connectionError(ConnectionErrorEvent connectionErrorEvent){
        setChanged();
        notifyObservers(connectionErrorEvent);
    }
}
