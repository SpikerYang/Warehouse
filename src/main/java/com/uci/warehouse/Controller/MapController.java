package main.java.com.uci.warehouse.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import main.java.com.uci.warehouse.GUI.ViewCenter;
import main.java.com.uci.warehouse.Model.*;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.java.com.uci.warehouse.Model.Warehouse.*;

public class MapController implements Initializable{
    //TODO @Jindong @Zheng

    private static final Logger logger = Logger.getLogger(MapController.class.getName());
    private ViewCenter viewCenter;

    @FXML
    private TextField startpoint, endpoint;
    @FXML
    private TextArea instruction;
    @FXML
    private ChoiceBox algorithm;

    private int OrderID;
    private Order order;
    private Map<Integer, double[]> map;
    private int[] start, end;
    private int runtime;
    private Pair[][] matrix;
    private int[][] graph;

    private void processOrder(){
        order =LoadFileController.getOrders().get(OrderID);
        List<Integer> list = order.getOrderList();

        matrix = RouteBFS.routeDistanceMatrix(order, map, start, end);
        graph = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                graph[i][j] = (int) matrix[i][j].getValue();
            }
        }
    }

    private String  NN(){
        long startTime = System.currentTimeMillis();


        TSP_NN tsp_nn = new TSP_NN(OrderID, graph);
        List<Integer> route = tsp_nn.nearestNeigh();
        String direction;
        direction = printRoute(matrix, route, start, end);

        //end time measure
        long endTime = System.currentTimeMillis();
        long timePeriod = endTime - startTime;
        logger.log(Level.INFO,"NN Runtime:"+timePeriod+ "  ms");

        showMap(matrix, route, start, end);;

        instruction.appendText("NN approach\n"+direction);
        return direction;
    }

/*
    private String DP(){
        //TODO @Siqian
           Do not support avoiding shelves


        TSP_DP tsp_dp = new TSP_DP();
        //int[][] graphForDP = order.getDistanceMatrixForDP(map, start, end, runtime);

        long startTime = System.currentTimeMillis();
        List<Integer> route = tsp_dp.getRoute(graphForDP);
        long endTime = System.currentTimeMillis();

        long timePeriod = endTime - startTime;
        logger.log(Level.INFO,"DP Runtime:"+timePeriod+ "  ms");

        String direction =printRoute(matrix, route, start, end);

        showMap(order, route, start, end);

        instruction.appendText("DP approach\n"+direction);
        return direction;
    }
 */

    private String DA(){
        long startTime = System.currentTimeMillis();
        //int[][] graphforGA = order.getDistanceMatrix(map, start, end);
        TSP_GA tsp_ga = new TSP_GA(30, graph.length - 2, 1000, 0.8f, 0.9f);
        tsp_ga.init(graph);
        List<Integer> route = tsp_ga.solve();

        long endTime = System.currentTimeMillis();
        long timePeriod = endTime - startTime;
        logger.log(Level.INFO,"DA Runtime:"+timePeriod+ "  ms");

        showMap(matrix, route, start, end);
        String direction = printRoute(matrix, route, start, end);
        instruction.appendText("DA approach\n"+direction);
        return direction;
    }

    private void showMap(Pair[][] matrix, List<Integer> route, int[] start, int[] end) {
    }


    public void RouteButtonClick(){
        logger.log(Level.INFO, "Route and show on map");
        String s = startpoint.getText();
        //start[0]=s.


        //showMap();
        //printInstructions();
    }
    public void completeButtonClick(){
        logger.log(Level.INFO, "Order complete. Update order status");
        //updatestatus(orderID);
    }
    public void printInstructions(){}

    public void returnButtonClick(){
        logger.log(Level.INFO, "Go to Order page from Map page.");
        viewCenter.gotoOrder();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setApp(ViewCenter viewCenter, int OrderID){
        this.viewCenter = viewCenter;
        this.OrderID=OrderID;
    }
}
