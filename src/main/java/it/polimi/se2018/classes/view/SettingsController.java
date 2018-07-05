package it.polimi.se2018.classes.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Leonard Gasi
 * controller of settingsCOntroller.fxml, it is used to ask the type of the connection (RMI or socket), the server IP address and the number of the port to the player
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
    @FXML
    private TextField doorTextField;

    private ObservableList<String> connectionList = FXCollections.observableArrayList(SOCKET_CONNECTION,RMI_CONNECTION);


    /**
     * Initialization of the two Combo Boxes
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectionComboBox.setItems(connectionList);

    }


    /**
     * validates the settings
     * @param event confirmButton clicked
     * @throws Exception
     */
    @FXML
    private void confirmButtonClicked(ActionEvent event) throws Exception {

        final String NULL_CONNECTION_COMBO_BOX_MESSAGE = "Scegli una connessione!";
        final String INVALID_IP_MESSAGE = "Inserisci un indirizzo IP valido!";
        final String INVALID_DOOR="inserisci una porta valida";
        if (connectionComboBox.getSelectionModel().isEmpty()){
            viewModel.alertMessage(NULL_CONNECTION_COMBO_BOX_MESSAGE);
            return;
        }

        if (!viewModel.isIP(serverIPTextField.getText())){
            viewModel.alertMessage(INVALID_IP_MESSAGE);
        }else if(doorTextField.getText().equals("")){
            viewModel.alertMessage(INVALID_DOOR);
        }else{
            if (connectionComboBox.getValue().equals(RMI_CONNECTION)){
                guiHandler.setClientType(RMI_CONNECTION,serverIPTextField.getText(),Integer.parseInt(doorTextField.getText()));
            }else{
                guiHandler.setClientType(SOCKET_CONNECTION,serverIPTextField.getText(),Integer.parseInt(doorTextField.getText()));
            }
            ((Node)(event.getSource())).getScene().getWindow().hide();
            guiHandler.userNameStage();
        }




    }

    /**
     * set the reference to the intermediary with the network
     * @param guiHandler reference to the GUIHandler
     */
    public void setGuiHandler(GUIHandler guiHandler){
        this.guiHandler=guiHandler;
    }

}
