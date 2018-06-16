package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class privateObjCardSelectionController implements Initializable {

    final private static String URL = "";


    @FXML
    private ImageView window1ImageView = new ImageView();
    @FXML
    private ImageView window2ImageView = new ImageView();
    @FXML
    private ImageView window3ImageView = new ImageView();
    @FXML
    private ImageView window4ImageView = new ImageView();

    // ImageView array for Windows
    private ImageView[] windowsImageViewArray;

    viewModel model = new viewModel();

    private int selected = -1;
    private String userName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // tool cards array assignment
        windowsImageViewArray = new ImageView[4];
        windowsImageViewArray[0] = window1ImageView;
        windowsImageViewArray[1] = window2ImageView;
        windowsImageViewArray[2] = window3ImageView;
        windowsImageViewArray[3] = window4ImageView;
    }
    public void setUserName(String name){
        userName = name;
    }
    @FXML
    private void selected1 (MouseEvent event){
        selected = 0;
    }
    @FXML
    private void selected2 (MouseEvent event){
        selected = 1;
    }
    @FXML
    private void selected3 (MouseEvent event){
        selected = 2;
    }
    @FXML
    private void selected4 (MouseEvent event){
        selected = 3;
    }
    @FXML
    private void setToolCardImageView( ToolCard[] toolCards){
        for (int i=0;i<4;i++){
            Image image = new Image(URL+toolCards[i].getName()+".jpg");
            windowsImageViewArray[i].setImage(image);
        }
    }
    @FXML
    private void confirmButtonClicked (ActionEvent event){

        if (selected==-1){
            final String SELECT_A_WINDOW_MESSAGE = "Seleziona una finestra!";
            model.alertMessage(SELECT_A_WINDOW_MESSAGE);
            return;
        }

        model.alertMessage(Integer.toString(selected));
        //notifica numero finestra selezionata

    }
}
