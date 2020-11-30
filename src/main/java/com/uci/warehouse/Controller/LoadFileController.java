package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.GUI.ViewCenter;
import main.java.com.uci.warehouse.Model.Order;
import main.java.com.uci.warehouse.Model.Warehouse;
import main.java.com.uci.warehouse.Util.MyAlert;

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

    @FXML
    private Button LoadFIle_Button;
    @FXML
    private TextField OrderFileName;
    @FXML
    private TextField locationFileName;
    @FXML
    private TextField outputFileName;


    public void loadFileButtonClick(){
        boolean getError = false;
        try {
            String orderFileAddr =  OrderFileName.getText(), locFileAddr = locationFileName.getText();
            logger.log(Level.INFO, "order file addr: " + orderFileAddr);
            logger.log(Level.INFO, "location file addr: " + locFileAddr);
            logger.log(Level.INFO, "output file name: " + outputFileName.getText() + ".txt");
            Warehouse.loadOrdersFromFile(orderFileAddr);
            Warehouse.loadLocationFromFile(locFileAddr);
            Warehouse.filename = outputFileName.getText() + ".txt";
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "LoadFile Error");
            getError = true;
        }

        if (!getError) {
            logger.log(Level.INFO, "LoadFile Finished");
            logger.log(Level.INFO, "Current quantity of orders: " + Warehouse.getQuantityOfAllOrders());
            viewCenter.gotoMenu();
        } else {
            MyAlert.sendErrorAlert("Load File Failed!", "Please try again.");
        }
    }

    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
