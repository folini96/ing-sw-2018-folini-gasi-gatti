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

    private static final String SOCKET_CONNESSION = "Socket";
    private static final String RMI_CONNECTION = "RMI";
    private static final String MULTI_PLAYER = "Multi player";
    private static final String SINGLE_PLAYER = "Single player";

    private ViewModel viewModel = new ViewModel();
    private GUIHandler guiHandler;
    @FXML
    private ComboBox<String> connectionComboBox;
    @FXML
    private ComboBox<String> playerModeComboBox;
    @FXML
    private TextField serverAddressTextField;

    ObservableList<String> connectionList = FXCollections.observableArrayList(SOCKET_CONNESSION,RMI_CONNECTION);
    ObservableList<String> playerModeList = FXCollections.observableArrayList(MULTI_PLAYER,SINGLE_PLAYER);

    /**
     * Initialization of the two Combo Boxes
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionComboBox.setItems(connectionList);
        playerModeComboBox.setItems(playerModeList);
    }

    /**
     * abilitate serverAddressTextField if socket connection is chosen
     * @param event action on connectionComboBox
     */
    @FXML
    private void serverAddressTextFieldManager(ActionEvent event){
        if (connectionComboBox.getValue().equals(SOCKET_CONNESSION))
            serverAddressTextField.setDisable(false);
        else
            serverAddressTextField.setDisable(true);
    }

    /**
     * validate the settings
     * @param event confirmButtonClicked
     * @throws Exception
     */
    @FXML
    private void confirmButtonClicked(ActionEvent event) throws Exception {

        final String INVALID_IP_MESSAGE = "Inserisci un formato IP valido!";
        final String NULL_CONNECTION_COMBO_BOX_MESSAGE = "Scegli una connessione!";
        final String NULL_PLAYER_MODE_COMBO_BOX_MESSAGE = "Scegli una modalità di gioco!";

        if (connectionComboBox.getSelectionModel().isEmpty()){
            viewModel.alertMessage(NULL_CONNECTION_COMBO_BOX_MESSAGE);
            return;
        }

        if (playerModeComboBox.getSelectionModel().isEmpty()){
            viewModel.alertMessage(NULL_PLAYER_MODE_COMBO_BOX_MESSAGE);
            return;
        }

        if (connectionComboBox.getValue().equals(SOCKET_CONNESSION))
            if (!(viewModel.isIP(serverAddressTextField.getText()))){
                viewModel.alertMessage(INVALID_IP_MESSAGE);
                serverAddressTextField.setText("");
                return;
            }

        //ESEGUI CONNESSIONE

        //SCEGLI MODALITà PARTITA


        //Da modificare
        guiHandler.setClientType(RMI_CONNECTION);
        ((Node)(event.getSource())).getScene().getWindow().hide();
        guiHandler.userNameStage();

    }
    public void setGuiHandler(GUIHandler guiHandler){
        this.guiHandler=guiHandler;
    }

}
