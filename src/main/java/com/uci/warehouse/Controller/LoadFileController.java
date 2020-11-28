package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.GUI.ViewCenter;
import main.java.com.uci.warehouse.Model.Order;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.java.com.uci.warehouse.Model.Warehouse.*;

public class LoadFileController implements Initializable {
    //TODO @qirui
    private static final Logger logger = Logger.getLogger(LoadFileController.class.getName());
    private ViewCenter viewCenter;

    private static Map<Integer, Order> orders;


    @FXML
    private Button LoadFIle_Button;
    @FXML
    private TextField OrderFileName;
    @FXML
    private TextField locationFileName;

    public static Map<Integer, Order> getOrders(){
        return orders;
    }


    public void loadFileButtonClick(){
        logger.log(Level.INFO, "LoadFile. Go to Menu");
        try {
            String orderFileAddr =  OrderFileName.getText(), locFileAddr = locationFileName.getText();
            logger.log(Level.INFO, "order file addr: " + orderFileAddr);
            logger.log(Level.INFO, "location file addr: " + locFileAddr);
            loadOrdersFromFile(orderFileAddr);
            loadLocationFromFile(locFileAddr);
        } catch (IOException e) {
            logger.log(Level.INFO, "LoadFile Error");
        }
//        loadLocationFromFileAndRetry();
        logger.log(Level.INFO, "LoadFile Finished");
        viewCenter.gotoMap();
    }

    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
