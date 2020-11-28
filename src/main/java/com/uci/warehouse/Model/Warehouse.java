package main.java.com.uci.warehouse.Model;

import javafx.util.Pair;

import java.io.*;

//import com.sun.tools.javac.util.Pair;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author spike
 * @Date: 2020-10-27 13:31
 */
public class Warehouse {

    //==============================================================================================================================
    //parameters might not be static if there are multiply warehouse or map
    //==============================================================================================================================

    private static Map<Integer, Order> orders;//<orderID, Order>
    private static AtomicInteger nextNotCompletedOrder;
    private static Map<Integer, double[]> productLocationMap;
    private static Map<ArrayList<Integer>, Integer> shelveMap;
    private static String[][] routeMap;
    private static int[] start = new int[2];
    private static int[] end = new int[2];
    private static String filename;
    private static Order order;

    private static readFile readfile;

    private static TSP_NN tsp_nn;// nearest neighbor approach : 2-approximation in O(n^2) time
    private static TSP_DP tsp_dp;// DP approach : optimal route in O(n^2*2^n) time
    private static TSP_GA tsp_ga;// GA approach

    /**
     * @param map
     */
    public Warehouse(Map<Integer, double[]> map) {
        productLocationMap = map;
    }

    public Warehouse() {
        orders = new HashMap<>();
        productLocationMap = new HashMap<>();
        nextNotCompletedOrder = new AtomicInteger(0);
    }

    //==============================================================================================================================
//                           functions of warehouse
//==============================================================================================================================
    public void addOrder(Order order) {
        if (orders.containsKey(order.getId())) try {
            throw new Exception("Order existed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.put(order.getId(), order);
    }

    /**
     * Create an order from console, first specify the quantity of products, then type in id of each of product seperated by blanks
     *
     * @return Order
     */
    public Order createOrder() {
        // get the quantity of products in the order
        System.out.println("Type the size of the order: ");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int id = orders.size();
        Order order = new Order(id);
        System.out.println("Type id of product separated by blanks: ");
        for (int i = 0; i < size; i++) {
            int productId = scanner.nextInt();
            order.addProduct(productId, 1);
        }
        return order;
    }

    /**
     * Add a list of orders from console, first specify the quantity of orders
     */
    public void addOrderList() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type how many orders do you have:");
        int size = scanner.nextInt();
        for (int i = 0; i < size; i++) {
            addOrder(createOrder());
        }
    }

    /**
     * Print order list to console
     */
    public void printOrderList() {
        for (int id : orders.keySet()) {
            System.out.println("order " + id + ": ");
            Map<Integer, Integer> map = orders.get(id).getProducts();
            for (int pId : map.keySet()) {
                System.out.print(pId + "," + map.get(pId) + " ");
            }
            System.out.print("\n");
        }
    }

    public Order getOrder(int orderId) {
        if (!orders.containsKey(orderId)) {
            handleOrderNotExistError();
        }
        return orders.get(orderId);
    }

    private void handleOrderNotExistError() {
        try {
            throw new Exception("No such Order!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completeOrder(int orderId) {
        Order order = getOrder(orderId);
        order.beCompleted();
    }

    public Order getNextNotCompletedOrder() {
        if (nextNotCompletedOrder.intValue() < orders.size()) {
            if (checkIfOrderIsNotComplete(nextNotCompletedOrder.intValue())) {
                return orders.get(nextNotCompletedOrder.intValue());
            } else {
                nextNotCompletedOrder.set(nextNotCompletedOrder.intValue() + 1);
                return getNextNotCompletedOrder();
            }
        } else {
            handleOrderNotExistError();
            return null;
        }
    }

    private boolean checkIfOrderIsNotComplete(int orderId) {
        Order order = getOrder(orderId);
        return order.getStatus().equals(Order.ORDER_UNCOMPLETED);
    }

    public List<Order> getOrderList() {
        return new ArrayList<>(orders.values());
    }

    public List<Order> getNotCompletedOrderList() {
        List<Order> notCompletedOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (checkIfOrderIsNotComplete(order.getId())) {
                notCompletedOrders.add(order);
            }
        }
        return notCompletedOrders;
    }

    public static void updateOrderStatus(int orderId) {
        if (!orders.containsKey(orderId)) try {
            throw new Exception("Order not existed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.get(orderId).setStatus("Completed");
    }

    /**
     * Get location of specific product by id
     *
     * @param productId
     * @return location
     */
    //change to static
    public static double[] getProductLocation(int productId) {
        if (!productLocationMap.containsKey(productId)) try {
            throw new Exception("No such Product!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productLocationMap.get(productId);
    }

    /**
     * Generate route by TSP Algorithm
     */
    public void getRoute() {
        //TODO
    }

    /**
     * Read location data from file
     *
     * @param warehouse
     */
    private static void loadLocationData(Warehouse warehouse) {
        //TODO
    }

    public static Map<ArrayList<Integer>, Integer> getshelf() throws FileNotFoundException {
        String filePath = "src/qvBox-warehouse-data-f20-v01.txt";
        readfile = new readFile();
        productLocationMap = readfile.readfile(filePath);


        //there is a productLocationMap in Warehouse class
        Warehouse warehouse = new Warehouse();
        warehouse.getShelveMap();


        return shelveMap;
    }

    /**
     * Load orders data from file
     */
    public static void loadOrdersFromFile(String file) throws IOException {
        String path;
        if (file.length() == 0) {
            System.out.println("Type in the path of the order file: ");
            Scanner scanner = new Scanner(System.in);
            path = scanner.nextLine();
        } else path = file;
        BufferedReader in = new BufferedReader(new FileReader(path));
        StringBuffer sb;
        while (in.ready()) {
            sb = (new StringBuffer(in.readLine()));
            Order order = new Order(orders.size());
            for (String product : sb.toString().split(",")) {
                order.addProduct(Integer.parseInt(product.trim()), 1);
            }
            orders.put(order.getId(), order);
        }
        in.close();
    }
//==============================================================================================================================
//                           map printing functions
//==============================================================================================================================

    /**
     * Print route to console with map
     */
    public void printRoute(ArrayList<Integer> arrayList) {
        Iterator<Integer> iterator = arrayList.iterator();
        ArrayList<ArrayList<Integer>> shelveArray = new ArrayList<>();
        int productId;
        double[] location = new double[2];
        int[] shelveLocation = new int[2];
        int sheleveId;
        int count = 1;
        ArrayList<Integer> tmparray = new ArrayList<>();
        while (iterator.hasNext()) {
            productId = iterator.next();
            location = productLocationMap.get(productId);
            tmparray = new ArrayList<>();
            tmparray.add((int) location[0]);
            tmparray.add((int) location[1]);
            shelveArray.add(tmparray);
            sheleveId = shelveMap.get(tmparray);
            System.out.println(count + "th product is in No." + sheleveId + " shelve<" + tmparray.get(0)
                    + ", " + tmparray.get(1) + "> and product's location is <" + location[0] + ", " + location[1] + ">");
            count++;
        }
        System.out.println("The map is show as below:");
        int[] sloction = new int[2];
        int id;
        for (int i = 0; i <= 20; i++) {
            if (i == 20) {
                System.out.print("   ");
                for (int j = 0; j < 40; j++) {
                    String jid = String.format("%-2s", j + 1);
                    System.out.print(jid + " ");
                }
                System.out.println(" ");
            } else {
                String iid = String.format("%-2s", 20 - i);
                System.out.print(iid + " ");
                for (int j = 0; j < 40; j++) {
                    tmparray = new ArrayList<>();
                    tmparray.add((int) j);
                    tmparray.add((int) i);
                    if (i == 19 && j == 0) {
                        System.out.print("S  ");
                    } else if (shelveArray.contains(tmparray)) {
                        id = shelveArray.indexOf(tmparray) + 1;
//                        String sid = String.format("%-2s", id);
//                        System.out.print(sid + " ");
                        System.out.print("__ ");
                    } else {
                        System.out.print("   ");
                    }
                }
                System.out.println(" ");
            }
        }
    }

    /**
     * Print the map with shelve
     * the map is size 40*20
     */
    public void printMap() {
        int[] sloction = new int[2];
        int id;
        ArrayList<Integer> tmparray = new ArrayList<>();
        System.out.println("--------------------The map is show as below:-----------------------");
        for (int i = 0; i <= 20; i++) {
            if (i == 20) {
                System.out.print("   ");
                for (int j = 0; j < 40; j++) {
                    String jid = String.format("%-2s", j);
                    System.out.print(jid + " ");
                }
                System.out.println(" ");
            } else {
                String iid = String.format("%-2s", 19 - i);
                System.out.print(iid + " ");
                for (int j = 0; j < 40; j++) {
                    tmparray = new ArrayList<>();
                    tmparray.add((int) j);
                    tmparray.add((int) 19 - i);
                    if (i == 19 && j == 0) {
                        System.out.print("   ");
                    } else if (shelveMap.containsKey(tmparray)) {
                        id = shelveMap.get(tmparray);
//                        String sid = String.format("%-2s", id);
//                        System.out.print(sid + " ");
                        System.out.print("□  ");
                        //System.out.print(Character.toString ((char) 255));
                    } else {
                        System.out.print("   ");
                    }
                }
                System.out.println(" ");
            }
        }
    }

    /**
     * get shelve information from product imfor
     */
    public void getShelveMap() {
        shelveMap = new HashMap<>();
        int id = 1;
        double[] location = new double[2];
        int[] shelveLocation = new int[2];
        Iterator<Integer> iterator = productLocationMap.keySet().iterator();
        ArrayList<Integer> tmparray = new ArrayList<>();
        while (iterator.hasNext()) {
            location = productLocationMap.get(iterator.next());
            tmparray = new ArrayList<>();
            tmparray.add((int) location[0]);
            tmparray.add((int) location[1]);
//            shelveLocation[0] = (int)location[0];
//            shelveLocation[1] = (int)location[1];
            if (!shelveMap.containsKey(tmparray)) {
                shelveMap.put(tmparray, id);
                id++;

            }
        }
    }


//==============================================================================================================================
//                           print TSP route instruction
//==============================================================================================================================


    /**
     * print route instruction
     *
     * @param order which order
     * @param route route list with start and end node
     */
    public static String printRoute(Order order, List<Integer> route, int[] start, int[] end) {

        int[][][] graph = order.getXYDistanceMatrix(productLocationMap, start, end);
//        for(double[][] g:graph){
//            for(double[] gg:g){
//                for(double ggg:gg){
//                    System.out.print(ggg+ "     ");
//                }
//
//            }
//            System.out.println(" ");
//        }

        List<Integer> list = order.getOrderList();
        list.add(0, -1);

        String direction = "Start at location (" + start[0] + "," + start[1] + ")\n";
        //     order.getOrderItemLocation(0,map)[0]+","+order.getOrderItemLocation(0,map)[1]+"\n";
        for (int i = 1; i < route.size() - 1; i++) {
            int xx, yy;

            xx = graph[route.get(i - 1)][route.get(i)][0];
            yy = graph[route.get(i - 1)][route.get(i)][1];

            if (xx > 0) {
                direction += "\tGo to east to (" + (int) getProductLocation(list.get(route.get(i)))[0] + "," + (int) (getProductLocation(list.get(route.get(i)))[1] - yy) + ")\n";
            } else if (xx < 0) {
                direction += "\tGo to west to (" + (int) getProductLocation(list.get(route.get(i)))[0] + "," + (int) (getProductLocation(list.get(route.get(i)))[1] - yy) + ")\n";
            }
            if (yy > 0) {
                direction += "\tGo to north to (" + (int) getProductLocation(list.get(route.get(i)))[0] + "," + (int) getProductLocation(list.get(route.get(i)))[1] + ")\n";
            } else if (yy < 0) {
                direction += "\tGo to south to (" + (int) getProductLocation(list.get(route.get(i)))[0] + "," + (int) getProductLocation(list.get(route.get(i)))[1] + ")\n";
            }
            direction += "Pick up product " + list.get(route.get(i)) + "\n";
        }
        //for the end point
        int xx = graph[route.get(route.size() - 2)][route.get(route.size() - 1)][0];
        int yy = graph[route.get(route.size() - 2)][route.get(route.size() - 1)][1];
        if (xx > 0) {
            direction += "\tGo to east to (" + end[0] + "," + (end[1] - yy) + ")\n";
        } else if (xx < 0) {
            direction += "\tGo to west to (" + end[0] + "," + (end[1] - yy) + ")\n";
        }
        if (yy > 0) {
            direction += "\tGo to north to (" + end[0] + "," + end[1] + ")\n";
        } else if (yy < 0) {
            direction += "\tGo to south to (" + end[0] + "," + end[1] + ")\n";
        }
        direction += "Done!\n";


        System.out.print(direction);
        return direction;
    }

    /**
     * print route instruction avoiding shelf
     *
     * @param matrix pair (ArrayList<int[]>,int)
     * @param route
     * @param start
     * @param end
     * @return
     */
    public static String printRoute(Pair[][] matrix, List<Integer> route, int[] start, int[] end) {
        List<Integer> list = order.getOrderList();
        list.add(0, -1);
        //list.add(route.size(),-2);
        List<int[]> subRoute;
        String direction = "Start at location (" + start[0] + "," + start[1] + ")\n";
        for (int i = 1; i < route.size(); i++) {
            subRoute = (List<int[]>) matrix[i - 1][i].getKey();
            int xx, yy;
            int[] from, to;
            int verticalMove = 0;//verticalMove: 0 no vertical move, 1 go north, -1 go south.
            //from=subRoute.get(0);
            for (int j = 1; j < subRoute.size(); j++) {
                from = subRoute.get(j - 1);
                to = subRoute.get(j);
                xx = to[0] - from[0];
                yy = to[1] - from[1];


                if (xx == 0) {
                    verticalMove = yy > 0 ? 1 : -1;
                    continue;
                }
                if (verticalMove != 0) {
                    direction += "\tGo to " + (verticalMove == 1 ? "north" : "south") + " to (" + from[0] + "," + from[1] + ")\n";
                }
                direction += "\tGo to " + (xx > 0 ? "east" : "west") + " to (" + to[0] + "," + from[1] + ")\n";
                //verticalMove=yy>0?1:-1;
                if (yy > 0) verticalMove = 1;
                else if (yy < 0) verticalMove = -1;
                else verticalMove = 0;
            }
            to = subRoute.get(subRoute.size() - 1);
            if (verticalMove != 0) {
                direction += "\tGo to " + (verticalMove == 1 ? "north" : "south") + " to (" + to[0] + "," + to[1] + ")\n";
            }
            if (i == route.size() - 1) {
                direction += "Done!\n";
            } else {
                direction += "Pick up product " + list.get(route.get(i)) + "\n";
            }


        }
        //System.out.print(direction);
        return direction;
    }

    public static String printColor(int code, String content) {
        return String.format("\033[%d;%dm%s\033[0m", code, 2, content);
    }

    public static void printRouteMap(Order order, List<Integer> route, int[] start, int[] end) {
        List<Integer> list = order.getOrderList();
        int[][][] graph = order.getXYDistanceMatrix(productLocationMap, start, end);
        String[][] routeMap = new String[20][40];
        int code = 31;

        //initialize
        ArrayList<Integer> tmparray = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 40; j++) {
                tmparray = new ArrayList<>();
                tmparray.add((int) j);
                tmparray.add((int) 19 - i);
                if (shelveMap.containsKey(tmparray)) {
                    routeMap[i][j] = "□  ";
                } else {
                    routeMap[i][j] = "   ";
                }

            }
        }

        list.add(0, -1);

        routeMap[19 - (int) start[1]][(int) start[0]] = "S  ";

        routeMap[19 - (int) getProductLocation(list.get(route.get(0)))[1]][(int) getProductLocation(list.get(route.get(0)))[0]] = String.format("%-3s", "S");
        for (int i = 1; i < route.size() - 1; i++) {
            int xx = graph[route.get(i - 1)][route.get(i)][0];
            int yy = graph[route.get(i - 1)][route.get(i)][1];
            if (xx > 0) {
                for (int j = (int) getProductLocation(list.get(route.get(i - 1)))[0] + 1; j < (int) getProductLocation(list.get(route.get(i)))[0]; j++) {
                    if (routeMap[19 - (int) getProductLocation(list.get(route.get(i - 1)))[1]][j] == "   ") {
                        routeMap[19 - (int) getProductLocation(list.get(route.get(i - 1)))[1]][j] = printColor(code, "---");
                    }
                }
                routeMap[19 - (int) getProductLocation(list.get(route.get(i - 1)))[1]][(int) getProductLocation(list.get(route.get(i)))[0] - 1] = printColor(code, "-->");
            } else if (xx < 0) {
                for (int j = (int) getProductLocation(list.get(route.get(i - 1)))[0] - 1; j > (int) getProductLocation(list.get(route.get(i)))[0]; j--) {
                    if (routeMap[19 - (int) getProductLocation(list.get(route.get(i - 1)))[1]][j] == "   ")
                        routeMap[19 - (int) getProductLocation(list.get(route.get(i - 1)))[1]][j] = printColor(code, "---");
                }
                routeMap[19 - (int) getProductLocation(list.get(route.get(i - 1)))[1]][(int) getProductLocation(list.get(route.get(i)))[0] + 1] = printColor(code, "<--");
            }
            if (yy > 0) {
                for (int j = (int) getProductLocation(list.get(route.get(i - 1)))[1]; j < (int) getProductLocation(list.get(route.get(i)))[1]; j++) {
                    if (routeMap[19 - j][(int) getProductLocation(list.get(route.get(i)))[0]] == "   ")
                        routeMap[19 - j][(int) getProductLocation(list.get(route.get(i)))[0]] = printColor(code, "|  ");
                }
                routeMap[20 - (int) getProductLocation(list.get(route.get(i)))[1]][(int) getProductLocation(list.get(route.get(i)))[0]] = printColor(code, "^  ");
            } else if (yy < 0) {
                for (int j = (int) getProductLocation(list.get(route.get(i - 1)))[1]; j > (int) getProductLocation(list.get(route.get(i)))[1]; j--) {
                    if (routeMap[19 - j][(int) getProductLocation(list.get(route.get(i)))[0]] == "   ")
                        routeMap[19 - j][(int) getProductLocation(list.get(route.get(i)))[0]] = printColor(code, "|  ");
                }
                routeMap[18 - (int) getProductLocation(list.get(route.get(i)))[1]][(int) getProductLocation(list.get(route.get(i)))[0]] = printColor(code, "V  ");
            }
            routeMap[19 - (int) getProductLocation(list.get(route.get(i)))[1]][(int) getProductLocation(list.get(route.get(i)))[0]] = printColor(code, String.format("%-3s", list.get(route.get(i))));
            code++;

//            if(route.get(i)==route.get(0)){
//                break;
//            }else{
//                routeMap[19-(int)getProductLocation(list.get(route.get(i)))[1]][(int)getProductLocation(list.get(route.get(i)))[0]] = String.format("%-3s", list.get(route.get(i)));
//            }
        }

        int xx = graph[route.get(route.size() - 2)][route.get(route.size() - 1)][0];
        int yy = graph[route.get(route.size() - 2)][route.get(route.size() - 1)][1];
        if (xx > 0) {
            for (int j = (int) getProductLocation(list.get(route.get(route.get(route.size() - 2))))[0] + 1; j < (int) end[0]; j++) {
                if (routeMap[19 - (int) getProductLocation(list.get(route.get(route.size() - 2)))[1]][j] == "   ") {
                    routeMap[19 - (int) getProductLocation(list.get(route.get(route.size() - 2)))[1]][j] = printColor(code, "---");
                }
            }
            routeMap[19 - (int) getProductLocation(list.get(route.get(route.size() - 2)))[1]][(int) end[0] - 1] = printColor(code, "-->");

        } else if (xx < 0) {
            for (int j = (int) getProductLocation(list.get(route.get(route.size() - 2)))[0] - 1; j > (int) end[0]; j--) {
                if (routeMap[19 - (int) getProductLocation(list.get(route.get(route.size() - 2)))[1]][j] == "   ")
                    routeMap[19 - (int) getProductLocation(list.get(route.get(route.size() - 2)))[1]][j] = printColor(code, "---");
            }
            routeMap[19 - (int) getProductLocation(list.get(route.get(route.size() - 2)))[1]][(int) end[0] + 1] = printColor(code, "<--");
        }
        if (yy > 0) {
            for (int j = (int) getProductLocation(list.get(route.get(route.size() - 2)))[1]; j < (int) end[1]; j++) {
                if (routeMap[19 - j][(int) end[0]] == "   ") routeMap[19 - j][(int) end[0]] = printColor(code, "|  ");
            }
            routeMap[20 - (int) end[1]][(int) end[0]] = printColor(code, "^  ");
        } else if (yy < 0) {
            for (int j = (int) getProductLocation(list.get(route.get(route.size() - 2)))[1]; j > (int) end[1]; j--) {
                if (routeMap[19 - j][(int) end[0]] == "   ") routeMap[19 - j][(int) end[0]] = printColor(code, "|  ");
            }
            routeMap[18 - (int) end[1]][(int) end[0]] = printColor(code, "V  ");
        }
        routeMap[19 - (int) start[1]][(int) start[0]] = "S  ";
        routeMap[19 - (int) end[1]][(int) end[0]] = printColor(code, "E  ");


        System.out.println("The route is show as below:");
//        for(int i=0;i<20;i++){
//            for(int j=0;j<40;j++){
//                System.out.print(routeMap[i][j]);
//            }
//            System.out.println(" ");
//        }
        for (int i = 0; i <= 20; i++) {
            if (i == 20) {
                System.out.print("   ");
                for (int j = 0; j < 40; j++) {
                    String jid = String.format("%-2s", j);
                    System.out.print(jid + " ");
                }
                System.out.println(" ");
            } else {
                String iid = String.format("%-2s", 19 - i);
                System.out.print(iid + " ");
                for (int j = 0; j < 40; j++) {
                    System.out.print(routeMap[i][j]);
                }
                System.out.println(" ");
            }
        }
    }

    public static void printRouteMap(Pair[][] matrix, List<Integer> route, int[] start, int[] end){
        List<Integer> list= order.getOrderList();
        list.add(0, -1);
        //list.add(route.size(),-2);
        List<int[]> subRoute;
        String[][] routeMap = new String[20][40];
        int code = 31;

        //initialize
        ArrayList<Integer> tmparray = new ArrayList<>();
        for(int i=0;i<20;i++){
            for(int j=0;j<40;j++){
                tmparray = new ArrayList<>();
                tmparray.add((int)j);
                tmparray.add((int)19-i);
                if(shelveMap.containsKey(tmparray)){
                    routeMap[i][j] = "□  ";
                }else{
                    routeMap[i][j] = "   ";
                }

            }
        }
        int[] lastfrom = new int[2];
        routeMap[19-start[1]][start[0]] = "S  ";
        lastfrom[0] = start[0];
        lastfrom[1] = start[1];
        for (int i = 1; i < route.size(); i++){
            subRoute = (List<int[]>) matrix[i - 1][i].getKey();
            int xx,yy;
            int[] from, to;
            int verticalMove=0;//verticalMove: 0 no vertical move, 1 go north, -1 go south.
            //from=subRoute.get(0);
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
                        for(int j=lastfrom[1]; j<from[1];j++){
                            if(routeMap[19-j][from[0]] == "   ") routeMap[19-j][from[0]] = printColor(code,"|  ");
                        }
                        routeMap[19 - from[1]][from[0]] = printColor(code,"^  ");

                    }else{
                        for(int j=lastfrom[1]; j>from[1];j--){
                            if(routeMap[19-j][from[0]] == "   ") routeMap[19-j][from[0]] = printColor(code,"|  ");
                        }
                        routeMap[19 - from[1]][from[0]] = printColor(code,"V  ");
                    }
                }
                if(xx>0){
                    for(int j=from[0]; j<to[0];j++){
                        if( routeMap[19-from[1]][j] == "   ") {
                            routeMap[19-from[1]][j] = printColor(code,"---");
                        }
                    }
                    routeMap[19 - from[1]][to[0]] = printColor(code,">  ");
                }else{
                    for(int j=from[0]; j>to[0];j--){
                        if(routeMap[19-from[1]][j] == "   ") routeMap[19-from[1]][j] = printColor(code,"---");
                    }
                    routeMap[19 - from[1]][to[0]] = printColor(code,"<--");
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
                    for(int j=lastfrom[1]; j<to[1];j++){
                        if(routeMap[19-j][to[0]] == "   ") routeMap[19-j][to[0]] = printColor(code,"|  ");
                    }
                    routeMap[19 - to[1]][to[0]] = printColor(code,"^  ");

                }else{
                    for(int j=lastfrom[1]; j>to[1];j--){
                        if(routeMap[19-j][to[0]] == "   ") routeMap[19-j][to[0]] = printColor(code,"|  ");
                    }
                    routeMap[19 - to[1]][to[0]] = printColor(code,"V  ");
                }
            }

//            if(verticalMove!=0){
//                direction+="\tGo to "+(verticalMove==1?"north":"south")+" to ("+to[0]+","+to[1]+")\n";
//            }
            if(i==route.size()-1){
                routeMap[19-to[1]][to[0]] = printColor(code,"E  ");
            }else{
//                direction+="Pick up product "+ list.get(route.get(i))+"\n";
                routeMap[19-to[1]][to[0]]  = printColor(code,"V  ");
                routeMap[20-to[1]][to[0]] =printColor(code,String.format("%-3s", list.get(route.get(i))).substring(3,6));
                code++;
            }
        }

        System.out.println("The route is show as below:");
//        for(int i=0;i<20;i++){
//            for(int j=0;j<40;j++){
//                System.out.print(routeMap[i][j]);
//            }
//            System.out.println(" ");
//        }
        for(int i = 0; i<=20;i++){
            if(i==20){
                System.out.print("   ");
                for(int j=0;j<40;j++){
                    String jid = String.format("%-2s", j);
                    System.out.print(jid + " ");
                }
                System.out.println(" ");
            }else{
                String iid = String.format("%-2s", 19-i);
                System.out.print(iid + " ");
                for(int j=0;j<40;j++){
                    System.out.print(routeMap[i][j]);
                }
                System.out.println(" ");
            }
        }


    }

//==============================================================================================================================
//                                      Core calls: Creat orders, process order
//==============================================================================================================================

    public static void menu_create_order(Warehouse warehouse) throws FileNotFoundException {

        int i = orders.size();
        warehouse.addOrderList();

        //getStartEnd();
        //-------------which algorithm------------
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease select the algorithm you want to use to get the route path-----> 1 for NN. 2 for DP. 3 for GA.");
        int algorithm_num;
        algorithm_num = scanner.nextInt();

        //------------get instruction and directions for these new order-----------------
        for (; i < orders.size(); i++) {
            processOrder(i, filename, start, end, algorithm_num);
        }

    }

    public static void getStartEnd() {
        //--------------------------dynamic start and end----------------------------------//

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the START point location seperated by a blank.");
        for (int i = 0; i < 2; i++) {
            start[i] = scanner.nextInt();
        }
        System.out.println("Please enter the END point location seperated by a blank.");
        for (int i = 0; i < 2; i++) {
            end[i] = scanner.nextInt();
        }
        System.out.println("Your start and end points are (" + start[0] + "," + start[1] + ") and (" + end[0] + "," + end[1] + ")\n");

    }

    /**
     * process given order
     *
     * @param filename export file
     * @param start    dynamic start
     * @param end      dynamic end
     */
    public static void processOrder(int OrderID, String filename, int[] start, int[] end, int algorithm_num) throws FileNotFoundException {

        //--------------------------------------- process orders----------------------------------------------------------------

        order = orders.get(OrderID);

        System.out.println("\n");
        System.out.print("-------------Order # " + OrderID+1 + "-------------\n");
        //print all the items
        List<Integer> list = order.getOrderList();
        System.out.print("Item location: ");
        for (Integer l : list) {
            System.out.print("(" + (int) getProductLocation(l)[0] + "," + (int) getProductLocation(l)[1] + ") ");
        }
        if(algorithm_num==0) {
            //- Ask algorithm--
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nPlease select the algorithm you want to use to get the route path-----> 1 for NN. 2 for DP. 3 for GA.");
            algorithm_num = scanner.nextInt();
        }
        //------------------1: run NN------------------------------
        if (algorithm_num == 1) {

            //start time measure
            long startTime = System.currentTimeMillis();

            Pair[][] matrix = RouteBFS.routeDistanceMatrix(order, productLocationMap, start, end);
            int[][] graph = new int[matrix.length][matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    graph[i][j] = (int) matrix[i][j].getValue();
                }
            }


            System.out.print("Nearest neighbor approach\n");
            //int[][] [] graph = order.getXYDistanceMatrix(productLocationMap,start,end);
            tsp_nn = new TSP_NN(OrderID, graph);

            List<Integer> route = tsp_nn.nearestNeigh();

            //String direction =printRoute(order, route,start,end);
            String direction;
            direction = printRoute(matrix, route, start, end);
            //end time measure
            long endTime = System.currentTimeMillis();
            System.out.print(direction);
            printRouteMap(matrix, route, start, end);
            long timePeriod = endTime - startTime;
            System.out.println("\nFor approach 1, this order takes time around  " + timePeriod + "  ms\n");

            //-----------whether finished?------------
            Scanner input = new Scanner(System.in);
            System.out.println("Finish picking up all the items of this order? y/n");
            String finsh = input.nextLine();

            if (finsh.equals("y")) {
                updateOrderStatus(OrderID);
                //------------------------------export direction to txt----------------
                exportFile.exportTxt(filename, "" + direction);
            }


        }
        //------------------2: run DP------------------------------
        else if (algorithm_num == 2) {
            System.out.print("DP approach\n");

//                List<Integer> route=tsp_nn.nearestNeigh();
//                String direction =printRoute(order, route,start,end);
            tsp_dp = new TSP_DP();
            int[][] graphForDP = order.getDistanceMatrixForDP(productLocationMap, start, end);

            long startTime = System.currentTimeMillis();
            List<Integer> route = tsp_dp.getRoute(graphForDP);
            long endTime = System.currentTimeMillis();

            long timePeriod = endTime - startTime;
            System.out.println("For approach 2, this order takes time around  " + timePeriod + "  ms");

            String direction = printRoute(order, route, start, end);

            if (route != null) {
                printRouteMap(order, route, start, end);

                System.out.print("\n\n");

                //-----------whether finished?------------
                Scanner input = new Scanner(System.in);
                System.out.println("Finish picking up all the items of this order? y/n");
                String finsh = input.nextLine();

                if (finsh.equals("y")) {
                    updateOrderStatus(OrderID);
                    //------------------------------export direction to txt----------------
                    exportFile.exportTxt(filename, "" + direction);
                }
            }


        }
        //------------------3: run GA------------------------------
        else if (algorithm_num == 3) {
            System.out.print("GA approach\n");
            int[][] graphforGA = order.getDistanceMatrix(productLocationMap, start, end);
            tsp_ga = new TSP_GA(30, graphforGA.length - 2, 1000, 0.8f, 0.9f);
            tsp_ga.init(graphforGA);

            long startTime = System.currentTimeMillis();
            List<Integer> route = tsp_ga.solve();
            long endTime = System.currentTimeMillis();

            long timePeriod = endTime - startTime;
            System.out.println("For approach 3, this order takes time around  " + timePeriod + "  ms");

            String direction = printRoute(order, route, start, end);

            if (route != null) {
                printRouteMap(order, route, start, end);

                System.out.print("\n\n");

                //-----------whether finished?------------
                Scanner input = new Scanner(System.in);
                System.out.println("Finish picking up all the items of this order? y/n");
                String finsh = input.nextLine();

                if (finsh.equals("y")) {
                    updateOrderStatus(OrderID);
                    //------------------------------export direction to txt----------------
                    exportFile.exportTxt(filename, "" + direction);
                }
            }
        }
    }

    public static void loadLocationFromFile(String file) throws FileNotFoundException {
        productLocationMap = readfile.readfile(file);
    }

    public static void loadLocationFromFileAndRetry(String file) {
        try {
            loadLocationFromFile(file);
        } catch (Exception e) {
            System.out.println("Load file Error!");
            loadLocationFromFileAndRetry(file);
        }
    }

    public static void loadOrdersFromFileAndRetry(String file) {
        try {
            Warehouse.loadOrdersFromFile(file);
        } catch (Exception e) {
            System.out.println("Load file Error!");
            loadOrdersFromFileAndRetry(file);
        }
    }

    //==============================================================================================================================
//                           main
//
//==============================================================================================================================
    public static void main(String[] args) throws FileNotFoundException {


        // ------------------------------Warehouse initiation  and read file--------------------------
        // not necessary for now if we only have one warehouse
        Warehouse warehouse = new Warehouse();

        try {
            loadOrdersFromFile("src/main/resources/qvBox-warehouse-orders-list-part01.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //warehouse.printOrderList();

        Scanner input = new Scanner(System.in);
        //read file

        loadLocationFromFile("src/main/resources/qvBox-warehouse-data-f20-v01.txt");

        //there is a productLocationMap in Warehouse class
        loadLocationData(warehouse);
        warehouse.getShelveMap();
        warehouse.printMap();

        //---------------------------------------Ask where to export---------------
        Scanner console = new Scanner(System.in);
        System.out.println("Please enter the filename to export.");
        filename = console.nextLine();
        filename += ".txt";
        //---------------------------------------Ask start & end---------------
        getStartEnd();


        // ------------------------------------ASK user for orders---------------------


        //Provide user w/ a menu option so they can choose to enter another order, load another order, find a product, etc.
        // ------------------------------------Menu options -------------------------------------
        //   1.load a current order  2.create a new order   3.find a product;
        while (true) {

            System.out.println("\n\n----------------------Welcome to the EasyWarehouse beta v1.0------------------------");

            System.out.println("--------------------------Please enter the option number----------------------------");
            System.out.println("------------1. Create a list of new orders");
            System.out.println("------------2. Load an existing order");
            System.out.println("------------3. Find a product");
            int option_number = input.nextInt();

            int query_order_num = 0;
            if (option_number == 1) {
                //create order list(map structure) and process all of them
                menu_create_order(warehouse);
                continue;
            } else if (option_number == 2) {
                //load a existing order
                System.out.println("Currently, there are " + orders.size() + " existing orders ");
                try {
                    System.out.println("Enter the index(1 based) of the order that you want to query");
                    query_order_num = input.nextInt();
                    if (query_order_num > orders.size()) {
                        throw new Exception("Order not existed!");
                    } else {
                        // print the current order:
                        Order query_order = orders.get(query_order_num - 1);
                        for (int i : query_order.getProducts().keySet()) {
                            System.out.println(i + " ");
                        }
                        processOrder(query_order_num , filename, start, end,0);
                        // System.out.println("---------------------------");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //------------------------------------when finish a order display the neighbour or pick up a specific one---------------------

                boolean backToMainMenu = false;
                while(!backToMainMenu) {
                    System.out.println("---------------Do you want to pick up a specific order(y/n)?-------------");
                    input = new Scanner(System.in);
                    String nextOrPick = input.nextLine();
                    if (nextOrPick.equals("n")) {
                        // pick up the next order by default;
                        query_order_num++;
                        processOrder(query_order_num, filename, start, end, 0);
                    } else if (nextOrPick.equals("y")) {
                        System.out.println("Please enter the order you want to pick");
                        query_order_num = input.nextInt();
                        processOrder(query_order_num, filename, start, end, 0);
                    }
                    System.out.println("Do you want to go back to the main menu(y/n)?");
                    String back_to_main = input.nextLine();
                    if (back_to_main.equals("y")) {
                       backToMainMenu = true;
                    }
                }
            }  else if (option_number == 3) {
                //query a product
                try {
                    System.out.println("Please enter the productID to get location");
                    InputStreamReader isr = new InputStreamReader(System.in);
                    BufferedReader br = new BufferedReader(isr);
                    String s1 = br.readLine();
                    int id = Integer.parseInt(s1);
                    double[] location = getProductLocation(id);
                    System.out.println("The location is (" + location[0] + "," + location[1] + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
