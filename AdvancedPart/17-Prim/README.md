在研究Prim算法之前，首先要了解一个概念。切分定理

#### 切分定理

**切分（Cut）**：把图中的节点分为两部分，成为一个切分

例如下图，该图上有5个顶点

![1576668502729](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576668502729.png)

现在，利用虚线，将图分割为2部分，第一部分有3个节点，第二部分有2个节点，这就叫做一个切分

![1576668602389](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576668602389.png)

所以切分可以通过这种式子来进行表示C = (S,T)，其中S = {A，B，D}，T = {C，E}

**横切边（Crossing Edge）**：如果一个边的两个顶点，分别属于切分的两部分，这个边就称为横切边

所以上图中的边BC,BE,DE就是横切边，因为这些边，一部分在左边的子图中，一部分在右边的子图中。

**切分定理**：给定任意切分，横切边中权值最小的边必然属于最小生成树

意思就是说，如上图的3条横切边，权值最小的一条，必然属于最小生成树的边

#### Prim算法执行过程

假设G = （V,E）是有权的连通图（无向），A是G中最小生成树的边集

1. G表示图，V表示顶点，E表示边
2. 组成最小生成树的边都会放到集合A中
3. A最开始的时候，是一个空集合

再定义一个集合S，用来存放切分的起点和切完后被切出来的顶点，所以集合S中的顶点都是属于V的，然后重复找到切分的最小横切边，然后将找到的边存放到结合A中，同时将找到的顶点放入集合S中。直到S = V为止，就说明找到了最小生成树的所有边和顶点。

以上执行过程比较抽象，如果将上面流程转换为流程图，则可以通过下面的流程来进行表示

假设现在有如下的图，现在从顶点A开始进行切分，所以首先将A加入到集合S中

![1576671847158](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576671847158.png)

在对顶点A进行切分，所以以顶点A的outEdges进行切分，利用虚线表示如下

![1576672007657](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576672007657.png)



通过这样切分以后，就会产生2条横切边，分别为4和8，结合前面的切分定理，最终会选择AB这条边最为最小生成树的边，现在找出了一条最小生成树的边，所以将AB这条边加入到集合A中，并且将横切边的另外一顶点B加入到集合S中，现在集合S中就存放了2个顶点{A，B}，最终切分后的结果如下

![1576672203737](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576672203737.png)

现在相当于将图切为了两个部分，一部分的顶点存放在集合S中，另外一部分继续存放在原来的内存中。所以以集合S中的顶点与原来图中的顶点，将两个部分中有边存在的部分作为一个切边，所以最终的切分如下

![1576672625965](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576672625965.png)

切边找到后，又根据切分定理，找出去权值最小的一条边，然后将边放入到集合A中，将横切边的另外一顶点放入集合S中，由于现在有两条边权值相同，所以任意选择一条均可。在本示例中选择BC条边，所以现在将新找到的一条边加BC入到A中，并且将C加入到S中，现在S中就存放了3个顶点{A,B,C}，切分后的结果如下

![1576672862606](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576672862606.png)

再利用前面寻找切分的方法，可以找到如下图所示的切分

![1576672953593](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576672953593.png)

根据现在的切分，找出 权值最小的边CI，将边CI加入到集合A中，另外一顶点I加入到S中，所以最终切分后的结果如下

![1576673183888](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576673183888.png)



再利用前面寻找切分的方法，可以找到下一个如下图所示的切分

![1576673260572](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576673260572.png)

这样一切，又可以找到权值最小的横切边CF，所以将边CF加入到结合A中，将顶点F加入到集合S中，最终切分后的结果如下

![1576673423722](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576673423722.png)

继续寻找新的切分，所以，很容易就知道新的切分如下所示

![1576673533327](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576673533327.png)

然后又从横切边中找出权值最小的边，所以本次权值最小的边为GF，所以将GF这条边加入到集合A中，顶点G加入到S中，最终切分后的结果如下

![1576673673660](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576673673660.png)

继续进行切分，找出一条最小生成树的边，所以本次的切分如下。不过需要注意，这里有一个小细节，IG这条边没有被最为一条横切边，原因是如果将这条边作为横切边以后，万一被选中，那么原来的最小生成树就会成环，这就不符合最小生成树的要求，所以在选择很切边时，可能有一些小细节需要注意

![1576673759512](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576673759512.png)

根据上面的切分，最终又会选择出一条权值最小的边HG，将这表边加入到集合A中，同时将H加入到集合S中，所以最终切分后的结果如下

![1576674027381](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576674027381.png)

继续进行切分，本次的切分如下所示

![1576674092694](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576674092694.png)

找出权值最小的边CD，将边CD加入到集合A中，将新的顶点D加入到S中，所以本次切分后的结果如下

![1576674230738](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576674230738.png)

继续进行切分，本次气氛如下图所示

![1576674314996](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576674314996.png)

找出权值最小的边DE，将DE加入到结合A中，将新的顶点E加入到集合S中，所以本次切分的结果如下

![1576674382178](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576674382178.png)

到现在可以发现，原来图中的所以顶点，都已经加入到了集合S中，所以即表示最小生成树已经完成。所以去掉多于的线以后，最终的最小生成树如下

![1576674516051](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/17-Prim/Resource/1576674516051.png)

所以经过这样一个流程下来，相信已经可以理解前面最新过程部分的概括了。

结合前面的分析，得到代码如下

```java
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
```

