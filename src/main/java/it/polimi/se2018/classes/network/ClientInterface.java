package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.events.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public interface ClientInterface {
     void main(String playerName);

}
