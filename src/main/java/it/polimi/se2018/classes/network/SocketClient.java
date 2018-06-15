package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient implements ClientInterface{
    private static final int PORT = 9000;
    private static final String HOST = "localhost";
    public void main(String username) {

    }

    public void sendToServer(ViewControllerEvent viewControllerEvent){

    }


}
