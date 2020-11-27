package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.GUI.ViewCenter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController {
    //TODO @Siqian
    private static final Logger logger = Logger.getLogger(MenuController.class.getName());
    private ViewCenter viewCenter;


    @FXML
    private Button LocatProduct_Button;
    @FXML
    private Button CreateNewOrder_Button;
    @FXML
    private Button LoadOrder_Button;

    public void LocatProduct_Button_Clilk(){
        logger.log(Level.INFO, "Locate a product. Go to Product on Map");

    }
    public void CreateNewOrder_Button_Clilk(){
        logger.log(Level.INFO, "Create New order/s. Go to CreateOrder");
        viewCenter.gotoCreateOrder();
    }
    public void LoadOrder_Button_Clilk(){
        logger.log(Level.INFO, "Load unfulfilled order. Go to LoadOrder");
    }

    public void setApp(ViewCenter viewCenter) {
    }
}
