package it.polimi.se2018.classes.network;


import it.polimi.se2018.classes.events.*;

import java.io.*;
import java.net.Socket;

/**
 * @author Andrea Folini
 * write and read the message exchanged with this client
 */
public class SocketVirtualClient extends Thread implements VirtualClientInterface{
    private static final String WINDOW_TO_CHOSE="window to chose";
    private static final String VIEW_CONTROLLER="view controller event";
    private static final String MODEL_VIEW="model view event";
    private static final String END_BY_TIME = "end by time";
    private static final String PING = "ping";
    private static final String RECONNECT = "reconnect";
    private static final String PLAYER_DISCONNECTED = "player disconnected";
    private static final String LAST_PLAYER = "last player left";
    private static final String END_GAME="end game";
    private Server server;
    private String username;
    private Socket socket;
    private int lobbyNumber;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;

    /**
     * constructor
     * @param clientConnection the socket of this client
     * @param username name of the client
     * @param server reference to the server
     * @param writer output stream of the socket
     * @param reader input stream of the socket
     */
    public SocketVirtualClient(Socket clientConnection, String username,Server server,ObjectOutputStream writer,ObjectInputStream reader){
        this.server=server;
        socket=clientConnection;
        this.username=username;
        this.writer = writer;
        this.reader = reader;
        this.start();

    }

    public String getUsername(){
        return username;
    }

    public void sendToClient(ModelViewEvent modelViewEvent) throws IOException {

            writer.reset();
            writer.writeUnshared(MODEL_VIEW);
            writer.writeUnshared(modelViewEvent);
       }
    public void sendWindowToChose (WindowToChoseEvent windowToChoseEvent){
        try{
            writer.reset();
            writer.writeUnshared(WINDOW_TO_CHOSE);
            writer.writeUnshared(windowToChoseEvent);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public int getLobbyNumber(){
        return lobbyNumber;
    }
    public void setLobbyNumber(int number){
        this.lobbyNumber=number;
    }

    /**
     * loop and read the socket input until it's closed
     */
    public void run(){
        String action;
        boolean loop = true;
        while ( loop && !this.socket.isClosed() ) {

            try {
                action=(String)reader.readUnshared();
                switch (action){
                    case VIEW_CONTROLLER:
                        server.sendToServer((ViewControllerEvent)reader.readUnshared(), lobbyNumber,false);
                        break;

                    case RECONNECT:
                        server.reconnectClient(this);
                }


            } catch (Exception e) {
                loop=false;

            }

        }
    }
    public void endByTime(String player) throws IOException{
       writer.reset();
       writer.writeUnshared(END_BY_TIME);
       writer.writeUnshared(player);
    }
    public void ping() throws IOException{
        writer.writeUnshared(PING);
    }
    public void otherPlayerDisconnected(OtherPlayerDisconnectedEvent otherPlayerDisconnectedEvent) throws IOException{
        writer.writeUnshared(PLAYER_DISCONNECTED);
        writer.writeUnshared(otherPlayerDisconnectedEvent.getPlayer());
    }

    public void lastPlayerLeft() throws IOException{
        writer.writeUnshared(LAST_PLAYER);
    }
    public void notifyEndMatchToDisconnected()throws IOException{
        writer.writeUnshared(END_GAME);
    }
}
