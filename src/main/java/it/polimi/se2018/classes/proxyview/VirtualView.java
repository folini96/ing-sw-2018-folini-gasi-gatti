package it.polimi.se2018.classes.proxyview;

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

    /**
     * @param message the event to visit
     */
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

    /**
     * @param modifiedWindowEvent the event to visit
     */
    public void visit (ModifiedWindowEvent modifiedWindowEvent){
        server.sendModifiedWindow(modifiedWindowEvent, matchNumber);
    }

    /**
     * @param startMatchEvent the event to visit
     */
    public void visit(StartMatchEvent startMatchEvent){
        server.sendStartMatchEvent(startMatchEvent, matchNumber);
    }

    /**
     * @param startRoundEvent the event to visit
     */
    public void visit (StartRoundEvent startRoundEvent){
        server.sendStartRoundEvent(startRoundEvent, matchNumber);
    }

    /**
     * @param startTurnEvent the event to visit
     */
    public void visit (StartTurnEvent startTurnEvent){
        server.sendStartTurnEvent(startTurnEvent, matchNumber);
    }

    /**
     * @param endRoundEvent the event to visit
     */
    public void visit (EndRoundEvent endRoundEvent){
        server.sendEndRoundEvent(endRoundEvent, matchNumber);
    }

    /**
     * @param modifiedDraftEvent the event to visit
     */
    public void visit (ModifiedDraftEvent modifiedDraftEvent){server.sendModifiedDraft(modifiedDraftEvent, matchNumber);}

    /**
     * @param endMatchEvent the event to visit
     */
    public void visit (EndMatchEvent endMatchEvent){
        server.sendEndMatchEvent(endMatchEvent, matchNumber);
    }

    /**
     * @param effectEvent the event to visit
     */
    public void visit (SendEffectEvent effectEvent){
        server.sendEffect(effectEvent,matchNumber);
    }

    /**
     * @param modifiedRoundTrack the event to visit
     */
    public void visit (ModifiedRoundTrack modifiedRoundTrack){
        server.sendModifiedRoundTrack(modifiedRoundTrack,matchNumber);
    }

    /**
     * @param newDiceFromBagEvent the event to visit
     */
    public void visit (NewDiceFromBagEvent newDiceFromBagEvent){
        server.sendNewDiceFromBag(newDiceFromBagEvent,matchNumber);
    }

    /**
     * @param modifiedTokenEvent the event to visit
     */
    public void visit (ModifiedTokenEvent modifiedTokenEvent){
        server.sendModifiedToken(modifiedTokenEvent,matchNumber);
    }

    /**
     * @param updateReconnectedClientEvent the event to visit
     */
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
     * notify to the controller that a player reconnected after suspension
     * @param reconnectClientEvent contains the name of the player reconnected
     */
    public void reconnect(ReconnectClientEvent reconnectClientEvent){
        setChanged();
        notifyObservers(reconnectClientEvent);
    }

    /**
     * notify that a player has been disconnected to the controller, so it can end his turn if the player was the one currently playing
     * @param connectionErrorEvent the event that represent a connection error of a client
     */
    public void connectionError(ConnectionErrorEvent connectionErrorEvent){
        setChanged();
        notifyObservers(connectionErrorEvent);
    }

    /**
     * cancel the server timer used for the window selection; used once every player in a match chose his window
     */
    public void cancelWindowTimer(){
        server.cancelWindowTimer(matchNumber);
    }
}
