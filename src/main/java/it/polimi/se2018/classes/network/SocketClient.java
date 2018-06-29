package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.view.GUIHandler;
import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient implements ClientInterface,ModelViewEventVisitor {
    private static final int PORT = 5463;
    private static final String HOST = "localhost";
    private SocketClientImplementation clientImplementation;
    private GUIHandler interfaceHandler;
    private String username;
    public void SocketClient(){

    }
    public void main(String username, GUIHandler interfaceHandler) {
        this.username=username;
        this.interfaceHandler=interfaceHandler;
        clientImplementation= new SocketClientImplementation(username,HOST,PORT, this);
    }

    public void sendToServer(ViewControllerEvent viewControllerEvent){
        clientImplementation.sendToServer(viewControllerEvent);
    }
    public void newUsername(String username){
        clientImplementation.sendUsername(username);
    }
    public void okUsername(){
        interfaceHandler.okUsername(username);
    }
    public void sendWindowToChose(WindowToChoseEvent windowToChoseEvent){
        interfaceHandler.windowToChose(windowToChoseEvent);
    }
    public void askUsername(){
        interfaceHandler.askUsername();
    }
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
    public void endByTime(){
        interfaceHandler.endByTime();
    }
}
