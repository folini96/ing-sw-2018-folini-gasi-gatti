package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.controller.MatchHandlerController;
import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.SelectedCoordinate;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.network.Server;

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

        //model and virtual view data sharing still needs to be implemented
    }

    public void showMessage (Message message){

    }

    private void sendPublicObjDeck(PublicObjCard[] publicObjCards){
        server.sendPublicObjDeck(publicObjCards);
    }
    private void sendPrivateCard (PrivateObjCard privateObjCard){

    }

    private void sendToolCardDeck (ToolCard[] toolDeck){

    }
    private void sendWindow(WindowSide window){

    }
    private void sendDraftPool(ArrayList<Dice> draftPool){

    }
    private void sendRoundTrack(Round[] roundTrack){

    }
    private void removeFavorToken(int removedToken){

    }
}
