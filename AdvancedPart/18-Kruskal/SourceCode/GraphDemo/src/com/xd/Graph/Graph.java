package com.xd.Graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Graph<V,E> {
    public Graph(WeightManager<E> weightManager) {
        this.weightManager = weightManager;
    }
    protected WeightManager<E> weightManager;
    public abstract int edgesSize();
    public abstract int verticesSize();
    public abstract void addVertex(V v);
    public abstract void addEdge(V from, V to, E weight);
    public abstract void addEdge(V from,V to);
    public abstract void removeVertex(V v);
    public abstract void removeEdge(V from, V to);

    public abstract List<V> toplogicalSort();
    public abstract void breadthFirstSearch(V begin,VertexVisitor visitor);
    public abstract void depthFirstSearch(V begin,VertexVisitor visitor);

    /*
    * 获取最小生成树的集合
    * */
    public abstract Set<EdgeInfo<V,E>> minimumSpanningTree();

    public interface WeightManager<E> {
        int compare(E w1,E w2);
        E add(E w1,E w2);
    }

    public interface VertexVisitor<V> {
        boolean visit(V v);
    }

    public static class EdgeInfo<V,E> {
        private V form;
        private V to;
        private E weight;

        public EdgeInfo(V form, V to, E weight) {
            this.form = form;
            this.to = to;
            this.weight = weight;
        }

        public V getForm() {
            return form;
        }

        public void setForm(V form) {
            this.form = form;
        }

        public V getTo() {
            return to;
        }

        public void setTo(V to) {
            this.to = to;
        }

        public E getWeight() {
            return weight;
        }

        public void setWeight(E weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EdgeInfo{" +
                    "form=" + form +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
