package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.GUI.ViewCenter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateOrderController implements Initializable {
    //TODO @Ziyi
    private static final Logger logger = Logger.getLogger(MapController.class.getName());
    private ViewCenter viewCenter;

    @FXML
    private TextField new_order_filename;

    @FXML
    private TextField new_order_size;

    @FXML
    private TextField new_order_products;

    @FXML
    private TextField new_order_start_point;

    @FXML
    private TextField new_order_end_point;


    public void createNewOrderButtonClick(){
        if(true) {
            logger.log(Level.INFO, "Create new order successful！ Go to Map page");
            //TODO
            // viewCenter.gotoMap();
        } else {
            logger.log(Level.WARNING, "something wrong.");
        }
    }
    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
