package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import sun.plugin2.os.windows.Windows;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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


    // position of the selected tool card (0,1,2)(-1 if not selected)
    private int selectedToolCard = -1;

    //position of the selected dice in the reserve
    private int reserveSelectedDice = -1;

    //placeDiceButtonClicked
    private boolean placeDiceButtonClicked = false;

    //position of the selected box on the window (row,column)
    private int[] windowSelectedDice = new int[] {-1,-1};

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
        List<Dice> dices = draftPool;
        String url;
        setRoundLabel(round+1);
        for (int i=0; i<9; i++){
            if (dices.isEmpty()){
                url = EMPTY_SPACE_FILE_NAME;

            }
            else{
                url = dices.get(0).getColor().name() + Integer.toString(dices.get(0).getValue());
                dices.remove(0);
            }
            setImageInGridPane(reserveGridPane, url, i,0,true);
        }
    }

    public void updateRoundTrack(Round round, int roundNumber){
        List<Dice> dices = round.getLeftDices();
        String url;
        for (int i=0; i<9; i++){
            if (dices.isEmpty()){
                url = null;
            }
            else{
                url = dices.get(0).getColor().name() + Integer.toString(dices.get(0).getValue());
                dices.remove(0);
            }
            setImageInGridPane(reserveGridPane, url, i,roundNumber,true);
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
        if (selectedToolCard==-1){
            guiModel.alertMessage(NO_TOOL_CARD_MESSAGE);
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

        guiHandler.placeDice(reserveSelectedDice,windowSelectedDice[0],windowSelectedDice[1]);
        resetValuesforPlaceDice();


    }
    public void setGuiHandler(GUIHandler guiHandler){
        this.guiHandler=guiHandler;
    }
    public void checkStartTurn(String playerName){
        setCurrentPlayerLabel(playerName);
      if (getIndex(playerName)==0){
          enableMainPlayerButtons();
      }

    }
    public void notValideMoveMessagge(String message){
        guiModel.alertMessage(message);
        resetValuesforPlaceDice();
        enableMainPlayerButtons();
    }
    public void modifiedWindow(WindowSide window,String player){
        enableMainPlayerButtons();
        updateScheme(player,window);
    }
}
