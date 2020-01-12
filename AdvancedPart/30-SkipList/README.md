首先来思考一个问题。

> 一个有序链表（下图），搜索，添加，删除的平均时间复杂度是多少？

![1578746131368](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/29-SkipList/Resource/1578746131368.png)

通过对链表这种数据结构的了解可以知道

1. 搜索必须要从表头节点开始，依次往后搜索，直到搜索到为止。所以链表搜索的时间复杂度为O(n)
2. 添加也是一样的，需要从左往右依次搜索，直到找到合适的插入位置为止，所以时间复杂度为O(n)
3. 删除依然是从左往右依次搜索，找到需要被删除的元素后，将元素删除掉，因此时间复杂度为O(n)

那么，能否通过二分搜索来优化有序链表，将搜索，添加，删除的平均时间复杂度降低至O(logn)？

答案是不能，因为链表没有像数组一样的高效随机访问（O(1)时间复杂度），所以不能像有序数组一样，直接进行二分搜索优化， 只能从头开始，或者从尾开始依次搜索，直到找到需要找的元素。因此不能通过二分搜索来进行优化。

既然不能通过二分搜索对链表进行优化，那么，是否有其他办法，让有序链表的搜索，添加，删除的平均时间复杂度降低至O(logn)，有的，跳表就可以实现。

#### 跳表（Skip List）

跳表，又叫做跳跃表，跳跃列表，在有序链表的基础上增加的“跳跃”的功能，是由William Pugh于1990年发布，设置的初衷是为了取代平衡树（比如红黑树）

跳表对比平衡树，有以下特点：

1. 跳表的实现和维护更加简单。相信大家都知道，红黑树的实现确实太复杂了，需要考虑很多的情况
2. 跳表的搜索，删除，添加的平均时间复杂度是O(long)

##### 使用跳表优化链表

例如，下图是一个非常普通的链表，而且该链表是有序的

![1578748296360](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/29-SkipList/Resource/1578748296360.png)

那怎么来对链表进行优化呢？

现在，可以将链表中的每一个节点想象成为一个公交车站，其中数组为公交站的名字，现在如果想从最左边开始，到达25这个公交站，按照现在的公交路线，经过公交站的顺序是从左往右的，最终到达25公交站

那如何才能办到，减少经过中间公交站的次数呢？是否可以办到从3公交站直接到达25公交站呢？是可以的，如果现在开通一条快速公交，可以从3公交站直接到达25公交站的话，就不用经过中间的这些公交站，直接就到达25公交站了，这样就可以更快的到达最终想要达到的站，这就是对链表进行优化的基本思路。

所以对上图的链表进行初步优化的话，结果如下

![1578748980979](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/29-SkipList/Resource/1578748980979.png)

这样，就在原来公交线路的基础上，增设了一条快速公交，减少了中间经过的站点，所以如果现在想要达到某个站的话，就可以优先选择快速路线，如此一来，就可以节省时间。

那么，能否再更快一点呢？更快一点则就是再增设一条经过站点更少的公交线路，这样从起点到达终点经过的站点更少，可以进一步节省时间，所以可以在原来快速路线的基础上，再提升一层，组成一个新的途径站点。重复压缩经过的站点，直到更优为止。所以上面的公交线路，经过优化后的结果如下

![1578749509078](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/29-SkipList/Resource/1578749509078.png)

所以，上面的流程，就是链表的优化思路，其实就是在有序链表的基础上，增加了一个跳跃的功能

##### 跳表的搜索

搜索流程如下：

1. 从顶层链表的首元素开始，从左往右搜索，直到找到一个大于或等于目标的元素，或者到达当前层链表的尾部
2. 如果该元素等于目标元素，则表明该元素已被找到
3. 如果该元素大于目标元素或已经到达链表的尾部，则退回到当前层的前一个元素，然后转入下一层进行搜索

所以跳表的搜索实现如下：

```java
public V remove(K key) {
    keyCheck(key);
    Node<K,V> node = first;
    Node<K,V>[] prevs = new Node[level];
    boolean exist = false;
    for (int i = level - 1; i >= 0 ; i--) {
        int cmp = -1;
        while (node.nexts[i] != null && (cmp = compare(node.nexts[i].key,key)) < 0 ) {
            node = node.nexts[i];
        }
        prevs[i] = node;
        if (cmp == 0) exist = true;
    }
    if (!exist) return null;//说明不存在
    Node<K,V> removedNode = node.nexts[0];
    for (int i = 0; i < removedNode.nexts.length; i++) {
        prevs[i].nexts[i] = removedNode.nexts[i];
    }
    size--;
    //更新跳表的层数
    int newLevel = level;
    while (--newLevel >0 && first.nexts[newLevel] == null ) {
        level = newLevel;
    }
    return removedNode.value;
}
```

##### 跳表的添加

假设现在要往下面的链表中插入一个新的节点17（红色节点）

![1578754789371](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/29-SkipList/Resource/1578754789371.png)

分析：

1. 通过搜索，找到插入节点17的合适位置
2. 随机确定节点的层数
3. 更新对应层节点的next

根据思路，实现如下：

```java
public V put(K key, V value) {
    keyCheck(key);
    Node<K,V> node = first;
    Node<K,V>[] prevs = new Node[level];//用来存放插入节点的前驱节点
    for (int i = level - 1; i >= 0 ; i--) {
        int cmp = -1;
        while (node.nexts[i] != null && (cmp = compare(node.nexts[i].key,key)) < 0) {
            node = node.nexts[i];//更新node
        }

        if (cmp == 0) {
            //节点本来就存在，覆盖key对应的value
            V oldValue = node.nexts[i].value;
            node.nexts[i].value = value;
            return oldValue;
        }

        //保存前驱节点
        prevs[i] = node;
    }
    //说明当前key 不存在，并且确定前驱节点为node
    int newLevel = randomLevel();
    Node<K,V> newNode = new Node<>(key,value,newLevel);
    //遍历所有的前驱节点,更新每一层的连线
    for (int i = 0; i < newLevel; i++) {
        if (i >= level) {//说明新节点的层数大于原来节点的最大层数，直接从头节点连线到当前节点
            first.nexts[i] = newNode;
        } else {
            newNode.nexts[i] =  prevs[i].nexts[i];
            prevs[i].nexts[i] = newNode;
        }
    }
    //更新跳表的层数
    level = Math.max(level,newLevel);
    size++;
    return null;
}
```



##### 跳表的删除

流程如下：

1. 搜索对应的节点
2. 如果没搜索到，直接返回
3. 如果搜索到了，更新对应层节点的next

根据思路，实现如下：

```java
public V get(K key) {
    keyCheck(key);
    Node<K,V> node = first;
    //nexts的索引是0 - （level - 1）
    for (int i = level - 1; i >= 0 ; i--) {
        int cmp = -1;
        while (node.nexts[i] != null && (cmp = compare(node.nexts[i].key,key)) < 0 ) {
            //当前节点的key，小于要找的节点的ke||说明找到当前层的最后了，所以应该会退到上一个节点，然后往下一层继续找
            node = node.nexts[i];//更新node
        }

        if (cmp == 0) {
            //说明找到了
            return node.nexts[i].value;
        }
        //当前节点的key，大于要找的节点的key，进入下一层进行查找
        //返回到上一个节点，进入下一层继续查找
    }
    //直到最后，还没找到，说明不存在
    return null;
}
```

##### 跳表的层数

关于跳表层数的相关结论

- 跳表是按层构造的，底层是一个普通的有序链表，高层相当于是底层的“快速通道”
  - 在第i层中的元素按某个固定的概率P（通常为1/2或1/4）出现在第i + 1 层中，产生越高的层数，概率越低
    所以有以下结论
    1. 元素层数恰好等于1的概率为1 - P
    2. 元素层数大于等于2的概率为P，而元素层数恰好等于2的概率为P* （1 - P）
    3. 元素层数大于等于3的概率为P^2，而元素层数恰好等于3的概率为P^2* （1 - P）
    4. 元素层数大于等于4的概率为P^3，而元素层数恰好等于4的概率为P^3* （1 - P）
    5. ...
    6. 一个元素的平均层数是1 / (1- P)
- 当P = 1 / 2时，每个元素所包含的平均指针数量是2
- 当P = 1 / 4时，每个元素所包含的平均指针数量是1.33（从指针数量来看，跳表会比红黑树更节省内存，因为红黑树每个节点都有至少2个指针）

##### 跳表复杂度分

通过前面的分析，可以计算出跳表每一层的元素数量

1. 第一层（最底层）链表固定有n个元素
2. 第二层链表平均有n * p个元素
3. 第三层链表平均有n * p ^ 2个元素
4. 第K层链表平均有n * p ^ K个元素

所以有以下结论：

1. 最高层的层数为log(1/p) n,平均有1 / p个元素
2. 在搜索时，每一层链表的预期查找步数最多是1 / P ,所以总的查找步数是 -(log(1/p) n)，所以，时间复杂度为O(logn)