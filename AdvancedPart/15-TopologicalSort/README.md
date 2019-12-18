在研究拓扑排序之前，先来了解一个概念。

#### AOV网（Activity On Vertex Network）

什么叫AOV网呢？在生活中经常有这种情况，一项大的工程，常常被分为多个小的子工程，然后小的子工程中之间可能存在一定的先后顺序，即某些子工程必须在其他一些子工程完成后才能开始。

在现代化管理中，人们常常用有向图来描述和分析一项工程的计划和实施过程，其中子工程被称为活动（Activity）

- 顶点表示活动，有向边表示活动之间的先后关系，这样的图，简称AOV网

例如：下图就表示一个AOV网

![1576585179323](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576585179323.png)

图中的每一个顶点就代表一个子工程（Activity），有向边代表活动之间的先后顺序与依赖关系，例如箭头A→B，就表示需要先完成活动A才能开始活动B，所以B完成以后才能开始活动C和活动D，只有活动C，活动B，活动D都完成以后，才能开始活动E，活动E完成以后，才能开始活动F。所以上图AOV网中活动之间的依赖关系如下

- B依赖于A
- C依赖于B
- D依赖于B
- E依赖于B，C，D
- F依赖于E

通过这个依赖关系，可以观察到，一个 顶点的依赖，是由该顶点的inEdges（入度）决定的。即有多少条边进入该顶点，如果该顶点有3条边进入，则说明该顶点有3个依赖

一个标准的AOV网需要满足的条件：**必须是一个有向无环图**（Directed Acyclic Graph，简称DAG）

#### 拓扑排序（Topological Sort）

什么叫拓扑排序，首先结合下图来理解一些基本概念

![1576585179323](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576585179323.png)

1. 前驱活动：有向边起点的活动称为终点的前驱活动；例如B称为C的前驱活动
   - 只有当一个活动的前驱全部完成后，这个活动才能进行；例如E只有当全部前驱B，C，D完成以后，才能进行
2. 后继活动：有向边终点的活动称为起点的后继活动；例如E称为C的后继活动

什么叫拓扑排序？

- 将AOV网中所有的活动，排成一个序列，使得每一个活动的前驱活动都排在该活动的前面；也就是说拓扑排序将所有的活动排好序后，根据这个顺序，恰好能将所有的活动都执行完

所以，上图的AOV网，如果要进行拓扑排序，最终排序的结果如下

- A→B→C→D→E→F 或者
- A→B→D→C→E→F

结果并不一定是唯一的。

那应该如何实现拓扑排序呢？

##### 实现思路

实现拓扑排序，可以利用卡恩算法（Kahn与1962年提出）完成拓扑排序

- 假设L是存放拓扑排序结果的列表
  1. 把所有入度为0的顶点放入L中，然后把这些顶点从图中去掉
  2. 重复操作1，直到找不到入度为0的顶点

根据这种思路，假设对下图进行拓扑排序

![1576587291266](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576587291266.png)

首先将度为0的顶点放入列表中

![1576587323281](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576587323281.png)

![1576587334438](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576587334438.png)

然后将这些顶点从图中删除，最终删除后的结果如下

![1576587384254](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576587384254.png)

基础将入度为0的顶点放入列表中

![1576587431514](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576587431514.png)

然后将A从图中删除，最终删除后的结果如下

![1576587466884](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576587466884.png)

继续将入度为0的顶点放入列表中

![1576587493084](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576587493084.png)

将B，D从图中删除，最终只剩下顶点F

![1576587536071](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576587536071.png)

现在F的入度为0，所以现在又将F放入列表中

![1576587570789](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/15-TopologicalSort/Resource/1576587570789.png)

最终，所有顶点都加入到了列表中，没有剩下入度为0的顶点，说明拓扑排序完成。另外如果列表中的元素个数与顶点的总数相同，也说明拓扑排序完成。

但是，如果已经找不到入度为0的顶点，但是列表中的元素个数少于顶点总数，则说明原图中存在环，无法进行拓扑排序。

由于卡恩算法的实现，是将度为0的顶点加入列表后，将这些顶点从图中删除，如果按照这种方法进行操作，会破坏原有的数据，所以在实现拓扑排序时，会结合卡恩算法进行适当的调整。步骤是这样的

1. 首先创建一个List，用来存放排序后顶点的值
2. 创建一个队列，用来存放当前入度为0的顶点
3. 创建一个表，备份所有顶点入度
4. 将当前入度为0的顶点入队
5. 将队头顶点出队，遍历当前顶点的outEdges，然后将遍历到的顶点，将这些顶点的inEdges减一，如果此时发现有减为0的顶点，则将该顶点入队，直到将outEdges遍历完为止。并将出队顶点的值添加到List中
6. 继续将队头元素出队，重复步骤5
7. 直到将队列中的元素全部出队为止，就完成了拓扑排序。

根据上面的分析，最终转换为代码的结果如下

```java
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
```

