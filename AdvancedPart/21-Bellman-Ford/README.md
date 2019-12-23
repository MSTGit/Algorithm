在前面，介绍了Dijkstra算法，计算图的最短路径，但是Dijkstra算法在计算最短路径时，有一个前提，就是不能有负权边，那如果在有负权边的情况下， 需要计算图的最短路径，应该怎么去实现呢？

在这种情况下，就需要使用到计算最短路径的另外一种算法来搞定了，它就是：**Bellman-Ford**

#### Bellman-Ford

> Bellman-Ford这种算法，也属于单源最短路径算法，并且支持有负权边，甚至还能检查是否具有负权环。

因为关于负权环的问题，在前面介绍最短路径时提到过，如果图中有负权环，是不支持有最短路径的。

算法原理：对所有的边进行V - 1次松弛操作（V是节点数量），得到所有可能的最短路径

例如下图

![1576852593139](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1576852593139.png)

该图现在一共有7条边，上面算法原理描述的意思就是说，对上面7条边，都进行V - 1次松弛操作就可以得到所以可能的最短路径。

疑惑：为什么要进行V - 1次松弛操作呢？是否只进行1次，或者2次松弛操作，就得到可能的最短路径呢？

答案是不行的，因为V - 1次松弛操作是这样来的，在运气好的时候，确实是可以做到对所有边进行1次松弛操作就能得到所有可能的最短路径，但是在最坏的情况下，所有的边都需要做V - 1次，所以为了保证一定计算出了可能的最短路径，就统一表示为V - 1次松弛操作。

##### 计算最短路径最好情况

下图的最好情况恰好是从左到右的顺序对边进行松弛操作

并且对所有边仅需进行一次松弛操作就能计算出A到达其他所有顶点的最短路径

![1577023160413](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577023160413.png)

上图表示的一个图，并且每个顶点之间的顺序为A→B→C→D→E，只不过这幅图与链表很相似。然后再来理解什么叫松弛操作，例如从这幅图来讲，对CD这条边进行松弛操作，就是对AD的最短路径进行更新；对DE进行松弛操作，就是对AE的最短路径进行更新。可以发现，这幅图的每一条边，只需要进行一次松弛操作，然后A到B,C,D,E的所有最短路径都可以计算出来了。

1. 对A→B进行松弛操作，就能计算出A→B之间的最短路径，为-3
2. 对B→C进行松弛操作，就能计算出A→C之间的最短路径，为-2
3. 对C→D进行松弛操作，就能计算出A→D之间的最短路径，为-1
4. 对D→E进行松弛操作，就能计算出A→E之间的最短路径，为0

但是基于Bellman-Ford的原理，说的是需要对每条边进行V- 1次松弛从操作，准能算出最短路径，那么如果是上图的情况，就需要对A→B，B→C，C→D，D→E进行4次松弛操作，就一定能计算出最短路径。所以对每条边进行V- 1次松弛从操作其实是正确的。只是上图这种情况比较特殊，一次就计算出来了。而且可以发现，即使上图的每一条边进行一次松弛操作就计算出了最短路径，但是就算对每条边进行V - 1次松弛操作，最终得到的结果也是一样的。

最终得到的结果如下图

![1577024535961](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577024535961.png)

##### 计算最短路径最坏情况

同样是下图进行松弛操作，但是这一次的顺序却和前面不一样，前面是从左到右进行松弛操作，这一次是从右到左进行松弛，即松弛的顺序为DE，CD，BC，AB，情况就不一样了

![1577023160413](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577023160413.png)

为什么有这种情况呢？因为Bellman-Ford是对所有边进行松弛，所以到时候要做的就是，先遍历所有的边，由于在前面实现图时，是将边存放到Set中，由于Set是没有顺序的是，所以到时候遍历边的时候，先拿到的是那一条边进行松弛操作，是不确定的，所以在最坏的情况下，就是对上图的边进行从右到左的顺序进行松弛操作。

第一轮松弛操作：

1. 对DE进行松弛操作
   由于现在A到D之间的最短路径还没有计算出来，所以这一次对DE进行松弛操作，计算AE之间的最短路径计算失败

2. 对CD进行松弛操作
   同样的，由于A到C之间的最短路径还没有计算出来，所以这一次对CD进行松弛操作，计算AD之间的最短路径失败

3. 对BC进行松弛操作
   由于A到B之间的最短路径还没有计算出来，所以这一次对BC进行松弛操作，计算AC之间的最短路径失败

4. 对AB进行松弛操作

   对AB进行松弛操作，由于A是起点，所以对AB这条边进行松弛操作时，由于A到A之间的最短路径为0，所以可以计算出A到B之间的最短路径，这一次松弛操作成功

所以在第一轮松弛操作时，只有对AB这条边松弛操作是成功的。可以理解为，这一轮松弛操作，只有顶点B计算出了最短路径，所以第一轮操作的结果如下

![1577025565274](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577025565274.png)

接下来，假设依然是按照从右往左的顺序来对每条边进行松弛操作，就会进行第二轮的松弛操作，操作如下

第二轮松弛操作：

1. 对DE进行松弛操作
   由于现在A到D之间的最短路径还没有计算出来，所以这一次对DE进行松弛操作，计算AE之间的最短路径计算失败

2. 对CD进行松弛操作
   同样的，由于A到C之间的最短路径还没有计算出来，所以这一次对CD进行松弛操作，计算AD之间的最短路径失败

3. 对BC进行松弛操作
   由于A到B之间的最短路径还已经计算出来，所以这一次对BC进行松弛操作，计算AC之间的最短路径成功

4. 对AB进行松弛操作

   和上一轮一样，这一次松弛也肯定是成功的，并且结果也一样。

所以在第二轮松弛操作结束后，又确定了顶点C到源点的最短路径，这一轮操作的结果如下

![1577025912374](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577025912374.png)

以此类推，进行第三轮松弛操作

第三轮松弛操作：

结合前面的规律，第三轮松弛操作，计算出了D到源点的最短路径，得到的结果如下

![1577026018696](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577026018696.png)

然后再进行下一轮的松弛操作

第四轮松弛操作：

第四轮松弛操作，计算出了顶点E到源点的最短路径，完成以后得到的结果如下

![1577026125422](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577026125422.png)

到这里，可以发现就计算出了所有顶点到源点之间的最短路径。并且可以发现，对每一条边一共是进行了4次的松弛操作。所以仍然的对每条边进行V - 1次松弛操作

所以，最坏的情况就是，在每一轮的松弛操作中，只能计算出一个点到源点之间的最短路径，一共要确定V-1个点，所以要进行V - 1次松弛操作。

上面就通过一个比较特殊的示例，用来说明了Bellman-Ford的最好情况与最坏情况，在进行松弛操作是，需要遍历从次数。接下来再通过一个实例，研究Bellman-Ford的整个执行过程。

#### Bellman-Ford实例

现假设对下图，利用Bellman-Ford算法对其计算最短路径

![1577028387945](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577028387945.png)

根据上图，一共有8条边，Bellman-Ford的原理，是对这8条边都进行松弛操作，而且没一条边都进行V - 1次松弛操作，现假设每次松弛操作的顺序都是：DC,DF,BC,ED,EF,BE,AE,AB

现在进行第一轮松弛：

结合前面对每一轮松弛操作的结果，可以知道这一轮松弛操作，DC,DF,BC,ED,EF,BE都是没有结果的，只有对AE,AB才会有结果，所以第一轮松弛操作的结果如下

![1577028715285](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577028715285.png)

第二次松弛操作：

第二轮松弛操作，DC,DF松弛操作会失败，BC,ED,EF会松弛操作成功，并且由于E多了一种新的选择A→B→E，从A到达E，并且发现新的路径路径长度更短，所以对会更新A到E之间的路径长度，原来的A→E这条边松弛操作，由于路径长度大于现在更新的长度，所以会松弛失败。最终第二轮松弛操作的结果如下所示

![1577029166891](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577029166891.png)

第三次松弛操作：

由于前面一轮的松弛操作，顶点D已经有最短路径，所以在这一轮松弛操作中，对DC，DF进行松弛操作可以成功。由于DC的松弛操作成功，最终会更新AC之间的最短路径为A→B→E→D→C，路径长度为17，对DF进行松弛操作时，得到的结果是21，大于原来的路径长度，所以会松弛失败。另外，还会更新的路径有AD，最短路径为A→B→E→D，AF，最短路径为A→B→E→F。BC之间的松弛操作会失败。最终这一轮松弛操作完成后的结果如下

![1577029793336](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577029793336.png)

第四次松弛操作：

这一次松弛操作，发现有更短的路径，AC的最短路径为A→B→E→D→C,路径长度为14。其他边的松弛操作都会四边，所以最终这一轮松弛操作的结果如下

![1577030065287](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/21-Bellman-Ford/Resource/1577030065287.png)

第五次松弛操作

这一轮松弛，所有顶点的最短路径没有变化。

所以经过上面的分析，一共进行了4次操作之后，就已经可以计算出A到其他所有顶点之间的最短路径。

##### 为什么Bellman-Ford能检测出有负权环？

因为根据Bellman-Ford的理论，在对每一条边进行V - 1 次松弛操作以后，就能确定每一条边的最短路径，所以，如果在对每一条边进行了V - 1次松弛操作以后，在进行一次松弛朝族，还能找到路径更短的最短路径，就说明有负权环。所以Bellman-Ford能检测出有负权环

结合前面的分析流程，得到的代码如下

```java
public Map<V, PathInfo<V, E>> bellmenFord(V begin) {
    Vertex<V,E> beginVertex = vertices.get(begin);
    if (beginVertex == null) return null;
    Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
    PathInfo<V, E> beginPath = new PathInfo<>();
    beginPath.weight = weightManager.zero();
    selectedPaths.put(begin,beginPath);
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
```

