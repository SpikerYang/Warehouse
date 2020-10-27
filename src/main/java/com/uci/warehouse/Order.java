package com.uci.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author spike
 * @Date: 2020-10-27 13:20
 */
public class Order {
    private int id;
    // key: ProductId, value: Quantity
    private Map<Integer, Integer> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    public Order(int id) {
        this.id = id;
        products = new HashMap<>();
    }

    public void addProduct(int productId, int quantity) {
        products.put(productId, products.getOrDefault(productId, 0) + quantity);
    }

    public void removeProduct(int productId, int quantity) {
        //TODO
    }

    /**
     * Generate distanceMatrix of order
     * @param locationMap map storing location of products
     * @return distanceMatrix
     */
    public double[][] getDistanceMatrix(Map<Integer, double[]> locationMap) {
        List<Integer> list = new ArrayList<>(products.keySet());
        double[][] m = new double[list.size()][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (!locationMap.containsKey(list.get(i)) || !locationMap.containsKey(list.get(j))) try {
                    throw new Exception("No such Product in locationMap!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m[i][j] = Math.pow((locationMap.get(list.get(i))[0] - locationMap.get(list.get(j))[0]), 2)
                        + Math.pow((locationMap.get(list.get(i))[1] - locationMap.get(list.get(j))[1]), 2);
            }
        }
        return m;
    }
}
