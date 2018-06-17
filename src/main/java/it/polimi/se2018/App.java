package it.polimi.se2018;


import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.events.StartRoundEvent;
import it.polimi.se2018.classes.events.StartTurnEvent;
import it.polimi.se2018.classes.model.Dice;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.network.ClientInterface;
import it.polimi.se2018.classes.network.RMIClient;
import it.polimi.se2018.classes.network.Server;
import it.polimi.se2018.classes.view.GUIHandler;
import it.polimi.se2018.classes.view.VirtualView;
import javafx.application.Application;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public void start(Stage primaryStage){

        GUIHandler prova = new GUIHandler();
        prova.main(primaryStage);

    }

}
