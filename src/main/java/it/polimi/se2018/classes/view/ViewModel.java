package it.polimi.se2018.classes.view;


import javafx.scene.control.Alert;


/**
 * @author Leonard Gasi
 * used to implement some methods that are usefull for the GUI controller classes
 */

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

