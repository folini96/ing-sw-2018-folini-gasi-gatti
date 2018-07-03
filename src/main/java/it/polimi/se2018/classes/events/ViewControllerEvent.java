package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

/**
 * @author Andrea Folini
 * implemented by the events that arrives from the clients
 */
public interface ViewControllerEvent {
    /**
     * call the right visit method in the visitor
     * @param visitor the visitor that called the method
     */
    void accept(ViewControllerVisitor visitor);
}
