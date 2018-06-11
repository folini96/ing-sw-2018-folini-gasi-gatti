package it.polimi.se2018.classes.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class settingsController implements Initializable {

    private static final String SOCKET_CONNESSION = "Socket";
    private static final String RMI_CONNECTION = "RMI";
    private static final String MULTI_PLAYER = "Multi player";
    private static final String SINGLE_PLAYER = "Single player";

    private viewModel ViewModel = new viewModel();

    @FXML
    private ComboBox<String> connectionComboBox;

    @FXML
    private ComboBox<String> playerModeComboBox;

    @FXML
    private TextField serverAddressTextField;



    ObservableList<String> connectionList = FXCollections.observableArrayList(SOCKET_CONNESSION,RMI_CONNECTION);
    ObservableList<String> playerModeList = FXCollections.observableArrayList(MULTI_PLAYER,SINGLE_PLAYER);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionComboBox.setItems(connectionList);
        playerModeComboBox.setItems(playerModeList);
    }

    public void serverAddressTextFieldManager(ActionEvent event){
        if (connectionComboBox.getValue().equals(SOCKET_CONNESSION))
            serverAddressTextField.setDisable(false);
        else
            serverAddressTextField.setDisable(true);
    }
    public void confirmButtonClicked(ActionEvent event) throws Exception {

        final String INVALID_IP_MESSAGE = "Inserisci un formato IP valido!";
        final String NULL_CONNECTION_COMBO_BOX_MESSAGE = "Scegli una connessione!";
        final String NULL_PLAYER_MODE_COMBO_BOX_MESSAGE = "Scegli una modalità di gioco!";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);


        if (connectionComboBox.getSelectionModel().isEmpty()){
            alert.setContentText(NULL_CONNECTION_COMBO_BOX_MESSAGE);
            alert.showAndWait();
            return;
        }

        if (playerModeComboBox.getSelectionModel().isEmpty()){
            alert.setContentText((NULL_PLAYER_MODE_COMBO_BOX_MESSAGE));
            alert.showAndWait();
            return;
        }

        if (connectionComboBox.getValue().equals(SOCKET_CONNESSION))
            if (!(ViewModel.isIP(serverAddressTextField.getText()))){
                alert.setContentText(INVALID_IP_MESSAGE);
                alert.showAndWait();
                serverAddressTextField.setText("");
                return;
            }

        //ESEGUI CONNESSIONE

        //SCEGLI MODALITà PARTITA

        ((Node)(event.getSource())).getScene().getWindow().hide();
        Stage newStage = ViewModel.showNewStage("userName.fxml");
        newStage.setResizable((false));



    }
}
