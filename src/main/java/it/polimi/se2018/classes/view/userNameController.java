package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * class controller for the userName selection GUI
 * @author Leonard Gasi
 */
public class userNameController {

    @FXML
    private TextField userNameTextField;

    /**
     * validate the chosen username
     * @param event userNameConfirmButton clicked
     * @throws Exception
     */
    @FXML
    private void userNameConfirmButtonClicked (ActionEvent event) throws Exception {

        // CONTROLLO USERNAME
        String name = userNameTextField.getText();

        ((Node)(event.getSource())).getScene().getWindow().hide();

        //da cambiare
        Stage st = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
        Parent sceneMain = loader.load();
        mainScreenController controller = loader.<mainScreenController>getController();
        controller.setMainPlayerName(name);
        Scene scene = new Scene(sceneMain);
        st.setScene(scene);
        st.setResizable(false);
        st.setTitle("Sagrada");
        st.show();
    }
}
