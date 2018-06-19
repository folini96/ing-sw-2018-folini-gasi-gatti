package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class ViewModel {

    /**
     * verify if a given String is a correct IP format
     * @param ipAddress IP to verify
     * @return true if the format is correct
     */
    public boolean isIP( String ipAddress ) {
        String[] tokens = ipAddress.split("\\.");
        if (tokens.length != 4) { return false; }
        for (String str : tokens) {
            int i = Integer.parseInt(str);
            if ((i < 0) || (i > 255)) { return false; }
        }
        return true;
    }

    //da cancellare
    public Stage showNewStage(String pattern)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(pattern));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Sagrada");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        return primaryStage;
    }

    /**
     * show an Information Dialog
     * @param message message to be shown
     */
    public void alertMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

