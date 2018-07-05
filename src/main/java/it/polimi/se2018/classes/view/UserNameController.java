package it.polimi.se2018.classes.view;


import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Leonard Gasi
 * controller of userName.fxml, it is used to ask the username to the player
 */
public class UserNameController {
    private GUIHandler guiHandler;
    private ViewModel viewModel = new ViewModel();

    @FXML
    private TextField userNameTextField;

    /**
     * validates the chosen username
     * @param event userNameConfirmButton clicked
     * @throws Exception
     */
    @FXML
    private void userNameConfirmButtonClicked (ActionEvent event) throws Exception {

        String name = userNameTextField.getText();
        guiHandler.createClient(name);

    }

    /**
     * set the reference to the intermediary with the network
     * @param guiHandler reference to the GUIHandler
     */
    public void setGuiHandler(GUIHandler guiHandler){
        this.guiHandler=guiHandler;
    }

    /**
     * notifies that the chosen username is not available
     */
    public void askUsername(){
        final String INVALID_USERNAME = "Il nome che hai inserito è già in uso. Inserire un altro nome";
        viewModel.alertMessage(INVALID_USERNAME);
    }

    /**
     * closes userName.fxml and shows windowSelection.fxml
     */
    public void closeUsernameScene(){
        userNameTextField.getScene().getWindow().hide();
        try{

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/windowSelection.fxml"));
            Parent root = loader.load();
            guiHandler.setWindowSelectionController(loader.getController());
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
     * notifies that the connection with the server has failed
     */
    public void connectionError(){
        SettingsController settingsController;
        final String LOST_CONNECTION = "Non è stato possibile connettersi al server";
        String message;
        message=LOST_CONNECTION;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        try{
            userNameTextField.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/settings.fxml"));
            Parent root = loader.load();
            settingsController= loader.getController();
            settingsController.setGuiHandler(guiHandler);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Sagrada");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
