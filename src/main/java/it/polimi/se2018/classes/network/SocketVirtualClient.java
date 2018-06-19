package it.polimi.se2018.classes.network;

import com.google.gson.JsonObject;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SocketVirtualClient extends Thread implements VirtualClientInterface{
    private static final String WINDOW_TO_CHOSE="window to chose";
    private static final String VIEW_CONTROLLER="view controller event";
    private static final String MODEL_VIEW="model view event";
    private static final String SENDMESSAGE = "message";
    private static final String SENDPUBLICOBJDECK = "public card deck";
    private static final String SENDPRIVATEOBJCARD = "private card";
    private static final String SENDTOOLDECK = "tool card deck";
    private static final String SENDWINDOW = "window";
    private static final String SENDDRAFT = "draft";
    private static final String SENDTOKEN = "favor token";
    private Server server;
    private String username;
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
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
    public void notValideMoveMessage (Message message){


    }
    public void sendToClient(ModelViewEvent modelViewEvent){
        try{
            writer.writeObject(MODEL_VIEW);
            writer.writeObject(modelViewEvent);
        }catch(IOException e){
            e.printStackTrace();
        }


    }
    public void sendWindowToChose (WindowToChoseEvent windowToChoseEvent){
        try{
            writer.reset();
            writer.writeObject(WINDOW_TO_CHOSE);
            writer.writeObject(windowToChoseEvent);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void run(){
        String action;
        boolean loop = true;
        while ( loop && !this.socket.isClosed() ) {

            try {
                action=(String)reader.readObject();
                switch (action){
                    case VIEW_CONTROLLER:
                        server.sendToServer((ViewControllerEvent)reader.readObject());
                        break;


                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
