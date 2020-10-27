package com.uci.warehouse.Test;

import com.uci.warehouse.Order;
import org.junit.Assert;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @Author spike
 * @Date: 2020-10-27 15:17
 */
class OrderTest {
    @Test
    void getDistanceMatrixTest() {
        Map<Integer, double[]> locationMap = new HashMap<>();
        locationMap.put(1, new double[]{2, 0});
        locationMap.put(45, new double[]{10, 14});
        locationMap.put(302, new double[]{6, 12});

        Order order = new Order(1);
        order.addProduct(1, 1);
        order.addProduct(45, 3);
        order.addProduct(302, 5);

        double[][] matrix = order.getDistanceMatrix(locationMap);
        //TODO
    }
}
