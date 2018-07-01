package it.polimi.se2018.classes.view;

import com.sun.org.apache.xpath.internal.operations.Bool;
import it.polimi.se2018.classes.events.EndMatchEvent;
import it.polimi.se2018.classes.events.ModifiedTokenEvent;
import it.polimi.se2018.classes.events.SendEffectEvent;
import it.polimi.se2018.classes.events.UpdateReconnectedClientEvent;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.model.effects.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * class controller for the multiplayer GUI
 * @author Leonard Gasi
 */

public class MainScreenController implements Initializable {


    // URL of pictures
    private final static String URL = "/img/";
    //name of the empty space picture
    private static final String EMPTY_SPACE_FILE_NAME = "VUOTO";
    //private object card prefix
    private static final String PRIVATE_OBJECT_CARD_PREFIX = "PO";
    //tool card prefix
    private static final String TOOL_CARD_PREFIX = "TC";
    private static final String ACTIVE_TOOL_CARD = "La carta utensile è attiva! ";
    private static final String MODIFY = "Scegli il dado della riserva che vuoi modificare.";
    private static final String MOVE = "Scegli il dado della finestra che vuoi spostare.";
    private static final String MOVETWO = "Scegli i dadi della finestra che vuoi spostare.";
    private static final String REROLL = "Rilancia tutti i dadi della riserva.";
    private static final String EXCHANGEWITHROUND = "Scegli un dado dalla riserva e successivamente il dado del tracciato del round con cui scambiarlo.";
    private static final String PLACEANOTHER = "Piazza il secondo dado.";
    private static final String EXCHANGEWITHBAG = "Scegli il dado della riserva da riporre nel sacchetto";
    private static final String MOVESELECTEDCOLOR = "Scegli il dado del tracciato dal round e successivamente sposta i dadi sulla finestra.";
    private static final String PLACENOVICINITYBOUNDS = "Scegli un dado della riserva e piazzalo senza rispettare il vincolo di adiacenza ad un altro dado.";
    private ViewModel guiModel = new ViewModel();
    private GUIHandler guiHandler;
    private boolean usingTool;
    private boolean placingDice;
    private boolean isMyTurn;
    private boolean modifyDice;
    private boolean upOrDown;
    private boolean changingUpOrDown;
    private boolean moveDice;
    private boolean moveTwice;
    private boolean moveSelectedRoundColor;
    private boolean windowDiceSelected;
    private boolean rerollDraft;
    private boolean alreadyPlaced;
    private boolean alreadyUsedTool;
    private boolean exchangeDice;
    private boolean fromRoundTrack;
    private int moveNumber;
    private int modifiedDice;
    private int diceRow;
    private int diceColumn;
    private int newRow;
    private int newColumn;
    private int roundNumber;
    private int diceInRound;
    @FXML
    private Label roundLabel = new Label();
    @FXML
    private Label currentPlayerLabel = new Label();
    @FXML
    private GridPane player1GridPane = new GridPane();
    @FXML
    private GridPane player2GridPane = new GridPane();
    @FXML
    private GridPane player3GridPane = new GridPane();
    @FXML
    private GridPane mainPlayerGridPane = new GridPane();
    @FXML
    private GridPane roundTrackGridPane = new GridPane();
    @FXML
    private GridPane reserveGridPane = new GridPane();
    @FXML
    private ImageView privateObjectImageView = new ImageView();
    @FXML
    private ImageView publicObject1ImageView = new ImageView();
    @FXML
    private ImageView publicObject2ImageView = new ImageView();
    @FXML
    private ImageView publicObject3ImageView = new ImageView();
    @FXML
    private ImageView toolCard1ImageView = new ImageView();
    @FXML
    private ImageView toolCard2ImageView = new ImageView();
    @FXML
    private ImageView toolCard3ImageView = new ImageView();
    @FXML
    private Label player1Label = new Label();
    @FXML
    private Label player2Label = new Label();
    @FXML
    private Label player3Label = new Label();
    @FXML
    private Label mainPlayerSFLabel = new Label();
    @FXML
    private Label player1SFLabel = new Label();
    @FXML
    private Label player2SFLabel = new Label();
    @FXML
    private Label player3SFLabel = new Label();
    @FXML
    private Label player1UselessLabel = new Label();
    @FXML
    private Label player2UselessLabel = new Label();
    @FXML
    private Label player3UselessLabel = new Label();
    @FXML
    private Button endTurnButton = new Button();
    @FXML
    private Button toolCardButton = new Button();
    @FXML
    private Button placeDiceButton = new Button();
    @FXML
    private Button diceMinusButton = new Button();
    @FXML
    private Button dicePlusButton = new Button();
    @FXML
    private ImageView selectedDiceImageView = new ImageView();
    @FXML
    private Label firstPlayerLabel = new Label();
    @FXML
    private Label toolCardSF1Label = new Label();
    @FXML
    private Label toolCardSF2Label = new Label();
    @FXML
    private Label toolCardSF3Label = new Label();
    @FXML
    private ImageView selectedDiceBisImageView = new ImageView();
    @FXML
    private Button throwDiceButton = new Button();

    // position of the selected tool card (0,1,2)(-1 if not selected)
    private int selectedToolCard = -1;

    //position of the selected dice in the reserve
    private int reserveSelectedDice = -1;

    //placeDiceButtonClicked
    private boolean placeDiceButtonClicked = false;

    //position of the selected box on the window (row,column)
    private int[] windowSelectedDice = new int[] {-1,-1};

    private int[] roundTrackSelectedDice = new int[] {-1,-1};

    // array with name of players
    private ArrayList<String> playersName = new ArrayList<>();

    // GridPane array
    private GridPane[] playersGridPaneArray;

    //Label array for player's name
    private Label[] playersNameLabelArray;

    // Label array for player's SF
    private Label[] playersSFLabelArray;

    //Label array for "Segnalini favore" labels
    private Label[] playersUselessLabelArray;

    // ImageView array for Tool Cards
    private ImageView[] toolCardsImageViewArray;

    // ImageView array for Public Objects
    private ImageView[] publicObjectImageViewArray;

    private Label[] toolCardSFLabelArray;

    /**
     * initialization and matching with the FXML file
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // players array assignment. Main player in position 0
        playersGridPaneArray = new GridPane[4];
        playersGridPaneArray[0]= mainPlayerGridPane;
        playersGridPaneArray[1]= player1GridPane;
        playersGridPaneArray[2]= player2GridPane;
        playersGridPaneArray[3]= player3GridPane;

        //player's name label array assignment
        playersNameLabelArray = new Label[4];
        playersNameLabelArray[1]= player1Label;
        playersNameLabelArray[2]= player2Label;
        playersNameLabelArray[3]= player3Label;

        //player's useless label array assignment
        playersUselessLabelArray = new Label[4];
        playersUselessLabelArray[1] = player1UselessLabel;
        playersUselessLabelArray[2] = player2UselessLabel;
        playersUselessLabelArray[3] = player3UselessLabel;

        //player's SF Label array assignment
        playersSFLabelArray = new Label[4];
        playersSFLabelArray[0] = mainPlayerSFLabel;
        playersSFLabelArray[1] = player1SFLabel;
        playersSFLabelArray[2] = player2SFLabel;
        playersSFLabelArray[3] = player3SFLabel;

        // tool cards array assignment
        toolCardsImageViewArray = new ImageView[3];
        toolCardsImageViewArray[0] = toolCard1ImageView;
        toolCardsImageViewArray[1] = toolCard2ImageView;
        toolCardsImageViewArray[2] = toolCard3ImageView;


        // public cards array assignment
        publicObjectImageViewArray = new ImageView[3];
        publicObjectImageViewArray[0] = publicObject1ImageView;
        publicObjectImageViewArray[1] = publicObject2ImageView;
        publicObjectImageViewArray[2] = publicObject3ImageView;

        toolCardSFLabelArray = new Label[3];
        toolCardSFLabelArray[0] = toolCardSF1Label;
        toolCardSFLabelArray[1] = toolCardSF2Label;
        toolCardSFLabelArray[2] = toolCardSF3Label;
        enablePlaceDiceButton();
    }

    private int getIndex(String name){
        for (int i=0; i<4; i++){
            if (name.equals(playersName.get(i)))
                return i;
        }
        return 0;
    }

    /**
     * set other players name
     * @param players array with the players
     */
    private void setOtherPlayersName(ArrayList<Player> players){

        for (Player player: players ){
            if (!player.getName().equals(playersName.get(0))){
                playersName.add(player.getName());
            }

        }

    }

    /** set main player name
     * @param name main player name
     */
    public void setMainPlayerName(String name){
        playersName.add(name);

    }

    /**
     *
     * @param round number that will be shown in round_label
     */
    private void setRoundLabel( int round){
        roundLabel.setText( Integer.toString(round));
    }

    /**
     *
     * @param player name of the player that will be shown in current_player_label
     */
    private void setVisiblePlayerGUI (String player, boolean b){
        int i = getIndex(player);
        playersNameLabelArray[i].setVisible(b);
        playersSFLabelArray[i].setVisible(b);
        playersGridPaneArray[i].setGridLinesVisible(b);
        playersUselessLabelArray[i].setVisible(b);
    }

    public void setCurrentPlayerLabel (String player){
        currentPlayerLabel.setText(player);
    }

    public void setFirstPlayerLabel (String player){
        firstPlayerLabel.setText(player);
    }

    private void setPrivateObjectImageView(PrivateObjCard privateObjCard){
        Image image = new Image(getClass().getResource(URL + PRIVATE_OBJECT_CARD_PREFIX + privateObjCard.getColor().name() + ".jpg").toExternalForm());
        privateObjectImageView.setImage(image);
    }

    private void setPublicObjectImageView(PublicObjCard[] publicObjCards){
        for (int i=0;i<3;i++){
            Image image = new Image(getClass().getResource(URL + publicObjCards[i].getName() + ".jpg").toExternalForm());
            publicObjectImageViewArray[i].setImage(image);
        }
    }

    private void setToolCardImageView(ToolCard[] toolCards){
        for (int i=0;i<3;i++){
            Image image = new Image(getClass().getResource(URL+TOOL_CARD_PREFIX + Integer.toString(toolCards[i].getNumber())+".jpg").toExternalForm());
            toolCardsImageViewArray[i].setImage(image);
            setToolCardSFLabel(i,toolCards[i].getToken());
        }

    }

    private void setPlayerLabel (String name){
        playersNameLabelArray[getIndex(name)].setText(name);

    }

    public void setPlayerSFLabel(String name, int SF){
        playersSFLabelArray[getIndex(name)].setText(Integer.toString(SF));
    }

    public void setToolCardSFLabel(int position, int SF){
        toolCardSFLabelArray[position].setText(Integer.toString(SF));
    }

    public void updateScheme(String name, WindowSide windowSide ){
        int p = getIndex(name);
        String resourceName;
        for (int i=0;i<4;i++){
            for (int j=0;j<5;j++){
                if (windowSide.getBoxScheme()[i][j].getDice()!=null){
                    resourceName = windowSide.getBoxScheme()[i][j].getDice().getColor().name() + Integer.toString(windowSide.getBoxScheme()[i][j].getDice().getValue());
                }
                else if ((windowSide.getBoxScheme()[i][j].getColor()==null)&&(windowSide.getBoxScheme()[i][j].getValue()==0)){
                    resourceName = EMPTY_SPACE_FILE_NAME;
                }
                else if (windowSide.getBoxScheme()[i][j].getColor()==null){
                    resourceName = Integer.toString(windowSide.getBoxScheme()[i][j].getValue());
                }
                else{
                    resourceName = windowSide.getBoxScheme()[i][j].getColor().name();
                }
                setImageInGridPane(playersGridPaneArray[p],resourceName,j,i,false);
            }
        }
    }

    public void updateRound(ArrayList<Dice> draftPool, int round){
        String url;
        setRoundLabel(round+1);
        updateDraft(draftPool);
    }
    public void updateDraft(ArrayList<Dice> draftPool){
        for (int i=0; i<9; i++){
            String url;
            if (draftPool.isEmpty()){
                url = EMPTY_SPACE_FILE_NAME;

            }
            else{
                url = draftPool.get(0).getColor().name() + Integer.toString(draftPool.get(0).getValue());
                draftPool.remove(0);
            }
            setImageInGridPane(reserveGridPane, url, i,0,true);
        }
    }

    public void setDisconnectedPlayer(String name){
        playersGridPaneArray[getIndex(name)].setOpacity(0.45);
        playersNameLabelArray[getIndex(name)].setDisable(true);
        playersSFLabelArray[getIndex(name)].setDisable(true);
        playersUselessLabelArray[getIndex(name)].setDisable(true);
    }

    public void setReconnectedPlayer(String name){
        playersGridPaneArray[getIndex(name)].setOpacity(1);
        playersNameLabelArray[getIndex(name)].setDisable(false);
        playersSFLabelArray[getIndex(name)].setDisable(false);
        playersUselessLabelArray[getIndex(name)].setDisable(false);
    }



    public void alertMainPlayerSuspended(){
        final String MAIN_PLAYER_DISCONNECTED_MESSAGE = "Sei stato sospeso dalla partita per inattività. Premi il bottone Riconnetti per rientrare alla fine del turno in corso";
        disableMainPlayerButtons();
        reserveGridPane.setDisable(true);
        roundTrackGridPane.setDisable(true);
        mainPlayerGridPane.setDisable(true);
        ButtonType reconnect = new ButtonType("Riconnetti", ButtonBar.ButtonData.OK_DONE);
        Alert alert=new Alert(Alert.AlertType.WARNING,MAIN_PLAYER_DISCONNECTED_MESSAGE,reconnect);
        alert.setTitle("Disconnessione");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            guiHandler.reconnect();
        }

    }


    public void updateRoundTrack(Round round, int roundNumber){

        List<Dice> dices = round.getLeftDices();
        String url;
        for (int i=0; i<9; i++){
            if (!dices.isEmpty()){
                url = dices.get(0).getColor().name() + Integer.toString(dices.get(0).getValue());
                dices.remove(0);
                setImageInGridPane(roundTrackGridPane, url, i,roundNumber,true);
            }

        }
    }

    public void configGUI(ArrayList<Player> players, PublicObjCard[] publicObjCards, ToolCard[] toolCards){
        setOtherPlayersName(players);
        setToolCardImageView(toolCards);
        setPublicObjectImageView(publicObjCards);

        for (Player player:players){
            setPlayerSFLabel(player.getName(),player.getToken());
            updateScheme(player.getName(),player.getWindow());
            if(getIndex(player.getName())!=0) {
                setPlayerLabel(player.getName());
                setVisiblePlayerGUI(player.getName(),true);
            }
            else{
                setPrivateObjectImageView(player.getPrivateObj());
            }
        }
    }
    /**
     *
     * @param gridPane GridPane that we want to modify
     * @param resourceName only name of the resource, without .jpg
     * @param col index of the column (starts from 0)
     * @param row index of the row (starts from 0)
     */
    private void setImageInGridPane(GridPane gridPane, String resourceName, int col, int row, boolean isRound) {
        ObservableList<Node> lista = gridPane.getChildren();
        Image image = new Image(URL+resourceName+".jpg");
        ImageView imageView = (ImageView) lista.get((((row)*gridPane.impl_getColumnCount()))+(col));
        imageView.setImage(image);
        if (isRound){
            if (resourceName.equals(EMPTY_SPACE_FILE_NAME)){
                imageView.setDisable(true);
            }
            else imageView.setDisable(false);
        }

    }

    private void enableEndTurnButton(){
        endTurnButton.setDisable(false);
    }
    private void disableEndTurnButton(){
        endTurnButton.setDisable(true);
    }
    private void enableToolCardButton(){
        toolCardButton.setDisable(false);
    }
    private void disableToolCardButton(){
        toolCardButton.setDisable(true);
    }
    private void enablePlaceDiceButton(){
        placeDiceButton.setDisable(false);
    }
    private void disablePlaceDiceButton(){
        placeDiceButton.setDisable(true);
    }
    private void enableThrowDiceButton(){
        throwDiceButton.setDisable(false);
    }
    private void disableThrowDiceButton(){
        throwDiceButton.setDisable(true);
    }
    private void setVisibleThrowDiceButton(){
        throwDiceButton.setVisible(true);
    }
    private void setNotVisibleThrowDiceButton(){
        throwDiceButton.setVisible(false);
    }
    private void enableMainPlayerButtons(){enablePlaceDiceButton();enableEndTurnButton();enableToolCardButton();}
    private void disableMainPlayerButtons(){disablePlaceDiceButton();;disableEndTurnButton();disableToolCardButton();}
    private void enablePlusMinusButtons(){ diceMinusButton.setDisable(false); dicePlusButton.setDisable(false);}
    private void disablePlusMinusButtons(){ diceMinusButton.setDisable(true); dicePlusButton.setDisable(true);}
    private void setVisiblePlusMinusButtons(){ diceMinusButton.setVisible(true);dicePlusButton.setVisible(true);}
    private void setNotVisiblePlusMinusButtons(){ diceMinusButton.setVisible(false);dicePlusButton.setVisible(false);}
    /**
     * notice the end of the round
     * @param event clicked on endTurnButton
     */
    @FXML
    private void endTurnButtonClicked(ActionEvent event){
        modifyDice=false;
        upOrDown=false;
        modifiedDice=-1;
        selectedDiceImageView.setImage(null);
        selectedDiceBisImageView.setImage(null);
        guiHandler.endTurn();
    }
    /**
     * notice the intention to use a Tool Card
     * @param event
     */
    @FXML
    private void toolCardButtonClicked(ActionEvent event){
        final String NO_TOOL_CARD_MESSAGE = "Seleziona una Tool Card!";
        disableMainPlayerButtons();
        selectedDiceImageView.setImage(null);
        if (selectedToolCard==-1){
            guiModel.alertMessage(NO_TOOL_CARD_MESSAGE);
            enableMainPlayerButtons();
            return;
        }else{
            usingTool=true;
            guiHandler.useToolCard(selectedToolCard);
        }

    }

    public void resetValuesforPlaceDice(){
        reserveSelectedDice = -1;
        windowSelectedDice[0]=-1;
        windowSelectedDice[1]=-1;
        placeDiceButtonClicked=false;
    }
    @FXML
    private void placeDiceButtonClicked(ActionEvent event){

        disableMainPlayerButtons();
        if (reserveSelectedDice==-1){
            final String NO_SELECTION_MESSAGE = "Seleziona un dado dalla riserva!";
            guiModel.alertMessage(NO_SELECTION_MESSAGE);
            enableMainPlayerButtons();
            return;
        }
        placeDiceButtonClicked = true;
    }

    @FXML
    private void selectedReserveDiceEvent(MouseEvent event){
        Node node = (Node) event.getSource();
        if (moveDice){
            moveTwice=false;
            moveDice=false;
            moveSelectedRoundColor=false;
            selectedDiceBisImageView.setImage(null);
        }
        try {
            reserveSelectedDice = GridPane.getColumnIndex(node);
        }
        catch (NullPointerException e){
            reserveSelectedDice = 0;
        }
        if (modifyDice){
            modifiedDice=reserveSelectedDice;
            guiHandler.modifyDice(reserveSelectedDice,-1);
            return;
        }

        if (upOrDown){
            modifiedDice=reserveSelectedDice;
        }
        ObservableList<Node> list = reserveGridPane.getChildren();
        ImageView imageView = (ImageView) list.get(reserveSelectedDice);
        Image image = imageView.getImage();
        selectedDiceImageView.setImage(image);
        if (exchangeDice){

            modifiedDice=reserveSelectedDice;
            if (fromRoundTrack){
                roundTrackGridPane.setDisable(false);
            }else{
                selectedDiceImageView.setImage(null);
                guiHandler.exchangeDice(reserveSelectedDice,-1,-1);
            }
            return;
        }
        placeDiceButtonClicked=false;
        enablePlaceDiceButton();
    }
    @FXML
    private void selectedWindowBoxEvent(MouseEvent event){
        Node node = (Node) event.getSource();
        if (moveDice){
            reserveGridPane.setDisable(true);
            try {
                windowSelectedDice[0] = GridPane.getRowIndex(node);
            }
            catch (NullPointerException e){
                windowSelectedDice[0]=0;
            }
            try {
                windowSelectedDice[1] = GridPane.getColumnIndex(node);
            }
            catch (NullPointerException e){
                windowSelectedDice[1]=0;
            }
            if (!windowDiceSelected){
                diceRow=windowSelectedDice[0];
                diceColumn=windowSelectedDice[1];
                setOpacityOthers(diceRow,diceColumn);
                windowDiceSelected=true;

            }else{
                if ((diceRow==windowSelectedDice[0])&&(diceColumn==windowSelectedDice[1])){
                    guiModel.alertMessage("Non puoi spostare il dado nella stessa posizione");
                }else{
                    newRow=windowSelectedDice[0];
                    newColumn=windowSelectedDice[1];
                    resetOpacityOthers();
                    if (!moveSelectedRoundColor){
                        roundNumber=-1;
                        diceInRound=-1;
                    }else{
                        roundTrackGridPane.setDisable(true);
                    }
                    guiHandler.moveDice(diceRow,diceColumn,newRow,newColumn,roundNumber,diceInRound);
                    windowDiceSelected=false;
                }


            }
            return;
        }
        if (!placeDiceButtonClicked){
            return;
        }


        try {
            windowSelectedDice[0] = GridPane.getRowIndex(node);
        }
        catch (NullPointerException e){
            windowSelectedDice[0]=0;
        }
        try {
            windowSelectedDice[1] = GridPane.getColumnIndex(node);
        }
        catch (NullPointerException e){
            windowSelectedDice[1]=0;
        }
        placingDice=true;
        disablePlaceDiceButton();
        guiHandler.placeDice(reserveSelectedDice,windowSelectedDice[0],windowSelectedDice[1]);

        resetValuesforPlaceDice();
        selectedDiceImageView.setImage(null);



    }

    @FXML
    private void selectedRoundTrackDiceEvent (MouseEvent event){

        Node node = (Node) event.getSource();
        try {
            roundTrackSelectedDice[0] = GridPane.getRowIndex(node);
        }
        catch (NullPointerException e){
            roundTrackSelectedDice[0]=0;
        }
        try {
            roundTrackSelectedDice[1] = GridPane.getColumnIndex(node);
        }
        catch (NullPointerException e){
            roundTrackSelectedDice[1]=0;
        }
        roundNumber=roundTrackSelectedDice[0];
        diceInRound=roundTrackSelectedDice[1];
        ObservableList<Node> list = roundTrackGridPane.getChildren();
        ImageView imageView = (ImageView) list.get(roundTrackSelectedDice[0]*9+roundTrackSelectedDice[1]);
        Image image = imageView.getImage();
        selectedDiceBisImageView.setImage(image);
        if(!exchangeDice){
            mainPlayerGridPane.setDisable(false);
        }else{
            selectedDiceBisImageView.setImage(null);
            guiHandler.exchangeDice(reserveSelectedDice,roundNumber,diceInRound);
        }


    }

    @FXML
    private void toolCard1Clicked(MouseEvent event){
        selectedToolCard = 0;
    }
    @FXML
    private void toolCard2Clicked(MouseEvent event){
        selectedToolCard = 1;
    }
    @FXML
    private void toolCard3Clicked(MouseEvent event){
        selectedToolCard = 2;
    }
    @FXML
    private void minusButtonClicked(ActionEvent event){
        if (upOrDown){
            if (reserveSelectedDice<0){
                guiModel.alertMessage("Selezionare un dado dalla riserva.");
            }else{
               modifiedDice=reserveSelectedDice;
               changingUpOrDown=true;
               guiHandler.modifyDice(reserveSelectedDice,0);
            }

        }

    }
    @FXML
    private void plusButtonClicked(ActionEvent event){
       if (upOrDown){
           if (reserveSelectedDice<0){
               guiModel.alertMessage("Selezionare un dado dalla riserva.");
           }else{
               modifiedDice=reserveSelectedDice;
               changingUpOrDown=true;
               guiHandler.modifyDice(reserveSelectedDice,1);
           }

       }

    }
    @FXML
    private void throwDiceButtonClicked(ActionEvent event){
        if (rerollDraft){
            guiHandler.rerollDraft();
        }
    }
    @FXML
    private void toolCardZoomButtonClicked(ActionEvent event){
        try{
            ArrayList<ImageView> toolImage=new ArrayList<>();
            ZoomController zoomController;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/zoom.fxml"));
            Parent root = loader.load();
            zoomController=loader.getController();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Sagrada");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
            for (int i=0;i<3;i++){
                toolImage.add(toolCardsImageViewArray[i]);
            }
            zoomController.setImages(toolImage);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    @FXML
    private void privateCardZoomButtonClicked(ActionEvent event){
        try{
            ArrayList<ImageView> privateImage=new ArrayList<>();
            ZoomController zoomController;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/zoom.fxml"));
            Parent root = loader.load();
            zoomController=loader.getController();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Sagrada");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
            privateImage.add(privateObjectImageView);
            zoomController.setImages(privateImage);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void publicCardZoomButtonClicked(ActionEvent event){
        try{
            ArrayList<ImageView> publicImage=new ArrayList<>();
            ZoomController zoomController;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/zoom.fxml"));
            Parent root = loader.load();
            zoomController=loader.getController();
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Sagrada");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
            for (int i=0;i<3;i++){
                publicImage.add(publicObjectImageViewArray[i]);
            }
            zoomController.setImages(publicImage);
        }catch(IOException e){
            e.printStackTrace();
        }
    }



    public int choseNewValue(Dice dice){
        final String CHOSE_DICE_NUMBER_MESSAGE = "Il dado estratto è " + dice.getColor().name()+ ". Quale valore vuoi assegnare al dado?";
        List<String> choices = new ArrayList<>();
        choices.add("1");
        choices.add("2");
        choices.add("3");
        choices.add("4");
        choices.add("5");
        choices.add("6");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
        dialog.setTitle("Sagrada");
        dialog.setHeaderText(null);
        dialog.setContentText(CHOSE_DICE_NUMBER_MESSAGE);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return Integer.parseInt(result.get());
        }
        else{
            return -1;
        }

    }
    public void newDiceValue(Dice dice){
        boolean okValue=false;
        int diceValue=0;
        while (!okValue){
            diceValue=choseNewValue(dice);
            if (diceValue>0){
                okValue=true;

            }
        }
        guiHandler.setValue(diceValue);
    }
    public void setOpacityOthers(int row, int column){
        ObservableList<Node> lista = playersGridPaneArray[0].getChildren();
        ImageView selected = (ImageView) lista.get(row*5 + (column));
        for (int i=0;i<4;i++){
            for (int j=0;j<5;j++){
                ImageView current = (ImageView) lista.get((((i)*playersGridPaneArray[0].impl_getColumnCount()))+(j));
                if (current!=selected){
                    current.setOpacity(0.4);
                }
            }

        }

    }

    public void resetOpacityOthers(){
        ObservableList<Node> lista = playersGridPaneArray[0].getChildren();

        for (int i=0;i<4;i++){
            for (int j=0;j<5;j++){
                ImageView current = (ImageView) lista.get((((i)*playersGridPaneArray[0].impl_getColumnCount()))+(j));
                current.setOpacity(1);
            }

        }
    }



    public void setGuiHandler(GUIHandler guiHandler){
        this.guiHandler=guiHandler;
    }
    public void checkStartTurn(String playerName){
        alreadyUsedTool=false;
        alreadyPlaced=false;
        windowDiceSelected=false;
        moveSelectedRoundColor=false;
        moveDice=false;
        moveTwice=false;
        moveNumber=0;
        selectedToolCard=-1;
        usingTool=false;
        modifyDice=false;
        upOrDown=false;
        placingDice=false;
        changingUpOrDown=false;
        rerollDraft=false;
        exchangeDice=false;
        fromRoundTrack=false;
        roundTrackGridPane.setDisable(true);
        setNotVisibleThrowDiceButton();
        setNotVisiblePlusMinusButtons();
        disableThrowDiceButton();
        setCurrentPlayerLabel(playerName);
        if (getIndex(playerName)==0){
          isMyTurn=true;
          reserveGridPane.setDisable(false);
          mainPlayerGridPane.setDisable(false);
          enableMainPlayerButtons();
        }else{
          isMyTurn=false;
          disableMainPlayerButtons();
          reserveGridPane.setDisable(true);
          mainPlayerGridPane.setDisable(true);
        }

    }
    public void notValideMoveMessagge(String message){
        if (placingDice){
            if (!((modifyDice)||(upOrDown)||(exchangeDice))){

                resetValuesforPlaceDice();
                enableMainPlayerButtons();
                if (alreadyUsedTool){
                    disableToolCardButton();
                }
            }else{
                reserveGridPane.setDisable(true);
                ObservableList<Node> list = reserveGridPane.getChildren();
                ImageView imageView = (ImageView) list.get(modifiedDice);
                Image image = imageView.getImage();
                selectedDiceImageView.setImage(image);
                enablePlaceDiceButton();
                reserveSelectedDice=modifiedDice;


            }
            placingDice=false;
        }

        if (changingUpOrDown){
            selectedDiceImageView.setImage(null);
            reserveSelectedDice=-1;
        }
        if (usingTool){
            usingTool=false;
            enableMainPlayerButtons();
            if (alreadyPlaced){
                disablePlaceDiceButton();
            }
        }
        selectedToolCard=-1;
        enableEndTurnButton();
        guiModel.alertMessage(message);

    }
    public void modifiedWindow(WindowSide window,String player){
        if ((isMyTurn)&&(placingDice)){
            reserveGridPane.setDisable(true);
            mainPlayerGridPane.setDisable(true);
            enableEndTurnButton();
            if(!alreadyUsedTool){
                enableToolCardButton();
            }
            disablePlaceDiceButton();
            alreadyPlaced=true;
        }
        if (moveDice){
            if (!alreadyPlaced){
                enablePlaceDiceButton();
                reserveGridPane.setDisable(false);
            }
            enableEndTurnButton();
            moveNumber++;
            if (moveTwice){
                if (moveNumber==2){
                    moveTwice=false;
                    moveDice=false;
                    moveSelectedRoundColor=false;
                    selectedDiceBisImageView.setImage(null);
                }
            }else{
                moveDice=false;

            }
        }

        selectedToolCard=-1;
        resetValuesforPlaceDice();
        selectedDiceImageView.setImage(null);
        placingDice=false;
        modifyDice=false;
        upOrDown=false;
        updateScheme(player,window);
    }
    public void modifiedDraft(ArrayList<Dice> draftPool){
        updateDraft(draftPool);
        if ((modifyDice)||(upOrDown)||(exchangeDice)){
            reserveGridPane.setDisable(true);
            ObservableList<Node> list = reserveGridPane.getChildren();
            ImageView imageView = (ImageView) list.get(modifiedDice);
            Image image = imageView.getImage();
            selectedDiceImageView.setImage(image);
            setNotVisiblePlusMinusButtons();
            changingUpOrDown=false;
            disablePlusMinusButtons();
            enablePlaceDiceButton();
            roundTrackGridPane.setDisable(true);

        }
        if (rerollDraft){
            setNotVisibleThrowDiceButton();
            disableThrowDiceButton();
            rerollDraft=false;
            enableEndTurnButton();
            enablePlaceDiceButton();
        }
    }
    public void endRound(Round[] roundTrack){
        modifiedRoundTrack(roundTrack);

    }
    public void modifiedRoundTrack(Round[] roundTrack){
        for (int i=0;i<10;i++){
            if(roundTrack[i]!=null){
                updateRoundTrack(roundTrack[i],i);
            }

        }
    }
    public void modifiedToken(ModifiedTokenEvent modifiedTokenEvent){
        setPlayerSFLabel(modifiedTokenEvent.getPlayer(),modifiedTokenEvent.getPlayerToken());
        setToolCardSFLabel(modifiedTokenEvent.getToolCard(),modifiedTokenEvent.getToolCardToken());
    }
    public void sendEffect(SendEffectEvent effectEvent){
        usingTool=false;
        alreadyUsedTool=true;
        disableToolCardButton();
        effectEvent.getEffect().accept(this);
    }
    public void visit(Exchange exchange){
        String cardMessage=ACTIVE_TOOL_CARD;
        reserveGridPane.setDisable(false);
        exchangeDice=true;
        switch (exchange.getEffectType()){
            case DRAFTPOOLROUNDTRACKEXCHANGE:
                fromRoundTrack=true;
                cardMessage=cardMessage + EXCHANGEWITHROUND;
                break;
            case DRAFTPOOLBAGEXCHANGE:
                cardMessage=cardMessage+EXCHANGEWITHBAG;
                break;
        }
        guiModel.alertMessage(cardMessage);
    }
    public void visit(Modify modify){
        String cardMessage=ACTIVE_TOOL_CARD;
        switch (modify.getEffectType()){
            case UPORDOWNVALUEMODIFY:
                setVisiblePlusMinusButtons();
                enablePlusMinusButtons();
                upOrDown=true;
                cardMessage=cardMessage + MODIFY;
                enablePlusMinusButtons();
                break;
            default:
                modifyDice=true;
                cardMessage=cardMessage+MODIFY;
                break;

        }
        guiModel.alertMessage(cardMessage);
    }
    public void visit(Move move){
        disableToolCardButton();
        if (!alreadyPlaced){
            enablePlaceDiceButton();
            reserveGridPane.setDisable(false);
        }
        mainPlayerGridPane.setDisable(false);
        reserveSelectedDice=-1;
        String cardMessage=ACTIVE_TOOL_CARD;
        moveDice=true;
        switch (move.getEffectType()){
            case NOVALUEBOUND:
                cardMessage=cardMessage + MOVE;
                break;
            case NOCOLORBOUND:
                cardMessage=cardMessage + MOVE;
                break;
            case MOVETWODICE:
                moveTwice=true;
                cardMessage=cardMessage + MOVETWO;
                break;
            case MOVETWODICESELECTEDCOLOR:
                moveTwice=true;
                moveSelectedRoundColor=true;
                roundTrackGridPane.setDisable(false);
                mainPlayerGridPane.setDisable(true);
                cardMessage=cardMessage+MOVESELECTEDCOLOR;
                break;
        }
        guiModel.alertMessage(cardMessage);
    }
    public void visit(PlacementWithoutVicinity placementWithoutVicinity){
        reserveSelectedDice=-1;
        enablePlaceDiceButton();
        String cardMessage=ACTIVE_TOOL_CARD;
        cardMessage=cardMessage+PLACENOVICINITYBOUNDS;
        guiModel.alertMessage(cardMessage);
    }
    public void visit(RerollDraftPool rerollDraftPool){
        String cardMessage=ACTIVE_TOOL_CARD;
        cardMessage=cardMessage+REROLL;
        guiModel.alertMessage(cardMessage);
        setVisibleThrowDiceButton();
        enableThrowDiceButton();
        rerollDraft=true;
    }
    public void visit(SecondPlacement secondPlacement){
        String cardMessage=ACTIVE_TOOL_CARD;
        cardMessage=cardMessage+PLACEANOTHER;
        guiModel.alertMessage(cardMessage);
        alreadyPlaced=false;
        enablePlaceDiceButton();
        reserveGridPane.setDisable(false);
        mainPlayerGridPane.setDisable(false);
        enableEndTurnButton();
    }

    public void endByTime(String player){
        if (getIndex(player)==0){
            disableMainPlayerButtons();
            selectedDiceImageView.setImage(null);
            alertMainPlayerSuspended();
        }else{
            setDisconnectedPlayer(player);
        }

    }
    public void updateReconnection(UpdateReconnectedClientEvent updateReconnectedClientEvent){
        if (getIndex(updateReconnectedClientEvent.getPlayer())==0){
            for (Player player:updateReconnectedClientEvent.getPlayers()){
                setPlayerSFLabel(player.getName(),player.getToken());
                updateScheme(player.getName(),player.getWindow());

            }
            updateDraft(updateReconnectedClientEvent.getDraftPool());
            for (int i=0;i<10;i++){
                if(updateReconnectedClientEvent.getRoundTrack()[i]!=null){
                    updateRoundTrack(updateReconnectedClientEvent.getRoundTrack()[i],i);
                }

            }
            for (int j=0;j<3;j++){
                setToolCardSFLabel(j,updateReconnectedClientEvent.getToolCards()[j].getToken());
            }
        }else{
            setReconnectedPlayer(updateReconnectedClientEvent.getPlayer());
        }
    }
    public void matchEnd(EndMatchEvent endMatch){
        final String POINTS_FIRST_PART_MESSAGE = "Punteggi finali:\n\n";
        final String MAIN_PLAYER_WINNER_MESSAGE = "Hai vinto!";
        final String OTHER_PLAYER_WINNER_MESSAGE = "Hai perso! Il vincitore è ";
        final String NEW_GAME_MESSAGE = "\nVuoi iniziare una nuova partita?";
        String pointsMessage;

        int maxPoints=-20;
        int winnerPosition=-1;
        String winner;
        disableMainPlayerButtons();
        reserveGridPane.setDisable(true);
        roundTrackGridPane.setDisable(true);
        mainPlayerGridPane.setDisable(true);
        for (int i=0; i< endMatch.getPoints().size();i++){
            if (endMatch.getPoints().get(i)>=maxPoints){
                maxPoints=endMatch.getPoints().get(i);
                winnerPosition=endMatch.getPoints().indexOf(endMatch.getPoints().get(i));
            }
        }
        winner=endMatch.getPlayers().get(winnerPosition);
        pointsMessage = POINTS_FIRST_PART_MESSAGE;
        for (int i=0; i<endMatch.getPlayers().size();i++){
            pointsMessage = pointsMessage + endMatch.getPlayers().get(i) + ":\t\t\n" + endMatch.getPoints().get(i).toString() + "\n";
        }
        if (winner.equals(playersName.get(0))){
            pointsMessage = pointsMessage + MAIN_PLAYER_WINNER_MESSAGE;
        }
        else {
            pointsMessage = pointsMessage + OTHER_PLAYER_WINNER_MESSAGE + winner;
        }
        pointsMessage=pointsMessage+NEW_GAME_MESSAGE;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End Match");
        alert.setHeaderText(null);
        alert.setContentText(pointsMessage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            // Azione da eseguire se si vuole fare nuova patita
        } else {
            // azione da eseguire se non si vuole fare nuova partita o si chiude finestra dialogo
        }
    }
    public void lastPlayerLeft(){
        final String MAIN_PLAYER_WINNER_MESSAGE = "Hai vinto perchè sei rimasto l'unico giocatore in partita";
        final String NEW_GAME_MESSAGE = "\nVuoi iniziare una nuova partita?";
        String message;
        message=MAIN_PLAYER_WINNER_MESSAGE+NEW_GAME_MESSAGE;
        disableMainPlayerButtons();
        reserveGridPane.setDisable(true);
        roundTrackGridPane.setDisable(true);
        mainPlayerGridPane.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End Match");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            // Azione da eseguire se si vuole fare nuova patita
        } else {
            // azione da eseguire se non si vuole fare nuova partita o si chiude finestra dialogo
        }
    }

}
