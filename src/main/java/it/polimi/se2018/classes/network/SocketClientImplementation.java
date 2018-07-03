package it.polimi.se2018.classes.network;



import it.polimi.se2018.classes.events.ModelViewEvent;
import it.polimi.se2018.classes.events.ViewControllerEvent;
import it.polimi.se2018.classes.events.WindowToChoseEvent;

import java.io.*;
import java.net.Socket;

/**
 * @author Andrea Folini
 * send the events to the server and listen for the messages sent on the socket
 */
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
    private static final String END_GAME="end game";
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private SocketClient client;

    /**
     * constructor
     * @param username name sent to the server
     * @param host ip of the server
     * @param port port of the server
     * @param client the references of the class used to communicate with the interface handler
     * @throws IOException caused by an error opening the socket
     */
    public  SocketClientImplementation(String username, String host, int port, SocketClient client) throws IOException{

        this.client=client;
        socket =  new Socket( host, port);
        writer = new ObjectOutputStream(socket.getOutputStream());
        sendUsername(username);
        this.start();



    }

    /**
     * send the client name to the server
     * @param username the name chosen by the player
     */
    public void sendUsername(String username){
        try{
             writer.reset();
             writer.writeObject(username);
        }catch (IOException e){
            client.connectionLost();
        }
    }

    /**
     * send an event to the server
     * @param viewControllerEvent the event from the interface
     */
    public void sendToServer(ViewControllerEvent viewControllerEvent){
        try{
            writer.reset();
            writer.writeUnshared(VIEW_CONTROLLER);
            writer.writeUnshared(viewControllerEvent);
        }catch (IOException e){
            client.connectionLost();
        }
    }

    /**
     * send a request to reconnect the client
     */
    public void reconnect(){
        try{
            writer.reset();
            writer.writeUnshared(RECONNECT);
        }catch (IOException e){
            client.connectionLost();
        }
    }

    /**
     * loop to listen the messages on the socket
     */
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
                     case END_GAME:
                         client.gameEnded();
                         closeConnection();
                         break;

                }


            } catch (Exception e) {
                client.connectionLost();
                loop=false;
            }

        }
    }

    /**
     * close the socket after the match ended
     */
    public void closeConnection(){
        try {
            socket.close();
        }catch (IOException e){
            //this exception won't cause any problem with the software, as this method is called at the end of the game
        }

    }


}
