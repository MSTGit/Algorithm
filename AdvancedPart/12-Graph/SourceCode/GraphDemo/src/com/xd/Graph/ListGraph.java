package com.xd.Graph;

import java.util.*;

public class ListGraph<V,E> implements Graph<V,E> {

    private Map<V, Vertex<V,E>> vertices = new HashMap<>();
    private Set<Edge<V,E>> edges = new HashSet<>();
    public void print() {
        System.out.println("顶点-------------------------");
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("out-------------------");
            System.out.println(vertex.outEdges);
            System.out.println("in---------------------");
            System.out.println(vertex.inEdges);
        });
        System.out.println("边------------------------------");
        edges.forEach((Edge<V,E> edge) -> {
            System.out.println(edge);
        });
    }
    @Override
    public int edgesSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    @Override
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;
        vertices.put(v,new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        //判断 from to顶点是否存在
        Vertex fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex(from);
            vertices.put(from,fromVertex);
        }
        Vertex toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex(to);
            vertices.put(to,toVertex);
        }
        Edge edge = new Edge(fromVertex,toVertex);
        edge.weight = weight;
        if (fromVertex.outEdges.remove(edge)){
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from,to,null);
    }

    @Override
    public void removeVertex(V v) {
        Vertex<V,E> vertex = vertices.remove(v);
        if (vertex == null) return;
        //删除顶点相关联的边
//        vertex.outEdges.forEach((Edge<V,E> edge) -> {
//            removeEdge(edge.from.value,edge.to.value);
//        });
//
//        vertex.inEdges.forEach((Edge<V,E> edge) -> {
//            removeEdge(edge.from.value,edge.to.value);
//        });
        //使用迭代器进行删除
        for (Iterator<Edge<V,E>> iterator = vertex.outEdges.iterator(); iterator.hasNext() ;) {
            Edge<V,E> edge = iterator.next();
            edge.to.inEdges.remove(edge);
            iterator.remove();//将当前遍历到的元素edge从集合vertex.outEdges中删掉
            edges.remove(edge);
        }

        for (Iterator<Edge<V,E>> iterator = vertex.inEdges.iterator(); iterator.hasNext() ;) {
            Edge<V,E> edge = iterator.next();
            edge.from.outEdges.remove(edge);
            iterator.remove();//将当前遍历到的元素edge从集合vertex.outEdges中删掉
            edges.remove(edge);
        }
    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex fromVertex = vertices.get(from);
        if (fromVertex == null) return;
        Vertex toVertex = vertices.get(to);
        if (toVertex == null) return;

        Edge edge = new Edge(fromVertex,toVertex);
        if (fromVertex.outEdges.remove(edge)){
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }

    private static class Vertex<V,E> {
        V value;
        Set<Edge<V,E>> inEdges = new HashSet<>();
        Set<Edge<V,E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value,((Vertex<V,E>)obj).value);
        }

        @Override
        public int hashCode() {
            return value == null ? 0 : value .hashCode();
        }

        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }
    }

    private static class Edge<V,E> {
        Vertex<V,E> from;
        Vertex<V,E> to;
        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            Edge<V,E> edge = (Edge<V,E>)obj;
            return Objects.equals(from,edge.from) && Objects.equals(to,edge.to);
        }

        @Override
        public int hashCode() {
            int fromCode = from .hashCode();
            int toCode = to.hashCode();
            return fromCode * 31 + toCode;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
