package it.polimi.se2018.classes.view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class userNameController {

    private viewModel ViewModel = new viewModel();

    @FXML
    private TextField userNameTextField;

    public void userNameConfirmButtonClicked (ActionEvent event) throws Exception{

        // CONTROLLO USERNAME

        ((Node)(event.getSource())).getScene().getWindow().hide();
        Stage newStage = ViewModel.showNewStage("mainScreen.fxml");
        newStage.setMinHeight(410);
        newStage.setMinWidth(620);
        newStage.setResizable(false);


    }
}
