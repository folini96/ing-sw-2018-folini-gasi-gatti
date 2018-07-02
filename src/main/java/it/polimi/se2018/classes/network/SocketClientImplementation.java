package it.polimi.se2018.classes.network;



import it.polimi.se2018.classes.events.ModelViewEvent;
import it.polimi.se2018.classes.events.ViewControllerEvent;
import it.polimi.se2018.classes.events.WindowToChoseEvent;

import javax.swing.text.View;
import java.io.*;
import java.net.Socket;

public class SocketClientImplementation extends Thread{
    private static final String ASK_USERNAME="username";
    private static final String OK_USERNAME ="ok username";
    private static final String WINDOW_TO_CHOSE="window to chose";
    private static final String VIEW_CONTROLLER="view controller event";
    private static final String MODEL_VIEW="model view event";
    private static final String END_BY_TIME = "end by time";
    private static final String RECONNECT = "reconnect";
    private static final String PLAYER_DISCONNECTED = "player disconnected";
    private static final String LAST_PLAYER = "last player left";
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private SocketClient client;
    public  SocketClientImplementation(String username, String host, int port, SocketClient client) throws IOException{

        this.client=client;
        socket =  new Socket( host, port);
        writer = new ObjectOutputStream(socket.getOutputStream());
        sendUsername(username);
        this.start();



    }
    public void sendUsername(String username){
        try{
             writer.reset();
             writer.writeObject(username);
        }catch (IOException e){
            client.connectionLost();
        }
    }
    public void sendToServer(ViewControllerEvent viewControllerEvent){
        try{
            writer.reset();
            writer.writeUnshared(VIEW_CONTROLLER);
            writer.writeUnshared(viewControllerEvent);
        }catch (IOException e){
            client.connectionLost();
        }
    }
    public void reconnect(){
        try{
            writer.reset();
            writer.writeUnshared(RECONNECT);
        }catch (IOException e){
            client.connectionLost();
        }
    }
    @Override
    public void run() {
        try{
            reader = new ObjectInputStream(socket.getInputStream());

        }catch(IOException e){
            client.connectionLost();
        }
        String action;
        boolean loop = true;
        while ( loop && !this.socket.isClosed() ) {

            try {
                 action=(String)reader.readUnshared();
                 switch (action){
                    case ASK_USERNAME:
                        socket.close();
                        client.askUsername();
                        break;
                    case OK_USERNAME:
                        client.okUsername();
                        break;
                    case WINDOW_TO_CHOSE:
                        client.sendWindowToChose((WindowToChoseEvent) reader.readUnshared());
                        break;
                    case MODEL_VIEW:
                        client.sendToClient((ModelViewEvent)reader.readUnshared());
                        break;
                     case END_BY_TIME:
                         client.endByTime((String)reader.readUnshared());
                         break;
                     case PLAYER_DISCONNECTED:
                         client.playerDisconnected((String)reader.readUnshared());
                         break;
                     case LAST_PLAYER:
                         client.lastPlayerLeft();
                         break;
                }


            } catch (Exception e) {
                client.connectionLost();
                loop=false;
            }

        }
    }
    public void closeConnection(){
        try {
            socket.close();
        }catch (IOException e){
            //this exception won't cause any problem with the software, as this method is called at the end of the game
        }

    }


}
