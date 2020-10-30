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

    private static TSP tsp;

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
    public double[] getProductLocation(int productId) {
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
        // Warehouse initiation
        Warehouse warehouse = new Warehouse();
        loadLocationData(warehouse);

        String filePath = "/Users/ziliu/Desktop/EECS221c/Heuristics-TSP-master/src/qvBox-warehouse-data-f20-v01.txt";
        readfile = new readFile();
        Map<Integer,double[]> map = readfile.readfile(filePath);
        // order initiation
        //TODO

        order = new Order(1);
        order.addProduct(0,1);
        order.addProduct(1,1);
        order.addProduct(45,1);
        order.addProduct(74,1);
        order.addProduct(102,1);

        // init the graph;
        double[][] graph = order.getDistanceMatrix(map);
        for(double[] g:graph){
            for(double gg:g){
                System.out.print(gg+ "     ");
            }
            System.out.println(" ");
        }

        // show the route;
        tsp = new TSP(1, graph);
        tsp.nearestNeigh(graph);
    }
}
