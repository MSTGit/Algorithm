🤔在n个动态的整数中搜索某个整数？(查看其是否存在)

如下列整数

![1569497236143](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1569497236143.png)

方法一：

使用动态数组存放元素，从第0个位置开始遍历搜索，这种方法的平均复杂度为O(n)

方法二：

维护一个有序的动态数组[下图]，使用二分搜索，最坏的时间复杂度为O(logn)

但是添加删除的平均复杂度为O(n)

![1569497457199](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1569497457199.png)

因此，针对这种需求，有没有更好的方案呢？

**使用二叉搜索树，添加，删除，搜索的最坏时间复杂度均可优化到O(logn)**

- #### 二叉搜索树(Binary Search Tree)

> 二叉搜索树是二叉树的一种，是应用非常广泛的一种二叉树，英文简称BST
>
> - 又被称为:二叉查找树，二叉排序树

如下就是一棵二叉搜索树

![1569497896384](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1569497896384.png)

特点：

- 任意一个节点的值都**大于**其**左**子树所有节点的值
- 任意一个节点的值都**小于**其**右**子树所有节点的值
- 它的左右子树也是一棵二叉搜索树

**二叉搜索树可以大大提高搜索数据的效率**

**二叉搜索树存储的元素必须具备可比较性**,如int，double等。如果比较的是自定义类型，需要指定比较的方式。不允许存储的值为null

- #### 二叉搜索树的接口设计

元素的数量

```java
int size();
```

是否为空

```java
boolean isEmpty();
```

清空所有元素

```java
void clear();
```

添加元素

```java
void add(E element);
```

删除元素

```java
void remove(E element);
```

是否包含某元素

```java
boolean contains(E element);
```

**需要注意的是**

对于我们现在使用的二叉树来说，它的元素是没有索引的概念的，因此不设计

- #### 添加节点

比如往以下二叉树中添加12,1

![1569500936791](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1569500936791.png)

添加12后，二叉树变为

![1569501321226](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1569501321226.png)

添加1后，二叉树变为

![1569501443804](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1569501443804.png)

添加步骤：

1.找到父节点parent

2.创建新节点node

3.parent.left = node 或者parent.right = node

遇到相等值的元素该如何处理？

1.建议覆盖旧的值

代码如下：

```java
public void add(E element) {
        elementNotNullCheck(element);
        if (root == null) {
            //添加的第一个节点
            root = new Node<>(element,null);
            size++;
            return;
        }
        //添加的不是第一个节点
        //找到父节点
        Node<E> node = root;
        Node<E> parent = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element,node.element);
            parent = node;
            if (cmp > 0) {
                //右节点
                node = node.right;
            } else if (cmp < 0) {
                //左节点
                node = node.left;
            } else  {
                //相等
                return;
            }
        }
        //看看插入到父节点的那个位置
        Node<E> newNode = new Node(element,parent);
        if (cmp > 0) {
            //右边
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }
```

如果我们直接通过Integer类型来进行比较的话，我们当中的元素为7,4,9,2,5,8,11,3,12,1时，然后通过工具，打印出来的结果为[具体实现，请查阅源码]

![1569506613143](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1569506613143.png)

同样的，我们也可以通过自定义的类进行打印，比如我们现在有一个Person的类，其中根据Person的age大小进行比较，我们也可以得出以下比较结果

![1569506580583](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1569506580583.png)