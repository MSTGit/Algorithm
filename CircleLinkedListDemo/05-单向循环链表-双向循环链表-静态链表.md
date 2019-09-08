- #### 单向循环链表

什么叫单向循环链表呢？相对于我们之前所了解的链表，多了一个循环，那我们来看看什么是单向循环链表。

普通的 单向链表

![1567932796325](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567932796325.png)

单向循环链表是这样的，最后一个节点的next指向了链表中的第一个节点，与我们之前学习的环形链表比较相似

![1567932899631](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567932899631.png)

通过前面的知识，我们已经能自己实现一个单向循环链表了，所以，我们先自己来实现一个单向循环链表吧，实现完成以后，再看看单向循环链表到底有什么用。

其实单向循环链表非常的简单，只需要在我们之前的单向链表当中，稍微做一点改进就可以了

添加方法的修改**add(int index, E element)**

```java
public void add(int index, E element) {
        rangeCheckForAdd(index);
        System.out.println("index == " + index);
        if (index == 0) {
          Node<E> newFirst =  new Node<E>(element,first);
          //拿到最后一个节点
           Node<E> last = (size == 0) ? newFirst : node(size - 1);
           last.next = newFirst;
           first = newFirst;
        } else  {
            Node<E> prev = node(index - 1);
            prev.next = new Node<>(element,prev.next);
        }
        size++;
    }
```

我们在添加元素的时候，只需要在index == 0 时，去维护循环的那一根线就可以了，不过，需要注意一种特俗的情况，即当一开始，链表为空的时候这种情况，添加完元素以后，为下图的所示，所以需要做特俗的判断

![1567933607309](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567933607309.png)

删除节点 **E remove(int index)**

同样的，在删除节点时，需要注意当删除的是头节点的情况，需要做特俗处理，并且需要注意的是，当size为1的时候，也需要特殊处理，否则会出现无法删除的情况，最终删除的代码如下所示

```java
public E remove(int index) {
        rangeCheck(index);
        Node<E> node = first;
        if (index == 0) {
            if (size == 1) {
                first = null;
            } else  {
                //拿到最后一个节点
                Node<E> last = node(size - 1);
                first = first.next;
                last.next = first;
            }
        } else  {
            Node<E> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
        }
        size--;
        return node.element;
    }
```

通过以上修改以后，单向循环链表通过了我们`LinkedListDemo`类的测试。接下来，我们看看双向循环链表。

- #### 双向循环链表

首先我们来回顾以下前面所介绍的双向链表

![1567825581248](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567825581248.png)

在以前，双向循环链表，如果是头节点，它的prev指向的是null，如果是尾节点，它的next指向的是null

如果是双向循环链表，则是这样的

![1567935768645](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567935768645.png)

如果是头节点，头节点的prev指向的是链表的尾节点，如果是尾节点，则尾节点的next指向的是头节点，那么在这种情况下，应该如何做链表的添加和删除呢？同样的，我们在原来双向链表的基础上进行修改，参照`CircleLinkedList.java`类

添加过程分析

假设有如下所示的链表对象，添加66的元素

![1567936287138](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567936287138.png)

如果我们是往最后的位置添加该节点，则添加完成后的引用应该是这样的

![1567936769142](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567936769142.png)

不过需要注意的是，当往链表最前面的节点添加元素时，也需要特殊的处理。

通过以上分析，将代码调整后如下所示**add(int index, E element)**

```java
 public void add(int index, E element) {
        rangeCheckForAdd(index);

        if (index == size) {
            Node<E> oldLast = last;
            last = new Node<>(oldLast,element,first);
            if (oldLast == null) {
                first = last;
                first.next = first;
                first.prev = first;
            } else  {
                oldLast.next = last;
                first.prev = last;
            }
        } else  {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(prev,element,next);
            next.prev = node;
            prev.next = node;
            if (index == 0) {
                first = node;
            }
        }
        size++;
    }
```



删除过程代码调整**E remove(int index)**

```java
 public E remove(int index) {
        rangeCheck(index);
        Node<E> node = first;
      if (size == 1) {
            first = null;
            last = null;
      } else  {
          Node<E> node = node(index);
          Node<E> prev = node.prev;
          Node<E> next = node.next;
          prev.next = next;
          next.prev = prev;
          if (node == first) {//等价于index == 0
              first = next;
          }
          if (node == last) {//等价于index == size - 1
              last = prev;
          }
      }
        size--;
        return node.element;
    }
```

通过以上修改以后，同样通过了我们`LinkedListDemo`类的测试。说明以上修改没有问题

接下来，做一个小练习吧！

练习 - 约瑟夫问题

假设圈里面是8个人，8个人排成一个圈，每个人都又自己的序号，从序号为1的人开始数数，数到3的时候枪毙，然后下一位又从1开始数数，重复如此，最终看看谁能活下来。

![1567938152208](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567938152208.png)

这个问题，通过借助本文的循环链表就非常好解决，不过为了把循环链表的威力发挥到最大，我们可以做下面一些事情

>  如何发挥循环链表的最大威力？
>
> - 增设一个成员变量和三个方法
>
>   > 成员变量：current：用于指向某个节点
>   >
>   > 三个方法：
>   >
>   > - void reset()：让current指向头节点first
>   > - E next()：让current往后走一步，也就是current= current.next
>   > - E remove()：删除current指向的节点，删除成功后，让current指向下一个节点

current如下所示

![1567939060989](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567939060989.png)

以下是方法的实现

**void reset()**

```java
public  void reset() {
    current = first;
}
```

**E next()**

```java
public E next() {
    if (current == null) return  null;
    current = current.next;
    return current.element;
}
```

**E remove()**

```java
public E remove() {
    if (current == null) return  null;
    Node<E> next = current.next;
    E element = remove(current);
    current = next;
   return element;
}
```

通过封装的方法与成员变量，即可轻松的解决上面的约瑟夫问题。代码如下<建议直接下载源码进行阅读>

```java
static  void josephus(){
    CirleLinkedList<Integer> list = new CirleLinkedList<>();
    for (int i = 1; i <= 8 ; i++) {
        list.add(i);
    }
    list.reset();//指向头节点
    while (!list.isEmpty()) {
        list.next();
        list.next();
        System.out.println( list.remove());
    }
}
```

- #### 静态链表

前面说介绍的链表，都是依赖于指针（引用）实现的，有些编程语言是没有指针的，比如早期的BASIC,FORTRAN语言，没有指针的情况下，如何实现链表？

- 可以通过数组来模拟链表，称为静态链表
- 数组的每个元素存放2个数据：值，下一个元素的索引
- 数组0位置存放的是头节点信息

![1567949993206](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567949993206.png)

将链表访问轨迹则为这样的、

![1567950090059](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567950090059.png)

串起来后，是这样的

![1567950135818](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567950135818.png)

🤔如果数组的每个元素只能存放一个数据呢？

那就使用两个数组，一个数组存放索引关系，一个数组存放值

练习：ArrayList进一步优化

![1567950359454](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567950359454.png)

在之前，我们的ArrayList是存在性能问题的，例如上图的一个数组对象，如果我们是头部进行新增元素或则删除元素，会对所有的元素进行移动操作

**删除前面的元素**

我们可以再增加一个成员变量，用来保存首元素的位置，并且永远记录首元素的位置，这样在删除元素时，就不用再对数组中的每个元素进行移动操作了

![1567950718653](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567950718653.png)

**往最前面添加元素**

如果我们要再数组的首节点添加元素的话，也是对首元素进行操作，比如现在有以下数组

![1567951002495](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567951002495.png)

假设我们还要继续往数组的最前面插入元素，我们则可以通过在数组尾部添加，下图所示

![1567951132417](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567951132417.png)

这种情况，如果我们要对数组进行遍历的话，就可以从8的位置遍历，然后索引增加，下一个访问索引为0的元素，我们可以对索引进行取余操作，就可以了

**往中间添加元素**

如果我们想要在数组中间插入元素

![1567951728868](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567951728868.png)

以前的话，我们是将后面的所有元素，往后移动，但是引入了记录首元素位置后，我们可以选择移动元素较少的一边，如将上图中的22，33往前移动

![1567951912498](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567951912498.png)

然后就可以在空出来的地方，插入新的元素

![1567952014922](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567952014922.png)

**删除中间的元素**

与往中间添加元素相似，如果我们要删除中间的元素，也是选择元素较少的一方来进行元素移动

![1567952167732](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567952167732.png)

删除完成后

![1567952249000](https://github.com/MSTGit/Algorithm/blob/master/CircleLinkedListDemo/Resource/1567952249000.png)

通过这种操作，可以极大的减少数组中元素的挪动，进而达到性能优化的目的。

