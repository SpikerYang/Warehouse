package com.uci.warehouse;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;

/**
 * @Author spike
 * @Date: 2020-10-27 13:31
 */
public class Warehouse {

    //==============================================================================================================================
    //parameters might not be static if there are multiply warehouse or map
    //==============================================================================================================================

    private static Map<Integer, Order> orders;
    private static Map<Integer, double[]> productLocationMap;
    private static Map<ArrayList<Long>, Integer> shelveMap;
    private static String[][] routeMap;

    private static Order order;

    private static readFile readfile;

    private static TSP_NN tsp_nn;// nearest neighbor approach : 2-approximation in O(n^2) time
    private static TSP_DP tsp_dp;// DP approch : optimal route in O(n^2*2^n) time

    /**
     * @param map
     */
    public Warehouse(Map<Integer, double[]> map){
        productLocationMap = map;
    }
    public Warehouse() {
        orders = new HashMap<>();
        productLocationMap = new HashMap<>();
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
     * @return Order
     */
    public Order createOrder() {
        // get the quantity of products in the order
        System.out.println("Type the size of the order: ");
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int id = orders.size();
        Order order = new Order(id);
        System.out.println("Type id of product seperated by blanks: ");
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
        return orders.get(orderId);
    }

    //get list of productID
    public List<Order> getOrderList() {
        return new ArrayList<>(orders.values());
    }

    public void updateOrderStatus(int orderId) {
        if (orders.containsKey(orderId)) try {
            throw new Exception("Order not existed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.get(orderId).setStatus("Completed");
    }

    /**
     * Get location of specific product by id
     * @param productId
     * @return location
     */
    //change to static
    public static double[] getProductLocation(int productId) {
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
     * @param warehouse
     */
    private static void loadLocationData(Warehouse warehouse) {
        //TODO
    }
//==============================================================================================================================
//                           map printing functions
//==============================================================================================================================
    /**
     * Print route to console with map
     */
    public void printRoute(ArrayList<Integer> arrayList) {
        Iterator<Integer> iterator = arrayList.iterator();
        ArrayList<ArrayList<Long>> shelveArray = new ArrayList<>();
        int productId;
        double[] location = new double[2];
        long[] shelveLocation = new long[2];
        int sheleveId;
        int count = 1;
        ArrayList<Long> tmparray = new ArrayList<>();
        while(iterator.hasNext()){
            productId = iterator.next();
            location = productLocationMap.get(productId);
            tmparray = new ArrayList<>();
            tmparray.add((long)location[0]);
            tmparray.add((long)location[1]);
            shelveArray.add(tmparray);
            sheleveId = shelveMap.get(tmparray);
            System.out.println(count + "th product is in No." + sheleveId + " shelve<" + tmparray.get(0)
                    + ", " + tmparray.get(1)+"> and product's location is <" + location[0] + ", " + location[1]+">");
            count++;
        }
        System.out.println("The map is show as below:");
        long[] sloction = new long[2];
        int id;
        for(int i = 0; i<=20;i++){
            if(i==20){
                System.out.print("   ");
                for(int j=0;j<40;j++){
                    String jid = String.format("%-2s", j+1);
                    System.out.print(jid + " ");
                }
                System.out.println(" ");
            }else{
                String iid = String.format("%-2s", 20-i);
                System.out.print(iid + " ");
                for(int j=0;j<40;j++){
                    tmparray = new ArrayList<>();
                    tmparray.add((long)j);
                    tmparray.add((long)i);
                    if(i==19 && j==0){
                        System.out.print("S  ");
                    }else if(shelveArray.contains(tmparray)){
                        id = shelveArray.indexOf(tmparray)+1;
//                        String sid = String.format("%-2s", id);
//                        System.out.print(sid + " ");
                        System.out.print("__ ");
                    }else{
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
    public void printMap(){
        long[] sloction = new long[2];
        int id;
        ArrayList<Long> tmparray = new ArrayList<>();
        System.out.println("--------------------The map is show as below:-----------------------");
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
                    tmparray = new ArrayList<>();
                    tmparray.add((long)j);
                    tmparray.add((long)i);
                    if(i==19 && j==0){
                        System.out.print("   ");
                    }else if(shelveMap.containsKey(tmparray)){
                        id = shelveMap.get(tmparray);
//                        String sid = String.format("%-2s", id);
//                        System.out.print(sid + " ");
                        System.out.print("□  ");
                    }else{
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
    public void getShelveMap(){
        shelveMap = new HashMap<>();
        int id = 1;
        double[] location = new double[2];
        long[] shelveLocation = new long[2];
        Iterator<Integer> iterator = productLocationMap.keySet().iterator();
        ArrayList<Long> tmparray = new ArrayList<>();
        while (iterator.hasNext()){
            location = productLocationMap.get(iterator.next());
            tmparray = new ArrayList<>();
            tmparray.add((long)location[0]);
            tmparray.add((long)location[1]);
//            shelveLocation[0] = (long)location[0];
//            shelveLocation[1] = (long)location[1];
            if(!shelveMap.containsKey(tmparray)){
                shelveMap.put(tmparray,id);
                id++;
                
            }
        }
    }

//==============================================================================================================================
//                           print TSP route instruction
//==============================================================================================================================


    /**
     * print route instruction
     * @param order which order
     * @param route route list with start and end node
     */
    public static String printRoute(Order order, List<Integer> route, int[]start,int[]end) {

        int[][][] graph = order.getXYDistanceMatrix(productLocationMap,start,end);
//        for(double[][] g:graph){
//            for(double[] gg:g){
//                for(double ggg:gg){
//                    System.out.print(ggg+ "     ");
//                }
//
//            }
//            System.out.println(" ");
//        }

    List<Integer> list= order.getOrderList();
        list.add(0, -1);

        String direction="Start at location ("+start[0]+","+start[1]+")\n";
        //     order.getOrderItemLocation(0,map)[0]+","+order.getOrderItemLocation(0,map)[1]+"\n";
        for (int i = 1; i < route.size()-1; i++){
            int xx,yy;

                 xx = graph[route.get(i - 1)][route.get(i)][0];
                 yy = graph[route.get(i - 1)][route.get(i)][1];

            if(xx>0){
                direction+="\tGo to east to ("+(int)getProductLocation(list.get(route.get(i)))[0]+","+(int)(getProductLocation(list.get(route.get(i)))[1]-yy)+")\n";
            }else if (xx<0){
                direction+="\tGo to west to ("+(int)getProductLocation(list.get(route.get(i)))[0]+","+(int)(getProductLocation(list.get(route.get(i)))[1]-yy)+")\n";
            }
            if(yy>0){
                direction+="\tGo to north to ("+(int)getProductLocation(list.get(route.get(i)))[0]+","+(int)getProductLocation(list.get(route.get(i)))[1]+")\n";
            }else if(yy<0){
                direction+="\tGo to south to ("+(int)getProductLocation(list.get(route.get(i)))[0]+","+(int)getProductLocation(list.get(route.get(i)))[1]+")\n";
            }
            direction+="Pick up product "+ list.get(route.get(i))+"\n";
        }
        //for the end point
        int xx=graph [route.get(route.size()-2)][route.get(route.size()-1)][0];
        int yy=graph [route.get(route.size()-2)][route.get(route.size()-1)][1];
        if(xx>0){
            direction+="\tGo to east to ("+end[0]+","+(end[1]-yy)+")\n";
        }else if (xx<0){
            direction+="\tGo to west to ("+end[0]+","+(end[1]-yy)+")\n";
        }
        if(yy>0){
            direction+="\tGo to north to ("+end[0]+","+end[1]+")\n";
        }else if(yy<0){
            direction+="\tGo to south to ("+end[0]+","+end[1]+")\n";
        }
        direction+="Done!\n";



        System.out.print(direction);
        return direction;
    }


    public static String printColor(int code,String content){
        return String.format("\033[%d;%dm%s\033[0m", code, 2, content);
    }


    public static void printRouteMap(Order order, List<Integer> route, int[] start,int[] end){
        List<Integer> list= order.getOrderList();
        int[][][] graph = order.getXYDistanceMatrix(productLocationMap,start,end);
        String[][] routeMap = new String[20][40];
        int code = 31;

        //initialize
        ArrayList<Long> tmparray = new ArrayList<>();
        for(int i=0;i<20;i++){
            for(int j=0;j<40;j++){
                tmparray = new ArrayList<>();
                tmparray.add((long)j);
                tmparray.add((long)i);
                if(shelveMap.containsKey(tmparray)){
                    routeMap[i][j] = "□  ";
                }else{
                    routeMap[i][j] = "   ";
                }

            }
        }

        list.add(0, -1);

        routeMap[19-(int)start[1]][(int)start[0]] = "S  ";

        routeMap[19-(int)getProductLocation(list.get(route.get(0)))[1]][(int)getProductLocation(list.get(route.get(0)))[0]] = String.format("%-3s", "S");
        for (int i = 1; i < route.size()-1; i++){
            int xx=graph [route.get(i-1)][route.get(i)][0];
            int yy=graph [route.get(i-1)][route.get(i)][1];
            if(xx>0){
                for(int j=(int)getProductLocation(list.get(route.get(i-1)))[0]+1; j<(int)getProductLocation(list.get(route.get(i)))[0];j++){
                    if( routeMap[19-(int)getProductLocation(list.get(route.get(i-1)))[1]][j] == "   ") {
                        routeMap[19 - (int) getProductLocation(list.get(route.get(i - 1)))[1]][j] = printColor(code,"---");
                    }
                }
                routeMap[19 - (int) getProductLocation(list.get(route.get(i - 1)))[1]][(int)getProductLocation(list.get(route.get(i)))[0]-1] = printColor(code,"-->");
            }else if (xx<0){
                for(int j=(int)getProductLocation(list.get(route.get(i-1)))[0]-1; j>(int)getProductLocation(list.get(route.get(i)))[0];j--){
                    if(routeMap[19-(int)getProductLocation(list.get(route.get(i-1)))[1]][j] == "   ") routeMap[19-(int)getProductLocation(list.get(route.get(i-1)))[1]][j] = printColor(code,"---");
                }
                routeMap[19 - (int) getProductLocation(list.get(route.get(i - 1)))[1]][(int)getProductLocation(list.get(route.get(i)))[0]+1] = printColor(code,"<--");
            }
            if(yy>0){
                for(int j=(int)getProductLocation(list.get(route.get(i-1)))[1]; j<(int)getProductLocation(list.get(route.get(i)))[1];j++){
                    if(routeMap[19-j][(int)getProductLocation(list.get(route.get(i)))[0]] == "   ")routeMap[19-j][(int)getProductLocation(list.get(route.get(i)))[0]] = printColor(code,"|  ");
                }
                routeMap[20 - (int)getProductLocation(list.get(route.get(i)))[1]][(int)getProductLocation(list.get(route.get(i)))[0]] = printColor(code,"^  ");
            }else if(yy<0){
                for(int j=(int)getProductLocation(list.get(route.get(i-1)))[1]; j>(int)getProductLocation(list.get(route.get(i)))[1];j--){
                    if(routeMap[19-j][(int)getProductLocation(list.get(route.get(i)))[0]] == "   ")routeMap[19-j][(int)getProductLocation(list.get(route.get(i)))[0]] = printColor(code,"|  ");
                }
                routeMap[18 - (int)getProductLocation(list.get(route.get(i)))[1]][(int)getProductLocation(list.get(route.get(i)))[0]] = printColor(code,"V  ");
            }
            routeMap[19-(int)getProductLocation(list.get(route.get(i)))[1]][(int)getProductLocation(list.get(route.get(i)))[0]] = printColor(code,String.format("%-3s", list.get(route.get(i))));
            code++;

//            if(route.get(i)==route.get(0)){
//                break;
//            }else{
//                routeMap[19-(int)getProductLocation(list.get(route.get(i)))[1]][(int)getProductLocation(list.get(route.get(i)))[0]] = String.format("%-3s", list.get(route.get(i)));
//            }
        }

        int xx=graph [route.get(route.size()-2)][route.get(route.size()-1)][0];
        int yy=graph [route.get(route.size()-2)][route.get(route.size()-1)][1];
        if(xx>0){
            for(int j=(int)getProductLocation(list.get(route.get(route.get(route.size()-2))))[0]+1; j<(int)end[0];j++){
                if( routeMap[19-(int)getProductLocation(list.get(route.get(route.size()-2)))[1]][j] == "   ") {
                    routeMap[19 - (int) getProductLocation(list.get(route.get(route.size()-2)))[1]][j] = printColor(code,"---");
                }
            }
            routeMap[19 - (int) getProductLocation(list.get(route.get(route.size()-2)))[1]][(int)end[0]-1] = printColor(code,"-->");

        }else if (xx<0){
            for(int j=(int)getProductLocation(list.get(route.get(route.size()-2)))[0]-1; j>(int)end[0];j--){
                if(routeMap[19-(int)getProductLocation(list.get(route.get(route.size()-2)))[1]][j] == "   ") routeMap[19-(int)getProductLocation(list.get(route.get(route.size()-2)))[1]][j] = printColor(code,"---");
            }
            routeMap[19 - (int) getProductLocation(list.get(route.get(route.size()-2)))[1]][(int)end[0]+1] = printColor(code,"<--");
        }
        if(yy>0){
            for(int j=(int)getProductLocation(list.get(route.get(route.size()-2)))[1]; j<(int)end[1];j++){
                if(routeMap[19-j][(int)end[0]] == "   ")routeMap[19-j][(int)end[0]] = printColor(code,"|  ");
            }
            routeMap[20 - (int)end[1]][(int)end[0]] = printColor(code,"^  ");
        }else if(yy<0){
            for(int j=(int)getProductLocation(list.get(route.get(route.size()-2)))[1]; j>(int)end[1];j--){
                if(routeMap[19-j][(int)end[0]] == "   ")routeMap[19-j][(int)end[0]] = printColor(code,"|  ");
            }
            routeMap[18 - (int)end[1]][(int)end[0]] = printColor(code,"V  ");
        }
        routeMap[19-(int)start[1]][(int)start[0]] = "S  ";
        routeMap[19-(int)end[1]][(int)end[0]] = printColor(code,"E  ");



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
//                           main
//
//==============================================================================================================================
    public static void main(String[] args) throws FileNotFoundException {

        // ------------------------------Warehouse initiation  and read file--------------------------
        // not necessary for now if we only have one warehouse
        Warehouse warehouse = new Warehouse();
        //read file
        String filePath = "src/qvBox-warehouse-data-f20-v01.txt";
        
        readfile = new readFile();
        productLocationMap=readfile.readfile(filePath);


        //there is a productLocationMap in Warehouse class
        loadLocationData(warehouse);
        // ------------------------------------ASK user for orders---------------------
        warehouse.getShelveMap();
        warehouse.printMap();
        warehouse.addOrderList();

        // print map
        //warehouse.getShelveMap();
        //warehouse.printMap();
        //TODO



        //fake a orderID :1
//        order = new Order(1);
//        //order.addProduct(0,1);
//        order.addProduct(1,1);
//        order.addProduct(45,1);
//        order.addProduct(74,1);
//        order.addProduct(102,1);
        //--------------------------dynamic start and end----------------------------------
        int[] start=new int[2];
        int[] end=new int[2];
        //
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the START point location seperated by a blank.");
        for(int i = 0; i < 2; i++){
            start[i]=scanner.nextInt();
        }
        System.out.println("Please enter the END point location seperated by a blank.");
        for(int i = 0; i < 2; i++){
            end[i]=scanner.nextInt();
        }
        System.out.println("Your start and end points are ("+start[0]+","+start[1]+") and ("+end[0]+","+end[1]+")\n");

        //--------------------------------------- process orders----------------------------------------------------------------
        for(int i=0;i<orders.size();i++){

            // init the graph;
            order=orders.get(0);
            int[][] [] graph_se = order.getXYDistanceMatrix(productLocationMap,start,end);
            //int[][] [] graph_default = order.getXYDistanceMatrix(productLocationMap);
            //int[][]graph=order.getDistanceMatrix(productLocationMap);

//        for(int[][] g:graph_se){
//            for(int[] gg:g){
//                System.out.print("("+gg[0]+ ","+gg[1]+")");
//            }
//            System.out.println(" ");
//        }

            //print all the items
            List<Integer> list= order.getOrderList();
            System.out.print("Item location: ");
            for(Integer l:list){
                System.out.print("("+(int)getProductLocation(l)[0]+","+(int)getProductLocation(l)[1]+") ");
            }
            System.out.println("\n");


            //-------------------------1. nearest neighbor approach : 2-approximation in O(n^2) time----------------------------
            System.out.print("1. nearest neighbor approach\n");
            long startTime = System.currentTimeMillis();
            tsp_nn = new TSP_NN(1, graph_se);
            
            
            List<Integer> route=tsp_nn.nearestNeigh();

            String direction =printRoute(order, route,start,end);

            printRouteMap(order,route,start,end);
            long endTime = System.currentTimeMillis();
            long timePeriod = endTime-startTime;
            System.out.println("For approach 1, this order takes time around  "+ timePeriod + "  ms");
            //System.out.print(direction);
            System.out.print("\n\n");
            //------------------------------export direction to txt----------------
            exportFile.exportTxt("\n\nOrder:"+i+"\n"+direction);


//        //---------------------------2. DP approach : optimal route in O(n^2*2^n) time-------------------------------
            System.out.print("2. DP approach\n");
            startTime = System.currentTimeMillis();
            
            tsp_dp = new TSP_DP();
            int[][] graphForDP = order.getDistanceMatrixForDP(productLocationMap, start, end);
            route = tsp_dp.getRoute(graphForDP);

            endTime = System.currentTimeMillis();
            timePeriod = endTime-startTime;
            System.out.println("For approach 2, this order takes time around  "+ timePeriod + "  ms");
            printRoute(order, route, start, end);
            printRouteMap(order,route,start,end);
            System.out.print("\n\n");
            //export direction to txt
            exportFile.exportTxt("\n\nOrder:1\n"+direction);
//
//        printRouteMap(order,route);

            try {
                System.out.println("Please enter the productID to get location");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String s1 = br.readLine();
                int id=Integer.parseInt(s1);
                double[] location = getProductLocation(id);
                System.out.println("The location is (" + location[0] + "," + location[1] + ")");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
