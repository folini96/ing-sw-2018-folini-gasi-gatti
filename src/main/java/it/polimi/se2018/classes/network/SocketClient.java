package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.view.GUIHandler;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
import java.io.IOException;

/**
 * intermediary between the client implementation and the interface handler
 */
public class SocketClient implements ClientInterface,ModelViewEventVisitor {
    private SocketClientImplementation clientImplementation;
    private GUIHandler interfaceHandler;
    private String username;
    private boolean matchEnded;
    public void SocketClient(){

    }
    public void main(String username, GUIHandler interfaceHandler,String serverIp,int serverPort) {
        this.username=username;
        matchEnded=false;
        this.interfaceHandler=interfaceHandler;
        try{
            clientImplementation= new SocketClientImplementation(username,serverIp,serverPort, this);
        }catch(IOException e){
            interfaceHandler.createClientError();
        }
    }

    public void sendToServer(ViewControllerEvent viewControllerEvent){
        clientImplementation.sendToServer(viewControllerEvent);
    }

    /**
     * notify the interface handler that the chosen username has been accepted
     */
    public void okUsername(){
        interfaceHandler.okUsername(username);
    }

    /**
     * send to the interface handler the windows
     * @param windowToChoseEvent contains 4 windows
     */
    public void sendWindowToChose(WindowToChoseEvent windowToChoseEvent){
        interfaceHandler.windowToChose(windowToChoseEvent);
    }

    /**
     * notify the interface handler that the username has been rejected and ask for a new one
     */
    public void askUsername(){
        interfaceHandler.askUsername();
    }

    /**
     * chose the right method  with the pattern visitor to notify the interface handler with the right event
     * @param modelViewEvent the event from the network
     */
    public void sendToClient(ModelViewEvent modelViewEvent){
        modelViewEvent.accept(this);
    }
    public void visit (Message message){
        interfaceHandler.showMessage(message);
    }
    public void visit(StartMatchEvent startMatchEvent){
        interfaceHandler.startMatch(startMatchEvent);
    }
    public void visit (StartRoundEvent startRoundEvent){
        interfaceHandler.startRound(startRoundEvent);

    }
    public void visit (StartTurnEvent startTurnEvent){
        interfaceHandler.startTurn(startTurnEvent);

    }
    public void visit (EndRoundEvent endRoundEvent){
        interfaceHandler.endRound(endRoundEvent);
    }
    public void visit (ModifiedWindowEvent modifiedWindowEvent){
        interfaceHandler.modifiedWindow(modifiedWindowEvent);
    }
    public void visit (ModifiedDraftEvent modifiedDraftEvent){
        interfaceHandler.modifiedDraft(modifiedDraftEvent);
    }
    public void visit (EndMatchEvent endMatchEvent){
        interfaceHandler.endMatch(endMatchEvent);
    }
    public void visit (SendEffectEvent effectEvent){
        interfaceHandler.sendEffect(effectEvent);
    }
    public void visit (ModifiedRoundTrack modifiedRoundTrack){
        interfaceHandler.modifiedRoundTrack(modifiedRoundTrack);
    }
    public void visit (NewDiceFromBagEvent newDiceFromBagEvent){
        interfaceHandler.askNewDiceValue(newDiceFromBagEvent);
    }
    public void visit (ModifiedTokenEvent modifiedTokenEvent){
        interfaceHandler.modifiedToken(modifiedTokenEvent);
    }
    public void visit (UpdateReconnectedClientEvent updateReconnectedClientEvent){
        interfaceHandler.updateReconnectedPlayer(updateReconnectedClientEvent);
    }

    /**
     * notify the interface handler that a turn ended because the time expired and a client will be suspended
     * @param player the player that will be suspended
     */
    public void endByTime(String player){
        interfaceHandler.endByTime(player);
    }
    public void reconnect(String username){
        clientImplementation.reconnect();
    }

    /**
     * notify the interface handler that another player got disconnected
     * @param player the disconnected player
     */
    public void playerDisconnected(String player){
        interfaceHandler.disconnectedPlayer(player);
    }

    /**
     * notify the interface handler that there are no other player in the game
     */
    public void lastPlayerLeft(){
        interfaceHandler.lastPlayerLeft();
    }

    /**
     * notify the interface handler that connection with the server has been lost
     */
    public void connectionLost(){
        if (!matchEnded){
            interfaceHandler.connectionToServerLost();
        }

    }
    public void deleteAfterMatch(){
        matchEnded=true;
        clientImplementation.closeConnection();
    }

    /**
     * notify the interface handler that a game ended, in the case that the player is still suspended
     */
    public void gameEnded(){
        interfaceHandler.gameEnded();
    }

}
