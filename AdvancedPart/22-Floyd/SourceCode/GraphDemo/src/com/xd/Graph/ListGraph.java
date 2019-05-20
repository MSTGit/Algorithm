package com.xd.Graph;

import com.xd.Heap.MinHeap;
import com.xd.UnionFind;

import java.util.*;

public class ListGraph<V,E> extends Graph<V,E> {

    private Map<V, Vertex<V,E>> vertices = new HashMap<>();
    private Comparator<Edge<V,E>> edgeComparator = (Edge<V,E> e1, Edge<V,E> e2) ->{

        return weightManager.compare(e1.weight,e2.weight);
    };
    private Set<Edge<V,E>> edges = new HashSet<>();
    public ListGraph() {
        super(null);
    }
    public ListGraph(WeightManager<E> weightManager) {
        super(weightManager);
    }



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


    @Override
    public List<V> toplogicalSort() {
        List<V> list = new ArrayList<>();
        Queue<Vertex<V,E>> queue = new LinkedList<>();
        Map<Vertex<V,E>,Integer> map = new HashMap<>();
        //将度为0的节点，都放入队列中
        vertices.forEach((V v,Vertex<V,E> vertex)->{
            if (vertex.inEdges.size() == 0) {
                queue.offer(vertex);
            } else {
                map.put(vertex,vertex.inEdges.size());
            }
        });
        while (!queue.isEmpty()) {
            Vertex<V,E> vertex = queue.poll();
            list.add(vertex.value);//将顶点的值，放入返回结果中
            for (Edge<V,E> edge: vertex.outEdges ) {
                int toIn = map.get(edge.to) - 1;
                if (toIn == 0) {
                    queue.offer(edge.to);
                } else {
                    map.put(edge.to,toIn);
                }
            }
        }
        return list;
    }

    @Override
    public void breadthFirstSearch(V begin, VertexVisitor visitor) {
        if (visitor == null) return;
        Vertex beginVertex = vertices.get(begin);
        Set<Vertex<V,E>> visitedVertices = new HashSet<>();
        if (beginVertex == null) return;
        Queue<Vertex<V,E>> queue = new LinkedList<ListGraph.Vertex<V,E>>();
        queue.offer(beginVertex);
        visitedVertices.add(beginVertex);
        while (!queue.isEmpty()) {
            Vertex<V,E> vertex = queue.poll();
            if (visitor.visit(vertex.value)) return;
            for (Edge<V,E> edge: vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) continue;
                queue.offer(edge.to);
                visitedVertices.add(edge.to);
            }
        }
    }

    @Override
    public void depthFirstSearch(V begin, VertexVisitor visitor) {
        if (visitor == null) return;
        Vertex beginVertex = vertices.get(begin);
        if (beginVertex == null) return;
        Set<Vertex<V,E>> visitedVertices = new HashSet<>();
        Stack<Vertex<V,E>> stack = new Stack<>();
        //先访问起点
        stack.push(beginVertex);
        visitedVertices.add(beginVertex);
        if (visitor.visit(beginVertex.value)) return;
        while (!stack.isEmpty()) {
            Vertex<V,E> vertex = stack.pop();
            for (Edge<V,E> edge: vertex.outEdges ) {
                if (visitedVertices.contains(edge.to)) continue;
                stack.push(edge.from);
                stack.push(edge.to);
                visitedVertices.add(edge.to);
                if (visitor.visit(edge.to.value)) return;
                break;
            }
        }
    }

    @Override
    public Set<EdgeInfo<V, E>> minimumSpanningTree() {
        return kruskal();
    }

    @Override
    public Map<V, PathInfo<V, E>> shortestPath(V begin) {
        return dijkstra(begin);
    }

    @Override
    public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
        Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();
        //初始化,将最开始能走的边都初始化paths中
        for (Edge<V,E> edge : edges) {
            Map<V, PathInfo<V, E>> map = paths.get(edge.from.value);
            if (map == null) {
                map = new HashMap<>();
                paths.put(edge.from.value,map);
            }
            PathInfo<V,E> pathInfo = new PathInfo<>(edge.weight);
            pathInfo.edgeInfos.add(edge.info());
            map.put(edge.to.value,pathInfo);
        }
        vertices.forEach((V v2,Vertex<V,E> vertex2) -> {
            vertices.forEach((V v1,Vertex<V,E> vertex1) -> {
                vertices.forEach((V v3,Vertex<V,E> vertex3) -> {
                    if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;
                    //v1 -> v2
                    PathInfo<V,E> path12 = getPathInfo(v1,v2,paths);
                    if (path12 == null) return;
                    //v2 -> v3
                    PathInfo<V,E> path23 = getPathInfo(v2,v3,paths);
                    if (path23 == null) return;
                    //v1 -> v3
                    PathInfo<V,E> path13 = getPathInfo(v1,v3,paths);
                    E newWeight = weightManager.add(path12.weight,path23.weight);
                    if (path13 != null && weightManager.compare(newWeight,path13.weight) >= 0) return;
                    if (path13 == null) {
                        path13 = new PathInfo<V,E>();
                        paths.get(v1).put(v3,path13);
                    } else {

                        path13.edgeInfos .clear();
                    }
                    path13.weight = newWeight;
                    path13.edgeInfos.addAll(path12.edgeInfos);
                    path13.edgeInfos.addAll(path23.edgeInfos);
                });
            });
        });
        return paths;
    }

    private PathInfo<V,E> getPathInfo(V from,V to,Map<V, Map<V, PathInfo<V, E>>> paths) {
        Map<V, PathInfo<V, E>> map = paths.get(from);
        return map == null ? null : map.get(to);
    }

    public Map<V, PathInfo<V, E>> bellmenFord(V begin) {
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;
        Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
        selectedPaths.put(begin,new PathInfo<>(weightManager.zero()));
        int count = vertices.size() - 1;
        for (int i = 0; i < count; i++) { //对每一条边进行V - 1次松弛操作
            for (Edge<V,E> edge : edges) {

                PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
                if (fromPath == null) continue;
                relax(edge,selectedPaths,fromPath);
            }
        }
        for (Edge<V,E> edge : edges) {

            PathInfo<V, E> fromPath = selectedPaths.get(edge.from.value);
            if (fromPath == null) continue;
            if (relax(edge,selectedPaths,fromPath)){
                System.out.println("有负权环");
                return null;
            }
        }

        selectedPaths.remove(begin);
        return selectedPaths;
    }
    public Map<V, PathInfo<V, E>> dijkstra(V begin) {
        Vertex<V,E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return null;
        Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
        Map<Vertex<V,E>, PathInfo<V, E>> paths = new HashMap<>();
        //初始化paths
//        for (Edge<V,E> edge: beginVertex.outEdges) {
//            PathInfo<V,E> path = new PathInfo<V,E>();
//            path.setWeight(edge.weight);
//            path.edgeInfos.add(edge.info());
//            paths.put(edge.to,path);
//        }
        paths.put(beginVertex,new PathInfo<>(weightManager.zero()));
        while (!paths.isEmpty()) {
            Map.Entry<Vertex<V,E>, PathInfo<V,E>> minEntry = getShortestPath(paths);
            //minEntry离开桌面
            Vertex<V,E> minVertex = minEntry.getKey();
            PathInfo<V,E> minPath = minEntry.getValue();
            selectedPaths.put(minVertex.value,minPath);
            paths.remove(minVertex);
            //对minVertex的outEdges进行松弛操作
            for (Edge<V,E> edge : minVertex.outEdges) {
                //如果edge.to已经离开桌面，就没必要进行松弛操作
                if (selectedPaths.containsKey(edge.to.value)) continue;;
                relaxDijkstra(edge,paths,minPath);
            }
        }
        //如果是无向图，前面会将起点也添加进去，所以最后将起点删除掉
        selectedPaths.remove(begin);
        return selectedPaths;
    }

    /*
    * 从paths中选出一个最短的路径出来
    * */
    private Map.Entry<Vertex<V,E>, PathInfo<V,E>> getShortestPath(Map<Vertex<V,E>, PathInfo<V,E>> paths) {
        Iterator<Map.Entry<Vertex<V,E>, PathInfo<V,E>>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V,E>, PathInfo<V,E>> minEntry = it.next();
        while (it.hasNext()) {
            Map.Entry<Vertex<V,E>, PathInfo<V,E>> entry = it.next();
            if (weightManager.compare(entry.getValue().weight,minEntry.getValue().weight) < 0) {
                minEntry = entry;
            }
        }
        return minEntry;
    }

    private boolean relax(Edge<V,E> edge, Map<V ,PathInfo<V,E>> paths, PathInfo<V,E> fromPath) {
        //新的可选的最短路径：beginVertex到edge.from的最短路径 + edge.weight
        //minEntry.getValue() + edge.weight;
        E newWeight = weightManager.add(fromPath.weight,edge.weight);
        //以前的最短路径：beginVertex到edge.to的最短路径
        PathInfo<V,E> oldPath = paths.get(edge.to.value);
        //如果新的路径大于等于原来路径，就不用做任何操作
        if (oldPath != null && weightManager.compare(newWeight,oldPath.weight) >= 0) return false;
        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to.value,oldPath);
        }else {
            oldPath.edgeInfos.clear();
        }
        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
        return true;
    }
    private void relaxDijkstra(Edge<V,E> edge, Map<Vertex<V,E> ,PathInfo<V,E>> paths, PathInfo<V,E> fromPath) {
        //新的可选的最短路径：beginVertex到edge.from的最短路径 + edge.weight
        //minEntry.getValue() + edge.weight;
        E newWeight = weightManager.add(fromPath.weight,edge.weight);
        //以前的最短路径：beginVertex到edge.to的最短路径
        PathInfo<V,E> oldPath = paths.get(edge.to);
        //如果新的路径大于等于原来路径，就不用做任何操作
        if (oldPath != null && weightManager.compare(newWeight,oldPath.weight) >= 0) return;
        if (oldPath == null) {
            oldPath = new PathInfo<>();
            paths.put(edge.to,oldPath);
        }else {
            oldPath.edgeInfos.clear();
        }
        oldPath.weight = newWeight;
        oldPath.edgeInfos.addAll(fromPath.edgeInfos);
        oldPath.edgeInfos.add(edge.info());
        paths.put(edge.to,oldPath);
    }

    private Set<EdgeInfo<V, E>> prim() {
        Iterator<Vertex<V,E>> it = vertices.values().iterator();
        if (!it.hasNext()) return null;
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        Set<Vertex<V,E>> addedVertices = new HashSet<>();
        Vertex<V,E> vertex = it.next();
        addedVertices.add(vertex);
        MinHeap<Edge<V,E>> heap = new MinHeap<>(vertex.outEdges,edgeComparator);
        int edgeSize = vertices.size() - 1;
        while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V,E> edge = heap.remove();
            if (addedVertices.contains(edge.to)) continue;
            edgeInfos.add(edge.info());
            addedVertices.add(edge.to);
            heap.addAll(edge.to.outEdges);
        }
        return edgeInfos;
    }

    private Set<EdgeInfo<V, E>> kruskal() {
        int edgeSize = vertices.size() - 1;
        if (edgeSize == -1) return null;
        MinHeap<Edge<V,E>> heap = new MinHeap<>(edges,edgeComparator);
        Set<EdgeInfo<V, E>> edgeInfos = new HashSet<>();
        UnionFind<Vertex<V,E>> uf = new UnionFind<>();
        vertices.forEach((V v , Vertex<V,E> vertex) ->{
            uf.makeSet(vertex);
        });
        while (!heap.isEmpty() && edgeInfos.size() < edgeSize) {
            Edge<V,E> edge = heap.remove();
            if (uf.isSame(edge.from,edge.to)) continue;
            edgeInfos.add(edge.info());
            uf.union(edge.from,edge.to);
        }
        return edgeInfos;
    }

    public void breadthFirstSearch(V begin) {
        Vertex beginVertex = vertices.get(begin);
        Set<Vertex<V,E>> visitedVertices = new HashSet<>();
        if (beginVertex == null) return;
        Queue<Vertex<V,E>> queue = new LinkedList<ListGraph.Vertex<V,E>>();
        queue.offer(beginVertex);
        visitedVertices.add(beginVertex);
        while (!queue.isEmpty()) {
            Vertex<V,E> vertex = queue.poll();
            System.out.println(vertex.value);
            for (Edge<V,E> edge: vertex.outEdges) {
                if (visitedVertices.contains(edge.to)) continue;
                queue.offer(edge.to);
                visitedVertices.add(edge.to);
            }
        }
    }

    public void depthFirstSearch(V begin) {
        Vertex beginVertex = vertices.get(begin);
        if (beginVertex == null) return;
        Set<Vertex<V,E>> visitedVertices = new HashSet<>();
        Stack<Vertex<V,E>> stack = new Stack<>();
        //先访问起点
        stack.push(beginVertex);
        visitedVertices.add(beginVertex);
        System.out.println(beginVertex.value);
        while (!stack.isEmpty()) {
            Vertex<V,E> vertex = stack.pop();
            for (Edge<V,E> edge: vertex.outEdges ) {
                if (visitedVertices.contains(edge.to)) continue;
                stack.push(edge.from);
                stack.push(edge.to);
                visitedVertices.add(edge.to);
                System.out.println(edge.to.value);
                break;
            }
        }
    }
//    @Override
//    public void depthFirstSearch(V begin) {
//        Vertex beginVertex = vertices.get(begin);
//        if (beginVertex == null) return;
//        depthFirstSearch(beginVertex,new HashSet<>());
//    }
//    private void depthFirstSearch(Vertex<V, E> vertex,Set<Vertex<V,E>> visitedVertices) {
//        System.out.println(vertex.value);
//        visitedVertices.add(vertex);
//        for (Edge<V, E> edge: vertex.outEdges ) {
//            if (visitedVertices.contains(edge.to)) continue;
//            depthFirstSearch(edge.to,visitedVertices);
//        }
//    }
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

        EdgeInfo<V,E> info() {
            return new EdgeInfo<>(from.value,to.value,weight);
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
