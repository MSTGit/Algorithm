#### 广度优先搜索（Breadth First Search）

在前面介绍的数据结构中，其实也有用到过类似于广度优先搜索的这种算法，二叉树层序遍历就是一种广度优先搜索

那再图里面，应该如何来遍历呢？

##### 无向图

假设现在有如下的无向图

![1576400625097](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576400625097.png)

要对图中所有的顶点进行遍历，其实与二叉树的层序遍历很相似，都是一层一层的进行遍历的。假设现在从顶点A开始遍历，A在第一层，A能直接访问的在第二层，那么第二层能直接访问到的在第三层，第三层能直接访问到的在第四层。所以最终每一层的顶点元素如下。不过需要注意，由于是无向图，所以会出现这种情况B能访问到G，但是G也能访问到B，所以在遍历是需要注意，已经遍历过的顶点，不能再进行遍历。

![1576400777232](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576400777232.png)

##### 有向图

如果现在遍历的是如下图所示的有向图，在有向图中，需要考虑的是，有没有该方向的线能访问到下一层的元素，例如从0出发，只能访问到1和4，虽然与2之间有线，但是方向不对，所以0不能直接访问到2

![1576400895261](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576400895261.png)

假设现在是一起点为0开始广度优先搜索，0能直接访问到的顶点是1和4，所以1和4在第二层，第二层能直接访问到的是2,6,7，所以2,6,7在第三层，以第三层顶点向下访问，能访问到的顶点只有5，所以5在第四层，最终以第四层向下访问的顶点是3，所以3在第五层。所以每一层元素如下

![1576401482881](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576401482881.png)

另外还有一些注意点需要注意，例如上图的有向图，从不同节点出发，最终遍历的结果是不一样的，假设现在以7为起点进行广度优先搜索的话， 最终只能访问到顶点7，其余顶点都无法访问，所以在选在遍历起点时，也需要特别的注意。而且还有可能存在某些节点，永远无法遍历的情况，例如如下的有向图

![1576401838770](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576401838770.png)

无论如何访问，都无法遍历到顶点1和顶点3。

#### 广度优先搜索-实现思路

回顾前面介绍二叉树的程序遍历时，最终是通过队列来进行实现的。其实在对图进行广度优先搜索时，也可以利用队列的方式来实现。就拿上面的图详图来说

![1576400625097](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576400625097.png)

在对每个顶点进行访问时，都是先将顶点放入队列中，然后再将顶点从队列中取出来，并且将顶点能直接访问到的顶点，继续放入队列中，所以搜索访问流程如下

1. 首先，将A入队，然后再将A取出来进行访问
   ![1576402510836](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576402510836.png)
2. 对A进行访问后，再将A能直接访问到的B，F进行入队，然后再对B出队进行访问
   ![1576402597507](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576402597507.png)
3. 对B进行访问后，在将B能直接访问的C，I，G进行入队，然后将队头元素F进行出队访问
   ![1576402670489](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576402670489.png)
4. 对F进行访问后，在将F能直接访问到的A，G，E入队，但是由于A，G已经入队过，所以只将E进行入队，并且将C进行出队访问
   ![1576402807483](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576402807483.png)
5. 对C进行访问后，将C能直接访问到的D进行入队，同时再将I进行出队访问
   ![1576402928400](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576402928400.png)
6. 对I进行访问后，由于I能抵达的B，C，D都已经入队过，所以不需要再入队，所以只需要将队头元素G取出，进行访问
   ![1576403024645](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576403024645.png)
7. 对H进行访问后，G能访问到的所有顶点中，左右H没有被加入队列，所以将H进行入队，然后将队头E进行出队访问
   ![1576403095418](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576403095418.png)
8. 对E进行访问后，由于所有顶点都已经入队，所以不需要再入队顶点，只需要价格队头元素取D出来进行访问即可
   ![1576403199701](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576403199701.png)
9. 对D进行访问后，同样所有顶点都已经入队，所以不需要再入队顶点，只需要价格队头元素取H出来进行访问即可
   ![1576403254285](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576403254285.png)
10. 对H进行访问后，队列中已经没有顶点，所以搜索结束
    ![1576403295516](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/13-BreadthFirstSearch/Resource/1576403295516.png)

通过观察可以发现，在从队列中取出顶点的顺序，恰好就是广度优先搜索的顺序

所以通过上面的分析逻辑，最终可以得到搜索的实现如下

```java
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
```

