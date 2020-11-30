package main.java.com.uci.warehouse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.Model.Warehouse;
import main.java.com.uci.warehouse.GUI.ViewCenter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateOrderController implements Initializable {
    //TODO @Ziyi
    private static final Logger logger = Logger.getLogger(MapController.class.getName());
    private ViewCenter viewCenter;


    @FXML
    private TextField new_order_size;

    @FXML
    private TextField new_order_products;


    public void createNewOrderButtonClick(){

        boolean input_correct = true;
        String oder_size = new_order_size.getText();
        String products_id = new_order_products.getText();
        if(products_id==null)
            input_correct =false;
        //
        if(input_correct) {
            logger.log(Level.INFO, "Create new order successful！ Go to Map page");
            String[] IDs = products_id.split(" ");
            List<Integer> product_IDs = new ArrayList<>();
            for(String id:IDs){
                product_IDs.add(Integer.valueOf(id));
                logger.log(Level.INFO, "Add new item to order, its id is: "+ id);
            }
            int size = Integer.valueOf(oder_size);
            Warehouse.addOrderFromGUI(size,product_IDs);
            viewCenter.gotoMap();
        } else {
            logger.log(Level.WARNING, "something wrong.");
        }
    }
    
    public void backToMainMenuButtonClick(ActionEvent actionEvent) {
        viewCenter.gotoMenu();
    }
    
    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
