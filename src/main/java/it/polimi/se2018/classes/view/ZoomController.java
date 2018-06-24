package it.polimi.se2018.classes.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ZoomController implements Initializable {
    @FXML
    private ImageView zoom1ImageView = new ImageView();
    @FXML
    private ImageView zoom2ImageView = new ImageView();
    @FXML
    private ImageView zoom3ImageView = new ImageView();

    private ImageView[] zoomImageViewArray;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zoomImageViewArray = new ImageView[3];
        zoomImageViewArray[0] = zoom1ImageView;
        zoomImageViewArray[1] = zoom2ImageView;
        zoomImageViewArray[2] = zoom3ImageView;
    }

    public void setImages(ArrayList<ImageView> images){
        if (images.size()==1){
            zoomImageViewArray[1].setImage(images.get(0).getImage());
        }
        else {
            int i=0;
            for (ImageView imageView: images){
                zoomImageViewArray[i].setImage(imageView.getImage());
                i++;
            }
        }
    }
}

