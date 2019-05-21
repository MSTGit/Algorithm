package com.xd;

import com.xd.Graph.Graph;
import com.xd.Graph.ListGraph;

import java.util.Map;
import java.util.Set;

public class GraphDemo {
    static Graph.WeightManager<Double> weightManager = new Graph.WeightManager<Double>() {
        @Override
        public int compare(Double w1, Double w2) {
            return w1.compareTo(w2);
        }

        @Override
        public Double add(Double w1, Double w2) {
            return w1 + w2;
        }

        @Override
        public Double zero() {
            return 0.0;
        }
    };
    public static void main(String[] args) {
        System.out.println("GraphDemo.main");

        //test();
//        testBFS();
        //testDFS();
//        testTopo();
        //testMinimumSpanningTree();
//        testShortesPath();
        testMultiShortesPath();
    }

    static void testMultiShortesPath(){
        Map<Object,Map<Object, Graph.PathInfo<Object,Double>>> sp = directedGraph(Data.NEGATIVE_WEIGHT1).shortestPath();
        sp.forEach((Object from ,Map<Object, Graph.PathInfo<Object,Double>> paths) -> {
            System.out.println(from + "--------------------------");
            paths.forEach((Object to, Graph.PathInfo<Object,Double> path) -> {
                System.out.println(to + " - " + path);
            });
        });
    }
    static void testShortesPath(){
        Map<Object, Graph.PathInfo<Object,Double>> sp = directedGraph(Data.SP).shortestPath("A");
        if (sp == null) return;
        sp.forEach((Object v, Graph.PathInfo<Object,Double> path) -> {
            System.out.println(v + " - " + path);
        });
    }

    static void testMinimumSpanningTree() {
        Set<Graph.EdgeInfo<Object,Double>> infos = undirectedGraph(Data.MST_01).minimumSpanningTree();
        for (Graph.EdgeInfo<Object,Double> info: infos
             ) {
            System.out.println(info);
        }
    }

    static void testTopo() {
        System.out.println(directedGraph(Data.TOPO).toplogicalSort());
    }

    static void testBFS() {
        //undirectedGraph(Data.BFS_01).breadthFirstSearch("A");
        directedGraph(Data.BFS_02).breadthFirstSearch(0,(Object value)-> {
            System.out.println(value);
            return false;
        });
    }

    static void testDFS(){
//        undirectedGraph(Data.DFS_01).depthFirstSearch(1);
        directedGraph(Data.DFS_02).depthFirstSearch("c",(Object value)-> {
            System.out.println(value);
            return false;
        });
    }


    static  void test() {
//        ListGraph<String , Integer> graph = new ListGraph<>();
//        graph.addEdge("V1","V0",9);
//        graph.addEdge("V1","V2",3);
//        graph.addEdge("V2","V0",2);
//        graph.addEdge("V2","V3",5);
//        graph.addEdge("V3","V4",1);
//        graph.addEdge("V0","V4",6);
//        graph.breadthFirstSearch("V1");
        //graph.removeEdge("V0","V4");
//        graph.removeVertex("V0");
//        graph.print();
    }

    /**
     * 有向图
     */
    private static Graph<Object, Double> directedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
            }
        }
        return graph;
    }

    /**
     * 无向图
     * @param data
     * @return
     */
    private static Graph<Object, Double> undirectedGraph(Object[][] data) {
        Graph<Object, Double> graph = new ListGraph<>(weightManager);
        for (Object[] edge : data) {
            if (edge.length == 1) {
                graph.addVertex(edge[0]);
            } else if (edge.length == 2) {
                graph.addEdge(edge[0], edge[1]);
                graph.addEdge(edge[1], edge[0]);
            } else if (edge.length == 3) {
                double weight = Double.parseDouble(edge[2].toString());
                graph.addEdge(edge[0], edge[1], weight);
                graph.addEdge(edge[1], edge[0], weight);
            }
        }
        return graph;
    }
}
