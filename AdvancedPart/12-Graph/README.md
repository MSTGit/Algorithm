#### 图（Graph）

在讨论图这种数据结构之前，先来回顾一下前面介绍的几种数据结构

线性结构

- 数组
- 链表
- 栈
- 队列
- 哈希表

![1576152375176](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576152375176.png)

树形结构

- 二叉树
- B树
- 堆
- Trie
- 哈夫曼树
- 并查集

![1576152410723](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576152410723.png)

接下来就是将要讨论到的图这种树形结构

![1576152510280](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576152510280.png)

通过观察，可以发现，图这种数据结构确实和前面的线性结构，树形结构很不一样，看起来更加复杂。不用担心，这里将一步一步的对图进行研究。首先，先来了解图的一些基本概念。

##### 图的基本概念

1. 图[下图]由顶点（vertex）和边（edge）组成，通常表示为G= （V，E）
   - G表示一个图，V的顶点集，E是边集
   - 顶点集V有穷且非空（下图1有6个顶点，下图2有6个顶点，下图3有6个顶点）
   - 任意两个顶点之间都可以用边来表示它们之间的关系，边集E可以是空的（下图1有6条边，下图2有2条边，下图3有0条边）

![1576152901962](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576152901962.png)

![1576153082836](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576153082836.png)

![1576153124242](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576153124242.png)

##### 图的应用

图结构的应用极其广泛

1. 社交网络
   ![1576153192812](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576153192812.png)
2. 地图导航
   ![1576153293290](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576153293290.png)
   ![1576153313801](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576153313801.png)
3. 游戏开发
   ![1576153353505](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576153353505.png)

其他应用场景不一一举例

##### 有向图（Directed Graph）

有向图的边是右明确方向的，例如下图的就表示一个有向图，每一条边都是有明确方向的

![1576153509562](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576153509562.png)

有向无环图（Directed Acyclic Graph，简称DAG）

- 如果有一个有向图，从任一顶点出发无法经过若干条边回到该顶点，那么它就是一个有向无环图

所以上图为一个有向无环图，下图则不是一个有向无环图

![1576153775169](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576153775169.png)

##### 出度、入度

出度、入度使用于有向图

![1576153901154](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576153901154.png)

出度（Out-degree）

- 一个顶点的出度为x，是指有x条边以该顶点为起点（以当前节点，出发的边的数量，就表示出度值）

  根据这条结论，可以知道，顶点11的出度为3

入度（In-degree）

- 一个顶点的入度为x，是指有x条边以该顶点为终点（以当前节点为重点的边，就表示入度）

  根据这条结论，可以知道，顶点11的入度为2

##### 无向图（Undirected Graph）

无向图的边，是没有方向的，如下图所示

![1576154251415](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576154251415.png)

这种有向图，类似于下面这种有向图

![1576154294843](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576154294843.png)

##### 混合图（Mixed Graph）

缓和图的边可能是无向的，也有可能是有向的，如下图所示

![1576154390994](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576154390994.png)

平行边

- 在无向图中，关联一对顶点的无向边如果多于一条，则称这些边为平行边
- 在有向图中，关联一对顶点的有向边如果多于一条，并且它们的方向相同，则称这些边为平行边

##### 多重图（Multigraph）

有平行边或者有自环的图（可以理解为比较复杂的图）

![1576154721708](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576154721708.png)

为什么上图是多重图呢？因为该图有平行边（红色线条），有自环边（蓝色线条）

##### 简单图（Simple Graph）

既没有平行边，也没有自环的图，成为简单图。从定义可以知道，简单图与多重图是相对的，所以在多重图之前，介绍的所有图，其实都是简单图

![1576154981977](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576154981977.png)

本节内容，也将主要对简单图进行讨论

##### 无向完全图（Undirected Complete Graph）

无向完全图的任意两个顶点之间都存在边

所以，n个顶点的无向完全图有n(n - 1) / 2条边，即(n - 1) + (n - 2) + ( n - 3) + ... + 3 + 2 + 1

![1576155412847](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576155412847.png)

##### 有向完全图（Directed Complete Graph）

有向完全图的任意两个顶点之间，都存在方向相反的两条边

所以，n个顶点的有向完全图有n(n - 1)条边

![1576155533635](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576155533635.png)

稠密图（Dense Graph）

如果边数接近于或者等于完全图，则称该图为稠密图（上图也是一个稠密图）

稀疏图（Sparse Graph）

如果边的数量远远少于完全图，就成为稀疏图

##### 有权图（Weighted Graph）

有权图的边可以拥有权值（Weight），例如下图的有向图，每一条边可以带上一个权值，权值代表一定的含义。

![1576155786511](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576155786511.png)

下图表示两个地点之间来往，所消耗的成本

![1576156040781](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576156040781.png)

当前，权值不仅仅可以是整数，还可以是小数，负数。根据情况而定，甚至还可以是自定义对象

![1576156121045](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576156121045.png)

##### 连通图（Connected Graph）

1. 如果顶点x和y之间存在可相互抵达的路径（直接或间接的路径），则称x和y是连通的。
2. 如果无向图G中任意两个顶点都是连通的，则称G为连通图[如下图所示]

![1576156416590](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576156416590.png)

##### 连通分量（Connected Component）

连通分量：无向图的几大连通子图（尽可能多的连通子图，子图：图中的图）

- 连通图只有一个连通分量，即其自身；非连通的无向图有多个连通分量

根据上面的定义，可以知道，下面的无向图有3个连通分量

![1576156619045](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576156619045.png)

##### 强连通图（Strongly Connected Graph）

如果有向图G中任意两个顶点都是连通的，则称G为强连通图[如下图所示]

![1576156932000](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576156932000.png)

##### 强连通分量（Strongly Connected Component）

强连通分量：有向图的极大强连通子图

- 强连通图只有一个连通分量，即其自身，非强连通的有向图有多个强连通分量

![1576157202852](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576157202852.png)

![1576157223538](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576157223538.png)

![1576157242781](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576157242781.png)

以上，就是关于图的一些基本概念，内容比较多，相信如果是第一次学习相关知识的话，很难记得住，不过没有关系，只要有一个印象即可，后面会在使用中慢慢熟悉。

#### 图的实现方案

前面介绍了一大堆与图相关的概念。那如果现在需要用代码来表达一个图，实现方案是怎么样的呢？图一般来讲，有2中实现方案，分别为

- 邻接矩阵（Adjacency Matrix）
- 邻接表（Adjacency List）

接下来就来了解这两种方案有什么区别

##### 邻接矩阵（Adjacency Matrix）

邻接矩阵的存储方式

- 用一维数组存放顶点信息
- 用二维数组存放边信息

###### 存储无向图

例如现在有如下图所示的无向图

![1576379223101](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576379223101.png)

首先，有一个存放顶点的一维数组，存放着上图中的4个顶点

![1576379326090](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576379326090.png)

然后，边用二维数组来进行表达，由于现在每一条边没有权值，所以两个顶点之间的值，如果为1，就边数存在边，为0就不存在边，所以上面的图可以通过下图的二维边数组进行表示。所以，一个顶点到另外一个顶点，有没有边，通过矩阵就可以表示清楚了

![1576379365838](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576379365838.png)

可以发现，如果是这种无向图，通过邻接矩阵的方式来存储的话，数据有一点冗余，例如V0到V2,V2到V0都存储了一次，这两次存储其实是有一点重复的

###### 存储有向图

如果用邻接矩阵来表示有向图的话[下图]



![1576379787409](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576379787409.png)

首先，这个有向图与无向图的顶点是一样的，所以顶点数组是一样的，存放着所有的顶点

![1576379326090](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576379326090.png)

边仍然是使用二维数组来进行存储，但是存储的内容却有所不同，首先仍然使用0表示两个顶点时间没有边，1表示两个顶点之间有边，由于是有向图，所以边数组中的存储的每一条边都有一条唯一的边进行对应，所以数据不冗余

![1576379864444](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576379864444.png)

所以，邻接矩阵比较适合稠密图。因为邻接矩阵可以表示大量的边信息，否则就会造成内存的浪费，因为如果存储的是稀疏图，则会在矩阵中存储大量的0，导致内存浪费。

###### 存储有权图

如果现在有如下所示的一个有权图

![1576380270754](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576380270754.png)

那在邻接矩阵中应该如何进行存储呢？

首先存储顶点与前面的存储方式一致，利用一个一维数组存放顶点

![1576380341528](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576380341528.png)

存储边时，就有一些区别了，由于是有权值的图，所以在存储边时，需要存储两个顶点对应边的权值，如果两个顶点之间没有边，则使用无穷大来表示，所以最终表示的结果如下

![1576380389209](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576380389209.png)

可以发现，利用邻接矩阵来表示图中顶点与边之间的关系，还是比较简单的，但是在某些时候，可能会导致内存的浪费。所以接下来研究邻接表是如何实现的。

##### 邻接表（Adjacency List）

邻接表，利用一个一维数组就可以存储了，数组中的每一个元素是一个链表对象。存储方式如下

###### 存储无向图

现在有如下的一个无向图

![1576380715080](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576380715080.png)

如果使用无向图来进行存储的话，最终存储的结果如下

![1576380757252](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576380757252.png)

表达的意思是这样的，

1.  首先从图中可以知道顶点V0可以到达V1，V2，V3，所以在顶点V0后面，就跟着1,2,3这3个节点，就代表V0能通往这三个节点
2. V1可以到达V0，V2，所以在顶点V1后面，跟着0,2这两个节点，就代表V1可以通往这两个节点
3. 以此类推。。。

可以发现，使用邻接表，在表示图时，只存储了当前顶点可以到达的顶点，不会存储其他无法到达的顶点，这样的话， 就不会导致内存浪费

###### 存储有向图

所以，如果你用邻接表存储如下的有向图

![1576381152938](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576381152938.png)

在邻接表中存储的结果如下，由于是有向图，所以邻接表与逆邻接表来进行表示，其中邻接表表示当前节点可以到达的节点，如下所示

![1576381194339](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576381194339.png)

逆邻接表表示哪些节点可以到达当前节点，例如V0可以通过V1，V2节点到达，所以在逆邻接表中，V0后面，就跟着1,2，其他顶点以此类推即可

![1576381483330](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576381483330.png)

###### 存储有权图

如下所示的有权图

![1576381557746](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576381557746.png)

利用邻接表进行存储如下

![1576381589881](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/12-Graph/Resource/1576381589881.png)

与前面的无向图/有向图存储方式相似，唯一的区别是在存储当前顶点可以到达的顶点时，需要存储两个顶点之间的权值

结合前面的分析，现在可以着手实现一个图了。

#### 图的实现

前面分析知道，图是由顶点和边组成的，所以肯定会定义两个对应的类与顶点和边进行对应。

- 顶点类中存储的数据
  - 顶点的值
  - 以顶点出去的边
  - 以顶点进来的边

所以可以通过这种方式，定义一个顶点类

```java
private static class Vertex<V,E> {
    V value;
    Set<Edge<V,E>> inEdges = new HashSet<>();
    Set<Edge<V,E>> outEdges = new HashSet<>();
}
```

- 边类中存储的数据
  - 出发到当前顶点的顶点
  - 当前顶点出发到的顶点
  - 权值

所以，边的定义如下

```java
private static class Edge<V,E> {
    Vertex<V,E> from;
    Vertex<V,E> to;
    E weight;
}
```

好的，顶点和边定义好以后，就可以添加顶点和边了。

##### 添加顶点

```java
public void addVertex(V v) {
    if (vertices.containsKey(v)) return;
    vertices.put(v,new Vertex<>(v));
}
```

##### 添加边

```java
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
```

##### 删除边

```java
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
```

##### 删除顶点

```java
public void removeVertex(V v) {
    Vertex<V,E> vertex = vertices.remove(v);
    if (vertex == null) return;
    //删除顶点相关联的边
    //使用迭代器进行删除
    for (Iterator<Edge<V,E>> iterator = vertex.outEdges.iterator(); iterator.hasNext() ;) 	{
        Edge<V,E> edge = iterator.next();
        edge.to.inEdges.remove(edge);
        iterator.remove();//将当前遍历到的元素edge从集合vertex.outEdges中删掉
        edges.remove(edge);
    }

    for (Iterator<Edge<V,E>> iterator = vertex.inEdges.iterator(); iterator.hasNext() ;) 	{
        Edge<V,E> edge = iterator.next();
        edge.from.outEdges.remove(edge);
        iterator.remove();//将当前遍历到的元素edge从集合vertex.outEdges中删掉
        edges.remove(edge);
    }
}
```

所以到这里，图的添加顶点，添加边，删除顶点，删除边都已经实现了。接下来研究一下如何对图进行遍历

#### 遍历

图的遍历

- 从图中某一顶点出发访问图中其余顶点，且每个顶点仅被访问一次

图有2中常见的遍历方式（有向图，无向图都适用）

- 广度优先搜索（Breadth First Search，BFD），又称为宽度优先搜索，横向优先搜索
- 深度优先搜索（Depth First Search，DFS）

> 发明“深度优先搜索”算法的2为科学家在1986年共同获得计算机领域的最高奖：图灵奖

关于这两种搜索遍历方式，将会在后面的章节中继续介绍。