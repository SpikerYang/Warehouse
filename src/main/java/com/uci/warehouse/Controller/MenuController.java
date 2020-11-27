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

public class MenuController implements Initializable{
    //TODO @zheng

    private static final Logger logger = Logger.getLogger(MenuController.class.getName());
    private ViewCenter viewCenter;

    @FXML
    private Button creat_new_order_button;

    @FXML
    private TextField login_username;

    @FXML
    private TextField login_password;


    public void createNewOrderButtonClick(){
        logger.log(Level.INFO, "User create a new order. Go to page createNewOrder.");
        viewCenter.gotoCreateOrder();
    }

    public void oirderListButtonClick(){
        logger.log(Level.INFO, "User try to get the order list. Go to order list createNewOrder.");
        viewCenter.gotoOrderList();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }
}
