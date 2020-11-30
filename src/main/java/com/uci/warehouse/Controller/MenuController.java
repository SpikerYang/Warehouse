package main.java.com.uci.warehouse.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.java.com.uci.warehouse.Draw.DrawMap;
import main.java.com.uci.warehouse.GUI.ViewCenter;
import main.java.com.uci.warehouse.Model.Warehouse;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController implements Initializable{
    //TODO @zheng

    private static final Logger logger = Logger.getLogger(MenuController.class.getName());
    private ViewCenter viewCenter;
    private DrawMap drawMap = new DrawMap();

    @FXML
    private Button create_new_order_button;

    @FXML
    private TextField login_username;

    @FXML
    private Canvas mapCanvas;

    @FXML
    private Circle product;

    @FXML
    private Pane menuPane;

    @FXML
    private TextField product_id;

    @FXML
    private Pane testPane;


    public void createNewOrderButtonClick(){
        logger.log(Level.INFO, "User create a new order. Go to page createNewOrder.");
        viewCenter.gotoCreateOrder();
    }

    public void oirderListButtonClick(){
        logger.log(Level.INFO, "User try to get the order list. Go to order list createNewOrder.");
        viewCenter.gotoOrderList();
    }

    public void findProductButtonClick(){
        logger.log(Level.INFO, "User input a id: ");
        double[] location = Warehouse.getProductLocation(Integer.valueOf(product_id.getText()));
//        menuPane.getChildren().addAll(
//                drawMap.drawProduct(location[0]+1, location[1]+1)
//        );

        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        drawMap.drawProduct(location[0]+1, location[1]+1,gc);
        System.out.println("The location is (" + location[0] + "," + location[1] + ")");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        Text t = new Text("pos");
//        t.setLayoutX(0);
//        t.setLayoutY(0);
//        menuPane.getChildren().addAll(t);


        for(int i=0;i<22;i++){
            menuPane.getChildren().addAll(drawMap.drawCoordinate(0,i+1,String.valueOf(i)));
        }
        for(int i=0;i<40;i++){
            menuPane.getChildren().addAll(drawMap.drawCoordinate(i+1,0,String.valueOf(i)));
        }


        Map<ArrayList<Integer>, Integer> shelveMap;
        shelveMap = Warehouse.returnShelveMap();
        for (ArrayList<Integer> tmplist: shelveMap.keySet()) {
            menuPane.getChildren().addAll(drawMap.drawShelve(tmplist.get(0)+1,tmplist.get(1)+1));
        }

//        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
//        gc.setFill(Color.BLACK);
//        gc.strokeLine(30,430 , 30 ,410 );
//        gc.strokeLine(30,410 , 70 ,410 );
//        gc.strokeLine(70,430 , 690 ,410 );
//        gc.strokeLine(690,430 , 690 ,410 );




    }

    public void setApp(ViewCenter viewCenter){
        this.viewCenter = viewCenter;
    }
}
