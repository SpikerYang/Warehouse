package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.java.com.uci.warehouse.GUI.ViewCenter;

import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

import main.java.com.uci.warehouse.Model.*;

public class OrderListController implements Initializable {
    //TODO @qirui
    private static final Logger logger = Logger.getLogger(MapController.class.getName());
    private ViewCenter viewCenter;

    @FXML
    private ListView<Integer> orderList;
    public void finishButtonClick(){
        if(true) {
            logger.log(Level.INFO, "User choose a order！ Go to Map page");
            viewCenter.gotoMenu();
        } else {
            logger.log(Level.WARNING, "something wrong.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Order> orderlist = Warehouse.getNotCompletedOrderList();
        for (Order order : orderlist) {
            orderList.getItems().add(order.getId());
        }
    }

    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }

}
