package it.polimi.se2018.classes.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * class controller for the multiplayer GUI
 * @author Leonard Gasi
 */


public class mainScreenController implements Initializable {

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

    // GridPane array
    private GridPane[] playersGridPaneArray;

    //Label array for player's name
    private Label[] playersNameLabelArray;

    // Label array for player's SF
    private Label[] playersSFLabelArray;

    // ImageView array for Tool Cards
    private ImageView[] toolCardsImageViewArray;

    // ImageView array for Public Objects
    private ImageView[] publicObjectImageViewArray;

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
        playersNameLabelArray[2]= player1Label;
        playersNameLabelArray[3]= player1Label;

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


        //setImageInGridPane(playersGridPaneArray[0],"view/img/2.jpg",2,3);
    }


    /**
     *
     * @param gridPane GridPane that we want to modify
     * @param url url of the picture
     * @param col index of the column (starts from 0)
     * @param row index of the row (starts from 0)
     */

    public void setImageInGridPane(GridPane gridPane, String url, int col, int row) {
        ObservableList<Node> lista = gridPane.getChildren();
        Image image = new Image(url);
        ImageView imageView = (ImageView) lista.get((((row)*gridPane.impl_getColumnCount()))+(col));
        imageView.setImage(image);

    }


    /**
     *
     * @param round number that will be shown in round_label
     */
    public void setRound( int round){
        roundLabel.setText( Integer.toString(round));
    }

    /**
     *
     * @param player name of the player that will be shown in current_player_label
     */
    public void setCurrentPlayer (String player){
        currentPlayerLabel.setText(player);
    }



}
