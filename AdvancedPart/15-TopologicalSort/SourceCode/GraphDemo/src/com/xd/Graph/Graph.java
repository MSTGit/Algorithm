package com.xd.Graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Graph<V,E> {
    int edgesSize();
    int verticesSize();
    void addVertex(V v);
    void addEdge(V from, V to, E weight);
    void addEdge(V from,V to);
    void removeVertex(V v);
    void removeEdge(V from, V to);

    List<V> toplogicalSort();
    void breadthFirstSearch(V begin,VertexVisitor visitor);
    void depthFirstSearch(V begin,VertexVisitor visitor);

    interface VertexVisitor<V> {
        boolean visit(V v);
    }
}