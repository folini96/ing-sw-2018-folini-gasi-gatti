package it.polimi.se2018.classes.network;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server implements RMIServerInterface, SocketServerInterface {
    private static int PORT = 1099; // porta di default
    public static void main(String[] args){
        rmiMain();
    }

    public static void rmiMain() {

        try {

            LocateRegistry.createRegistry(PORT);

        } catch (RemoteException e) {

            System.out.println("Registry già presente!");
        }

        try {

            RMIServerImplementation serverImplementation = new RMIServerImplementation();


            Naming.rebind("//localhost/MyServer", serverImplementation);


        } catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

    }

    public void notValideMoveMessage (){

    }
    public void sendPublicObjCard(){

    }
    public void sendPrivateObjCard(){

    }
    public void sendToolCard(){

    }
    public void sendWindow(){

    }
    public void sendDraftPool(){

    }
    public void sendRoundTrack(){

    }
    public void removeFavorToken(){

    }
}
