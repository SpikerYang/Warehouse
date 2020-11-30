package main.java.com.uci.warehouse.Model;

import java.util.ArrayList;
import java.util.List;

public class TSP_BF {

    private int[][] distanceMatrix;

    private int minDistance = Integer.MAX_VALUE;

    private List<Integer> optimal_route;

    public TSP_BF(int[][] distanceMatrix){
        this.distanceMatrix=distanceMatrix;
    }

    public int getMinDistance(int[][] distance){
        int n=distance.length;
        boolean[] seen = new boolean[n];
        seen[0]= true;
        List<Integer> tempRoute = new ArrayList<>();
        //tempRoute.add(0);
        dfs(distance,0,1,0,seen,tempRoute);
        return minDistance;
    }

    private void dfs(int[][] distance, int curSum, int numOfPoints, int curPoint, boolean[]seen,List<Integer> ls){
        if(numOfPoints==distance.length){
            int totalSum= curSum+distance[curPoint][0];
            if(totalSum<minDistance) {
                minDistance = totalSum;
                optimal_route =new ArrayList<>(ls);
            }
            return;
        }
        for(int i=0;i<distance[curPoint].length;++i){
            if(seen[i] || i==curPoint)
                continue;
            seen[i]=true;
            ls.add(i);
            dfs(distance,curSum+distance[curPoint][i],numOfPoints+1,i,seen,ls);
            seen[i]=false;
            ls.remove(ls.size()-1);
        }

    }

    private String printRoute(List<Integer> route){
        String ret= "0";
        for(int i:route){
            ret+="->"+i;
        }
        ret+="->0";
        return ret;
    }
    
}

