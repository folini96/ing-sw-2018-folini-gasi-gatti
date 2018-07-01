package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

public class ConnectionErrorEvent implements ViewControllerEvent{
    String disconnectedClient;
    public ConnectionErrorEvent(String disconnectedClient){
        this.disconnectedClient=disconnectedClient;
    }

    public String getDisconnectedClient() {
        return disconnectedClient;
    }

    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
