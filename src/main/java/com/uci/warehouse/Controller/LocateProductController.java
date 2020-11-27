package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.GUI.ViewCenter;

import java.util.logging.Logger;

public class LocateProductController {

    //TODO @Siqian

    private static final Logger logger = Logger.getLogger(LocateProductController.class.getName());
    private ViewCenter viewCenter;
    @FXML
    private Button backToMenu_Button;
    @FXML
    private Button Locate_Button;
    @FXML
    private TextField productID;
}
