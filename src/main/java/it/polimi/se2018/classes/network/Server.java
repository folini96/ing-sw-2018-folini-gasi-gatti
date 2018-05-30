package it.polimi.se2018.classes.network;

import it.polimi.se2018.classes.Events.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server  {
    private static int PORT = 1099; // porta di default
    private RMIServerImplementation rmiHandler;
    public void main(){
        rmiMain();
        socketMain();
    }

    public void rmiMain() {

        try {

            LocateRegistry.createRegistry(PORT);

        } catch (RemoteException e) {

            System.out.println("Registry già presente!");
        }

        try {

            rmiHandler = new RMIServerImplementation();
            Naming.rebind("//localhost/MyServer", rmiHandler);

        }catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        }
         catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

    }
    public static void socketMain(){

    }

    public void sendMessage(Message message){
        rmiHandler.notValideMoveMessage(message);
    }


}
