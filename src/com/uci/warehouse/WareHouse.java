package com.uci.warehouse;

import java.util.Map;

/**
 * @Author spike
 * @Date: 2020-10-27 13:31
 */
public class WareHouse {
    private Map<Integer, Order> orders;
    private Map<Integer, double[]> locationMap;

    public WareHouse(Map<Integer, Order> orders, Map<Integer, double[]> locationMap) {
        this.orders = orders;
        this.locationMap = locationMap;
    }

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }


}
