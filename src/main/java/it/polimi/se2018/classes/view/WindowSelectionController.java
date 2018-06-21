package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.view.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sun.nio.ch.WindowsAsynchronousFileChannelImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WindowSelectionController implements Initializable {

    final private static String URL = "/img/";
    private GUIHandler guiHandler;

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

    ViewModel viewModel = new ViewModel();

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
    public void setWindowImageView( WindowSide[] windowSides){
        for (int i=0;i<4;i++){
            Image image = new Image(getClass().getResource(URL+windowSides[i].getName()+".jpg").toExternalForm());
            windowsImageViewArray[i].setImage(image);
        }
    }
    @FXML
    private void confirmButtonClicked (ActionEvent event){

        if (selected==-1){
            final String SELECT_A_WINDOW_MESSAGE = "Seleziona una finestra!";
            viewModel.alertMessage(SELECT_A_WINDOW_MESSAGE);
            return;
        }
        showMainScreen();
        guiHandler.sendChosenWindow(selected);


    }
    public void showMainScreen(){
        window1ImageView.getScene().getWindow().hide();
        try{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/mainScreen.fxml"));
            Parent root = loader.load();
            guiHandler.setMainScreenController(loader.getController());
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Sagrada");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void setGuiHandler(GUIHandler guiHandler){
        this.guiHandler=guiHandler;
    }
}
