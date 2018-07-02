package it.polimi.se2018;

import it.polimi.se2018.classes.network.Server;
import it.polimi.se2018.classes.view.GUIHandler;

import java.util.Scanner;

public class App{

    public static void main(String[] args){
        String type;
        int socketPort;
        int rmiPort;
        int playTime;
        int lobbyTime;
        Server server;
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Premere s per aprire il server o c per aprire un client");
        type=reader.nextLine();
        if (type.equals("s")){
            System.out.println("Scegliere la porta per Socket");
            socketPort=reader.nextInt();
            System.out.println("Scegliere la porta per RMI");
            rmiPort=reader.nextInt();
            System.out.println("Scegliere il tempo di attesa per la creazione della lobby (in secondi)");
            lobbyTime=reader.nextInt();
            System.out.println("Scegliere il tempo disponibile per ogni turno (in secondi)");
            playTime=reader.nextInt();
            server=new Server();
            server.main(rmiPort,socketPort,lobbyTime,playTime);
        }else if (type.equals("c")){
            javafx.application.Application.launch(GUIHandler.class);
        }
    }
}

