package com.xd;

import com.xd.Graph.Graph;
import com.xd.Graph.ListGraph;

public class GraphDemo {
    public static void main(String[] args) {
        System.out.println("GraphDemo.main");

        test();
    }

    static  void test() {
        ListGraph<String , Integer> graph = new ListGraph<>();
        graph.addEdge("V1","V0",9);
        graph.addEdge("V1","V2",3);
        graph.addEdge("V2","V0",2);
        graph.addEdge("V2","V3",5);
        graph.addEdge("V3","V4",1);
        graph.addEdge("V0","V4",6);

        //graph.removeEdge("V0","V4");
        graph.removeVertex("V0");
        graph.print();
    }
}
