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
    private Socket socket;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private SocketClient client;
    public  SocketClientImplementation(String username, String host, int port, SocketClient client){

        try {
            this.client=client;
            socket =  new Socket( host, port);
            writer = new ObjectOutputStream(socket.getOutputStream());
            sendUsername(username);
            this.start();
        } catch (IOException e) {
            System.out.println("Connection Error.");
        }


    }
    public void sendUsername(String username){
        try{
             writer.reset();
             writer.writeObject(username);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendToServer(ViewControllerEvent viewControllerEvent){
        try{
            writer.reset();
            writer.writeUnshared(VIEW_CONTROLLER);
            writer.writeUnshared(viewControllerEvent);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try{
            reader = new ObjectInputStream(socket.getInputStream());

        }catch(IOException e){
            e.printStackTrace();
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
                }


            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }


}
