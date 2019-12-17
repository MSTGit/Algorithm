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

    void breadthFirstSearch(V begin);
    void depthFirstSearch(V begin);
}
