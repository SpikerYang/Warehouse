package main.java.com.uci.warehouse.Model;

import java.util.*;
import java.math.*;

public class TSP_NN {
    int orderID;
    private Order order;

    private int[][] graph;
    /*={ {0,10,15,20},
            {10,0,35,25},
            {15,35,0,30},
            {20,25,30,0}};;*/
    private int amountOfItem;

    private ArrayList<Integer> minroute =new ArrayList<>();

    private Set<Integer> unvisited = new HashSet<Integer>();


    public TSP_NN(int OrderID,int[][] graph){
        orderID= OrderID;
        order = new Order(orderID);
        this.graph = graph;
        //amountOfItem= graph.length-1;
       //route = new int[amountOfItem];


    }


    public ArrayList<Integer> nearestNeigh(){

        int distance=Integer.MAX_VALUE;
        minroute = new ArrayList<>();
        for(int k=0; k<graph.length; k++) {
            for (int i = 0; i < graph.length; i++) {

                unvisited.add(i);
            }

            //double[] temp = new double[amountOfItem];
            //String path="0";
            String path = "" + k;
            ArrayList<Integer> route = new ArrayList<>();
            route.add(k);
            int s = -graph[0][graph.length - 1];//distance
            int i = k;//current node
            int j;//next node
            //start point is node 0, end point is node size-1
            while (!unvisited.isEmpty()) {

                unvisited.remove(i);
                if (unvisited.isEmpty()){
                    break;
                }
                if (i == 0&&unvisited.contains(graph.length - 1)) {
                    j = graph.length - 1;
                } else if (i == graph.length - 1&&unvisited.contains(0)) {
                    j = 0;
                } else {
                    j = selectmin(i);
                }
                path += "-->" + j;
                route.add(j);
                s = s + graph[i][j];
                i = j;
            }
            path += "-->" + k;
            //route.add(k);

            s+=graph[i][k];
            System.out.println(s+ "route:" + path);
            if(s<distance){
                distance=s;
                minroute=route;
            }

        }
        //System.out.println("route:" + path);
        System.out.println("distance:" + distance);
        //System.out.println(route);
        for(int g:minroute){
            System.out.print(g+" ");
        }
        System.out.println();
        return formatRoute(minroute);
    }
    public int selectmin(int i){
        double m=Double.MAX_VALUE;
        int n=0;

       for(int u:unvisited){
           if(m>=graph[i][u]){
               m=graph[i][u];
               n=u;
           }
       }
       return n;
    }

    private ArrayList<Integer> formatRoute(ArrayList<Integer> route){
        int a;
        for(int i=0; i<route.size(); i++){
            if(route.get(i)==0){
                if(minroute.get(next(i))==graph.length-1){
                    a=-1;
                }else a=1;

                return formatRoute(a,i);
            }
        }
        return null;
    }

    private ArrayList<Integer> formatRoute(int a,int i){
        ArrayList<Integer> route = new ArrayList<>();
        route.add(minroute.get(i));
        for(int j=1;j<graph.length;j++){
            if(a==-1){
                i=previous(i);
                route.add(minroute.get(i));
            }else{
                i=next(i);
                route.add(minroute.get(i));
            }


        }
        for(int g:route){
            System.out.print(g+" ");
        }
        return route;
    }
    private int previous(int i){
        if(i!=0) return i - 1;
        else return minroute.size() - 1;

    }
    private int next(int i){
        if(i!=minroute.size() - 1) return i + 1;
        else return 0;

    }



//    public static void main (String[] args){
//        TSP_NN a = new TSP_NN(4);
//        a.nearestNeigh();
//    }

}