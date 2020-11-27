package main.java.com.uci.warehouse.Model;

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
        //list.add(0, -1);//not for dynamic startpoint
        return list;
    }





    /**
     * Generate distanceMatrix of products
     * @param locationMap map storing location of products
     * @return distanceMatrix
     */
    public int[][] getDistanceMatrix(Map<Integer, double[]> locationMap) {
        List<Integer> list = new ArrayList<>(products.keySet());
        //add startpoint at first
        list.add(0, -1);
        //add endpoint at last
        list.add(list.size(),-2);
        locationMap.put(-1, new double[]{0,0});
        locationMap.put(-2, new double[]{0,0});
        int[][] m = new int[list.size()][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (!locationMap.containsKey(list.get(i)) || !locationMap.containsKey(list.get(j))) try {
                    throw new Exception("No such Product in locationMap!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m[i][j] = Math.abs((int)locationMap.get(list.get(i))[0] - (int)locationMap.get(list.get(j))[0])
                        + Math.abs((int)locationMap.get(list.get(i))[1] - (int)locationMap.get(list.get(j))[1]);
            }
        }
        return m;
    }


    /**
     * Overload with dynamic start end points
     * Generate distanceMatrix of products
     * @param locationMap map storing location of products
     * @return distanceMatrix
     */
    public int[][] getDistanceMatrix(Map<Integer, double[]> locationMap, int[] start, int[] end) {
        List<Integer> list = new ArrayList<>(products.keySet());
        list.add(0, -1);
        list.add(list.size(),-2);
        locationMap.put(-1, new double[]{start[0],start[1]});
        locationMap.put(-2, new double[]{end[0],end[1]});
        int[][] m = new int[list.size()][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (!locationMap.containsKey(list.get(i)) || !locationMap.containsKey(list.get(j))) try {
                    throw new Exception("No such Product in locationMap!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m[i][j] = Math.abs((int)locationMap.get(list.get(i))[0] - (int)locationMap.get(list.get(j))[0])
                        + Math.abs((int)locationMap.get(list.get(i))[1] - (int)locationMap.get(list.get(j))[1]);
            }
        }
        return m;
    }

    /**
     * Generate distanceMatrix of products in X and Y direction for Dynamic Programming
     * @param locationMap map storing location of products
     * @return getXYDistanceMatrix
     * @author SiqianWan
     */
    public int[][] getDistanceMatrixForDP(Map<Integer, double[]> locationMap,  int[] start, int[] end) {
        List<Integer> list = new ArrayList<>(products.keySet());
        //add startpoint at first
        list.add(0, -1);
        locationMap.put(-1, new double[]{0,0});
        int[][] m = new int[list.size()][list.size()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (!locationMap.containsKey(list.get(i)) || !locationMap.containsKey(list.get(j))) try {
                    throw new Exception("No such Product in locationMap!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m[i][j] = Math.abs((int)locationMap.get(list.get(i))[0] - (int)locationMap.get(list.get(j))[0])
                        + Math.abs((int)locationMap.get(list.get(i))[1] - (int)locationMap.get(list.get(j))[1]);
            }
        }

        //change the first row of distance matrix
        for(int i = 0; i < list.size(); i++) {
            m[0][i] = Math.abs((int)locationMap.get(list.get(i))[0] - start[0])
                    + Math.abs((int)locationMap.get(list.get(i))[1] - start[1]);
        }
        //change the first column of distance matrix
        for (int i = 0; i < list.size(); i++) {
            m[i][0] = Math.abs((int)locationMap.get(list.get(i))[0] - end[0])
                    + Math.abs((int)locationMap.get(list.get(i))[1] - end[1]);
        }
        m[0][0] = Math.abs(start[0] - end[0]) + Math.abs(start[1] - end[1]);
        return m;
    }

    /**
     * Generate distanceMatrix of products in X and Y direction
     * @param locationMap map storing location of products
     * @return getXYDistanceMatrix
     * Jindong
     */
    public int[][][] getXYDistanceMatrix(Map<Integer, double[]> locationMap) {
        List<Integer> list = getOrderList();
        list.add(0, -1);
        //add endpoint at last
        list.add(list.size(),-2);
        locationMap.put(-1, new double[]{0,0});
        locationMap.put(-2, new double[]{0,0});
        int[][][] m = new int[list.size()][list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (!locationMap.containsKey(list.get(i)) || !locationMap.containsKey(list.get(j))) try {
                    throw new Exception("No such Product in locationMap!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m[i][j][0] = -(int)locationMap.get(list.get(i))[0] + (int)locationMap.get(list.get(j))[0];
                m[i][j][1] = -(int)locationMap.get(list.get(i))[1] + (int)locationMap.get(list.get(j))[1];
            }
        }
        return m;
    }

    public int[][][] getXYDistanceMatrix(Map<Integer, double[]> locationMap,int[] start, int[] end) {
        List<Integer> list = getOrderList();
        //add endpoint at last
        list.add(0,-1);
        list.add(list.size(),-2);
        locationMap.put(-1, new double[]{start[0],start[1]});
        locationMap.put(-2, new double[]{end[0],end[1]});
        int[][][] m = new int[list.size()][list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (!locationMap.containsKey(list.get(i)) || !locationMap.containsKey(list.get(j))) try {
                    throw new Exception("No such Product in locationMap!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m[i][j][0] = -(int)locationMap.get(list.get(i))[0] + (int)locationMap.get(list.get(j))[0];
                m[i][j][1] = -(int)locationMap.get(list.get(i))[1] + (int)locationMap.get(list.get(j))[1];
            }
        }
        return m;
    }
}
