- #### 树形结构

> 在前面章节中介绍到的数据结构，都为线性结构，比如链表，数组，队列等，都属于线性结构，类似于通过一根线串在一起

![1569326231986](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569326231986.png)

树形结构示意图

![二叉树](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569326276818.png)

![多叉树](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569326298149.png)

现实生活中的树，如果将现实生活中的树倒过来，就类似于树形结构

![1569326332050](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569326332050.png)

- #### 生活中经常用到的树形结构

1.公司的组织架构

![1569326550287](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569326550287.png)

2.平时的文件夹组织

![1569326603326](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569326603326.png)

总结：1.使用树形结构可以大大提高效率

​            2.树形结构是算法面试的重点



- #### 树(Tree)的基本概念

> - 节点，根节点，父节点，子节点，兄弟节点
> - 一棵树可以没有任何节点，称为空树
> - 一棵树可以只有一个节点，也就是只有根节点
> - 子树，左子树，右子树
> - 节点的**度**(degree):子树的个数
> - 树的**度**：所有节点度中的最大值
> - 叶子节点(leaf):度为0的节点
> - 非叶子节点：度不为0的节点
> - 层数(level):根节点在第一层，根节点的子节点在第二层，以此类推(有些书籍也从第0层开始计算)
> - 节点的**深度**(depth):从根节点到当前节点的唯一路径上的节点总数
> - 节点的**高度**(height):当从前节点到最远叶子节点的路径上的节点总数
> - 树的深度:所有节点深度中的最大值
> - 树的高度:所有节点高度中的最大值(一般来讲，树的深度与树的高度相等)

![多叉树](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569326298149.png)

- ##### 有序树

> - 树中任意节点的子节点之间有顺序关系(如上面树中，节点2,3,4,5,6是完全按照顺序排列的，如果调换位置，就变成了另外一棵树)

- ##### 无序树

> - 树种的任意节点的子节点之间没有顺序关系，也称为自由树

- ##### 森林

> - 由m(m ≥ 0)棵互不相交的树组成的集合

- #### 二叉树(Binary Tree)

![1569329918589](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569329918589.png)

> **二叉树的特点**
>
> - 每个节点的度最大为2(最多拥有2棵子树)
> - 左子树和右子树是有顺序的
> - 即使某个节点只有一颗子树，也要区分左右子树

二叉树有以下几种形态

1.空树

![1569330125361](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569330125361.png)

2.只有一个节点

![1569330155373](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569330155373.png)

3.只有左子树

![1569330177474](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569330177474.png)

4.只有右子树

![1569330199137](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569330199137.png)

![1569330254306](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569330254306.png)

5.右左右子树

![1569330220346](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569330220346.png)

 🤔二叉树是有序树还是无序树？

- #### 二叉树的性质

![1569330455760](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569330455760.png)

![1569330549453](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569330549453.png)

> - 非空二叉树的第i层，最多有2^(i - 1)个字节点(i ≥ 1)
> - 在高度为h的二叉树上，最多有2^h - 1个节点(h ≥ 1)
> - 对于任何一颗非空的二叉树，如果叶子节点个数为n0，度为2的节点个数为n2,则有：n0 = n2 +1
> - - 假设度为1的节点个数为n1，那么二叉树的节点总数为 n = n0 + n1 +n2
>   - 二叉树的边数T= n1 +  2 * n2 = n - 1 = n0 + n1 + n2 - 1



- #### 真二叉树(Proper Binary Tree)

> 真二叉树：所有节点的度，要么为0，要么为2

![1569331161342](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569331161342.png)

下图不是真二叉树

![1569331210101](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569331210101.png)



- #### 满二叉树(Full Binary Tree)

> 满二叉树：所有节点的度都要么为0，要么为2，并且所有的叶子节点都在最后一层

![1569331356078](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569331356078.png)

> 在同样高度的二叉树中，满二叉树的叶子节点数量最多，总节点数量最多
>
> 满二叉树一定是真二叉树，真二叉树不一定是满二叉树

假设满二叉树的高度为h(h ≥ 1)，那么

第i层的节点数量：2^(i - 1)

叶子节点的数量:2(h - 1)

总节点数量n: n = 2^h - 1 = 2^0 + 2^1 +2^2 + …… + 2^(h - 1)



- #### 完全二叉树(Complete Binary Tree)

> 完全二叉树:叶子节点只会出现在最后2层，并且最后1层的**叶子**节点都**靠左**对齐

![1569331870996](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569331870996.png)

完全二叉树的记忆方法：所有节点从上往下，从左往右一次排布，就位完全二叉树，如下图

![1569332166943](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569332166943.png)

如果所有节点都排满了，就叫做满二叉树

![1569332216547](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569332216547.png)

> 完全二叉树从**根节点**至**倒数第二层**是一个**满二叉树**
>
> 满二叉树一定是一棵完全二叉树，完全二叉树不一定是满二叉树

- ##### 完全二叉树的性质

> 度为1的节点只有左子树
>
> 度为1的节点，要么是1个，要么是0个
>
> 同样节点数量的二叉树，完全二叉树的高度最小

假设完全二叉树的高度为h(h ≥ 1)，那么

至少有2^(h - 1)个节点(2^0 + 2^1 + …… + 2^(h - 2) +1)

最多有2^ - 1个节点(满二叉树)

如果此时，总节点数量为n，那么有结论 2^(h -1) ≤ n ＜ 2^h

对 2^(h -1) ≤ n ＜ 2^h取对数，则有h - 1 ≤ log2n ＜ h,可以得出，h与n之间的关系为 h = log2n (向下取整) +1，即 h = floor(log2n) +1

> 一棵有n个节点的完全二叉树(n＞0)[下图],从上到下，从左到右对节点从1开始进行编号，对任意第i个节点

- 如果i = 1，它是根节点
- 如果i > 1 ,它的父节点编号为floor(i / 2)
- 如果2i ≤ n ,它的左子节点编号为2i
- 如果2i ＞ n ,它没有左子节点
- 如果2i + 1 ≤ n ,它的右子节点编号为2i + 1
- 如果2i + 1＞ n，它没有右子节点

> 一棵有n个节点的完全二叉树(n＞0)[下图],从上到下，从左到右对节点从0开始进行编号，对任意第i个节点

- 如果i = 0，它是根节点
- 如果i > 0 ,它的父节点编号为floor((i - 1) / 2)
- 如果2i + 1 ≤ n - 1 ,它的左子节点编号为2i + 1
- 如果2i  + 1＞ n - 1 ,它没有左子节点
- 如果2i + 2 ≤ n  - 1,它的右子节点编号为2i + 2
- 如果2i + 1＞ n，它没有右子节点

![1569331870996](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569331870996.png)

- ##### 下面的二叉树不是完全二叉树

![1569417448928](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569417448928.png)

- #### 面试题

如果一棵完全二叉树有768个节点，求叶子节点的个数

解题思路：

1. 假设叶子节点个数为n0，度为1的节点个数为n1，度为2的节点个数为n2
2. 总结点个数 n = n0 +n1 + n2 ,而且由前面的公式知道，n0 = n2 + 1

因此有 n = 2n0 + n1 - 1

由于题目告知二叉树为完全二叉树，那么我们知道度为1的节点数量，为1或者为0，所以

- 当n1为1时，n = 2n0，n必然是偶数，所以叶子节点是数量n0 = n / 2,非叶子节点个数 n1 +n2 = n / 2
- 当n1位0时，n = 2n0 - 1，n必然位奇数，所以叶子节点的数量n0 = (n +1) / 2，非叶子节点的数量 n1 + n2 = (n - 1) / 2

所以最终，叶子节点的数量n0 = floor((n +1) / 2) = ceilling(n / 2)

非叶子节点的数量n1 + n2 = floor(n / 2) = ceilling((n - 1) / 2)

因此最终结果为384

- #### 国外资料的说法

> Full Binary Tree: **完满**二叉树
>
> - 所有非叶子节点的度都为2
> - 也就是我们所说的"真二叉树“

![1569419190351](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569419190351.png)

> Perfect Binary Tree:完美二叉树
>
> - 所有非叶子节点的度都为2，且所有的叶子节点都在最后一层
> - 也就是我们所说的"满二叉树“

![1569419344147](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569419344147.png)

> Complete Binary Tree:完全二叉树
>
> - 和我们说的一样

![1569419403055](https://github.com/MSTGit/Algorithm/tree/master/BinaryTreeNote/Resource1569419403055.png)