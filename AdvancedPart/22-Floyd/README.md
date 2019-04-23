结合前面的Dijkstra算法或者Bellman-Ford算法，其实也可以计算出任意两个顶点之间的最短路径。例如下图

![1576852593139](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/22-Floyd/Resource/1576852593139.png)

以Dijkstra为例：

如果要计算出任意两个顶点之间的最短路径，其实可以遍历所有的顶点，对每一个顶点做一次Dijkstra操作，其实也可以计算出任意两个顶点之间的最短路径。

是的，上面这种做法，是计算任意两个顶点之间最短路径的一种做法，不过现在有比这种做法更好的另外一种做法，那就是使用Floyd来实现。

#### Floyd

Floyd属于多源最短路径算法，能够求出任意两个顶点之间的最短路径，支持负权边。

Floyd的时间复杂度：O(V^3)，效率比执行V次Dijkstra算法要好（V是顶点数量）

##### 算法原理

- 从任意顶点i到任意顶点j的最短路径不外乎两种可能
  1. 直接从i到j
  2. 从i经过若干个顶点到j
- 现假设有一种状态dist(i,j)，为顶点i到顶点j的最短路径距离
  - 对于每一个顶点k，都要执行dist(i,k) + dist(k,j) < dist(i,j)是否成立
    - 如果成立，证明从i到k再到j的路径比i直接到j的路径短，就更新dist(i,j) = dist(i,k) + dist(k,j)
    - 当遍历完所有的顶点k，dist(i,j) 中记录的边是i到j的最短路径距离

所以，结合上面的算法原理，可以分析得到Floyd算法的伪代码

```java
public void floyd(V v) {
    for (int k = 0; k < v; k++) {
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < v; j++) {
                if (dist(i,k) + dist(k,j) < dist(i,j)) {
                    dist(i,j) = dist(i,k) + dist(k,j);
                }
            }
        }
    }
}
```

所以，可以发现，上面的伪代码非常简答， 并且只需要将上面的伪代码转换为基于ListGraph的代码即可

最终得到的实现代码如下

```java
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
```