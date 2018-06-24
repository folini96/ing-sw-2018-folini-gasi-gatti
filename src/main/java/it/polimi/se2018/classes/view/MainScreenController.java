package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.events.EndMatchEvent;
import it.polimi.se2018.classes.model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


import javax.swing.*;
import java.net.URL;
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

    private ViewModel guiModel = new ViewModel();
    private GUIHandler guiHandler;
    private Boolean alredyPlaced;
    private Boolean placingDice;
    private Boolean isMyTurn;

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

    private boolean plusMinus = true;
    private boolean plusMinusClicked = false;

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
            publicObjectImageViewArray[i].setImage(image);
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

    public void alertPlayerDisconnected(String name){
        final String PLAYER_DISCONNECTED_MESSAGE = " è stato disconnesso!";
        guiModel.alertMessage(name+PLAYER_DISCONNECTED_MESSAGE);
    }
    public void alertPlayerReconnected(String name){
        final String PLAYER_RICONNECTED_MESSAGE = " si è riconnesso!";
        guiModel.alertMessage(name+PLAYER_RICONNECTED_MESSAGE);
    }
    public void alertMainPlayerDisconnected(String name){
        final String MAIN_PLAYER_DISCONNECTED_MESSAGE = "Sei stato disconnesso!";
        guiModel.alertMessage(name+MAIN_PLAYER_DISCONNECTED_MESSAGE);
    }
    public void alertPlayerRiconnected(String name){
        final String MAIN_PLAYER_RICONNECTED_MESSAGE = "Sei stato riconesso!";
        guiModel.alertMessage(name+MAIN_PLAYER_RICONNECTED_MESSAGE);
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
       // setToolCardImageView(toolCards);
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
        selectedDiceImageView.setImage(null);
       guiHandler.endTurn();
    }
    /**
     * notice the intention to use a Tool Card
     * @param event
     */
    @FXML
    private void toolCardButtonClicked(ActionEvent event){
        final String NO_TOOL_CARD_MESSAGE = "Seleziona una Tool Card!";
        // segnala che si vuole utilizzare una tool card
        disableMainPlayerButtons();
        if (selectedToolCard==-1){
            guiModel.alertMessage(NO_TOOL_CARD_MESSAGE);
            enableMainPlayerButtons();
            return;
        }
        // segnala la tool card da utilizzare con selectedToolCard
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
        try {
            reserveSelectedDice = GridPane.getColumnIndex(node);
        }
        catch (NullPointerException e){
            reserveSelectedDice = 0;
        }
        ObservableList<Node> list = reserveGridPane.getChildren();
        ImageView imageView = (ImageView) list.get(reserveSelectedDice);
        Image image = imageView.getImage();
        selectedDiceImageView.setImage(image);
        placeDiceButtonClicked=false;
        enablePlaceDiceButton();
    }
    @FXML
    private void selectedWindowBoxEvent(MouseEvent event){
        if (!placeDiceButtonClicked){
            return;
        }

        Node node = (Node) event.getSource();
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
        alredyPlaced=true;
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
        ObservableList<Node> list = roundTrackGridPane.getChildren();
        ImageView imageView = (ImageView) list.get(roundTrackSelectedDice[0]*10+roundTrackSelectedDice[1]);
        Image image = imageView.getImage();
        selectedDiceBisImageView.setImage(image);


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
        plusMinusClicked = true;
        plusMinus = true;
    }
    @FXML
    private void plusButtonClicked(ActionEvent event){
        plusMinusClicked = true;
        plusMinus = false;
    }
    @FXML
    private void throwDiceButtonClicked(ActionEvent event){
        //rilancia i dadi
    }
    @FXML
    private void toolCardZoomButtonClicked(ActionEvent event){
        // zoom tool card
    }
    @FXML
    private void privateCardZoomButtonClicked(ActionEvent event){
        // zoom OBIETTIVO PRIVATO
    }
    @FXML
    private void publicCardZoomButtonClicked(ActionEvent event){
        // ZOOM OBIETTIVI PUBBLICI
    }
    public void resetPlusMinus(){
        plusMinusClicked = false;
    }

    public int getSelectedToolCard(){
        return selectedToolCard;
    }

    public int choseNewValue(){
        final String CHOSE_DICE_NUMBER_MESSAGE = "Quale valore vuoi assegnare al dado?";
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

    public void SetOpacityOthers(int row, int column){
        ObservableList<Node> lista = playersGridPaneArray[0].getChildren();
        ImageView selected = (ImageView) lista.get(row*5 + (column));
        for (Node node: lista){
         ImageView current = (ImageView) node;
         if (current!=selected){
             current.setOpacity(0.4);
         }
        }
    }

    public void ResetOpacityOthers(){
        ObservableList<Node> lista = playersGridPaneArray[0].getChildren();
        for (Node node: lista){
            ImageView current = (ImageView) node;
            node.setOpacity(1);
            }
    }



    public void setGuiHandler(GUIHandler guiHandler){
        this.guiHandler=guiHandler;
    }
    public void checkStartTurn(String playerName){
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
            placingDice=false;
            alredyPlaced=false;
            resetValuesforPlaceDice();
            enableMainPlayerButtons();
        }
        guiModel.alertMessage(message);

    }
    public void modifiedWindow(WindowSide window,String player){
        if ((isMyTurn)&&(placingDice)){
            reserveGridPane.setDisable(true);
            mainPlayerGridPane.setDisable(true);
            enableMainPlayerButtons();
            disablePlaceDiceButton();
        }

        updateScheme(player,window);
    }
    public void modifiedDraft(ArrayList<Dice> draftPool){
        updateDraft(draftPool);
    }
    public void endRound(Round[] roundTrack){
        for (int i=0;i<10;i++){
            if(roundTrack[i]!=null){
                updateRoundTrack(roundTrack[i],i);
            }

        }

    }
    public void endMatch(ArrayList<String> players, ArrayList<Integer> points){
        String message= "";
        Integer maxPoints=0;
        int winnerPosition=0;
        String winner;

        for (Integer point:points){
            if (point>maxPoints){
                maxPoints=point;
                winnerPosition=points.indexOf(point);

            }
        }
        winner=players.get(winnerPosition);
        for (String name:players){
            message=message + name + "punteggio: " + points.get(players.indexOf(name)).toString() + ", ";
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Il vincitore è "+winner);
        alert.setContentText(message);
        alert.showAndWait();

    }
    public void endByTime(){
        disableMainPlayerButtons();
        selectedDiceImageView.setImage(null);

    }

    public void matchEnd(EndMatchEvent endMatch){
        final String POINTS_FIRST_PART_MESSAGE = "Punteggi finali:\n\n";
        final String MAIN_PLAYER_WINNER_MESSAGE = "Hai vinto!";
        final String OTHER_PLAYER_WINNER_MESSAGE = "Hai perso! Il vincitore è ";
        final String NEW_GAME_MESSAGE = "\nVuoi iniziare una nuova partita?";
        String pointsMessage;

        int maxPoints=-1;
        int winnerPosition=-1;
        String winner;
        for (int i=0; i< endMatch.getPoints().size();i++){
            if (endMatch.getPoints().get(i)>maxPoints){
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


}
