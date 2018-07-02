package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.view.MainScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class controller for the userName selection GUI
 * @author Leonard Gasi
 */
public class UserNameController {
    private GUIHandler guiHandler;
    private ViewModel viewModel = new ViewModel();

    @FXML
    private TextField userNameTextField;
    /**
     * validate the chosen username
     * @param event userNameConfirmButton clicked
     * @throws Exception
     */
    @FXML
    private void userNameConfirmButtonClicked (ActionEvent event) throws Exception {

        String name = userNameTextField.getText();
        guiHandler.createClient(name);

    }
    public void setGuiHandler(GUIHandler guiHandler){
        this.guiHandler=guiHandler;
    }
    public void askUsername(){
        final String INVALID_USERNAME = "Il nome che hai inserito è già in uso. Inserire un altro nome";
        viewModel.alertMessage(INVALID_USERNAME);
    }
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
    public void connectionError(){
        final String LOST_CONNECTION = "Non è stato possibile connettersi al server";
        String message;
        message=LOST_CONNECTION;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
}
