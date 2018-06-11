package it.polimi.se2018.classes.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;

public class viewModel {


    public boolean isIP( String ipAddress ) {
        String[] tokens = ipAddress.split("\\.");
        if (tokens.length != 4) { return false; }
        for (String str : tokens) {
            int i = Integer.parseInt(str);
            if ((i < 0) || (i > 255)) { return false; }
        }
        return true;
    }

    public Stage showNewStage(String pattern)throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(pattern));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Sagrada");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        return primaryStage;
    }
}

