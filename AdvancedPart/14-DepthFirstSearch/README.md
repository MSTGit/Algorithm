#### 深度优先搜索（Depth First Search）

与广度优先搜索一样，在二叉树搜索部分，也有用到过类似于深度优先搜索的这种算法，二叉树前序遍历就是一种深度优先搜索

现以图为例，假设现在有如下无序图

![1576495271069](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576495271069.png)

如果以1为起点，进行深度优先搜索的话，其搜索的流程是如下

1. 选择一条路径（如选择3-7路径）进行依次遍历，知道遍历到最底部7后，不能再往下进行搜索
2. 回退一个顶点到3，判断顶点3是否有其他子路径，如果有，则继续遍历其他子路径，没有则继续往上回退一个顶点
3. 回退一个顶点到1，发现顶点1还有其他未遍历的路径，则选择一条未遍历的路径，依次遍历到最底部，直到不能再往下继续搜索位置。
4. 这样一直重复，直到把所有的路径，所有的节点都搜索一遍位置。

如果现在是如下的有向图

![1576495991025](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576495991025.png)

假设现在以顶点a为起点，进行深度优先搜索。

由于现在是有向图，所以可以发现，顶点d是无法访问的，因为从a出发，无论怎么搜索，最终都无法搜索到顶点d

所以，如果以a为起点进行深度优先搜索的话，可能会有以下情况

1. a→e→f→c→d，通过这种方式搜索，可以发现，一条线就价格所有的顶点搜索完了，这是一种情况
2. a→e→c→b，这个时候发现该路径的所有节点都访问完了，就会回退到上一个节点到c，发现该节点仍然访问完了，则继续往回退到e，到e后，发现还有一条路径可以搜索，则往顶点f的方向进行搜索，将所有能访问的顶点搜索完后，结束搜索。所以最终的搜索顺序为a→e→c→b→f
3. 依次类推，按照前面的搜索逻辑，还有其他结果。

所以可以发现，即使是以相同的起点进行搜索，最终的结果，可能都不是唯一的。

根据上面的分析逻辑，最终可以得到搜索的实现如下

##### 递归方式

```java
public void depthFirstSearch(V begin) {
    Vertex beginVertex = vertices.get(begin);
    if (beginVertex == null) return;
    depthFirstSearch(beginVertex,new HashSet<>());
}

private void depthFirstSearch(Vertex<V, E> vertex,Set<Vertex<V,E>> visitedVertices) {
    System.out.println(vertex.value);
    visitedVertices.add(vertex);
    for (Edge<V, E> edge: vertex.outEdges ) {
        if (visitedVertices.contains(edge.to)) continue;
        depthFirstSearch(edge.to,visitedVertices);
    }
}
```

##### 非递归实现

前面的递归调用，在每一次递归调用函数时，都会在开辟一段栈空间，用来存放该次函数调用的地址，直到递归调用结束，最后调用的函数调用结束，该函数的栈空间被回收，然后依次回收该前面申请的栈空间。其实可以发现，递归调用的函数空间申请和释放，与栈这种数据结构很相似，栈的特点是先进后出。所以上面这种递归调用可以调整为使用栈来实现。

同样假设有如下图

![1576495271069](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576495271069.png)

然后利用栈来实现并且以1为起点，进行深度优先搜索的过程如下

1. 将1进行入栈
   ![1576501178871](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576501178871.png)

2. 访问该顶点1，将1加入到已访问的集合中，并将1从栈中弹出，然后拿出1中outEdges的其中一条边（假设现在选择1-3这条边）
   ![1576501190460](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576501190460.png)

3. 将选择边的from,to按顺序进行入栈
   ![1576501316276](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576501316276.png)

4. 访问选择边的to，并将to加入到已访问顶点的集合中

5. 停止遍历from(1)中的边，将选择的边（1-3）继续往下进行搜索访问

6. 将3弹出
   ![1576501812930](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576501812930.png)

   重复2-5步骤。

7. 将边（3-7）进行入栈，访问该顶点，并且将该顶点加入到已访问的集合中。
   ![1576501848105](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576501848105.png)

8. 弹出7，然后选择一条边。由于7只有一条边，并且边的to已经访问过，所以不会往栈中添加边，最终7弹出后结果如下
   ![1576502057437](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502057437.png)

9. 继续弹出栈中的3，同样的，由于顶点3只有一条边，并且边的to已经访问过，所以不会往栈中添加边，那么最终栈中的结果如下
   ![1576502159720](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502159720.png)

10. 继续弹出1
    ![1576502362142](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502362142.png)
    发现顶点1的outEdges中还有边没有访问，那么就会选择一条没有访问过的边。（假设此选择的是1-5），停止边的遍历，然后将新的边加入到栈中
    ![1576502438467](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502438467.png)

11. 访问顶点5，然后将5加入到已访问的集合中

12. 弹出栈顶顶点5
    ![1576502516077](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502516077.png)
    然后从5的outEdges中选择一条边，由于所有边已经访问过了，所以只弹出，不加入任何顶点

13. 弹出栈顶顶点1
    ![1576502635669](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502635669.png)

14. 继续从1的outEdges中还有边没有访问，那么就会选择一条没有访问过的边。（假设此选择的是1-6），停止边的遍历，然后将新的边加入到栈中
    ![1576502688042](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502688042.png)

15. 访问顶点6，然后将6加入到已访问的集合中，然后将6从栈顶弹出
    ![1576502773354](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502773354.png)
    然后从6的outEdges中选择一条边，由于所有边已经访问过了，所以只弹出，不加入任何顶点

16. 弹出栈顶顶点1
    ![1576502635669](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502635669.png)

17. 继续从1的outEdges中还有边没有访问，那么就会选择一条没有访问过的边。（假设此选择的是1-2），停止边的遍历，然后将新的边加入到栈中
    ![1576502883543](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502883543.png)

18. 访问顶点2，然后将2加入到已访问的集合中，然后将2从栈顶弹出
    ![1576502941718](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502941718.png)
    然后从2的outEdges中选择一条边，由于有一条从未访问过的边，所以将未访问过的边（2-4）加入到栈中
    ![1576503028507](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576503028507.png)

19. 访问顶点4，然后将4加入到已访问的集合中，然后将4从栈顶弹出
    ![1576503067821](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576503067821.png)
    然后从4的outEdges中选择一条边，由于所有边已经访问过了，所以只弹出，不加入任何顶点

20. 继续弹出栈顶元素2
    ![1576503130906](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576503130906.png)
    然后从2的outEdges中选择一条边，由于所有边已经访问过了，所以只弹出，不加入任何顶点

21. 继续弹出栈顶元素1
    ![1576502362142](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502362142.png)

22. 继续从1的outEdges中还有边没有访问，那么就会选择一条没有访问过的边。（假设此选择的是1-0），停止边的遍历，然后将新的边加入到栈中
    ![1576503251455](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576503251455.png)

23. 访问顶点0，然后将0加入到已访问的集合中，然后将0从栈顶弹出
    ![1576502773354](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502773354.png)
    然后从0的outEdges中选择一条边，由于所有边已经访问过了，所以只弹出，不加入任何顶点

24. 弹出栈顶顶点1
    ![1576502635669](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/14-DepthFirstSearch/Resource/1576502635669.png)
    现在，发现所有的outEdges都已经访问过了，所以访问结束。整个搜索过程就结束了

所以根据这个思路，实现的代码如下

```java
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
```

