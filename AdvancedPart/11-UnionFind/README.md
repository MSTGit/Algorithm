#### 并查集（Union Find）

##### 需求分析

假设现在有这样一个需求，如下图的每一个点代表一个村庄，每一条线就代表一条路，所以有些村庄之间有连接的路，有些村庄没有连接的路，但是有间接连接的路

![1575630469468](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575630469468.png)

根据上面的条件，能设计出一个数据结构，能快速执行下面2个操作

1. 查询两个村庄之间是否有连接的路
2. 连接两个村庄

为了完成上面的需求，能不能使用前面介绍的数据结构呢，例如：数组，链表，平衡二叉树，集合？其实是可以的，只是效率上高与低的问题。

例如使用动态数组完成上面这种操作，可以通过下面的方式完成。

1. 将每个图用一个数组来对应，所以在这里，需要三个数组
   - 判断两个村庄之间是否有连接的路
     判断两个元素是否在同一个数组中即可，如果两个元素在同一个元素中，就代表它们之间，有连接的路，否则就没有
   - 连接两个村庄
     将两个村庄的元素，整合到一个数组即可

其他几种数据结构操作也类似。但是使用这些数据结构存在一个问题，它们的查询，连接时间复杂度都是O(n)，所以用这些数据结构来完成上面的需求，不合适。但是在本节内容中介绍的并查集能够办到查询，连接的均摊时间复杂度都是O(α(n)),α(n) < 5，所以并查集非常适合解决这类“连接”相关的问题

#### 并查集

> 并查集和叫做不相交集合（Disjoint Set）

并查集有2个核心操作

1. 查找（Find）：查找元素所在的集合（这里的集合并不是特指Set这种数据结构，是指广义上的数据集合）
2. 合并（Union）：将两个元素所在的集合合并为一个集合

并查集有2种常见的实现思路

1. Quick Find
   - 查找（Find）的时间复杂度为：O(1)
   - 合并（Union）的时间复杂度为：O(n)
2. Quick Union
   - 查找（Find）的时间复杂度为：O(logn)，可以优化至O(α(n))，α(n) ＜ 5
   - 合并（Union）的时间复杂度为：O(logn)，可以优化至O(α(n))，α(n) ＜ 5

所以，通过Quick Find来实现并查集的话，查找的效率会比Quick Union高，但是合并效率会比Quick Union低，那在开发中用哪一个呢？在开发中，一般使用Quick Union这种思路

##### 并查集如何存储数据

现假设并查集处理的数据都是整型，那么可以用整型数组来存储数据，其中数组的索引代表对应的元素编号，然后数组中存储的数据为该元素对应所在的集合，所以如果用下图来表示对应村庄对应的集合，其中索引代表村庄的标号，编号对应的值代表村庄所在的集合

![1575632678948](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575632678948.png)

可以知道，村庄0,1,3是相互有路相连，村庄2/5分别独立，没有路与其他相连，村庄5,6,7是相互有路相连的。那么如果使用形象的图来表示的话，村庄0,1,3，可以用下图来进行表示

![1575633032311](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575633032311.png)

解释：

1. 村庄索引0的父节点为村庄索引1
2. 村庄索引3的父节点为村庄索引1
3. 村庄索引1·的父节点为村庄索引1

也可以认为，索引对应的元素，代表父节点的索引，所以每一个节点，都有一根线，指向其父节点，所以从这个图，可以看出，0,1,3的父节点都1，所以属于同一个集合。以此类推2表示单独的一个集合

![1575633309231](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575633309231.png)

根据上面的定义，索引4的村庄的父节点索引为5，所以其实索引4与5之间是有联系的，并且5,6,7,之间本来也存在联系，所以最终可以用下图来进行表示，所以可以认为4,5,6,7也是属于同一个集合的。

![1575633359449](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575633359449.png)

所以，如果并查集是整数的话，就可以通过数组来表示每个元素之间的关系。所以并查集是可以用数组来实现的树形结构（二叉堆，优先级队列也是可以用数组实现的树形结构）

##### 接口定义

所以，根据前面的介绍，并查集需要定义以下接口

```java
/**
 * 查找v所属的集合（根节点）
 */
int find(int v);
/**
 * 合并v1,v2所属的集合
 */
void union(int v1, int v2);
/**
 * 检查v1,v2是否属于同一个集合
 */
boolean isSame(int v1, int v2);
```

##### 初始化

前面介绍了并查集的两种实现方式，不过，不管是使用哪种方式实现，都需要对数据进行初始化，初始化时，每个元素各自属于一个单元素集合。例如开始的时候，有如下的5个节点，其中索引0的元素存储元素0，索引1的元素存储元素1，索引2的元素存储元素2，索引3的元素存储元素3，索引4的元素存储元素4

![1575640101584](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575640101584.png)

然后用图来表示如下，其中，每一个元素属于一个单元素集合，即自己成为一个集合，这样代表所有的元素都不在同一个集合内。

![1575640333826](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575640333826.png)

所以初始化的实现代码为

```java
public UnionFind(int capacity) {
    if (capacity < 0) {
        throw new IllegalArgumentException("capacity must be >= 1");
    }

    parents = new int[capacity];
    for (int i = 0; i < parents.length; i++) {
        parents[i] = i;
    }
}
```

##### Quick Find - Union

在使用Quick Find实现上图的元素时，首先要进行的是初始化，前面已经介绍过了，所以在这里就不再赘述。初始化完成后，如果现在需要对1,0执行Union操作的话，有两种做法，即将0的箭头，指向1，或者将1的箭头指向0，这样就代表两个元素属于同一个集合中。现规定，如果执行union(v1,v2)的话，统一将v1的箭头指向v2。所以，如果现在执行union(1,0)操作的话，只需要将索引为1指向的元素，值改为0即可。即下图所示

![1575640862412](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575640862412.png)

对应的关系图如下

![1575640904189](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575640904189.png)

同样的，如果要执行union(1,2)操作的话，按照上面的操作，就是将索引为1指向的元素，改为2即可

![1575641081435](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575641081435.png)

但是修改后有一个问题，在以前，0,1是属于同一个集合的，现在1，2要变为同一个集合，所以还需要将索引0指向的元素也修改为2

![1575641206500](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575641206500.png)

最终的关系图如下

![1575641247410](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575641247410.png)

执行union(4,0)操作的话，根据上面的流程，最终得到的结果为

![1575641344015](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575641344015.png)

对应的关系图如下

![1575641371535](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575641371535.png)

执行union(3,2)，得到的结果为

![1575641418761](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575641418761.png)

执行完成后得到的关系图如下

![1575641454897](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575641454897.png)

所以，发现细节了吗？使用Quick Find来实现Union的话，得到的结果很平，每一个节点，向上找一次，就能找到自己的根节点。那基于这种条件，继续在研究如何实现Find操作

所以合并的实现代码为

```java
public void union(int v1, int v2) {
    int p1 = find(v1);
    int p2 = find(v2);
    if (p1 == p2) return;
    for (int i = 0; i < parents.length; i++) {
        if (parents[i] == p1) {
            parents[i] = p2;
        }
    }
}
```

##### Quick Find - Find

如果使用的是上面这种Union方式，可以发现，数组中存储的数据，就是每个索引元素对应的根节点，所以如果有下图的元素

![1575641886648](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575641886648.png)

对应的关系图为

![1575641928332](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575641928332.png)

所以根据每个索引元素对应的根节点的结论可以知道，如果执行下面的操作

- find(0)得到的结果为2
- find(1)得到的结果为2
- find(3)得到的结果为3

所以Quick Find的Find操作，时间复杂度为O(1)

查找的实现为

```java
public int find(int v) {
    rangeCheck(v);
    return parents[v];
}
```

所以，到这里，通过Quick Find的方式，就实现了并查集，不过，从合并的实现可以发现，在合并时，需要对所有元素进行一次遍历，所以合并的时间复杂度为O(n)。接下来，再来研究并查集的另外一种实现Quick Union

##### Quick Union- Union

前面说到，Quick Union的Find与Union时间复杂度都是O(logn)，对比Quick Find中的Union时间复杂度O(n)来讲，性能提升很多，接下来就看以下，Quick Union是如何实现的。

首先最开始的步骤与Quick Find是一样的，都需要初始化，每一个元素属于一个单元素集合。即下列的5个元素

![1575640101584](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575640101584.png)

组成单元素集合后

![1575640333826](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575640333826.png)

初始化完成后，就开始利用Quick Union的思路执行Union操作。

执行union(1,0)，现依然规定，左边的元素，跟随右边的元素，即这里合并后的话，是让左边元素的根节点，等于右边元素的根节点。即现在合并的1,0，其中1的根节点为1,0的根节点为0，由于左边元素的根节点等于右边元素的根节点，所以合并完成后，索引1的元素，根节点变为0，这一步，看起来和Quick Find没什么区别。

![1575640862412](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575640862412.png)

合并后的关系图如下

![1575640904189](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575640904189.png)

接下来，如果继续做union(1,2)操作的话，就有区别了。根据前面的结论，所以需要将索引1的根节点改为索引2的根节点。由于现在1的根节点为0，2的根节点为2，所以只需要让两个根节点来处理就好了，即让0与2进行连接就好了，最终就是索引为0的节点，父节点变为2。

![1575695915704](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575695915704.png)

合并后的关系图如下

![1575695945820](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575695945820.png)

继续执行union(4,1)操作，所以就是将4的根节点指向1的根节点，最终需要处理的节点就是4,2，达到的效果就是4指向2

![1575696093755](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575696093755.png)

合并后的关系图如下

![1575696125962](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575696125962.png)



再执行union(0,3)，也就是说需要将0,3进行合并，仍然是找到0的根节点与3的根节点，然后将0根节点的父节点指向3的父节点即可

![1575696253098](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575696253098.png)

合并后的关系图如下

![1575696274096](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575696274096.png)

所以，最终看到的效果就是上图这样，这种操作对比之前Quick Find做的union操作就不一样了，Quick Find树的高度，最多就是2，但是采用Quick Union这种思路的话， 永远都是找到根节点进行操作，情况就不一样了，Quick Find是将左边集合中所有元素根节点，都改为右边元素的根节点，Quick只需要对左边元素的根节点进行操作即可

所以实现代码如下

```java
public void union(int v1, int v2) {
    int p1 = find(v1);
    int p2 = find(v2);
    if (p1 == p2) return;
    parents[p1] = p2;
}
```

接下来在研究Find操作是如何实现的

##### Quick Union - Find

首先，Find操作的目的是要返回当前集合的根节点，所以例如下图中的集合

![1575696863844](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575696863844.png)

对应的关系图如下

![1575696887081](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575696887081.png)

如果要查找1的根节点，就是从节点1开始，一直往上找，直到找到的节点，发现根节点是自己时，就说明已经找到根节点了，将该根节点进行返回即可。

所以

- find(0)得到的结果为2
- find(1)得到的结果为2
- find(3)得到的结果为3

Find操作的时间复杂度我O(logn)，由于Find的时间复杂度为O(logn)，所以Union操作的时间复杂度也为O(logn)

Find的实现代码如下

```java
public int find(int v) {
    rangeCheck(v);
    while (v != parents[v]) {
        v = parents[v];
    }
    return v;
}
```

这样，Quick Union也实现了union与find操作。由于Quick Union与Quick Find之间时间复杂度的区别，所以建议使用性能更高的Quick Union。

#### Quick Union优化

在Union的过程中，可能会出现树不平衡的情况，甚至可能会退化成为链表，例如下图

![1575698283870](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575698283870.png)

如果现在要执行union(1,3)，按照以前的操作，是将1的根节点，指向3的根节点，所以最终的结果如下

![1575698379553](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575698379553.png)

所以，如果一直按照这种方式进行合并的话，最终就真的会退化成为链表，一旦退化成为链表，最终find操作的时间复杂度就变为O(n)，所以需要对前面的方案进行优化。

优化方案

1. 基于size的优化：将元素少的树，嫁接到元素多的树
2. 基于rank的优化：矮的树，嫁接到高的树

##### 基于size的优化

例如有下图的两个集合，现在要将其合并

![1575699431331](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575699431331.png)

由于，现在是将元素少的树，嫁接到元素多的树上， 所以最终合并后的结果为

![1575699488063](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575699488063.png)

基于这种方式的实现，需要知道每个集合中有多少个元素，所以，要存储以下每个集合的size，由于在初始化时，全是单元素集合，所以在初始化时，也需要将size进行初始化，所以初始化的代码如下

```java
public UnionFind_QuickUnion_Size(int capacity) {
    super(capacity);
    sizes = new int[capacity];
    for (int i = 0; i < sizes.length; i++) {
        sizes[i] = 1;
    }
}
```

最终优化的部分，一定是合并集合的部分，所以只需要对union函数部分的代码进行优化就可以了

所以对size优化后的代码为

```java
public void union(int v1, int v2) {
    int p1 = find(v1);
    int p2 = find(v2);
    if (p1 == p2) return;
    //size少的，嫁接到size多的上
    if (sizes[p1] > sizes[p2]) {
        parents[p2] = p1;//将p2嫁接到p1上
        sizes[p1] += sizes[p2];//更新size
    } else {
        parents[p1] = p2;
        sizes[p2] += sizes[p1];
    }
}
```

然后现在对前面的几种实现，利用相同数量的元素进行对比，最终得到的结果如下

![1575701690669](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575701690669.png)

可以看到，基于size的优化，速度非常快，效果非常明显。

但是，基于size的优化，也可能存在树不平衡的问题。

例如需要将下图中的两个集合进行合并

![1575701872832](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575701872832.png)

如果是使用基于size的优化，最终合并的结果为

![1575701914019](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575701914019.png)

所以为了解决这种问题，将使用基于rank的优化

##### 基于rank的优化

基于rank的优化，是不比较集合中的数量，而是比较集合中树的高度、基于树高进行优化的话，现在对下图中的两个集合进行合并，则是将树矮的树，合并到树高的树上

![1575701872832](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575701872832.png)

所以，最终是将根节点为4的树，嫁接上根节点为3的树上，最终合并完成后如下

![1575702202121](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575702202121.png)

很明显，这种优化，从树的平衡来讲，树会更加平衡，是由于基于size的优化的。

基于rank的优化，同样需要在初始化时，初始化每个单元素集合的高度进行初始化，所以初始化时的实现如下

```java
private int[] ranks;
public UnionFind_QuickUnion_Rank(int capacity) {
    super(capacity);
    ranks = new int[capacity];
    for (int i = 0; i < ranks.length; i++) {
        ranks[i] = 1;
    }
}
```

与基于size的优化相同，优化的部分也是在合并时，所以只需要对合并部分的代码进行优化即可。所以优化后的代码如下

```java
public void union(int v1, int v2) {
    int p1 = find(v1);
    int p2 = find(v2);
    if (p1 == p2) return;
    //rank值小的，嫁接到rank值大的树上
    if (ranks[p1] > ranks[p2]) {
        parents[p2] = p1; //将矮的树，嫁接到高的树上
        //由于是矮的树嫁接到高的树上，所以不需要更新树高
    } else if (ranks[p1] < ranks[p2]){
        parents[p1] = p2;
    } else {
        //树高相等，进行合并，所以树高会增加1
        parents[p1] = p2;
        ranks[p2] += 1;
    }
}
```

最终，将优化后的两种方案，用更大的测试数据进行测试后，得到的比较结果如下

![1575703234247](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575703234247.png)

可以发现，基于rank的优化，表现同样非常的优秀。不过需要注意，这并不意味着基于rank的优化性能低于基于size的优化，因为这两种优化，出发点不一样。基于rank的优化，主要是为了解决基于size优化中，出现不平衡的情况，建议使用基于rank的优化。

虽然两种优化，效果都非常好，不过仍然还有进一步的优化空间。接下来继续研究关于优化的问题

#### 路径压缩（Path Compression）

什么叫路径压缩呢？比如说现在有如下的两个集合

![1575703534625](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575703534625.png)

现在基于Quick Union并且基于rank的优化，进行union(1,5)合并的话，得到的结果如下

![1575705174993](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575705174993.png)

合并后，可以发现，虽然有了基于rank的优化，树会相对平衡一点，但是，随着union的次数增加，树的高度依然会越来越高，最终导致find操作会变得越来越慢。所以，基于这样的问题的存在，可以进行路径压缩优化。

> 路径压缩
>
> - 在find时使路径上的所有节点都指向根节点，从而降低树的高度

这句话的意思就是说，执行完find操作以后，这条路径上的所有节点，都直接指向根节点，所以例如执行find(1)操作，执行完成后的结果如下图

![1575705522863](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575705522863.png)

可以发现，执行完find操作后，原来路径上的节点2,3,4都直接指向了根节点，通过这样的优化，树的高度仅进一步降低，所以如果继续执行find(0),find(7)操作，最终在find后，路径上的所有节点，都直接指向根节点，所以最终的结果如下

![1575705772525](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575705772525.png)

通过这样的优化后，以后在进行find操作时，就会变得非常快。而且由于union也要使用到find，所以最终union效率也会提升。所以，最终要达到的效果就是树越矮越好。接下来就研究，如何实现。

前面说到，路径压缩是对find进行优化，所以这次需要对find方法重新实现，最终find的实现如下

```java
public int find(int v) {
    rangeCheck(v);
    if (v != parents[v]) {
        //修改v的父节点，通过递归调用，最终找到根节点，将v的父节点指向根节点
        //顺便将整条路径节点的父节点都修改为根节点
        parents[v] = find(parents[v]);
    }
    return parents[v];
}
```

通过优化后，与size，rank优化进行对比，最终得到的结果如下

![1575707270042](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575707270042.png)

可以看到，最终的效果依然非常明显。但是依然有以下注意点

1. 路径压缩使路径上的所有节点都指向根节点，所以实现成本稍高，例如有递归调用，会开辟更多的栈空间

所以，基于这种问题，还有另外2中更优的做法，不仅能降低树高，实现成本也会比路径压缩更低，分别为

1. 路径分裂（Path Spliting）
2. 路径减半（Path Halving）

路径分裂，路径减半的效率差不多，但都比路径压缩要好

##### 路径分裂（Path Spliting）

路径分裂：使路径上的每个节点都指向其祖父节点（parent的parent）

例如，下列的集合

![1575707740917](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575707740917.png)

在执行find(1)操作时，会将1指向3,3指向5,5指向7,2指向4,4指向6,6指向7，所以最终执行完毕后的结果如下图

![1575707820263](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575707820263.png)

所以，可以看到，使用了路径分裂这种优化方案的话， 树的高度的确是降低了，不像路径压缩一样，直接将所有子节点平铺开。所以拆分的成本会比路径压缩低一些。所以基于这种思路，最终实现的代码如下

```java
public int find(int v) {
    rangeCheck(v);
    while (v != parents[v]) {
        //将当前节点的父节点保存下来
        int p = parents[v];
        //然后让当前节点指向祖父节点
        parents[v] = parents[parents[v]];
        //更新节点,重复执行操作
        v = p;
    }
    return v;
}
```

通过与前面几种优化方案进行对比，最终得到的比较结果如下

![1575708473495](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575708473495.png)

可以发现，路径分裂方案确实进一步优化了性能。说明这种路径分裂方案是可行的，那接下来再研究路径减半这种优化方案。

##### 路径减半（Path Halving）

路径减半：使路径上的每隔一个节点就指向其祖父节点（parent的parent）

对比前面的路径分裂，路径分裂是每一个节点，都指向祖父节点，路径减半是每隔一个节点就指向祖父节点。那究竟是什么意思呢？例如有下图的集合

![1575708762776](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575708762776.png)

现在执行find(1)操作，根据上面的定义，即执行完find(1)后，1指向祖父节点，3指向祖父节点，5指向祖父节点，即3指向5,5指向7，其余元素保持不变，所以最终的结果如下图

![1575708895437](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575708895437.png)

其实可以发现，这种方案对比前面的路径分裂，效果类似。因为最红优化后的树高，是一样的。现在已经知道了优化办法，根据这种优化办法，最终得到的优化代码如下

```java
public int find(int v) {
    rangeCheck(v);
    while (v != parents[v]) {
        //然后让当前节点指向祖父节点
        parents[v] = parents[parents[v]];
        //parents[v]为祖父节点，祖父节点重复执行操作
        v = parents[v];
    }
    return v;
}
```

最终通过下图几种优化方案的对不，可以发现，路径减半和路径分裂的性能很接近

![1575709324802](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575709324802.png)

理论上来讲，路径减半与路径分裂两种算法，都非常优秀。并且总体来讲，都要由于其他优化方案。

#### 总结

摘自《维基百科》：https://en.wikipedia.org/wiki/Disjoint-set_data_structure

![1575709769104](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/11-UnionFind/Resource/1575709769104.png)

大概意思是

1. 使用路径压缩，分裂或者减半 + 基于rank或者size的优化
   - 可以确保每个操作的均摊时间复杂度为O(α(n))，α(n) < 5

个人建议的搭配

1. Quick Union
2. 基于rank的优化
3. Path Halving或者Path Spliting

#### 自定义类型

之前的使用都是基于整型数据，如果其他自定义类型也想使用并查集，如何实现呢？可以参考以下方案

1. 通过一些方法，将自定义类型转换为整型后，使用并查集（比如生成哈希值）
2. 使用链表+映射（Map）

为什么可以使用链表实现呢？因为并查集本质上就是树形结构，只不过是通过数组来表达这种树形结构。然后树形结构中的每一条分支，都是一条链表。