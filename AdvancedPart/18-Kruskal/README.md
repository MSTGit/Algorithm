#### Kruskal算法

以Prim算法一样，Kruskal算法也可以用来计算图的最小生成树。

#### Kruskal算法执行过程

首先了解以下Kruskal算法的描述

> 按照边的权重顺序（从小到大）将边加入到生成树中，直到生成树中含有V - 1 条边位置（V是顶点数量）
>
> - 如果待加入的这条边加入后，会导致树成环，则不加入这条边（从经验来讲，从第3条边开始，就可能会与生成树形成环）

结合图片，再来走一遍Kruskal的执行流程，假设现在有图如下

![1576754983150](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/18-Kruskal/Resource/1576754983150.png)

根据Kruskal算法的逻辑，首先会选择权值最小的一条边。所以HG这条边一定会成为最小生成树的一条边

选出一条边以后，再从剩下的边中选择一条权值最小的边。现在剩下的边中，最小权值是2，并且有两条，这种情况，选择任意一条都可以，假设现在选择的是IC这一条边作为生成树的一条边，最终的结果如下

![1576755266459](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/18-Kruskal/Resource/1576755266459.png)

然后再继续从生下的边中，选择一条边作为生成树的一条边。但是由于现在已经找出了2条边作为生成树的边，所以现在找出第三条以后，就需要判断新找到的边，是否会导致原来的生成树成环，如果不会，就将这条边作为生成树的边，如果会，则继续找权值次小的一条边，直到找到不成环的边位置。所以在找第三条边时，依然会找出权值为2的边GF，最为生成树的边。最终的结果如下

![1576755531065](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/18-Kruskal/Resource/1576755531065.png)

继续依照上面的逻辑，寻找权值最小的边，所以从剩下的边中，找到了权值为4的边，本次先选择AB这条边作为生成树的一条边，所以选择后的结果如下

![1576755771411](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/18-Kruskal/Resource/1576755771411.png)



继续从权值最小的边中寻找合适的边作为生成树的边，本次选择到的依然是权值为4的边CF，所以选择后的结果如下

![1576755872730](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/18-Kruskal/Resource/1576755872730.png)

继续中权值最小的边中寻找合适的边作为生成树的边，本次最小权值为6，是IG边，不过请注意，这次不能选择这条边作为生成树的边，因为一旦选择这条边，就会导致原生成树成环，所以不能选择这条边。

那就一次从小到大进行寻找，找到权值为7的边，不过请注意，权值为7的边IH一旦选择的话，也会导致生成树成环，所以依然不能选，所以最终选择的边是CD，所以选择后的结果如下

![1576756353476](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/18-Kruskal/Resource/1576756353476.png)

然后再从剩下的边中寻找一条边，由于前面已经排除了两条边，所以本次从8开始选择，由于现在有两条边的权值都为8，所以选择任意一条均可，在本示例中以选择AH边为例，最终选择后的结果如下

![1576756409494](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/18-Kruskal/Resource/1576756409494.png)

继续从剩下的边中，寻找权值最小的边，由于本次权值最小的边BC会导致生成树成环，所以不能选，所以最终选择的是权值为9的边DE，所以选择后的结果如下

![1576756521872](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/18-Kruskal/Resource/1576756521872.png)

现在还剩权值为10,11,14的边没有选，但是到现在，算法就已经结束了。因为现在选择的边的数量，已经达到了V - 1 

所以，通过Kruskal算法，最终计算出来的最小生成树如下所示

![1576756688019](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/18-Kruskal/Resource/1576756688019.png)



##### 解决判断是否成环的问题

那应该如何判断新加入的边是否会导致生成树成环呢？

可以利用并查集来判断即将加入的点是否成员的问题。判断的逻辑如下

1. 将选择的边中的两个点利用并查集合并到一个集合中
2. 由于并查集的特性，如果合并的边，其中有一个顶点已经在并查集的集合中，新加入的边与原来的边会合并到一个集合中，例如先加入的边HG与即将加入的边GF，最终会合并到一个集合中。否则就会保存到不同的集合中
3. 如果新选中的边，两个顶点都在并查集的同一个集合中，那么就会导致生成树成环，例如集合中的顶点H,G,F,CI，如果现在将边IG加入，就会成环。所以I,G两个顶点现在是在同一个集合中，所以利用并查集的话，可以很容易的判断出新加入的边是否会导致生成树成环。

结合前面的分析，最终Kruskal算法的实现如下

```java
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
```

