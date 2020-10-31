package com.uci.warehouse;

import java.util.*;
import java.math.*;

public class TSP_NN {
    int orderID;
    private Order order;

    private double[][] graph;
    /*={ {0,10,15,20},
            {10,0,35,25},
            {15,35,0,30},
            {20,25,30,0}};;*/
    private int amountOfItem;

    private ArrayList<Integer> route =new ArrayList<>();

    private Set<Integer> unvisited = new HashSet<Integer>();


    public TSP_NN(int OrderID,double[][] graph){
        orderID= OrderID;
        order = new Order(orderID);
        this.graph = graph;
        amountOfItem= graph.length;
       //route = new int[amountOfItem];
        for (int i = 0; i < amountOfItem; i++) {

            unvisited.add(i);
        }

    }


    public ArrayList<Integer> nearestNeigh(double[][] graph){

        //double[] temp = new double[amountOfItem];
        //String path="0";
        String path="0";
        route.add(0);
        double s=0;//distance
        int i=0;//current node
        int j;//next node
        //default start point is node 0
        while(!unvisited.isEmpty()){

            unvisited.remove(i);
            j = selectmin(i);

            path+="-->" + j;
            route.add(j);
            s = s + graph[i][j];
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
           return 0;
        }
       for(int u:unvisited){
           if(m>=graph[i][u]){
               m=graph[i][u];
               n=u;
           }
       }
       return n;
    }


    public static void main (String[] args){
        //TSP_NN a = new TSP_NN(4);
        //a.nearestNeigh();
    }

}