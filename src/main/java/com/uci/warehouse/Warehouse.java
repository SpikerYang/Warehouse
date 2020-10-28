package com.uci.warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author spike
 * @Date: 2020-10-27 13:31
 */
public class Warehouse {
    private Map<Integer, Order> orders;
    private Map<Integer, double[]> locationMap;

    public Warehouse() {
    }

    public Warehouse(Map<Integer, Order> orders, Map<Integer, double[]> locationMap) {
        this.orders = orders;
        this.locationMap = locationMap;
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

    public static void main(String[] args) {
        // Warehouse initiation
        Warehouse warehouse = new Warehouse();
        loadLocationData(warehouse);

        // order initiation
        //TODO


    }
}
