package main.java.com.uci.warehouse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import main.java.com.uci.warehouse.Model.Warehouse;
import main.java.com.uci.warehouse.GUI.ViewCenter;
import main.java.com.uci.warehouse.Util.MyAlert;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.java.com.uci.warehouse.Model.Warehouse.getQuantityOfAllOrders;

public class CreateOrderController implements Initializable {
    //TODO @Ziyi
    private static final Logger logger = Logger.getLogger(MapController.class.getName());
    private ViewCenter viewCenter;


    @FXML
    private TextField new_order_size;

    @FXML
    private TextField new_order_products;


    public void createNewOrderButtonClick(){

        String oder_size = new_order_size.getText();
        String products_id = new_order_products.getText();
        try {
            logger.log(Level.INFO, "Create new order successful！ Go to Map page");
            String[] IDs = products_id.split(" ");
            List<Integer> product_IDs = new ArrayList<>();
            int size = Integer.valueOf(oder_size);
            for(String id:IDs){
                Warehouse.getProductLocation(Integer.valueOf(id));
                product_IDs.add(Integer.valueOf(id));
                logger.log(Level.INFO, "Add new item to order, its id is: "+ id);
            }
            if (product_IDs.size() != size) throw new Exception();
            Warehouse.addOrderFromGUI(size,product_IDs);
            viewCenter.gotoMap(getQuantityOfAllOrders()-1);
        } catch (Exception e) {
            MyAlert.sendErrorAlert("Error", "Invalid input!");
            e.printStackTrace();
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
