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
import java.util.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.uci.warehouse.Util.MyAlert;

import static java.lang.Integer.parseInt;
import static main.java.com.uci.warehouse.Model.Warehouse.*;

public class MapController implements Initializable {
    //TODO @Jindong @Zheng

    private static final Logger logger = Logger.getLogger(MapController.class.getName());
    private ViewCenter viewCenter;

    @FXML
    private TextField startpoint, endpoint;
    @FXML
    private TextArea instruction;
    @FXML
    private ChoiceBox algorithm;
    @FXML
    private TextField runtime_TF;

    private int OrderID;
    private Order order;
    private Map<Integer, double[]> map;
    private int[] start = new int[2], end = new int[2];
    private int runtime;
    private Pair[][] matrix;
    private int[][] graph;
    private boolean routed = false;
    private String direction;
    private Set<ArrayList<Integer>> shelves;


    private String NN() {
        long startTime = System.currentTimeMillis();


        TSP_NN tsp_nn = new TSP_NN(OrderID, graph);
        List<Integer> route = tsp_nn.nearestNeigh();
        direction = printRoute(matrix, route, start, end,order);

        //end time measure
        long endTime = System.currentTimeMillis();
        long timePeriod = endTime - startTime;
        logger.log(Level.INFO, "NN Runtime:" + timePeriod + "  ms");

        showMap(matrix, route, start, end);
        ;
        instruction.clear();
        instruction.appendText("NN approach\n" + direction);
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

         direction =printRoute(matrix, route, start, end);

        showMap(order, route, start, end);
        instruction.clear();
        instruction.appendText("DP approach\n"+direction);
        return direction;
    }
 */

    private String DA() {
        long startTime = System.currentTimeMillis();
        //int[][] graphforGA = order.getDistanceMatrix(map, start, end);
        TSP_GA tsp_ga = new TSP_GA(30, graph.length - 2, 1000, 0.8f, 0.9f);
        tsp_ga.init(graph);
        List<Integer> route = tsp_ga.solve();

        long endTime = System.currentTimeMillis();
        long timePeriod = endTime - startTime;
        logger.log(Level.INFO, "DA Runtime:" + timePeriod + "  ms");

        showMap(matrix, route, start, end);
        direction = printRoute(matrix, route, start, end,order);
        instruction.clear();
        instruction.appendText("DA approach\n" + direction);
        return direction;
    }

    private void showMap(Pair[][] matrix, List<Integer> route, int[] start, int[] end) {
    }


    private boolean isIllegalPosition(int[] p) {

        if (p[0] < 0 || p[0] > 39 || p[1] < 0 || p[1] > 19) return false;
        ArrayList<Integer> location = new ArrayList<>();
        location.add(p[0]);
        location.add(p[1]);
        if (shelves.contains(p)) return false;
        return true;
    }

    public void getshelf(Map<Integer, double[]> productLocationMap) {
        shelves = new HashSet<>();
        for (double[] item : productLocationMap.values()) {
            ArrayList<Integer> shelf = new ArrayList<>();
            shelf.add((int) item[0]);
            shelf.add((int) item[1]);
            shelves.add(shelf);
        }
    }

    public void RouteButtonClick() {
        preprocess();

        getshelf(map);
        if (!isIllegalPosition(start) || !isIllegalPosition(end) || runtime <= 0) {
            instruction.clear();
            instruction.appendText("ERROR! Illegeal input");
            return;
        }
        logger.log(Level.INFO, "Route and show on map");
        logger.log(Level.INFO, "algorithm is "+algorithm.getItems().toString());
        switch (algorithm.getValue().toString()) {
            case "NN":
                NN();
                break;
            case "DP":
                //DP();
                break;
            case "DA":
                DA();
                break;
            default:
                logger.log(Level.INFO, "Algorithm is not selected.");
                return;
        }
        routed = true;
    }

    private void preprocess() {
        map = Warehouse.getproductLocationMap();
        logger.log(Level.INFO, "get map");
        OrderID=getQuantityOfAllOrders()-1;
        logger.log(Level.INFO, "get orderID:"+OrderID);
        //---------getText--------------
        try {
            runtime = parseInt(runtime_TF.getText());
        } catch (NumberFormatException e) {
            //viewCenter.gotoMap();
            MyAlert.sendErrorAlert("Illegal Input","Illegal runing time");

        }
        try {
            String s = startpoint.getText();
            String e = endpoint.getText();
            Scanner scanner = new Scanner(s);
            start[0] = scanner.nextInt();
            start[1] = scanner.nextInt();
            scanner = new Scanner(e);
            end[0] = scanner.nextInt();
            end[1] = scanner.nextInt();
        } catch (Exception e) {
            //viewCenter.gotoMap();
            MyAlert.sendErrorAlert("Illegal Input","Illegal Start or End location");

        }

        getshelf(map);
        if (!isIllegalPosition(start) || !isIllegalPosition(end) || runtime <= 0) {
            instruction.clear();
            MyAlert.sendErrorAlert("Illegal Input","Start or End location out of range");
        }

        order = Warehouse.getOrder(OrderID);
        logger.log(Level.INFO, "get order from orders in Warehouse.java  "+order);
        //List<Integer> list = order.getOrderList();
        //map = Warehouse.getproductLocationMap();
        matrix = RouteBFS.routeDistanceMatrix(order, map, start, end);
        graph = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                graph[i][j] = (int) matrix[i][j].getValue();
            }
        }
    }

    public void completeButtonClick() {
        if (routed) {
            logger.log(Level.INFO, "Order complete. Update order status. OrderID:" + OrderID);
            updateOrderStatus(OrderID);
            exportFile.exportTxt(Warehouse.getfilename(), "" + direction);
            viewCenter.gotoCreateOrder();
        }
    }

    public void returnButtonClick() {
        logger.log(Level.INFO, "Go to Order page from Map page.");
        viewCenter.gotoMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        algorithm.getItems().addAll("NN", "DP", "DA");
        //instruction.appendText(startpoint.getText());
    }

    public void setApp(ViewCenter viewCenter) {
        this.viewCenter = viewCenter;
        //this.OrderID = OrderID;
    }
}
