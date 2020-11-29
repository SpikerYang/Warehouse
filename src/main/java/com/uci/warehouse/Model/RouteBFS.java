package main.java.com.uci.warehouse.Model;



//import javax.xml.soap.Node;




import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map;

public class RouteBFS {

    private Order order;
    private int orderID;
    private int distance;
    private List<int[]> route;
    private List<int[]> reversRoute;
    private Map<int[],int[]> nodes=new HashMap<>();
    private Set<ArrayList<Integer>> shelves;


    public RouteBFS(Order order){
        this.order=order;
    }

    public boolean isShelf(ArrayList<Integer> location){

        return shelves.contains(location);
    }
    public boolean isShelf(ArrayList<Integer> location,boolean isGoUp)  {
        //getshelf();
        ArrayList<Integer> shelf = new ArrayList<>();
        shelf.add(location.get(0));
        shelf.add(location.get(1)+(isGoUp?1:-1));
        return shelves.contains(shelf);
    }


    public itemNode BFS(ArrayList<Integer> node1,ArrayList<Integer> node2)  {

        //route.add(node1);
        int dyy=node2.get(1)-node1.get(1);
        boolean isGoUp=dyy>0;
//        if(dyy==0){
//            distance=Math.abs(node1[0]-node2[0]);
//            route.add(node2);
//            return;
//        }
        Map<ArrayList<Integer>, itemNode>  thislayer= new HashMap<>();
        Map<ArrayList<Integer>, itemNode> nextlayer= new HashMap<>();
        itemNode end=new itemNode(node2,Integer.MAX_VALUE,null);
        //boolean isinbetween= isInBetween(node2);

        nextlayer.put(node1,new itemNode(node1,0,null));
        for(int i= 0; i <=Math.abs(dyy);i++){
            thislayer.clear();
            thislayer.putAll(nextlayer);
            nextlayer.clear();
            itemNode next;

            for(itemNode node:thislayer.values()){
                //thislayer.remove(node.location);

                //node2 is in between
                if(node2.get(1)%2==0 && i==Math.abs(dyy)-1){
                    int finalDistance = Math.abs(node2.get(0)-node.location.get(0))+1;
                    if(node.distance+finalDistance<end.distance){
                        end.distance=node.distance+finalDistance;
                        end.previous=node;
                    }
                    continue;
                }


                if(i==Math.abs(dyy)){

                    int finalDistance = Math.abs(node2.get(0)-node.location.get(0));
                    if(node.distance+finalDistance<end.distance){
                        end.distance=node.distance+finalDistance;
                        end.previous=node;
                    }
                    continue;
                }

                if(!isShelf(node.location,isGoUp)){
                    next=findVertical(node,isGoUp);
                    nextlayer.put(next.location,next);
                    continue;
                }
                next=findLeft(node,isGoUp);
                //TODO
                //compare distance
                if (nextlayer.containsKey(next.location)){
                    if(nextlayer.get(next.location).distance>next.distance){
                        nextlayer.put(next.location,next);
                    }
                }else{
                    nextlayer.put(next.location,next);
                }
                next=findRight(node,isGoUp);
                if (nextlayer.containsKey(next.location)){
                    if(nextlayer.get(next.location).distance>next.distance){
                        nextlayer.put(next.location,next);
                    }
                }else{
                    nextlayer.put(next.location,next);
                }
            }
        }
        getRoute(end);
        return end;


    }
    private void getRoute(itemNode end){
        route =new ArrayList<>();
        reversRoute =new ArrayList<>();
//        route.add(new int[]{end.location.get(0),end.location.get(0)});
//        reversRoute.add(new int[]{end.location.get(0),end.location.get(0)});
        itemNode n=end;
        while(n!=null){
            reversRoute.add(new int[]{n.location.get(0),n.location.get(1)});
            route.add(0,new int[]{n.location.get(0),n.location.get(1)});
            n=n.previous;
        }

    }

    private boolean isInBetween(itemNode node) {
        ArrayList<Integer> location = new ArrayList<>();
        location.addAll(node.location);
        while (location.get(0)>0) {
            if(isShelf(location))return true ;
            location.set(0, location.get(0) - 1);
        }
        while (location.get(0)<40) {
            if(isShelf(location))return true ;
            location.set(0, location.get(0) + 1);
        }
        return false;
    }

    private itemNode findLeft(itemNode node, boolean isGoUp) {
        int xx=0;
        ArrayList<Integer> location=new ArrayList<>();
        location.addAll(node.location);
        while(isShelf(location,isGoUp)){

            location.set(0,location.get(0)-1);
            xx--;
            if(location.get(0)<0){
                return null;
            }
        }
        itemNode v=new itemNode(node,xx,0);
        return new itemNode(v,0,isGoUp?1:-1);
        //return new itemNode(node,xx,0);

    }
    private itemNode findRight(itemNode node, boolean isGoUp)  {
        int xx=0;
        ArrayList<Integer> location=new ArrayList<>();
        location.addAll(node.location);
        while(isShelf(location,isGoUp)){
            location.set(0,location.get(0)+1);
            xx++;
            if(location.get(0)>40){
                return null;
            }
        }
        itemNode v=new itemNode(node,xx,0);
        return new itemNode(v,0,isGoUp?1:-1);
        //return new itemNode(node,xx,0);
    }
    private itemNode findVertical(itemNode node, boolean isGoUp){
        ArrayList<Integer> location=new ArrayList<>();
        location.addAll(node.location);
        if(isGoUp){
            location.set(1,location.get(0)+1);
        }else{
            location.set(1,location.get(0)-1);
        }

        return new itemNode(node,0,isGoUp?1:-1);
    }

    public void getshelf(Map<Integer, double[]> productLocationMap){
        shelves = new HashSet<>();
        for(double[] item:productLocationMap.values()){
            ArrayList<Integer> shelf = new ArrayList<>();
            shelf.add((int)item[0]);
            shelf.add((int)item[1]);
            shelves.add(shelf);
        }
    }

    public static Pair<ArrayList<int[]>,Integer>[][] routeDistanceMatrix(Order order, Map<Integer, double[]> map, int[] start, int[] end)  {
        RouteBFS routbfs =new RouteBFS(order);
        routbfs.getshelf(map);
        List<Integer> list = new ArrayList<>(order.getProducts().keySet());
        list.add(0, -1);
        list.add(list.size(),-2);
        map.put(-1, new double[]{start[0],start[1]-1});//start and end are true location. -1 because will be +1 in the for loop
        map.put(-2, new double[]{end[0],end[1]-1});
        Pair[][] m = new Pair[list.size()][list.size()];
        //Pair<ArrayList<Integer>,Integer> entry;
        for (int i = 0; i <list.size(); i++){
            m[i][i]=new Pair(null, 0);
            for(int j = i+1; j < list.size(); j++){

                double[] location =map.get(list.get(i));
                ArrayList<Integer> loc1= new ArrayList<>();
                loc1.add((int)location[0]);
                loc1.add((int)location[1]+1);//get item from north
                location=map.get(list.get(j));
                ArrayList<Integer> loc2= new ArrayList<>();
                loc2.add((int)location[0]);
                loc2.add((int)location[1]+1);//get item from north
                itemNode n=routbfs.BFS(loc1,loc2);
                m[i][j]=new Pair(routbfs.route,n.distance);
                m[j][i]=new Pair(routbfs.reversRoute,n.distance);
            }
        }
        return m;
    }

    public class itemNode {
        public ArrayList<Integer> location;
        public int distance;
        public itemNode previous;

        public itemNode(ArrayList<Integer> location, int distance,itemNode previous){
            this.location = location;
            this.distance = distance;
            this.previous = previous;
        }
        public itemNode(itemNode preNode,int xx,int yy){
            this.location= new ArrayList<>();
            this.location.add(preNode.location.get(0)+xx);
            this.location.add(preNode.location.get(1)+yy);

            this.distance=preNode.distance+Math.abs(xx)+Math.abs(yy);
            this.previous=preNode;
        }




    }

}
