package main.java.com.uci.warehouse.Controller;

import javafx.fxml.Initializable;
import main.java.com.uci.warehouse.GUI.ViewCenter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderListController implements Initializable {
    //TODO @qirui
    private static final Logger logger = Logger.getLogger(MapController.class.getName());
    private ViewCenter viewCenter;

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

    }

    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }

}
