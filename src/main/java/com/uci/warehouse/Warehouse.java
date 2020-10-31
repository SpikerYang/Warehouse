package com.uci.warehouse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author spike
 * @Date: 2020-10-27 13:31
 */
public class Warehouse {
    private static Map<Integer, Order> orders;
    private static Map<Integer, double[]> productLocationMap;

    private static Order order;

    private static readFile readfile;

    private static TSP_NN tsp_nn;// nearest neighbor approach : 2-approximation in O(n^2) time
    //private static TSP_DP tsp_dp;// DP approch : optimal route in O(n^2*2^n) time

    /**
     * @param map
     */
    public Warehouse(Map<Integer, double[]> map){
        productLocationMap = map;
    }

    public Warehouse() {
    }


    public void addOrder(Order order) {
        if (orders.containsKey(order.getId())) try {
            throw new Exception("Order existed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.put(order.getId(), order);
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
     * Print route to console
     */
    public void printRoute() {
        //TODO
    }

    /**
     * Read location data from file
     * @param warehouse
     */
    private static void loadLocationData(Warehouse warehouse) {
        //TODO
    }

    public static void main(String[] args) throws FileNotFoundException {
        //read file
        String filePath = "src/qvBox-warehouse-data-f20-v01.txt";
        readfile = new readFile();
        productLocationMap=readfile.readfile(filePath);
        // Warehouse initiation
        // not necessary for now if we only have one warehouse
        Warehouse warehouse = new Warehouse();
        //there is a productLocationMap in Warehouse class
        loadLocationData(warehouse);
        // order initiation
        //TODO

        order = new Order(1);
        //order.addProduct(0,1);
        order.addProduct(1,1);
        order.addProduct(45,1);
        order.addProduct(74,1);
        order.addProduct(102,1);

        // init the graph;
        double[][] graph = order.getDistanceMatrix(productLocationMap);
//        for(double[] g:graph){
//            for(double gg:g){
//                System.out.print(gg+ "     ");
//            }
//            System.out.println(" ");
//        }

        // show the route;
        tsp_nn = new TSP_NN(1, graph);
        ArrayList route=tsp_nn.nearestNeigh(graph);
        //System.out.println(route);
        printRoute(order, route);

    }

    /**
     * print route instruction
     * @param order which order
     * @param route route list with start and end node
     */
    public static void printRoute(Order order, List<Integer> route){

        double[][][] graph = order.getXYDistanceMatrix(productLocationMap);
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


        String direction="Start at location ("+getProductLocation(list.get(route.get(0)))[0]+","+getProductLocation(list.get(route.get(0)))[1]+")\n";
           //     order.getOrderItemLocation(0,map)[0]+","+order.getOrderItemLocation(0,map)[1]+"\n";
        for (int i = 1; i < route.size(); i++){
            double xx=graph [route.get(i-1)][route.get(i)][0];
            double yy=graph [route.get(i-1)][route.get(i)][1];
            if(xx>0){
                direction+="\tG0 to east to ("+getProductLocation(list.get(route.get(i)))[0]+","+(getProductLocation(list.get(route.get(i)))[1]-yy)+")\n";
            }else if (xx<0){
                direction+="\tG0 to west to ("+getProductLocation(list.get(route.get(i)))[0]+","+(getProductLocation(list.get(route.get(i)))[1]-yy)+")\n";
            }
            if(yy>0){
                direction+="\tG0 to north to ("+getProductLocation(list.get(route.get(i)))[0]+","+getProductLocation(list.get(route.get(i)))[1]+")\n";
            }else if(yy<0){
                direction+="\tG0 to south to ("+getProductLocation(list.get(route.get(i)))[0]+","+getProductLocation(list.get(route.get(i)))[1]+")\n";
            }
            if(route.get(i)==route.get(0)){
                direction+="Done!";
                break;
            }
            direction+="Pick up product "+ list.get(route.get(i))+"\n";
        }
        System.out.print(direction);
    }
}
