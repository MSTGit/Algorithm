#### 生成树（Spanning Tree）

生成树（Spanning Tree），也称为支撑树

连通图的极小连通子图，它含有图中全部的n个顶点，恰好只有n - 1条边

> 连通图的概念：
>
> 1. 无向图
> 2. 任意两个顶点之间，都有相互可抵达的路径（直接或者间接的路径均可）
>
> 极小连通子图：
>
> 1. 连通图，用最少的边，也可以成为一个连通图，即最小的连通子图。并且子图，依然是一个连通图

了解了连通图和极小连通子图后，现在来观察如下连通

![1576590944775](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/16-MinimumSpanningTree/Resource/1576590944775.png)

A,B,C,D,E均可到达任意一个节点。所以如上连通图的极小连通子图，可以如下所示

![1576591141689](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/16-MinimumSpanningTree/Resource/1576591141689.png)

极小连通子图也叫做生成树，支撑树。因为可以发现，上面的连通图，只依靠4条边，就将整个图支撑起来了。并且可以发现，一个连通图的支撑树，可能有能多棵，所以上图连通图的支撑树，还有如下一些

![1576591306943](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/16-MinimumSpanningTree/Resource/1576591306943.png)

![1576591328323](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/16-MinimumSpanningTree/Resource/1576591328323.png)

![1576591350551](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/16-MinimumSpanningTree/Resource/1576591350551.png)

![1576591377699](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/16-MinimumSpanningTree/Resource/1576591377699.png)



上面这些树，都是连通图的支撑树。

#### 最小生成树（Minimum Spanning Tree）

最小生成树（Minimum Spanning Tree，简称MST）

- 也称为最小权重生成树（Minimum Weight Spanning Tree），最小支撑树

最小生成树，即在所有的生成树中，总权值最小的一棵树，适用于有权的无向连通图

例如现有如下的连通图

![1576591833818](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/16-MinimumSpanningTree/Resource/1576591833818.png)

根据上面对最小生成树的定义，可以计算出该连通图的最小生成树如下

![1576591906938](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/16-MinimumSpanningTree/Resource/1576591906938.png)

> ##### 最小生成树的应用场景
>
> 在n个城市之间铺设光缆，使他们之间都可以相互通信，铺设光缆的费用很高，且各个城市之间因为距离不同等因素，铺设光缆的费用也不同，如何使铺设光缆的总费用最低？
>
> 了解了最小生成树，这种问题就很简单了，每个城市之间铺设光缆的费用，就是连通图中边的权值，最终找出n个城市之间的最小生成树就可以了。这就是最小生成树的一种应用场景。

需要注意，如果图的每一条边的权值都互不相同，那么最小生成树将只有一个，否则可能会有对个最小生成树

求最小生成树的2个经典算法

- Prim（普里姆算法）
- Kruskal（克鲁斯克尔算法）

这两个算法的原理及实现，详见后面章节。