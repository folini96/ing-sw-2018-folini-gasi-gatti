package it.polimi.se2018;

import it.polimi.se2018.classes.*;
import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.model.MatchHandlerModel;
import it.polimi.se2018.classes.network.RMIClient;
import it.polimi.se2018.classes.network.Server;
import it.polimi.se2018.classes.view.VirtualView;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) {
        Server server=new Server();
        //RMIClient client= new RMIClient();
        //RMIClient client1=new RMIClient();

        server.main();



    }
}
