package main.java.com.uci.warehouse.Controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import main.java.com.uci.warehouse.Draw.DrawMap;
import main.java.com.uci.warehouse.GUI.ViewCenter;
import main.java.com.uci.warehouse.Model.*;
import javafx.scene.canvas.Canvas;


import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.uci.warehouse.Util.MyAlert;

import static java.lang.Double.parseDouble;
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
    @FXML
    private Pane routePane;
    @FXML
    private Canvas routeCanvas;

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
    private DrawMap drawMap = new DrawMap();



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

        instruction.clear();
        instruction.appendText("NN approach\n" + direction);
        return direction;
    }


    private String DP(){

        long startTime = System.currentTimeMillis();
        TSP_DP tsp_dp = new TSP_DP();

        List<Integer> route = tsp_dp.getRoute(graph, runtime);

        String direction;
        direction = printRoute(matrix, route, start, end,order);
        //end time measure
        long endTime = System.currentTimeMillis();
        long timePeriod = endTime - startTime;
        logger.log(Level.INFO, "DP Runtime:" + timePeriod + "  ms");



        showMap(matrix, route, start, end);
        instruction.clear();
        instruction.appendText("DP approach\n"+direction);
        return direction;
    }

    private String GA() {

        long startTime = System.currentTimeMillis();
        //int[][] graphforGA = order.getDistanceMatrix(map, start, end);
        TSP_GA tsp_ga = new TSP_GA(30, graph.length - 2, 1000, 0.8f, 0.9f);
        tsp_ga.init(graph);
        List<Integer> route = tsp_ga.solve(runtime);

        long endTime = System.currentTimeMillis();
        long timePeriod = endTime - startTime;
        logger.log(Level.INFO, "GA Runtime:" + timePeriod + "  ms");

        showMap(matrix, route, start, end);
        direction = printRoute(matrix, route, start, end,order);
        instruction.clear();
        instruction.appendText("GA approach\n" + direction);
        return direction;
    }

    private void showMap(Pair[][] matrix, List<Integer> route, int[] start, int[] end) {

        List<Integer> list= order.getOrderList();
        list.add(0, -1);
        List<int[]> subRoute;
        String[][] routeMap = new String[20][40];
        int color = 0;

        GraphicsContext gc = routeCanvas.getGraphicsContext2D();
        Canvas canvas = new Canvas( 820,460);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


        int[] lastfrom = new int[2];
        drawMap.drawText(start[0],start[1],color,"S",gc);
//        routeMap[19-start[1]][start[0]] = "S  ";
        lastfrom[0] = start[0];
        lastfrom[1] = start[1];
        for (int i = 1; i < route.size(); i++){
            subRoute = (List<int[]>) matrix[route.get(i-1)][route.get(i)].getKey();
            int xx,yy;
            int[] from, to;
            int verticalMove=0;//verticalMove: 0 no vertical move, 1 go north, -1 go south.
            for(int k=1; k<subRoute.size(); k++){
                from=subRoute.get(k-1);
                to=subRoute.get(k);
                xx=to[0]-from[0];
                yy=to[1]-from[1];
                if(xx==0){
                    verticalMove=yy>0?1:-1;
                    continue;
                }
                if(verticalMove!=0){
                    if(verticalMove==1){
                        drawMap.drawRoute(lastfrom[0],lastfrom[1],from[0],from[1],0,color,gc);
//                        drawMap.printIndLine(lastfrom[0],lastfrom[1]-1,from[0],from[1]);
//                        for(int j=lastfrom[1]; j<from[1];j++){
//                            if(routeMap[19-j][from[0]] == "   ") routeMap[19-j][from[0]] = printColor(code,"|  ");
//                        }
//                        routeMap[19 - from[1]][from[0]] = printColor(code,"^  ");
                    }else{
                        drawMap.drawRoute(lastfrom[0],lastfrom[1],from[0],from[1],1,color,gc);
//                        drawMap.printIndLine(lastfrom[0],lastfrom[1]+1,from[0],from[1]);

//                        for(int j=lastfrom[1]; j>from[1];j--){
//                            if(routeMap[19-j][from[0]] == "   ") routeMap[19-j][from[0]] = printColor(code,"|  ");
//                        }
//                        routeMap[19 - from[1]][from[0]] = printColor(code,"V  ");
                    }
                }
                if(xx>0){
                    drawMap.drawRoute(from[0],from[1],to[0],from[1],2,color,gc);
//                    drawMap.printIndLine(from[0],from[1],to[0],from[1]);
//                    for(int j=from[0]; j<to[0];j++){
//                        if( routeMap[19-from[1]][j] == "   ") {
//                            routeMap[19-from[1]][j] = printColor(code,"---");
//                        }
//                    }
//                    routeMap[19 - from[1]][to[0]] = printColor(code,">  ");
                }else{
                    drawMap.drawRoute(from[0],from[1],to[0],from[1],3,color,gc);
//                    drawMap.printIndLine(from[0],from[1],to[0],from[1]);
//                    for(int j=from[0]; j>to[0];j--){
//                        if(routeMap[19-from[1]][j] == "   ") routeMap[19-from[1]][j] = printColor(code,"---");
//                    }
//                    routeMap[19 - from[1]][to[0]] = printColor(code,"<--");
                }
                lastfrom[0] = to[0];
                lastfrom[1] = to[1];
//                direction+="\tGo to "+(xx>0?"east":"west")+" to ("+to[0]+","+from[1]+")\n";
                //verticalMove=yy>0?1:-1;
                if(yy>0) verticalMove=1;
                else if (yy<0) verticalMove=-1;
                else verticalMove=0;
            }
            to = subRoute.get(subRoute.size()-1);
            if(verticalMove!=0){
                if(verticalMove==1){
                    drawMap.drawRoute(lastfrom[0],lastfrom[1]-1,to[0],to[1],0,color,gc);
//                    drawMap.printIndLine(lastfrom[0],lastfrom[1],to[0],to[1]);
                }else{
                    drawMap.drawRoute(lastfrom[0],lastfrom[1]+1,to[0],to[1],1,color,gc);
//                    drawMap.printIndLine(lastfrom[0],lastfrom[1],to[0],to[1]);
                }
            }
            if(i==route.size()-1){
                drawMap.drawText(to[0],to[1],color,"E",gc);
//                routeMap[19-to[1]][to[0]] = printColor(code,"E  ");
            }else{
//                direction+="Pick up product "+ list.get(route.get(i))+"\n";
                drawMap.drawRoute(to[0],to[1],to[0],to[1]-1,1,color,gc);
//                routeMap[19-to[1]][to[0]]  = printColor(code,"V  ");
//                routeMap[20-to[1]][to[0]] =printColor(code,String.format("%-3s", list.get(route.get(i))));
                color++;
            }
        }


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
            instruction.appendText("ERROR! Illegal input");
            MyAlert.sendErrorAlert("Illegal Input","Illegal start, end or running item ");
            return;
        }

        logger.log(Level.INFO, "algorithm is "+algorithm.getValue().toString());
        try {switch (algorithm.getValue().toString()) {
            case "NN":
                NN();
                break;
            case "DP":
                DP();
                break;
            case "GA":
                GA();
                break;
            default:
                logger.log(Level.INFO, "Algorithm is not selected.");
                return;
        }
            logger.log(Level.INFO, "Route and show on map");
        }catch(Exception e){
            MyAlert.sendErrorAlert("Error","Illegal order or order no found");
        }

        routed = true;
    }

    private void preprocess() {

        //---------getText--------------
        try {
            runtime = (int)(1000*parseDouble(runtime_TF.getText()));
        } catch (NumberFormatException e) {
            //viewCenter.gotoMap();
            MyAlert.sendErrorAlert("Illegal Input","Illegal running time");

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
            exportFile.exportTxt(Warehouse.getfilename(), "/nOrder:"+OrderID+"\n" + direction);
            viewCenter.gotoMenu();
        }
    }

    public void returnButtonClick() {
        logger.log(Level.INFO, "Go to Order page from Map page.");
        viewCenter.gotoMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        algorithm.getItems().addAll("NN", "DP", "GA");
        for(int i=0;i<22;i++){
            routePane.getChildren().addAll(drawMap.drawCoordinate(0,i+1,String.valueOf(i)));
        }
        for(int i=0;i<40;i++){
            routePane.getChildren().addAll(drawMap.drawCoordinate(i+1,0,String.valueOf(i)));
        }


        Map<ArrayList<Integer>, Integer> shelveMap;
        shelveMap = Warehouse.returnShelveMap();
        for (ArrayList<Integer> tmplist: shelveMap.keySet()) {
            routePane.getChildren().addAll(drawMap.drawShelve(tmplist.get(0)+1,tmplist.get(1)+1));
        }

        //instruction.appendText(startpoint.getText());
        //get map and orderID
        map = Warehouse.getproductLocationMap();
        logger.log(Level.INFO, "get map");
//        OrderID=getQuantityOfAllOrders()-1;
//        logger.log(Level.INFO, "get orderID:"+OrderID);
    }

    public void setApp(ViewCenter viewCenter, int orderID) {
        this.viewCenter = viewCenter;
        this.OrderID = orderID;
        logger.log(Level.INFO, "get orderID:"+OrderID);
    }
}
