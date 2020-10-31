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
    private String status;
    // key: ProductId, value: Quantity
    private Map<Integer, Integer> products;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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


    //get location of the ith item in the order
    public double [] getOrderItemLocation(int index, Map<Integer, double[]> map) {
        List<Integer> list = new ArrayList<>(products.keySet());
        System.out.println(list);
        list.add(0, -1);
        return map.get(list.get(index));
    }

    /**get list of productID
     * includes start location as index 0, value -1
    **/

    public List<Integer> getOrderList(){
        List<Integer> list = new ArrayList<>(products.keySet());
        list.add(0, -1);
        return list;
    }



    /**
     * Generate distanceMatrix of products
     * @param locationMap map storing location of products
     * @return distanceMatrix
     */
    public double[][] getDistanceMatrix(Map<Integer, double[]> locationMap) {
        List<Integer> list = new ArrayList<>(products.keySet());
        list.add(0, -1);
        locationMap.put(-1, new double[]{0,0});
        double[][] m = new double[list.size()][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (!locationMap.containsKey(list.get(i)) || !locationMap.containsKey(list.get(j))) try {
                    throw new Exception("No such Product in locationMap!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m[i][j] = Math.abs(locationMap.get(list.get(i))[0] - locationMap.get(list.get(j))[0])
                        + Math.abs(locationMap.get(list.get(i))[1] - locationMap.get(list.get(j))[1]);
            }
        }
        return m;
    }


    /**
     * Generate distanceMatrix of products in X and Y direction
     * @param locationMap map storing location of products
     * @return getXYDistanceMatrix
     * Jindong
     */
    public double[][][] getXYDistanceMatrix(Map<Integer, double[]> locationMap) {
        List<Integer> list = getOrderList();
        //add endpoint at last
        list.add(list.size(),-1);
        locationMap.put(-1, new double[]{0,0});
        double[][][] m = new double[list.size()][list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (!locationMap.containsKey(list.get(i)) || !locationMap.containsKey(list.get(j))) try {
                    throw new Exception("No such Product in locationMap!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m[i][j][0] = -locationMap.get(list.get(i))[0] + locationMap.get(list.get(j))[0];
                m[i][j][1] = -locationMap.get(list.get(i))[1] + locationMap.get(list.get(j))[1];
            }
        }
        return m;
    }
}
