package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.network.Server;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class VirtualView implements Observer, ViewInterface{
    Server server;
    MatchHandlerController controller;
    public VirtualView(Server server, MatchHandlerController controller){
        this.server=server;
        this.controller=controller;
    }
    public VirtualView(){

    }


    @Override
    public void update(Observable model, Object arg) {
        ModelViewEvent event=(ModelViewEvent)arg;
        event.accept(this);
    }

    public void showMessage (Message message){

    }


    public void visit(PublicObjCard publicObjCard){
        //server.sendPublicObjCard(publicObjCard);
        System.out.println("ok");
    }
    public void visit(Player player){
        server.sendPrivateObjCard(player.getPrivateObj(), player.getName());
    }
    public void visit(ToolCard toolCard){
        server.sendToolCard(toolCard);
    }
    public void visit(WindowSide window){
        server.sendWindowSide(window);
    }
    public void visit(Dice dice){
        server.sendDice(dice);
    }
    public void visit(Round round){
        server.sendRound(round);
    }
    public void visit(int removedToken){
        server.removeFavorToken(removedToken);
    }
}
