package it.polimi.se2018.classes.view;

import com.sun.scenario.Settings;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.Player;
import it.polimi.se2018.classes.network.ClientInterface;
import it.polimi.se2018.classes.network.RMIClient;
import it.polimi.se2018.classes.network.SocketClient;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIHandler {
    private static final String SOCKET_CONNESSION = "Socket";
    private static final String RMI_CONNECTION = "RMI";
    private static final String MULTI_PLAYER = "Multi player";
    private static final String SINGLE_PLAYER = "Single player";
    private SettingsController settingsController;
    private UserNameController userNameController;
    private WindowSelectionController windowSelectionController;
    private MainScreenController mainScreenController;
    private ClientInterface virtualServer;
    private String clientType;
    private String username;
    public void main(Stage primaryStage){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/settings.fxml"));
            Parent root = loader.load();
            settingsController= loader.getController();
            settingsController.setGuiHandler(this);
            primaryStage.setTitle("Sagrada");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
        }catch (IOException e){
            e.printStackTrace();
        }


    }
    public void setClientType(String clientType){
        this.clientType=clientType;

    }
    public void setWindowSelectionController(WindowSelectionController windowSelectionController){
        this.windowSelectionController=windowSelectionController;
        windowSelectionController.setGuiHandler(this);
    }
    public void setMainScreenController(MainScreenController mainScreenController){
        this.mainScreenController=mainScreenController;
        mainScreenController.setGuiHandler(this);
    }
    public void userNameStage(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/userName.fxml"));
            Parent root = loader.load();
            userNameController= loader.getController();
            userNameController.setGuiHandler(this);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Sagrada");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void askUsername(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userNameController.askUsername();

            }
        });

    }
    public void okUsername(String username){
        this.username=username;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userNameController.closeUsernameScene();

            }
        });


    }
    public void createClient(String username){
        if (clientType==RMI_CONNECTION){
            virtualServer=new RMIClient();
            virtualServer.main(username,this);
        }else{
            virtualServer=new SocketClient();
            virtualServer.main(username,this);
        }
    }
    public void windowToChose(WindowToChoseEvent windowToChoseEvent){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                windowSelectionController.setWindowImageView(windowToChoseEvent.getWindows());

            }
        });

    }
    public void sendChosenWindow(int chosenWindow){
        virtualServer.sendToServer(new ChoseWindowEvent(chosenWindow,username));
    }
    public void startMatch(StartMatchEvent startMatchEvent){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.setMainPlayerName(username);
                mainScreenController.configGUI(startMatchEvent.getPlayers(),startMatchEvent.getPublicObjCards(),startMatchEvent.getToolCards());
            }
        });
    }
    public void startRound(StartRoundEvent startRoundEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.updateRound(startRoundEvent.getDraftPool(),startRoundEvent.getRound());
            }
        });

    }
    public void startTurn(StartTurnEvent startTurnEvent){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.checkStartTurn(startTurnEvent.getPlayer());
            }
        });
    }
    public void placeDice(int draftDice,int row,int column){
        virtualServer.sendToServer(new PlaceDiceEvent(draftDice,row,column));

    }
    public void showMessage(Message message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.notValideMoveMessagge(message.getMessage());
            }
        });

    }
    public void endTurn(){
        virtualServer.sendToServer(new EndTurnEvent());
    }
    public void modifiedWindow(ModifiedWindowEvent modifiedWindowEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.modifiedWindow(modifiedWindowEvent.getWindow(),modifiedWindowEvent.getPlayer());
            }
        });

    }
}
