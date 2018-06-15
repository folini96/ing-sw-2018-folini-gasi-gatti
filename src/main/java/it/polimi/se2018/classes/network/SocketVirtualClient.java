package it.polimi.se2018.classes.network;

import com.google.gson.JsonObject;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SocketVirtualClient extends Thread implements VirtualClientInterface{
    private static String SENDMESSAGE = "message";
    private static String SENDPUBLICOBJDECK = "public card deck";
    private static String SENDPRIVATEOBJCARD = "private card";
    private static String SENDTOOLDECK = "tool card deck";
    private static String SENDWINDOW = "window";
    private static String SENDDRAFT = "draft";
    private static String SENDTOKEN = "favor token";

    private String username;
    private Socket socket;
    private DataOutputStream writer;
    private DataInputStream reader;
    public SocketVirtualClient(Socket clientConnection, String username){
        socket=clientConnection;
        this.username=username;
        try{
            writer = new DataOutputStream(clientConnection.getOutputStream());
            reader = new DataInputStream(clientConnection.getInputStream());
        }catch (IOException e){
            System.out.println("Errore di connessione");
        }
    }

    public String getUsername(){
        return username;
    }
    public void notValideMoveMessage (Message message){
        try{
            writer.writeUTF(SENDMESSAGE);
            writer.writeUTF(message.getMessage());
            
        }catch (IOException e){
            System.out.println("Errore nell'invio del messaggio");
        }

    }
    public void sendToClient(ModelViewEvent modelViewEvent){

    }
    public void sendWindowToChose (WindowToChoseEvent windowToChoseEvent){

    }

    public void run(){

    }
}
