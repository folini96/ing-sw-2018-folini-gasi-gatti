package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.events.*;
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

/**
 * @author Andrea Folini
 * handle every message exchanged between the interface and the server, notifying the gui or the network
 */
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

    /**
     * start javaFX application
     * @param primaryStage javaFX parameter
     */
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

    /**
     * @param clientType user choice between socket or rmi
     * @param serverIp ip of the server
     * @param serverPort port of socket or rmi
     */
    public void setClientType(String clientType,String serverIp,int serverPort){
        this.clientType=clientType;
        this.serverIp=serverIp;
        this.serverPort=serverPort;
    }

    /**
     * @param windowSelectionController controller of the window for window selection
     */
    public void setWindowSelectionController(WindowSelectionController windowSelectionController){
        this.windowSelectionController=windowSelectionController;
        windowSelectionController.setGuiHandler(this);
    }

    /**
     * @param mainScreenController controller of the window for the main screen of the game
     */
    public void setMainScreenController(MainScreenController mainScreenController){
        this.mainScreenController=mainScreenController;
        mainScreenController.setGuiHandler(this);
    }

    /**
     * show the window for the username selection
     */
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

    /**
     * ask the username again if it was rejected by the server
     */
    public void askUsername(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userNameController.askUsername();

            }
        });

    }

    /**
     * notify that the username has been accepted
     * @param username the chosen username
     */
    public void okUsername(String username){
        this.username=username;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userNameController.closeUsernameScene();

            }
        });


    }

    /**
     * create the socket or rmi client
     * @param username the username of the player
     */
    public void createClient(String username){
        if (clientType==RMI_CONNECTION){
            virtualServer=new RMIClient();
            virtualServer.main(username,this,serverIp,serverPort);
        }else{
            virtualServer=new SocketClient();
            virtualServer.main(username,this,serverIp,serverPort);
        }
    }

    /**
     * notify a connection error while creating the client
     */
    public void createClientError(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userNameController.connectionError();

            }
        });
    }

    /**
     * @param windowToChoseEvent the windows for the player; after the timer runs out a random one will be choose
     */
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

    /**
     * @param chosenWindow the choice of the player among the windows
     */
    public void sendChosenWindow(int chosenWindow){
        windowSelectionTimer.cancel();
        virtualServer.sendToServer(new ChoseWindowEvent(chosenWindow,username));
    }

    /**
     * send every parameter to set the gui in the beginning of the match
     * @param startMatchEvent the object that contains everything needed to set the gui
     */
    public void startMatch(StartMatchEvent startMatchEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.setMainPlayerName(username);
                mainScreenController.configGUI(startMatchEvent.getPlayers(),startMatchEvent.getPublicObjCards(),startMatchEvent.getToolCards());
            }
        });
    }

    /**
     * notify the start of a new round to the player
     * @param startRoundEvent contains the data needed for a new round
     */
    public void startRound(StartRoundEvent startRoundEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.updateRound(startRoundEvent.getDraftPool(),startRoundEvent.getRound());
                mainScreenController.setFirstPlayerLabel(startRoundEvent.getFirstPlayer());
            }
        });

    }

    /**
     * notify the start of a new turn to the player
     * @param startTurnEvent contains the name of the player that is playing
     */
    public void startTurn(StartTurnEvent startTurnEvent){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.checkStartTurn(startTurnEvent.getPlayer());
            }
        });
    }

    /**
     * notify to the network that a player want to place a dice
     * @param draftDice the number representing the dice of the draft
     * @param row row where the player want to place the dice
     * @param column column where the player want to place the dice
     */
    public void placeDice(int draftDice,int row,int column){
        virtualServer.sendToServer(new PlaceDiceEvent(draftDice,row,column));

    }

    /**
     * send to the player the message coming from the network
     * @param message the message for the player
     */
    public void showMessage(Message message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.notValideMoveMessagge(message.getMessage());
            }
        });

    }

    /**
     * send to the network that the current player is ending his turn
     */
    public void endTurn(){
        virtualServer.sendToServer(new EndTurnEvent());
    }

    /**
     * send to the player the window that has been modified
     * @param modifiedWindowEvent the modified window
     */
    public void modifiedWindow(ModifiedWindowEvent modifiedWindowEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.modifiedWindow(modifiedWindowEvent.getWindow(),modifiedWindowEvent.getPlayer());
            }
        });

    }

    /**
     * send to the player the new round track
     * @param modifiedRoundTrack the modified round track
     */
    public void modifiedRoundTrack(ModifiedRoundTrack modifiedRoundTrack){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.modifiedRoundTrack(modifiedRoundTrack.getRoundTrack());
            }
        });
    }

    /**
     * send to the player the new draft
     * @param modifiedDraftEvent the modified draft
     */
    public void modifiedDraft(ModifiedDraftEvent modifiedDraftEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.modifiedDraft(modifiedDraftEvent.getDraftPool());
            }
        });
    }

    /**
     * notify the player that the current round ended
     * @param endRoundEvent contains an updated round track
     */
    public void endRound(EndRoundEvent endRoundEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.endRound(endRoundEvent.getRoundTrack());
            }
        });
    }

    /**
     * notify the end of the match
     * @param endMatchEvent contains the points done by every player
     */
    public void endMatch(EndMatchEvent endMatchEvent){
        matchEnded=true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.matchEnd(endMatchEvent);
            }
        });
    }

    /**
     * notify that his request to use a tool card has been accepted, end send the active effect
     * @param effectEvent the effect of the chosen tool card
     */
    public void sendEffect(SendEffectEvent effectEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.sendEffect(effectEvent);
            }
        });
    }

    /**
     * notify to the player that one of the players has been suspended for inactivity
     * @param player the player suspended
     */
    public void endByTime(String player){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.endByTime(player);
            }
        });
    }

    /**
     * send to network a request to use a tool card
     * @param toolCard the chosen tool card
     */
    public void useToolCard(int toolCard){
        virtualServer.sendToServer(new UseToolCardEvent(toolCard));
    }

    /**
     * send to network a request to modify a dice
     * @param draftDice the dice that the player want to modify
     * @param upOrDown used to know the type of the modification
     */
    public void modifyDice(int draftDice, int upOrDown){
        virtualServer.sendToServer(new ModifyDiceEvent(draftDice,upOrDown));
    }

    /**
     * send to network a request to move a dice on the window
     * @param diceRow row of the dice
     * @param diceColumn column of the dice
     * @param newRow row of the new position
     * @param newColumn column of the new position
     * @param round number of the round where the dice chosen on the round is
     * @param diceInRound the dice chosen on the round track
     */
    public void moveDice(int diceRow,int diceColumn, int newRow, int newColumn, int round,int diceInRound){
        virtualServer.sendToServer(new MoveDiceEvent(diceRow,diceColumn,newRow,newColumn,round,diceInRound));
    }

    /**
     * send to network that the player want to roll every dice in the draft
     */
    public void rerollDraft(){
        virtualServer.sendToServer(new RerollDraftEvent());
    }

    /**
     * send to network a request to exchange a draft dice with one in the dice bag or in the round track
     * @param draftDice draft dice chosen
     * @param roundNumber round where the round track chosen dice is
     * @param diceInRound round track chosen dice
     */
    public void exchangeDice(int draftDice, int roundNumber, int diceInRound){
        virtualServer.sendToServer(new ExchangeEvent(draftDice,roundNumber,diceInRound));
    }

    /**
     * ask to the player the value that he wants to assign to the dice extract from the dice bag
     * @param newDiceFromBagEvent the dice extract from dice bag
     */
    public void askNewDiceValue(NewDiceFromBagEvent newDiceFromBagEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.newDiceValue(newDiceFromBagEvent.getExtractedDice());
            }
        });
    }

    /**
     * send to player a modification of a tool card and of a player favor tokens
     * @param modifiedTokenEvent contains the new token values
     */
    public void modifiedToken(ModifiedTokenEvent modifiedTokenEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.modifiedToken(modifiedTokenEvent);
            }
        });
    }

    /**
     * send to network the value assigned from the player to the dice extracted from dice bag
     * @param diceValue the dice value chosen
     */
    public void setValue(int diceValue){
        virtualServer.sendToServer(new SetValueEvent(diceValue));
    }

    /**
     * send a request of reconnection to the network
     */
    public void reconnect(){
        if (matchEnded){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    mainScreenController.matchEndedWhileDisconnected();
                }
            });
        }else{
            virtualServer.reconnect(username);
        }

    }

    /**
     * send to the player that a player has been reconnected
     * @param updateReconnectedClientEvent contains the name of the reconnected player end everything needed to update his interface
     */
    public void updateReconnectedPlayer(UpdateReconnectedClientEvent updateReconnectedClientEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.updateReconnection(updateReconnectedClientEvent);
            }
        });
    }

    /**
     * notify to the player that another player has been disconnected due to a connection error
     * @param player the disconnected player
     */
    public void disconnectedPlayer(String player){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.setDisconnectedPlayer(player);
            }
        });
    }

    /**
     * notify that the player is the only one still in the lobby and that has won the game
     */
    public void lastPlayerLeft(){
        matchEnded=true;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainScreenController.lastPlayerLeft();
            }
        });
    }

    /**
     * notify that a connection error happened
     */
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

    /**
     * close the player connection after the game ending
     */
    public void closeConnectionAfterEnd(){
        virtualServer.deleteAfterMatch();
    }

    /**
     * notify that a game ended, in case the player was still suspended
     */
    public void gameEnded(){
        matchEnded=true;
    }

}
