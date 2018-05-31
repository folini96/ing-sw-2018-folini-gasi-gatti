package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.model.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ViewInterface {
    void showMessage (Message message);
    //Interface that will be implemented in VirtualView and View classes. There will be the methods that the controller
    //will request to the view (to implement tool cards effects)
}
