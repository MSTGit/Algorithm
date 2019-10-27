- #### 集合(Set)

集合也是一种数据结构，其中集合有以下的特点

1. 不存放重复的元素
2. 常用语去重
   - 存放新增的IP，统计新增IP量
   - 存放词汇，统计词汇量

集合的接口大概有以下几个

```java
int size();
boolean isEmpty();
void clear();
boolean contains(E element);
void add(E element);
void remove(E element);
void traversal(Visitor<E> visitor);
```

🤔集合的内部实现能否直接利用以前学过的数据结构？

1. 动态数组
2. 链表
3. 二叉搜索树(AVL树，红黑树)

在这里我们通过两种方式来进行实现：

1.使用链表来实现集合，其实现方式很简单，几乎重用了我们链表的逻辑，由于集合不存放重复的元素，因此我们需要对集合的添加方法进行处理，处理方法如下：

```java
public void add(E element) {
    int index = list.indexOf(element);
    if (index != List.ELEMENT_NOT_FOUND) {
        list.set(index,element);
    } else {
        list.add(element);
    }
}
```

完整的实现方法可以查阅demo源码<ListSet>；

1.使用红黑树来实现集合，使用红黑树使用集合，以上方法可以全部沿用红黑树中的方法，因为在红黑树中，我们以前在实现时，就支持不存放重复的元素，因此可以直接调用红黑树的方法来进行实现。

与链表的实现方式一样，完整的实现方法可以查阅demo源码<TreeSet>；

- #### ListSet VS TreeSet

由于ListSet与TreeSet在实现方式上的差异，我们来对两种实现的效率进行对比，结合前面对链表与红黑树的介绍，我们可以得出以下结论

|                | ListSet | TreeSet |
| :------------: | :-----: | :-----: |
| 添加时间复杂度 |  O(n)   | O(logn) |
| 删除时间复杂度 |  O(n)   | O(logn) |
| 搜索时间复杂度 |  O(n)   | O(logn) |

最终体现到结果上是这样的：

我们对其做单词分析，将一些文件，导入到内存，然后拆分为一个个单词，最终将这多个单词利用ListSet与TreeSet来做添加，删除，搜索操作，最终得出的的时间差异结果如下（可在demo中进行测试）

![1571833942461](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1571833942461.png)

最终，我们看到，通过ListSet来对25万个单词进行添加，删除，搜索，去重，一共花了7秒钟的时间，但是红黑树只花了0.169秒，通过时间的比较，我们就能直观的感受到红黑树的强大。

但是有一点需要注意，使用红黑树来实现集合，有一个限制，就是红黑树中的元素，必须具有可比较性，即可以比较大小。