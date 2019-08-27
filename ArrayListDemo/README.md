
- #### 什么是数据结构

> 数据结构是计算机存储、组织数据的方式

| 数据结构类型 |                线性结构                |                        树形结构                        |     图形结构     |
| :----------: | :------------------------------------: | :----------------------------------------------------: | :--------------: |
|     示例     | 线性表（数组、链表、栈、队列、哈希表） | 二叉树、AVL树、红黑树、B树、堆、Trie、哈夫曼树、并查集 | 邻接矩阵、邻接表 |

线性结构示意图

![1566035015907](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566035015907.png)

树形结构示意图

![1566035057443](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566035057443.png)

图形结构示意图



![1566035090827](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566035090827.png)

提示：在实际应用中，根据使用场景来选择最合适的数据结构

- #### 线性表

> 线性表是具有n个相同类型元素的有限序列（n ≥ 0）；如图所示

![1566035864320](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566035864320.png)

其中：

a(1)是首节点（首元素），a(n)是尾节点（尾元素）

a(1)是a(2)的前驱结点，a(2)是a(1)的后继节点

> 常见的线性表有

1. 数组
2. 链表
3. 栈
4. 队列
5. 哈希表（散列表）

- #### 数组

> 数组是一种顺序存储的线性表，所有元素的内存地址是连续的

例如以下代码

```java
int[] array = new int[]{11,22,33};
```

其对应的内存结构示意图如下所示

![1566036738665](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566036738665.png)

在很多编程语言中，数组都有一个致命的缺点，无法动态修改数组的容量。

在实际开发中，我们更希望数组的容量是可以动态改变的

- #### 动态数组接口设计

接下来，我们可以自己设计一个动态数组的类，其中为该类提供以下接口，供外界使用，分别为

```java
int size();//获取元素的数量
boolean isEmpty();//判断数组是否为空
boolean contains(E element);//判断数组是否包含某个元素
void add(E element); //往数组的最前面添加元素
E get(int index); //获取数组中索引为index的元素
E set(int index，E element); //往数组的第index位设置元素
void add(int index,E element); //往数组的index位添加元素
E remove(int index); //移除数组中索引为index的元素
int indexOf(E element); //查看元素的位置
void clear(); //删除数组中的所有元素
```

 - - ##### 部分重要接口解析

   - 添加元素 - add(E element)

   在添加元素时，我们会讲新添加的元素放到数组的最后面，因此用图表示如下

   ![1566134574339](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566134574339.png)

    ![1566134788687](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566134788687.png)

   通过观察，我们可以发现，新增元素是，是直接往size的位置存放数据，因此就会有以下代码

   ```java
   elements[size] = element;
   size++;
   ```

   

   - 删除元素- remove(int index)

   我们假设此时，size = 7 ,index  = 3.通过图行表示如下

   ![1566135655813](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566135655813.png)

   删除第3个元素，需要对后面元素做如下的操作

   1. 将55移动到原来44的位置![1566136152730](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566136152730.png)
   2. 讲66移动到原来55的位置![1566136192849](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566136192849.png)
   3. 将77移动到原来66的位置![1566136239653](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566136239653.png)

   最后移动完成后，还需要做一个size--的操作。详细代码请通过demo查看

   🤔最后一个元素怎么处理呢?

   - 在某个位置添加元素 - add(int index, E element)

   我们假设此时size = 5 , index = 2.则我们通过图形表示如下

   ![1566137184817](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566137184817.png)

   同样的，我们也需要对index = 2之后（包括）的元素进行往后移动，其操作如下所示

   1. 先将55往后挪动到下一个元素的位置![1566137399761](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566137399761.png)
   2. 然后再将44挪动到原来55的位置![1566137441884](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566137441884.png)
   3. 再然后将33挪动到原来44的位置![1566137491839](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566137491839.png)
   4. 最后，将空出来的位置，用来存放新的元素![1566137540105](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566137540105.png)
   5. 最后将size++

   ⚠对数据的挪动要讲究顺序，否则会出现下图这种异常的情况

   ![1566137709352](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566137709352.png)

   ![1566137737543](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566137737543.png)

   ![1566137758293](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566137758293.png)

- #### 如何扩容

1. 假设当前数组的容量为4，并且该数组已经全部存满了元素，如以下图形所示![1566139803985](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566139803985.png)
2. 则如果还需要往该数组中存放元素，则需要重新申请一块更大的连续内存，来保存数组中的元素，如下图所示![1566140258724](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566140258724.png)
3. 将原来数组中的元素，对应拷贝到新的存储空间中![1566140524043](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566140524043.png)
4. ArrayList指向新申请的存储空间地址![1566140595759](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566140595759.png)
5. 最后，原来的数组，由于没有任何引用指向该存储空间，则该存储空间的内存会被系统回收掉![1566140678916](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566140678916.png)

详细与扩容相关的代码，请查阅demo文件中ArrayList类中的ensureCapacity(int capacity)方法实现

- #### 对象数组

在前面的例子中，我们在数组中存放的数据都是int类型，因此在数组指向的存储空间中，是直接存放在其中的，但是在实际情况中，可能存放的是各种各样的类型，因此，在实际情况下，对象是通过下面的方式进行存储的

创建一个能存放7个Object对象的数组

```java
Object[] objects = new Object[7]
```

那么objects的内存布局如下图所示![1566214957377](https://github.com/MSTGit/Algorithm/blob/master/ArrayListDemo/resource/1566214957377.png)

通过内存布局图，我们可以清楚的知道，数组中每个元素实际存储的是Object对象的内存地址，其内存地址指向该Object对象。由于在实际开发中，每个object所占用的存储空间可能并不一样，为了统一数组中每个元素所占用的空间都一样，因此采用存储对象地址的方式，来进行存储，从而达到可以存放不同类型对象的效果。

##### 对象数组内存管理细节

在前面 **删除元素- remove(int index)**部分，我们是直接将元素往前挪动，后面空出来的空间，不做处理，不会有问题，但是当我们在数组中存入对象的时候，就不能这样做了，因为不做处理的话，会导致看似被删掉的对象，实际并没有销毁，因为数组中的元素，仍然引用的该对象，使其不会被销毁，因此我们应该做如下的操作

```java
    public E remove(int index) {
        rangeCheck(index);
        E old = elements[index];
        for (int i = index + 1; i <= size - 1; i++) {
            elements[i - 1] = elements[i];
        }
        elements[--size] = null;
    return old;
    }
```

同样的，清空数组中说有元素时，也需要做清空对象操作

```java
    public void clear () {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
         size = 0;
    }
```

