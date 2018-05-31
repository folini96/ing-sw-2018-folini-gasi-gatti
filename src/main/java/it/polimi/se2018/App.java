package it.polimi.se2018;

import it.polimi.se2018.classes.*;
import it.polimi.se2018.classes.events.Message;
import it.polimi.se2018.classes.network.RMIClient;
import it.polimi.se2018.classes.network.Server;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) {
        Server server=new Server();
        RMIClient client= new RMIClient();
        RMIClient client1=new RMIClient();

        server.main();
        //client.main("Icardi");
        //client1.main("Icardi");


    }
}
