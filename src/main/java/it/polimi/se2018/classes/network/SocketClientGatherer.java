package it.polimi.se2018.classes.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClientGatherer extends Thread {
    private static final String ASK_USERNAME="username";
    private static final String OK_USERNAME ="ok username";
    private Server server;
    private final int port;
    private ServerSocket serverSocket;

    public SocketClientGatherer( Server server, int port) {
        this.server = server;
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true) {

            Socket newSocketClientConnection;
            try {

                newSocketClientConnection = serverSocket.accept();
                try {
                    String username;
                    ObjectInputStream getUsername = new ObjectInputStream(newSocketClientConnection.getInputStream());
                    username = (String)getUsername.readObject();
                    ObjectOutputStream askUsername;
                    askUsername = new ObjectOutputStream(newSocketClientConnection.getOutputStream());
                    if (!server.checkUsername(username)) {
                        askUsername.writeObject(ASK_USERNAME);
                     }else{
                        askUsername.writeObject(OK_USERNAME);
                        server.addClient(new SocketVirtualClient(newSocketClientConnection,username,server,askUsername,getUsername));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
