package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.view.VirtualView;

public interface ModelViewEvent {
    void accept(VirtualView visitor);


}
