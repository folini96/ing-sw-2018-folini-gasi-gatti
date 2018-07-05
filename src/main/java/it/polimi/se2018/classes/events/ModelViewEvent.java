package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;
/**
 * @author Andrea Folini
 * implemented by the events that arrives from the model
 */
public interface ModelViewEvent {
    /**
     * call the right visit method in the visitor
     * @param visitor the visitor that called the method
     */
    void accept(ModelViewEventVisitor visitor);


}
