package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.GUI.ViewCenter;

import java.util.logging.Level;
import java.util.logging.Logger;

import static main.java.com.uci.warehouse.Model.Warehouse.*;

public class LoadFileController {
    //TODO @qirui
    private static final Logger logger = Logger.getLogger(LoadFileController.class.getName());
    private ViewCenter viewCenter;


    @FXML
    private Button LoadFIle_Button;
    @FXML
    private TextField OrderFileName;
    @FXML
    private TextField loctionFileName;

    public void LoadFIle_ButtonClick(){
        logger.log(Level.INFO, "LoadFile. Go to Menu");
        loadOrdersFromFileAndRetry(OrderFileName.getText());
        //loadLocationFromFileAndRetry(loctionFileName.getText());
        //TODO @qirui
        viewCenter.gotoMap();
    }
}
