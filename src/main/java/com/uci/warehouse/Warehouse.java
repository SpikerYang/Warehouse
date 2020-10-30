package com.uci.warehouse;

import java.util.*;

/**
 * @Author spike
 * @Date: 2020-10-27 13:31
 */
public class Warehouse {
    private Map<Integer, Order> orders;
    private Map<Integer, double[]> locationMap;
    private Map<ArrayList<Long>, Integer> shelveMap;


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

    /**
     * Generate route by TSP Algorithm
     */
    public void getRoute() {
        //TODO
    }

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
            location = locationMap.get(productId);
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
                        String sid = String.format("%-2s", id);
                        System.out.print(sid + " ");
                    }else{
                        System.out.print("   ");
                    }
                }
                System.out.println(" ");
            }
        }
    }
    /**
     * Read location data from file
     * @param warehouse
     */
    private static void loadLocationData(Warehouse warehouse) {
        //TODO
    }


    /**
     * Print the map with shelve
     * the map is size 40*20
     */
    public void printMap(){
        long[] sloction = new long[2];
        int id;
        ArrayList<Long> tmparray = new ArrayList<>();
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
                    }else if(shelveMap.containsKey(tmparray)){
                        id = shelveMap.get(tmparray);
                        String sid = String.format("%-2s", id);
                        System.out.print(sid + " ");
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
        int id = 1;
        double[] location = new double[2];
        long[] shelveLocation = new long[2];
        Iterator<Integer> iterator = locationMap.keySet().iterator();
        ArrayList<Long> tmparray = new ArrayList<>();
        while (iterator.hasNext()){
            location = locationMap.get(iterator.next());
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

    public static void main(String[] args) {
        // Warehouse initiation
        Warehouse warehouse = new Warehouse();
        loadLocationData(warehouse);

        // order initiation
        //TODO


    }
}
