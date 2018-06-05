package it.polimi.se2018.classes.network;

import java.io.*;
import java.net.Socket;

public class SocketClientImplementation extends Thread{
    private Socket socket;
    private DataOutputStream writer;
    private BufferedReader reader;
    private SocketClient client;
    public  SocketClientImplementation(String username, String host, int port, SocketClient client){
        try {
            socket =  new Socket( host, port);
            this.client=client;
            System.out.println(username + " Connected.");

            writer = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));


            this.start();

        } catch (IOException e) {
            System.out.println("Connection Error.");
        }

    }

    @Override
    public void run() {
        String action;
        boolean loop = true;
        while ( loop && !this.socket.isClosed() ) {

            try {
                action=reader.readLine();




            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
