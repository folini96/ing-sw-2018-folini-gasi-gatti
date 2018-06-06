package it.polimi.se2018.classes.model;

import it.polimi.se2018.classes.view.VirtualView;

public interface ModelViewEvent {
    void accept(VirtualView visitor);


}
