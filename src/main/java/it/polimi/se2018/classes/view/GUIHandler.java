package it.polimi.se2018.classes.view;

import com.sun.scenario.Settings;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.model.Player;
import it.polimi.se2018.classes.network.ClientInterface;
import it.polimi.se2018.classes.network.RMIClient;
import it.polimi.se2018.classes.network.SocketClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GUIHandler extends Application {
    private static final String SOCKET_CONNESSION = "Socket";
    private static final String RMI_CONNECTION = "RMI";
    private SettingsController settingsController;
    private UserNameController userNameController;
    private WindowSelectionController windowSelectionController;
    private MainScreenController mainScreenController;
    private ClientInterface virtualServer;
    private String clientType;
    private String username;
    private Timer windowSelectionTimer;
    private String serverIp;
    private int serverPort;
    private boolean matchEnded;
    public void start(Stage primaryStage){
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
            matchEnded=false;
        }catch (IOException e){
            e.printStackTrace();
        }



    }
    public void setClientType(String clientType,String serverIp,int serverPort){
        this.clientType=clientType;
        this.serverIp=serverIp;
        this.serverPort=serverPort;
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
            virtualServer.main(username,this,serverIp,serverPort);
        }else{
            virtualServer=new SocketClient();
            virtualServer.main(username,this,serverIp,serverPort);
        }
    }
    public void createClientError(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userNameController.connectionError();

            }
        });
    }
    public void windowToChose(WindowToChoseEvent windowToChoseEvent){
        TimerTask windowSelectionTask = new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                int randomInt = random.nextInt(4);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        windowSelectionController.showMainScreen();

                    }
                });
                virtualServer.sendToServer(new ChoseWindowEvent(randomInt,username));

            }
        };
        windowSelectionTimer=new Timer();
        windowSelectionTimer.schedule(windowSelectionTask, windowToChoseEvent.getTimer());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                windowSelectionController.setWindowImageView(windowToChoseEvent.getWindows());

            }
        });

    }
    public void sendChosenWindow(int chosenWindow){
        windowSelectionTimer.cancel();
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
                mainScreenController.setFirstPlayerLabel(startRoundEvent.getFirstPlayer());
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
    public void modifiedRoundTrack(ModifiedRoundTrack modifiedRoundTrack){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.modifiedRoundTrack(modifiedRoundTrack.getRoundTrack());
            }
        });
    }
    public void modifiedDraft(ModifiedDraftEvent modifiedDraftEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.modifiedDraft(modifiedDraftEvent.getDraftPool());
            }
        });
    }
    public void endRound(EndRoundEvent endRoundEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.endRound(endRoundEvent.getRoundTrack());
            }
        });
    }
    public void endMatch(EndMatchEvent endMatchEvent){
        matchEnded=true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.matchEnd(endMatchEvent);
            }
        });
    }
    public void sendEffect(SendEffectEvent effectEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.sendEffect(effectEvent);
            }
        });
    }
    public void endByTime(String player){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.endByTime(player);
            }
        });
    }
    public void useToolCard(int toolCard){
        virtualServer.sendToServer(new UseToolCardEvent(toolCard));
    }
    public void modifyDice(int draftDice, int upOrDown){
        virtualServer.sendToServer(new ModifyDiceEvent(draftDice,upOrDown));
    }
    public void moveDice(int diceRow,int diceColumn, int newRow, int newColumn, int round,int diceInRound){
        virtualServer.sendToServer(new MoveDiceEvent(diceRow,diceColumn,newRow,newColumn,round,diceInRound));
    }
    public void rerollDraft(){
        virtualServer.sendToServer(new RerollDraftEvent());
    }
    public void exchangeDice(int draftDice, int roundNumber, int diceInRound){
        virtualServer.sendToServer(new ExchangeEvent(draftDice,roundNumber,diceInRound));
    }
    public void askNewDiceValue(NewDiceFromBagEvent newDiceFromBagEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.newDiceValue(newDiceFromBagEvent.getExtractedDice());
            }
        });
    }
    public void modifiedToken(ModifiedTokenEvent modifiedTokenEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.modifiedToken(modifiedTokenEvent);
            }
        });
    }
    public void setValue(int diceValue){
        virtualServer.sendToServer(new SetValueEvent(diceValue));
    }
    public void reconnect(){
        if (matchEnded){
            //todo:blocca riconnessione
        }else{
            virtualServer.reconnect(username);
        }

    }
    public void updateReconnectedPlayer(UpdateReconnectedClientEvent updateReconnectedClientEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.updateReconnection(updateReconnectedClientEvent);
            }
        });
    }
    public void disconnectedPlayer(String player){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.setDisconnectedPlayer(player);
            }
        });
    }
    public void lastPlayerLeft(){
        matchEnded=true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.lastPlayerLeft();
            }
        });
    }
    public void connectionToServerLost(){
        if ((!matchEnded)&&(mainScreenController!=null)){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mainScreenController.connectionLost();
                }
            });
        }
    }
    public void closeConnectionAfterEnd(){
        virtualServer.deleteAfterMatch();
    }
    public void gameEnded(){
        matchEnded=true;
    }

}
