- #### 映射(Map)

> Map 在有些变成语言中也叫做指点（dictionary，比如Python，Objective-C，Swift等）

其中Map的结构类似于如下的对应关系

![1571834952531](https://github.com/MSTGit/Algorithm/blob/master/MapDemo/Resource/1571834952531.png)

要求：**Map的每一个key是唯一的**

说到这里，我们就自己来设计一个Map吧！

##### Map提供的接口

```java
int size();
boolean isEmpty();
void clear();
V put(K key, V value);
V get(K key);
V remove(K key);
boolean containsKey(K key);
boolean containsValue(V value);
void traversal(Visitor<K,V> visitor);
```

类似Set，Map可以利用前面了解到的链表，二叉搜索树（AVL树，红黑树）等数据结构来实现。

由于在上一章节中，我们使用到了链表来实现Set，其性能非常差，因此在本章节中，直接使用红黑树来进行实现。

最终实现源码请查阅demo源码<TreeMap>

完成之后，我们也对其做了单词的统计，统计在一堆文件中，某个单词出现的次数。统计的方法也在demo源码中。

- #### Map与Set的比较

前面我们已经知道了，下图是一个Map

![1571834952531](https://github.com/MSTGit/Algorithm/blob/master/MapDemo/Resource/1571834952531.png)

当我们去掉Map的value，只留下Key之后

![1571840942857](https://github.com/MSTGit/Algorithm/blob/master/MapDemo/Resource/1571840942857.png)

我们发现，它就是一个Set，因为在Map中，它的key一定是唯一的。

所以：**Map的所有key组合在一起，其实就是一个Set**，因此Set可以间接利用Map来做内部实现

因此其内部实现可以参阅demo文件中的<TreeSet>