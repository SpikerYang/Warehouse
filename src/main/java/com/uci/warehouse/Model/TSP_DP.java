package main.java.com.uci.warehouse.Model;

import java.io.IOException;
import java.util.*;
import java.util.Map;

public class TSP_DP {
    private static int INFINITY = 100000000;

    private static class Index {
        int currentVertex;
        Set<Integer> vertexSet;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Index index = (Index) o;

            if (currentVertex != index.currentVertex) return false;
            return !(vertexSet != null ? !vertexSet.equals(index.vertexSet) : index.vertexSet != null);
        }

        @Override
        public int hashCode() {
            int result = currentVertex;
            result = 31 * result + (vertexSet != null ? vertexSet.hashCode() : 0);
            return result;
        }

        private static Index createIndex(int vertex, Set<Integer> vertexSet) {
            Index i = new Index();
            i.currentVertex = vertex;
            i.vertexSet = vertexSet;
            return i;
        }
    }

    private static class SetSizeComparator implements Comparator<Set<Integer>>{
        @Override
        public int compare(Set<Integer> o1, Set<Integer> o2) {
            return o1.size() - o2.size();
        }
    }

    public List<Integer> getRoute(int[][] distance) {
        long startTime = System.currentTimeMillis();
        long endTime;
        //stores intermediate values in map
        java.util.Map<Index, Integer> minCostDP = new HashMap<>();
        java.util.Map<Index, Integer> parent = new HashMap<>();

        //改成-2
        List<Set<Integer>> allSets = generateCombination(distance.length - 2);

        for(Set<Integer> set : allSets) {
            for(int currentVertex = 1; currentVertex < distance.length - 1; currentVertex++) {
                if(set.contains(currentVertex)) {
                    continue;
                }
                Index index = Index.createIndex(currentVertex, set);
                int minCost = INFINITY;
                int minPrevVertex = 0;
                //to avoid ConcurrentModificationException copy set into another set while iterating
                Set<Integer> copySet = new HashSet<>(set);
                for(int prevVertex : set) {
                    int cost = distance[prevVertex][currentVertex] + getCost(copySet, prevVertex, minCostDP);
                    if(cost < minCost) {
                        minCost = cost;
                        minPrevVertex = prevVertex;
                    }
                }
                //this happens for empty subset
                if(set.size() == 0) {
                    minCost = distance[0][currentVertex];
                }
                minCostDP.put(index, minCost);
                parent.put(index, minPrevVertex);
                ////////////////////////////////////
                endTime = System.currentTimeMillis();
                if ((endTime-startTime)>60000){
                    System.out.println("Time out!");
                    List<Integer> route = getTourWhenTimeOut(parent, currentVertex, set, distance.length);
                    return route;
                }
                ////////////////////////////////////
            }
        }

        Set<Integer> set = new HashSet<>();
        //改成length - 1
        for(int i=1; i < distance.length - 1; i++) {
            set.add(i);
        }
        int min = Integer.MAX_VALUE;
        int prevVertex = -1;
        //to avoid ConcurrentModificationException copy set into another set while iterating
        Set<Integer> copySet = new HashSet<>(set);
        for(int k : set) {
            //distance[k][0]改成distance[k][终点]
            int cost = distance[k][distance.length - 1] + getCost(copySet, k, minCostDP);
            if(cost < min) {
                min = cost;
                prevVertex = k;
            }
        }

        //createindex(0,set)改成create(终点，set) eg.(4,[1,2,3])
        parent.put(Index.createIndex(distance.length - 1, set), prevVertex);
        List<Integer> res;
        res = getTour(parent, distance.length);
        //      printTour(res);

        //不用变了，返回min
        System.out.println("distance:" + min);
        return res;
    }

    private void printTour(List<Integer> route) {
        StringJoiner joiner = new StringJoiner("-->");
        route.forEach(v -> joiner.add(String.valueOf(v)));
        System.out.print("route:");
        System.out.println(joiner.toString());
    }

    private List<Integer> getTourWhenTimeOut(java.util.Map<Index, Integer> parent, int currentVertex, Set<Integer> currentSet, int totalVertices) {
        //the route already generate
        Integer start = currentVertex;
        Deque<Integer> stack = new LinkedList<>();
        currentSet.add(start);
        while(true) {
            stack.push(start);
            currentSet.remove(start);
            start = parent.get(Index.createIndex(start, currentSet));
            if(start == null) {
                break;
            }
        }
        List<Integer> res = new ArrayList<>();
        stack.forEach(v -> res.add(v));

        //generate the rest of tour
//        Set<Integer> set = new HashSet<>();
//        res.forEach(v -> set.add(v));
//        for(int vertex = 0; vertex < totalVertices; vertex++) {
//            if(set.contains(vertex)) {
//                continue;
//            }
//            res.add(vertex);
//        }
        res.add(totalVertices - 1);
        return res;
    }

    private List<Integer> getTour(java.util.Map<Index, Integer> parent, int totalVertices) {
        Set<Integer> set = new HashSet<>();
        for(int i=1; i < totalVertices; i++) {
            set.add(i);
        }

        //start一开始应设置成终点， 如3个城市，设置成4
        Integer start = totalVertices - 1;
        Deque<Integer> stack = new LinkedList<>();
        while(true) {
            stack.push(start);
            set.remove(start);
            start = parent.get(Index.createIndex(start, set));
            if(start == null) {
                break;
            }
        }
        List<Integer> res = new ArrayList<>();
        stack.forEach(v -> res.add(v));
        //删掉这一行，不用赋值了
//        res.set(res.size() - 1, res.size() - 1);
        return res;
    }

    private int getCost(Set<Integer> set, int prevVertex, Map<Index, Integer> minCostDP) {
        set.remove(prevVertex);
        Index index = Index.createIndex(prevVertex, set);
        int cost = minCostDP.get(index);
        set.add(prevVertex);
        return cost;
    }

    private List<Set<Integer>> generateCombination(int n) {
        int input[] = new int[n];
        for(int i = 0; i < input.length; i++) {
            input[i] = i+1;
        }
        List<Set<Integer>> allSets = new ArrayList<>();
        int result[] = new int[input.length];
        generateCombination(input, 0, 0, allSets, result);
        Collections.sort(allSets, new SetSizeComparator());
        return allSets;
    }

    private void generateCombination(int input[], int start, int pos, List<Set<Integer>> allSets, int result[]) {
        if(pos == input.length) {
            return;
        }
        Set<Integer> set = createSet(result, pos);
        allSets.add(set);
        for(int i=start; i < input.length; i++) {
            result[pos] = input[i];
            generateCombination(input, i+1, pos+1, allSets, result);
        }
    }

    private static Set<Integer> createSet(int input[], int pos) {
        if(pos == 0) {
            return new HashSet<>();
        }
        Set<Integer> set = new HashSet<>();
        for(int i = 0; i < pos; i++) {
            set.add(input[i]);
        }
        return set;
    }
}