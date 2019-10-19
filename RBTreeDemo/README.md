- #### 红黑树

> 红黑树也是一种自平衡的二叉搜索树
>
> - 以前也叫做平衡二叉B树(Symmetric Binary B-tree)

首先，来了解以下红黑树的性质

红黑树必须满足一下5条性质

1. 节点是RED或者BLACK
2. 根节点是BLACK
3. 叶子节点(外部节点，空节点)都是BLACK(红黑树要把度为1或者0的节点，最终都要变为度为2的节点)
4. RED节点的子节点都是BLACK
   - RED节点的parent都是BLACK
   - 从根节点到叶子节点的所有路径上不能有2个连续的RED节点
5. 从任一节点到叶子节点的所有路径都包含相同数目的BLACK节点

![1570796428119](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1570796428119.png)

看完以后，是不是感觉很复杂？这都是什么鬼东西。。。

![1570796945999](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1570796945999.png)![1570796960730](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1570796960730.png)![1570796984766](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1570796984766.png)



那么:什么在这些规则的约束之下，就能保证平衡呢？

##### 请问下面这棵是红黑树吗？

![1570931199620](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1570931199620.png)

这不是一棵红黑树。结合上面的规则，红黑树要把度为1或者0的节点，最终都要变为度为2的节点，因此在节点38的地方，会虚拟出一个null的子节点，来使得节点38的度为2，由于有了该节点，就使得从任一节点到叶子节点的所有路径都包含相同数目的BLACK节点结论不成立，因此不是一棵红黑树。

- #### 红黑树的等价变换

![1571053392680](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571053392680.png)

我们现在尝试将黑色节点的红色子节点上升一层，与上一层节点在同一层，变化之后的结果

![1571053252045](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571053252045.png)

上升以后，我们发现，现在的这棵红黑树有点类似于我们前面介绍的B树。其对应的B树为

![1571053431717](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571053431717.png)

我们看到，转换后的B树为一棵4阶B树。因此有以下的结论

1. 红黑树和4阶B树（2-3-4树）具有等价性
2. BLACK节点与他的RED子节点融合在一起，形成1个B树节点
3. 红黑树的BLACK节点个数与4阶B树的节点总个数相等（上图红黑树中有5个黑色节点，B树种也有5个节点）

⚠网上有一些教程，用2-3树与红黑树进行类比，这是极其不严谨的，2-3树并不能完美匹配红黑树的所有情况

注意：由于图片空间大小有限，后面展示的红黑树都会省略NULL节点

- #### 红黑树 VS 4阶B树

红黑树1.红黑红

![1571054015985](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571054015985.png)

对应的B树节点

![1571054036764](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571054036764.png)

红黑树2.黑红

![1571054064438](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571054064438.png)

对应的B树节点

![1571054088476](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571054088476.png)

红黑树3.红黑

![1571054124211](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571054124211.png)

对应的B树节点

![1571054143379](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571054143379.png)

红黑树4.黑

![1571054171833](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571054171833.png)

对应的B树节点

![1571054196741](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571054196741.png)

🤔如果上图最底层的BLACK节点是不存在的，在B树中是什么样的情形？

答：B树只有根节点

##### 几个英文单词

![1571054487331](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571054487331.png)

根据上图，来理解一下以下几个单词

> parent：父节点
>
> sibling：兄弟节点（17与33互为兄弟节点，25与46互为兄弟节点）
>
> uncle：叔父节点（parent的兄弟节点，如50节点的uncle节点为25，因为parent节点为46,25与46节点互为兄弟节点）
>
> grand：祖父节点（parent的父节点，38为50的祖父节点）

##### 辅助函数

在编写红黑树代码时，需要使用到的一些辅助函数，因此在编写之前， 先封装好这些辅助函数

在Node类中新增一个获取兄弟节点的方法

```java
/*
* 获取兄弟节点
* */
public Node<E> sibling(){
    if (isLeftChild()){
        return parent.right;
    }
    if (isRightChild()){
        return parent.left;
    }
    //没有父节点，因此没有兄弟节点
    return null;
}
```

在红黑树中封装以下几个方法

```java
//给节点染色
private  Node<E> color(Node<E> node ,boolean color) {
    if (node == null)return node;
    //染色
    ((RBNodel<E>)node).color = color;
    return node;
}

//将节点染成红色
private Node<E> red(Node<E> node) {
   return color(node,RED);
}

//将节点染成黑色
private Node<E> black(Node<E> node) {
    return color(node,BLACK);
}

//获取当前节点的颜色
private boolean colorOf(Node<E> node) {
    return node == null ? BLACK : ((RBNodel<E>)node).color;
}

//判断当前节点是否为黑色
private boolean isBlack(Node<E> node) {
    return colorOf(node) == BLACK;
}
//判断当前节点是否为红色
private boolean isRed(Node<E> node) {
    return colorOf(node) == RED;
}
```

- #### 添加

假设现有以下一棵红黑树，需要注意的是，学习红黑树的时候，要尽量和B树的知识点结合起来，并且看到红黑树的时候，就要能联想出B树的形状

![1571056303498](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571056303498.png)

由前面B树部分知道

1. 在B树中，新元素必定是添加到叶子节点中
2. 4阶B树所有节点的元素个数X都符合1≤ X ≤ 3

另外需要注意，结合前面红黑树的条件，因此我们

1. 建议新添加的节点默认为RED，这样能够让红黑树的性质尽快满足（能满足红黑树条件1,2,3,5。性质4不一定满足）
2. 如果新添加的是根节点，染成BLACK即可

##### 添加的所有情况

由于红黑树是一棵4阶B树，因此上图包含了4阶B树的子节点的所有情况，由于B树在添加子节点的时候，只可能添加到最后一层，因此新添加的节点，只可能添加到最后一层节点的左或者右[下图]

![1571057152344](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571057152344.png)

但是，有些情况却是不合理的，比如25节点，此时，25的左右已经有子节点了，因此不可能存在，同样的46,76也如此，因此真正可能添加的地方如下图所示，所以在红黑树中添加一个子节点，一共有12种情况

![1571057275916](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571057275916.png)

**其中，有4中情况满足红黑树的性质4：parent为BLACK**[下图]，在这种情况下

> 同样也满足4阶B树的性质
>
> 因此不需要做额外的处理

![1571057482771](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571057482771.png)

有8种情况不满足红黑树的性质4：parent为RED（Double Red）

情况1：新加的节点在17的左边

![1571057805188](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571057805188.png)

情况2：新加的节点在17的右边

![1571057872628](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571057872628.png)

情况3：新加的节点在33的左边

![1571057987825](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571057987825.png)

情况4：新加的节点在33的右边

![1571058008677](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571058008677.png)

情况5：新加的节点在50的左边

![1571058077345](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571058077345.png)

情况6：新加的节点在50的右边

![1571058101560](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571058101560.png)

情况7：新加的节点在72的左边

![1571058127025](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571058127025.png)

情况8：新加的节点在72的右边

![1571058150981](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571058150981.png)

整合到一起为下列情况

![1571058202861](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571058202861.png)

##### 添加 - 修复性质4 - LL/RR

现有以下一棵红黑树，当前的情况是往Left>Left或者Right>Right两种出现了两个红色的情况，我们看到这种，首先就能想到可以使用旋转的方式来进行修复。不过，我们先站在B树的角度来进行分析，新的子节点52或者60添加进节点以后，必然会成为B树的一个节点，变成B树节点的前提是，节点52现在需要变为黑色节点46要变为红色节点，或者72要变为黑色节点76要变为红色节点，因此，我们首先需要做的就是对节点进行染色，然后需要调换46与50；72与76节点的父子关系，50与76分别成为对应子树的根节点

![1571139532529](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571139532529.png)

因此，最终通过对应的染色旋转以后的结果为（46坐旋转，76右旋转）

![1571140382649](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571140382649.png)

实现的步骤

1. parent染成BLACK，grand染成RED
2. grand进行单旋操作

##### 添加 - 修复性质4 - LR/RL

通过以下的一棵二叉树，然后添加对应的子节点后出现了两个红色的情况，即grand的Left>Right或者Right>Left为红色

![1571140641529](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571140641529.png)

通过前面的结论，依然是通过染色和旋转来修复性质4，修复完成后的结果为

![1571140819263](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571140819263.png)

实现步骤

1. 自己染成BLACK，grand染成RED
2. 进行双旋操作

> LR:parent 左旋转，grand右旋转
>
> RL:parent 右旋转，grand坐旋转

以上的介绍的4中情况，都属于uncle节点不是红色的，即节点48,52,60,74的uncle节点都为null，即为黑色。我们接下来在来看另外的4种需要修复的情况

##### 添加 - 修复性质4 - 上溢 - LL

通过下图，我们首先从B树角度出发，在当前节点中添加子节点10时，由于是4阶B树，因此会出现上溢，根据B树的性质，上溢，会先最中间的节点向上与父节点进行合并，然后剩下的元素分裂成为两个节点，所以如果我们先来考虑分裂后的两个节点

1. 分裂后的两个节点，17,33成为了子树的根节点，因此需要将两个节点染成黑色
2. 节点25上溢，与父节点进行合并
3. 对于25的父节点来讲，25的上溢对当前节点是在做添加操作，因此把节点25染成红色
4. 然后再对和并后的节点进行修复

![1571141441180](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571141441180.png)

因此上溢后的结果为

![1571142317231](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571142317231.png)

实现步骤

1. parent，uncle染成BLACK
2. grand向上合并，并且将grand节点染成RED，当做是新添加的节点进行处理

grand向上合并时，可能会发生上溢，若上溢持续到根节点，只需要将根节点染成BLACK

##### 添加 - 修复性质4 - 上溢 - RR

同样的，下图的情况和LL是一样的，因此也是同样的处理方式来进行修复

![1571142748909](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571142748909.png)

上溢合并后的结果为

![1571142805883](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571142805883.png)

实现步骤

1. parent，uncle染成BLACK
2. grand向上合并，并且将grand节点染成RED，当做是新添加的节点进行处理

##### 添加 - 修复性质4 - 上溢 - LR

下面是添加出现上溢的LR情况

![1571142961336](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571142961336.png)

上溢合并后的结果为

![1571143001423](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571143001423.png)



修复步骤与上溢的LL/RR一样

##### 添加 - 修复性质4 - 上溢 - RL

下面是添加出现上溢的RL情况

![1571143090711](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571143090711.png)

上溢合并后的结果为

![1571143122726](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571143122726.png)

修复步骤与上溢的LL/RR一样

到这里，红黑树添加的所有情况就分析完了，那我们来总结一下

1. 红黑树的添加一共有12种情况
2. 其中4种情况，添加后的父节点为黑色，此时不需要做任何处理
3. 另外8种情况，添加的时候，父节点为红色，不满足性质4
4. 8种需要修复的情况中，有4种情况新添加的节点的uncle节点不是红色，因此不会发生上溢，需要通过旋转来进行修复
5. 8种需要修复的情况中的另外4种，uncle节点是红色，只需要将新添加节点的parent节点与uncle节点染成黑色，grand节点染成红色，当做是新添加的节点，向上合并

- #### 删除

前面，我们在B树部分说到，B树最后真正被删除的元素都在叶子节点中。因此，在红黑树中，如果想删除任意一个节点，最后真正被移除掉的，一定是最后一层的叶子节点当中

##### 删除 - RED节点

比如现在有这样一棵红黑树

![1571225641786](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571225641786.png)

如果这个时候，我们删除掉红黑树的红色节点，如72.删除掉了以后，我们发现并没有违背红黑树的性质，依然是一棵红黑树，因此直接删掉就可以了，其他红色叶子节点也如此，因此删除红色节点的结论是**直接删除，不需要做任何调整**

##### 删除 - BLACK节点

同样的，我们通过上图，可以看到，如果删除黑色节点，有多种情况，其中可以分为3种

1. 拥有2个RED子节点的BLACK节点，**不可能被直接删除，因为会找他的子节点替代删除**，因此不用考虑这种情况
2. 拥有一个RED子节点的BLACK节点，是下图中的46和76节点![1571226058210](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571226058210.png)
3. 删除的是黑色叶子节点，如节点88

因此在删除过程中，真正需要处理的是另外两种情况中的三个几点

![1571226142219](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571226142219.png)

##### 删除 - 拥有1个RED子节点的BLACK节点

如下一个二叉树

![1571226536599](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571226536599.png)

如果我们发现，用于取代被删除节点的子节点是RED，那么我们可以知道，该节点是46或者76的情况

如果用于取代的节点不存在，那么可以判断出该节点为88的情况

如果将该子树中的46,76删除，然后通过改变父子关系后的结果为

![1571227038801](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571227038801.png)

我们看到，这个时候出现了双红的情况，因此此时我们需要将替代的子节点染成BLACK即可保持红黑树的性质，如下图

![1571227106907](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571227106907.png)

这样，就删除完成了

##### 删除 - BLACK叶子节点 - sibling为BLACK

删除BLACK的叶子节点，其实也分为2种情况，一种为父节点为黑色的叶子节点，一种为父节点为红色的叶子节点

![1571312577013](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571312577013.png)

如果该节点为二叉搜索树的话，当我们删除节点46或者88的时候，直接删除就好了，但是如果我们从B树的角度出发，当我们删除了46或者88两个叶子节点以后，由于删除的是叶子节点，因此会发生下溢的情况，当发生下溢的时候，由于B树的特性，会首先看是否可以从兄弟节点中借出元素来，如果可以借，就直接从兄弟节点中借出元素来，填充被删除节点的位置，其中兄弟节点可以借出节点的情况有以下三种

![1571312963380](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571312963380.png)

从图中可以看出，只有兄弟节点中，有红色节点的时候，才可以借出节点来，否则不能借

因此这种情况下

1. BLACK叶子节点被删除后，会导致B树节点下溢（比如删除88）
2. 如果sibling至少有一个RED子节点，就可以向兄弟节点借一个节点

但是，应该怎么借呢？

因此我们先来看可以借节点的**第一种**情况，假设有以下的一棵二叉树

![1571313347392](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571313347392.png)

按照我们以前的做法是，付节点应该到被删除节点的位置，兄弟节点中，挑一个节点到父节点的位置中

![1571313445119](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571313445119.png)

因此，首先我们先删除掉节点88

![1571313488728](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571313488728.png)

删掉以后会下溢，因此会从兄弟节点中挑选出一个节点，到父节点中，父节点移动到被删除节点的位置，因此其实要做的事情就是旋转，现在我们的需求是78移动到80的位置，80移动到88的位置，这就是我们前面AVL树种的LR情况，因此我们需要先对76进行左旋转，然后对80进行右旋转

最终旋转以后的效果为

![1571313849543](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571313849543.png)



不过需要注意的是，完成旋转操作以后，需要做以下两点

1. 旋转之后的中心节点要继承parent的颜色
2. 旋转之后的左右节点染成BLACK

可以借节点的**第二种**情况

![1571314446841](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571314446841.png)

这种情况也是一样的，删除掉88以后

![1571314495569](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571314495569.png)

会发生下溢的情况，结合前面的理解，我们很容易想到，现在是AVL树种的LL情况，因此我们只需要将80进行右旋转就可以了，其余操作与上一种情况一致，因此最后的结果是下图

![1571314625434](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571314625434.png)

然后来看看可以借节点的**第三种**情况

![1571314675242](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571314675242.png)

同样的，删除掉节点88以后的结果为

![1571314713811](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571314713811.png)

删除掉以后，我们发现，当前好像既是AVL树中的LL情况，又是AVL树中的LR的情况。对的，没错，两种都可以，都可以办到，最终的结果让80下到原来被删除节点的位置，在这里我们就选择简单的一种方式吧，我们使用LL的方式，因此最终旋转的结果如下图所示

![1571315027512](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571315027512.png)

不过需要注意的是，由于在这里旋转可以通过两种方式进行，因此如果使用LR的方式旋转的话，结果与上图不一致，但是最终的结果都是正确的。最终完成旋转以后，也要进行上面两种情况的染成操作

看完了兄弟可以借节点，那么我们接下来再来看看兄弟不能借的情况

通过前面的介绍，我们知道，如果兄弟节点能借子节点，sibling中有BLACK节点，并且至少有一个RED节点。那么反过来，当兄弟节点不能借的时候，sibling中依然有BLACK节点，但是没有RED子节点，如下面的情况，如果我们要删除88，我们应该怎么办呢？

![1571315988533](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571315988533.png)

根据前面B树的知识，我们知道，如果发生下溢的情况，并且兄弟节点又借不出子节点时，父节点会下溢与兄弟节点进行合并。因此整个流程如下所示

首先删除节点88

![1571316174134](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571316174134.png)

然后88的父节点下溢，与兄弟节点进行合并

![1571316234180](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571316234180.png)

最后需要将sibling染成RED，parent染成BLACK就可以修复红黑树的性质

但是如果被删除节点的parent是BLACK，并且sibling节点也不能借出子节点的时候[下图]，又应该怎么处理呢？

![1571316419605](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571316419605.png)

如果按照以前的逻辑进行修复的话，80会下溢[下图]

![1571316609015](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571316609015.png)

此时我们看到，parent也会发生下溢的情况，因此我们这个时候只需要把parent当做被删除的节点去进行处理就可以了

##### 删除 - BLACK叶子节点 - sibling为RED

另外一种情况，即删除的是黑色叶子节点，但是兄弟节点为黑色的情况，如下图

![1571318325147](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571318325147.png)

在这种情况下，如果我们删除黑色节点88，那肯定会发生下溢的情况，但是此时由于兄弟节点为红色，因此不能从兄弟节点借出节点出来，填充被删除的位置，那么我们就应该去找兄弟节点的子节点，看兄弟节点的子节点是否可以借出节点出来，修复红黑树的性质。

其实我们想做的事情很简单，我们就是想从兄弟节点中借节点出来，但是目前的情况，我们只能去找兄弟节点的子节点去接，所以我们想到的是，把兄弟节点的子节点变为我们的兄弟节点，这样我们就可以直接去找兄弟节点借了，示意图如下

![1571318775882](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571318775882.png)

这个时候，我们可以对80进行右旋转，旋转以后，节点55就会成为80的父节点，然后76就成为了80的左子树节点，最后的结果为

![1571318909520](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571318909520.png)

好了，现在我们又回到了sibling是BLACK的情况，因此我们可以套用我们之前的处理逻辑，最终的结果为

![1571319143757](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571319143757.png)

通过上面的分析，最终转化为代码为

```java
protected void afterRemove(Node<E> node,Node<E> replacement) {
    //如果被删除的节点是红色，不需要做任何处理，直接删除
    if (isRed(node)) return;
    //用于取代node的子节点是红色
    if (isRed(replacement)){
        black(replacement);
        return;
    }
    Node<E> parent = node.parent;
    //删除的是根节点
    if (parent == null) return;
    //删除的是黑色叶子节点【下溢】
    //判断被删除的node是左还是右
    boolean left = parent.left == null || node.isLeftChild();
    Node<E> sibling = left ? parent.right : parent.left;
    if (left) {//被删除的节点在左边，兄弟节点在右边
        if (isRed(sibling)){//兄弟节点是红色
            black(sibling);
            red(parent);
            rotateLeft(parent);
            //更换兄弟
            sibling = parent.right;
        }
        //兄弟节点必然是黑色
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            //兄弟节点没有一个红色子节点，不能借，父节点只能向下与兄弟合并
            boolean parentBlack = isBlack(parent);
            black(parent);
            red(sibling);
            if (parentBlack) {
                //父节点是黑色，父节点有向下合并，因此父节点的位置又产生了下溢，此时当做父节点被删除掉了
                afterRemove(parent,null);
            }
        } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
            if (isBlack(sibling.right)) {//兄弟节点的右边是黑色，兄弟要先旋转
                rotateRight(sibling);
                sibling = parent.right;
            }
            color(sibling,colorOf(parent));
            black(sibling.right);
            black(parent);
            rotateRight(parent);
        }
    } else {//被删除的节点在右边，兄弟节点在左边
        if (isRed(sibling)){//兄弟节点是红色
            black(sibling);
            red(parent);
            rotateRight(parent);
            //更换兄弟
            sibling = parent.left;
        }
        //兄弟节点必然是黑色
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            //兄弟节点没有一个红色子节点，不能借，父节点只能向下与兄弟合并
            boolean parentBlack = isBlack(parent);
            black(parent);
            red(sibling);
            if (parentBlack) {
                //父节点是黑色，父节点有向下合并，因此父节点的位置又产生了下溢，此时当做父节点被删除掉了
                afterRemove(parent,null);
            }
        } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
            if (isBlack(sibling.left)) {//兄弟节点的左边是黑色，兄弟要先旋转
                rotateLeft(sibling);
                sibling = parent.left;
            }
            color(sibling,colorOf(parent));
            black(sibling.left);
            black(parent);
            rotateRight(parent);
        }
    }
}
```

然后我们将代码优化，去除其中一个参数以后的代码为

```java
protected void afterRemove(Node<E> node) {
    //如果被删除的节点是红色
    // 或者用于取代node的子节点是红色
    if (isRed(node)){
        black(node);
        return;
    }
    Node<E> parent = node.parent;
    //删除的是根节点
    if (parent == null) return;
    //删除的是黑色叶子节点【下溢】
    //判断被删除的node是左还是右
    boolean left = parent.left == null || node.isLeftChild();
    Node<E> sibling = left ? parent.right : parent.left;
    if (left) {//被删除的节点在左边，兄弟节点在右边
        if (isRed(sibling)){//兄弟节点是红色
            black(sibling);
            red(parent);
            rotateLeft(parent);
            //更换兄弟
            sibling = parent.right;
        }
        //兄弟节点必然是黑色
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            //兄弟节点没有一个红色子节点，不能借，父节点只能向下与兄弟合并
            boolean parentBlack = isBlack(parent);
            black(parent);
            red(sibling);
            if (parentBlack) {
                //父节点是黑色，父节点有向下合并，因此父节点的位置又产生了下溢，此时当做父节点被删除掉了
                afterRemove(parent);
            }
        } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
            if (isBlack(sibling.right)) {//兄弟节点的右边是黑色，兄弟要先旋转
                rotateRight(sibling);
                sibling = parent.right;
            }
            color(sibling,colorOf(parent));
            black(sibling.right);
            black(parent);
            rotateRight(parent);
        }
    } else {//被删除的节点在右边，兄弟节点在左边
        if (isRed(sibling)){//兄弟节点是红色
            black(sibling);
            red(parent);
            rotateRight(parent);
            //更换兄弟
            sibling = parent.left;
        }
        //兄弟节点必然是黑色
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            //兄弟节点没有一个红色子节点，不能借，父节点只能向下与兄弟合并
            boolean parentBlack = isBlack(parent);
            black(parent);
            red(sibling);
            if (parentBlack) {
                //父节点是黑色，父节点有向下合并，因此父节点的位置又产生了下溢，此时当做父节点被删除掉了
                afterRemove(parent);
            }
        } else {//兄弟节点至少有一个红色子节点,向兄弟节点借元素
            if (isBlack(sibling.left)) {//兄弟节点的左边是黑色，兄弟要先旋转
                rotateLeft(sibling);
                sibling = parent.left;
            }
            color(sibling,colorOf(parent));
            black(sibling.left);
            black(parent);
            rotateRight(parent);
        }
    }
}
```

到这里，红黑树的添加删除就介绍完了。接下来，我们在看一下关于红黑树的平衡问题。

- #### 红黑树的平衡

红黑树与AVL树一样，都是平衡二叉搜索树，AVL树之所以叫平衡二叉搜索树，是因为AVL树里面维护了一个平衡因子，AVL树的平衡是靠平衡因子来保证的，因此AVL树通过平衡因子就能保证每一棵子树的左右节点高度差不会超过1，这样就能保证每一棵子树的高度差都是相近的，这样看起来，就非常的平衡。

那么红黑树也叫做平衡二叉搜索树，那么红黑树是怎么平衡的呢？为什么维护好红黑树的5条性质，就能保证红黑树的是平衡的呢？

**通过5条性质，可以保证红黑树等价于4阶B树。**

下图是红黑树与B树的等价变换

![1571450697121](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571450697121.png)

我们知道，由于B树的高度非常的矮，因此从B树的角度出发，B树的平衡的。由于红黑树与4阶B树是等价的，因此，既然B树是平衡的，那么红黑树也应该是平衡的。

但是可能你又会有疑问，B树的矮是因为B树的一个节点存储的多个元素（如B树节点 38,55），但是将B树展开成为红黑树的时候，我们发现红黑树还是很高，如B树节点的38,55本来是一层的，展开成红黑树以后，我们发现变为了2层，B树节点17,25也如此。最终我们发现，高度为2的B树，展开成红黑树以后，高度变为了4。那么红黑树高度变得这么高，还叫平衡吗？说到这里，我们就要来回顾一下平衡的概念：**给相同数目的节点，如果这些节点组成的二叉树，高度越低，我们就说这棵树越平衡**，因此只要左右子树的高度差不要太夸张，就叫可以这棵树是平衡的。恰好，红黑树的平衡就是这种高度差不太夸张的平衡。红黑树的平衡可以保证高度差不会像二叉搜索树一样，可以夸张到最终二叉搜索树退化成为链表。

说了这么多，其实红黑树还有一条性质：**没有一条路径会大于其他路径的2倍**

也就是说，最长的路径最多是最短路径的两倍。即 最长路径长度 ≤ 2 * 最短路径长度

因此我们可以认为：**红黑树的平衡是一种弱平衡，属于黑高度平衡**，红黑树的任何一条路径，它的黑高度都是相等的

最终，红黑树的最大高度是2 * log(n - 1)，依然是O(logn)级别

- #### 红黑树的平均复杂度

由于红黑树是一棵二叉搜索树，二叉搜索树的添加，删除，搜索的复杂度是与高度有关的，由于红黑树的树高依然是O(logn)，因此红黑树的添加，删除，搜索有以下结论

- 搜索：O(logn)
- 添加：O(logn)，O(1)次的旋转操作
- 删除：O(logn)，O(1)次的旋转操作

- #### AVL树 VS 红黑树

|                            AVL树                             |                            红黑树                            |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
|        平衡标准比较严格：每个左右子树的高度差不超过1         |      平衡标准比较宽松：没有一条路径会大于其他路径的2倍       |
| 最大高度是1.44 * log(n +2) - 1.328(100W个节点，AVL树的最大树高28) |    最大高度是2 * log(n +1)(100W个节点，AVL树的最大树高40)    |
| 添加，删除，搜索都是O(logn)的复杂度，其中个添加仅需O(1)次旋转调整，删除最多需要O(logn)次旋转调整 | 添加，删除，搜索都是O(logn)的复杂度，其中个添加，删除仅需O(1)次旋转调整 |

AVL树与红黑树的选择

1. 如果搜索次数远远大于插入和删除，选择AVL树
2. 入股哦搜索，插入，删除的次数几乎差不多，选择红黑树

AVL树与红黑树对比总结

1. 相对于AVL树来说，红黑树牺牲了部分平衡性来换取插入/删除操作时少量的旋转操作，整体来说性能要由于AVL树
2. 红黑树的平均统计性能由于AVL树，实际应用中更多选择使用红黑树

- #### 二叉搜索树/AVL树/红黑树的对比

如果现在有下列的一些数据依次添加到对应的二叉树中

10,35,47,11,5,57，39,14,27,26,84,75,63,41,37,24,96

最终，二叉搜索树的形状如下图所示

![1571453617601](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571453617601.png)

我们可以看到，该二叉树是极其不平衡的。如果使用AVL树来存储这些元素，最终的形状如下图

![1571453706324](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571453706324.png)

可以看到，结果非常的平衡，左右子树的高度差不会超过1。最终通过红黑树的存储，形状如下图

![1571453782182](https://github.com/MSTGit/Algorithm/blob/master/RBTreeDemo/Resource/1571453782182.png)

最终我们看到，高度对比二叉搜索树来讲，已经少了很多，但是对比AVL树来讲，看起来又不那么平衡。