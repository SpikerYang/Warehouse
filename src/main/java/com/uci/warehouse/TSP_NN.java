package com.uci.warehouse;

import java.util.*;
import java.math.*;

public class TSP_NN {
    int orderID;
    private Order order;

    private int[][][] graph;
    /*={ {0,10,15,20},
            {10,0,35,25},
            {15,35,0,30},
            {20,25,30,0}};;*/
    private int amountOfItem;

    private ArrayList<Integer> route =new ArrayList<>();

    private Set<Integer> unvisited = new HashSet<Integer>();


    public TSP_NN(int OrderID,int[][][] graph){
        orderID= OrderID;
        order = new Order(orderID);
        this.graph = graph;
        amountOfItem= graph.length-1;
       //route = new int[amountOfItem];
        for (int i = 0; i < amountOfItem; i++) {

            unvisited.add(i);
        }

    }


    public ArrayList<Integer> nearestNeigh(){

        //double[] temp = new double[amountOfItem];
        //String path="0";
        String path="0";
        route.add(0);
        int s=0;//distance
        int i=0;//current node
        int j;//next node
        //start point is node 0, end point is node size-1
        while(!unvisited.isEmpty()){

            unvisited.remove(i);
            j = selectmin(i);

            path+="-->" + j;
            route.add(j);
            s = s + graph[i][j][0]+graph[i][j][1];
            i = j;
        }
        System.out.println("route:" + path);
        System.out.println("distance:" + s);
        //System.out.println(route);
        return route;
    }
    public int selectmin(int i){
        double m=Double.MAX_VALUE;
        int n=0;
       if (unvisited.isEmpty()){
           return graph.length-1;
        }
       for(int u:unvisited){
           if(m>=graph[i][u][0]+graph[i][u][1]){
               m=graph[i][u][0]+graph[i][u][1];
               n=u;
           }
       }
       return n;
    }


//    public static void main (String[] args){
//        TSP_NN a = new TSP_NN(4);
//        a.nearestNeigh();
//    }

}