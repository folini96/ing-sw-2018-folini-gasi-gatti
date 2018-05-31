package it.polimi.se2018.classes.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClientGatherer extends Thread {
    private Server server;
    private final int port;
    private ServerSocket serverSocket;


    public SocketClientGatherer( Server server, int port) {
        this.server = server;
        this.port = port;

        // Inizializzo il server socket
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        String username="default";
        // In loop attendo la connessione di nuovi client

        System.out.println("Waiting for clients.\n");

        while(true) {

            Socket newSocketClientConnection;

            try {

                newSocketClientConnection = serverSocket.accept();
                try {
                    BufferedReader getUsername = new BufferedReader(new InputStreamReader(newSocketClientConnection.getInputStream()));
                    username = getUsername.readLine();
                    while (!server.checkUsername(username)) {
                        OutputStreamWriter askUsername;
                        System.out.println("The username "+username+" is already use. Waiting for another username");

                        try {
                            askUsername = new OutputStreamWriter(newSocketClientConnection.getOutputStream());
                            askUsername.write("New Username");
                            askUsername.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                server.addClient(new SocketVirtualClient(newSocketClientConnection,username));


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
