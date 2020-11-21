package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.GUI.ViewCenter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapController implements Initializable{
    private static final Logger logger = Logger.getLogger(MapController.class.getName());
    private ViewCenter viewCenter;



    public void returnButtonClick(){
        logger.log(Level.INFO, "Go to Order page from Map page.");
        viewCenter.gotoOrder();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }
}
