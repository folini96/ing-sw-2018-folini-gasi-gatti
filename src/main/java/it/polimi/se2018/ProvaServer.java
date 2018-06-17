package it.polimi.se2018;

import it.polimi.se2018.classes.network.RMIClient;
import it.polimi.se2018.classes.network.Server;

public class ProvaServer {
    public static void main( String[] args ) {
        Server server=new Server();
        //RMIClient client= new RMIClient();
        //RMIClient client1=new RMIClient();

        server.main();
        //client.main("prova");

    }
}
