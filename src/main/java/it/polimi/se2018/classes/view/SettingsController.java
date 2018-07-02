package it.polimi.se2018.classes.view;

import it.polimi.se2018.classes.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * class controller for the settings GUI
 * @author Leonard Gasi
 */
public class SettingsController implements Initializable {

    private static final String SOCKET_CONNECTION = "Socket";
    private static final String RMI_CONNECTION = "RMI";

    private ViewModel viewModel = new ViewModel();
    private GUIHandler guiHandler;
    @FXML
    private ComboBox<String> connectionComboBox;
    @FXML
    private TextField serverIPTextField;

    private ObservableList<String> connectionList = FXCollections.observableArrayList(SOCKET_CONNECTION,RMI_CONNECTION);


    /**
     * Initialization of the two Combo Boxes
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionComboBox.setItems(connectionList);

    }


    /**
     * validate the settings
     * @param event confirmButtonClicked
     * @throws Exception
     */
    @FXML
    private void confirmButtonClicked(ActionEvent event) throws Exception {

        final String NULL_CONNECTION_COMBO_BOX_MESSAGE = "Scegli una connessione!";
        final String INVALID_IP_MESSAGE = "Inserisci un indirizzo IP valido!";

        if (connectionComboBox.getSelectionModel().isEmpty()){
            viewModel.alertMessage(NULL_CONNECTION_COMBO_BOX_MESSAGE);
            return;
        }

        if (!viewModel.isIP(serverIPTextField.getText())){
            viewModel.alertMessage(INVALID_IP_MESSAGE);
        }

        // codice per indirizzo ip ( per avere testo-->> serverIPTextField.getText()


        if (connectionComboBox.getValue().equals(RMI_CONNECTION)){
            guiHandler.setClientType(RMI_CONNECTION);
        }else{
            guiHandler.setClientType(SOCKET_CONNECTION);
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();
        guiHandler.userNameStage();

    }
    public void setGuiHandler(GUIHandler guiHandler){
        this.guiHandler=guiHandler;
    }

}
