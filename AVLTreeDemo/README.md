#### AVL树

> AVL树是最早发明的自平衡二叉搜索树之一， AVL取名于两位发明者的名字，有人把AVL树叶念做“艾薇儿树”

**平衡因子**（Balance Factor）：某节点的左右子树的高度差

例如有以下的二叉搜索树

![1570623268318](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570623268318.png)

其中节点5,10,14位叶子节点，由于叶子节点的左右子树为空，因此左右子树的高度差为0，即叶子节点的平衡因子为0

其中节点12的平衡因子为1，因为12左子树的高度为1，右子树高度为0，左右子树的高度差为1

节点13的平衡因子也为1，因为13左子树的高度为2，右子树的高度为1，因此左右子树的高度差为1

节点4的平衡因子为-1，因为4左子树的高度为0，右子树的高度为1，因此左右子树的高度差为-1

节点9的平衡因子为-3，因为9的左子树高度为0，右子树高度为3，因此左右子树的高度差为-3

节点7的平衡因子为-2，因为7的左子树高度为2，右子树高度为4，因子左右子树的高度差为-2

**AVL树的特点**

- 每个节点的平衡因子只可能是1,0，-1（绝对值 ≤ 1，如果超过1，称之为“失衡”）
- 每个节点的左右子树高度差不能超过1
- 搜索，添加，删除，的时间复杂度为O(logn)

下图即为一棵AVL树及每个节点的平衡因子

![1570623592432](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570623592432.png)

**平衡对比**

假设现在

输入如下数据：35,37,34,56,25,62,57,9,74,32,94,80,75,100,16,82

普通二叉搜索树为

![1570624087035](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570624087035.png)

如果是一棵AVL树的话，则是这样的

![1570624833668](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570624833668.png)

可见，AVL树可以保证平衡因子在指定的范围内

通过前面章节，我们已经了解了二叉搜索树，AVL树和红黑树是在二叉搜索树的基础上，进行了优化，因此有以下的继承结构，通过该结构，可以继续实现AVL树

![1570625030679](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570625030679.png)

在进行实现一棵AVL树之前，我们先来了解以下一些概念

- #### 添加导致失衡

下图为一棵二叉搜索树的一部分

![1570625888661](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570625888661.png)

现我们往下面这棵树中添加13，添加后的结果为

![1570626477296](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570626477296.png)

在没有添加13之前，这棵子树是平衡的，但是由于13的添加，导致该树节点14失衡，此时14的平衡因子为2，同样的，此时15也失去了平衡，由于右边高度整体的增加，导致节点9也失去了平衡，此时9的平衡因子为-2

添加13后

- 可能导致的最坏情况是：**所有祖先节点都失衡**
- 父节点，非祖先节点，都不可能失衡

可以通过以下方法来解决失衡的问题

- #### LL-右旋转（单旋）

假设现有如下的部分二叉树

![1570626806171](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570626806171.png)

从图中可以看到，当前子树处于平衡状态，节点n的左右子树高度相等，节点p的左右子树高度相等，节点g的右子树比左子树高度低1，即当前平衡因子为1

现在往节点n的左子树中，添加一个子节点[下图]，添加以后，当前节点g失去平衡

![1570627003549](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570627003549.png)

这种情况下， 我们可以进行有旋转，来平衡

![1570627261094](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570627261094.png)

首先断开g的左边，再断开p的右边，断开后的结果为

![1570627355417](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570627355417.png)

然后通过修改节点的左右关系，来进行旋转，即

- g.left = p.right
- p.right = g
- 让p称为这棵子树的根节点

修改完成后的结果为

![1570627588929](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570627588929.png)

经过顺序调整后的结果为

![1570627670863](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570627670863.png)

通过旋转以后，g达到了平衡，并且旋转完成后，仍然是一棵二叉搜索树：T0 < n < T1 < p < T2 < g < T3，没有破坏二叉搜索树的性质，并且由于高度没有增加，因此整棵树也达到了平衡

**还需要注意维护的内容**

- T2,p,g的parent属性
- 先后更新g,p的高度

------

- #### RR - 左旋转（单旋）

同样的，假设现在有如下的部分二叉树

![1570628481314](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570628481314.png)

现在，该子树处于平衡状态，当往n的右子树中，添加一个子节点后，结果变为下图，节点g的平衡因子变为的-2，此时节点g失衡

![1570628605468](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570628605468.png)

此时，需要做事情为进行左旋转

![1570628668575](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570628668575.png)

同样的，先断开节点g的右边，再断开p的左边，断开后的结果为

![1570628739453](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570628739453.png)

然后通过修改节点的左右关系，来进行旋转，即

- g.right = p.left
- p.left = g
- 让p成为这棵子树的根节点

![1570628844967](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570628844967.png)

最终调整后的效果如下

![1570628892176](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570628892176.png)

此时，该子树的高度有恢复到之前的高度，并且整棵树恢复了平衡，恢复后仍然为一个二叉搜索树，T0 < g < T1 < p < T2 < n < T3

**还需要注意维护的内容**

- T1,p,g的parent属性
- 先后更新g,p的高度

------

- #### LR - RR左旋转，LL右旋转（双旋）

假设现有如下的二叉搜索树子树

![1570629544879](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570629544879.png)

像这种情况，如果依然是往节点n的任意子树中添加一个节点的话，添加后的结果为，此时，导致g的平衡因子变为2，该二叉搜索树失衡

![1570629673224](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570629673224.png)

我们可以先对p进行左旋转

![1570629814907](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570629814907.png)

根据前面的经验，我们容易知道，旋转后的结果为

![1570629872356](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570629872356.png)

这样搞定之后，变成了我们LL右旋转的情况，此时对g进行右旋转

![1570630008474](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570630008474.png)

旋转完成后的结果

![1570630050478](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570630050478.png)

通过这样的旋转之后，该子树回到了平衡状态

------

- #### RL - LL右旋转，RR左旋转（双旋）

现有如下的一个二叉搜索树子树

![1570630202007](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570630202007.png)

往节点n的左右任意子树中添加一个子节点后，节点g的平衡因子变为了2，整个二叉搜索树失去平衡

![1570630284643](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570630284643.png)

首先对p进行一次右旋转

![1570630366700](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570630366700.png)

旋转后的结果为

![1570707247036](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570707247036.png)

这样搞定之后，变为了RR左旋转的情况，因此我们需要对g左一次左旋转

![1570707136587](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570707136587.png)

通过旋转以后，最终的结果为下图所示，最终整一个二叉树又回到了红线以上，并且g,n,p，三个节点都平衡

![1570630556037](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570630556037.png)

通过上面的分析以后，旋转的核心代码为

```java
/*
* 左旋转
* */
private void rotateLeft(Node<E> grand) {
    Node<E> parent = grand.right;
    Node<E> child = parent.left;
    grand.right = child;
    parent.left = grand;
    afterRotate(grand,parent,child);
}

/*
 * 右旋转
 * */
private void rotateRight(Node<E> grand) {
    Node<E> parent = grand.left;
    Node<E> child = parent.right;
    grand.left = child;
    parent.right = grand;
    afterRotate(grand,parent,child);
}

private void afterRotate(Node<E> grand,Node<E> parent,Node<E> child){
    //让parent成为子树的根节点
    parent.parent = grand.parent;
    if (grand.isLeftChild()) {
        grand.parent.left = parent;
    } else if (grand.isRightChild()) {
        grand.parent.right = parent;
    } else {
        //grand是root节点
        root = parent;
    }

    //更新child的parent
    if (child != null) {
        child.parent = grand;
    }

    //更新grand的parent
    grand.parent = parent;

    //更新grand，parent的高度
    updateHeight(grand);
    updateHeight(parent);
}
```

- #### 统一所有旋转操作

以下是不同情况下，失衡与修复平衡后的结果

![1570710549415](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570710549415.png)

![1570710566381](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570710566381.png)

![1570710602463](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570710602463.png)

![1570710632329](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570710632329.png)

针对不同的情况，我们可以通过同样的一个逻辑来进行处理

由于二叉搜索树的性质知道，节点中的元素，从左到右依次排序是从小到大的一个顺序，因此不管在旋转前，还是旋转后，都是同样的情况，并且根据上图的4中情况发现，旋转后的结果是一样的，因此我们只需要清除最终结果的顺序是多少，就可以了

通过这种方式，转换成代码为

```java
private void rotate(
        Node<E> r,// 子树的根节点
        Node<E> a,Node<E> b,Node<E> c,
        Node<E> d,
        Node<E> e,Node<E> f,Node<E> g
        ) {
    //让d成为子树的根节点
    d.parent = r.parent;
    if (r.isLeftChild()) {
        r.parent.left = d;
    } else if (r.isRightChild()) {
        r.parent.right = d;
    } else  {
        root = d;
    }
    //a- b- d
    b.left = a;
    if (a != null) {
        a.parent = b;
    }
    b.right = c;
    if (c != null) {
        c.parent = b;
    }
    updateHeight(b);
    //e - f - g

    f.left = e;
    if (e != null) {
        e.parent = f;
    }
    f.right = g;
    if (g != null) {
        g.parent = f;
    }
    updateHeight(f);
    //b - d - f
    d.left = b;
    d.right = f;
    b.parent = d;
    f.parent = d;
    updateHeight(d);
}
```

然后在调用的时候，将节点对应传入参数即可

```java
private void rebalance2(Node<E> grand){
    Node<E> parent = ((AVLNodel<E>)grand).tallerChild();
    Node<E> node = ((AVLNodel<E>)parent).tallerChild();
    if (parent.isLeftChild()) { //L
        if (node.isLeftChild()) {
            //LL
			rotate(grand,node.left,node,node.right,parent,parent.right,grand,grand.right);
        } else  {
            //LR
            rotate(grand,parent.left,parent,node.left,node,node.right,grand,grand.right);
        }
    } else  {//R
        if (node.isLeftChild()) {
            //RL
            rotate(grand,grand.left,grand,node.left,node,node.right,parent,parent.right);
        } else  {
            //RR
            rotate(grand,grand.left,grand,parent.left,parent,node.left,node,node.right);
        }
    }
}
```

- #### 删除导致的失衡

假设现在有如下的一棵二叉搜索树，我们要对 其做删除16的操作

![1570712880285](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570712880285.png)

可以看到，在删除16之前，整棵树处于平衡状态，当我们删除掉16以后，节点15的平衡因子变为了2,15失去了平衡[下图]，需要注意，在做删除操作时，只可能导致**父节点**失衡，除付节点一万的其余节点都不可能失衡

![1570712978276](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570712978276.png)

因此，在这种失衡的情况下，我们也需要通过旋转的方式，来恢复二叉树的平衡

- #### LL - 右旋转(单旋)

假设现在有以下的一棵平衡二叉树

![1570713914898](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570713914898.png)

当我们将红色方块部分的节点删除以后

![1570714013993](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570714013993.png)

删除以后，节点g的平衡因子变为了2，g失去了平衡，由于现在仍然是LL的情况，因此我们现在需要多g进行右旋转

![1570714070235](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570714070235.png)

旋转之后的结果为

![1570714109811](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570714109811.png)

旋转之后，节点g恢复到了平衡状态，由于整一棵子树的高度没有发生变化，这整一棵树的高度也没有发生变化，因此恢复了平衡。

**但是**如果

1. 绿色节点不存在，更高层的祖先节点也可能失衡，需要再次恢复平衡，然后又可能导致更高层的祖先节点失衡。。。[下图]，在这种情况下，整棵子树的高度减小了1，也就意味着，更高节点会失衡，因此需要再次恢复平衡，直到整棵树恢复平衡为止![1570714417741](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570714417741.png)
2. 在极端的情况下， 所有的祖先节点都需要进行恢复平衡的操作，共O(logn)次调整

- #### RR - 左旋转(单旋)

同样的，现在有以下的一棵平衡二叉树

![1570714717986](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570714717986.png)

当我们删除掉红色方块的节点后，节点g的平衡因子变为了-2，g失去了平衡

![1570714796859](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570714796859.png)

因此现在需要对节点g进行一次左旋的操作

![1570714842755](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570714842755.png)

旋转完成后的结果为

![1570714865148](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570714865148.png)

旋转完成后，三个节点都恢复了平衡，可以看到，恢复平衡后，整一棵子树的高度没有发生变化，因此整棵树的变化也没有发生变化，因此整一棵树仍然处于平衡状态

**但是**如果

1. 上图中绿色的方块不存在[下图]，旋转完成后的子树，高度减少了1，这样就可能导致再网上的节点失去平衡，因此需要做LL中的操作，来让整棵树恢复平衡

![1570715099878](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715099878.png)

- #### LR - RR坐旋转，LL右旋转(双旋)

同样的，现在有如下的一棵二叉搜索树

![1570715227281](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715227281.png)

我们删除掉红色方块的节点之后，节点g的平衡因子变为了2，节点g失去平衡

![1570715280896](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715280896.png)

因此我们需要先对p进行左旋转

![1570715331918](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715331918.png)

旋转完成以后，再对节点g进行右旋转

![1570715369276](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715369276.png)

最终旋转完成后的结果

![1570715396076](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715396076.png)

旋转完成后，子树的高度没有发生变化

- #### RL- LL右旋转，RR左旋转(双旋)

同样的，现在有如下的一棵二叉树

![1570715594449](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715594449.png)

我们删除红色方块的节点之后

![1570715711026](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715711026.png)

此时，节点g的平衡因子变为了-2，节点g失去了平衡，因此需要对p进行右旋转的操作

![1570715764392](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715764392.png)

旋转完成之后，再需要对节点g进行右旋转的操作

![1570715791584](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570715791584.png)

最终旋转完成后的结果

![1570716018833](https://github.com/MSTGit/Algorithm/blob/master/AVLTreeDemo/Resource/1570716018833.png)

因此删除节点旋转逻辑与添加接天的旋转逻辑相同！

- #### 总结

> 添加
>
> - 可能会导致**所有祖先节点**都失衡
> - 只要让高度最低的失衡节点恢复平衡，整棵树就恢复平衡[仅需O(1)次调整]

> 删除
>
> - 只可能会导致**父节点**失衡
> - 让父节点恢复平衡后，可能会导致更高层的祖先节点失衡[最多需要O(logn)次调整]

> 平均时间复杂度
>
> - 搜索：O(logn)
> - 添加：O(logn)，仅需O(1)次的旋转操作
> - 删除：O(logn)，最多需要O(logn)次的旋转操作



本节内容略显抽象，建议结合源码阅读。
