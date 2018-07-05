package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

/**
 * @author Andrea Folini
 * used from the server to notify the controller that a client lost the connection
 */
public class ConnectionErrorEvent implements ViewControllerEvent{
    private String disconnectedClient;

    /**
     * constructor
     * @param disconnectedClient the name of the disconnected client
     */
    public ConnectionErrorEvent(String disconnectedClient){
        this.disconnectedClient=disconnectedClient;
    }

    /**
     * @return the name of the disconnected client
     */
    public String getDisconnectedClient() {
        return disconnectedClient;
    }

    /**
     * @param visitor the visitor that called the method
     */
    @Override
    public void accept(ViewControllerVisitor visitor) {
        visitor.visit(this);
    }
}
