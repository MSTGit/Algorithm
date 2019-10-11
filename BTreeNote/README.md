- #### B树(B-tree，B-树)

> B树是一种平衡的**多路**搜索树，多用于文件系统，数据库的实现

![1570797365799](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570797365799.png)

![1570797385191](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570797385191.png)

![1570797414137](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570797414137.png)

仔细观察B树，有什么眼前一亮的特点吗？

1. 1个节点可以存储超过2个元素，可以拥有超过2个子节点
2. 有用二叉搜索树的一些性质
3. 平衡，每个节点的所有子树高度一致
4. 比较矮

- #### m阶B树的性质(m ≥ 2)

阶：从上图可以看到，有3阶B树，有4阶B树，有5阶B树，这里的阶表示一个节点，最多可以拥有多少个子节点

性质：假设一个节点存储的元素个数为X

1. 根节点：1 ≤ X ≤ m - 1
2. 非根节点：⌈m / 2⌉ - 1 ≤ X ≤ m - 1 (⌈⌉表示向上取整)
3. 如果有子节点：子节点个数 Y = X + 
   - 根节点：2 ≤ Y ≤ m
   - 非根节点：⌈m / 2⌉ ≤ Y ≤ m

例如：m = 3 ,此时 Y的取值范围为 2 ≤ Y≤ 3，因此可以成为(2,3)树，或者2-3树

在如：m = 4 ,此时 Y的取值范围为 2 ≤ Y≤ 4，因此可以成为(2,4)树，或者2-3-4树

或者：m = 5 ,此时 Y的取值范围为 3 ≤ Y≤ 5，因此可以成为(3,5)树，或者2-3-4-5树

思考：如果m = 2，那么B树是什么样子？

- #### B数 VS 二叉搜索树

![1570799353669](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570799353669.png)

![1570799386714](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570799386714.png)

1. B树和二叉搜索树，在逻辑上是等价的
2. 多代节点合并，可以获得一个超级节点（二叉搜索树通过父子节点的合并，可以转换为B树中的一个节点）
   - 2代合并的超级节点，最多拥有4个子节点（至少是4阶B树）
   - 3代合并的超级节点，最多拥有8个子节点（至少是8阶B树）
   - n代合并的超级节点，最多拥有2^n个子节点（至少是2^n阶B树）
3. m阶B树，最多需要logm代合并

- #### 搜索

和二叉搜索树的搜索类似

1. 先从节点内部，从小到大开始搜索元素
2. 如果命中，搜索结束
3. 如果未命中，再去对应的子节点中搜索元素，重复步骤1

- #### 添加

1. 新添加的元素必定是添加到叶子节点

例如下图，现要往该B数种插入元素55

![1570800552995](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570800552995.png)

插入完成后的结果为

![1570800593813](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570800593813.png)

然后再往B树种插入元素95，完成后的结果为

![1570800689736](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570800689736.png)

如果再插入98呢？（假设这是一颗4阶B树）

此时，最右下角的叶子节点的元素个数将超过限制，这种现象可以称为**上溢（overflow）**

##### 上溢问题的解决

当节点发生上溢时，此时该节点的元素个数必然等于该B树的阶数，如下图的一个5阶B树节点，添加一个元素后

![1570804352465](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570804352465.png)

假设上溢节点，最中间元素的位置为K

1. 将K位置的元素向上与付节点合并
2. 再将[0,K - 1] 和 [K +1,m - 1]位置的元素分裂成为2个子节点[下图]

![1570801571382](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570801571382.png)

一次分裂完毕以后，有可能导致父节点上溢，依照上述方法解决，在极端的情况下，有可能一直分裂到根节点，假设上图上溢的节点为根节点，最终分裂后的结果为

![1570801829006](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570801829006.png)

通过上面的结论，可以完成下面一棵B树的添加操作

![1570801996247](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570801996247.png)

首先添加元素98，此时右下角的节点发生上溢，分裂合并后的结果

![1570802060983](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570802060983.png)

然后再添加元素52，此时没有发生上溢，直接添加到了节点中

![1570802113030](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570802113030.png)

接着我们添加元素54，通过这次添加，值为50的节点发生上溢，分裂合并后导致父节点上溢，最终父节点也会分裂合并，最终合并到了祖父节点中

![1570802218848](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570802218848.png)

- #### 删除

##### 叶子节点

假如需要删除的元素在叶子节点中，那么直接删除即可，如下图的B树

![1570802298249](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570802298249.png)

现在对其删除30的操作，删除后的结果为

![1570802329955](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570802329955.png)

##### 非叶子节点

假如需要删除的元素在非叶子节点中，如下面的B树中

![1570802432175](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570802432175.png)

执行删除60的操作，此时如果直接删除的话，根节点中就只剩一个元素了，此时不能形成该节点有3个子节点的条件，因此这时候需要

1. 找到前驱或者后继元素，覆盖所需删除元素的值
2. 再把前驱或后继元素删除

通过该方法，删除完成后的结果为

![1570802665650](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570802665650.png)

通过观察上图可以发现，**非叶子节点**的全区或者后继元素，必定在**叶子节点**中

所以在这里的删除前驱或者后继元素，就是最开始提到的情况，删除的元素在叶子节点中

**因此真正的删除元素，都是发生在叶子节点当中**

通过以上的结论，假设下图是一棵5阶B树

![1570803181103](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570803181103.png)

如果我们删除元素22。由于是5阶B树，所以每个节点至少存储2-4个元素

叶子节点被删除掉一个元素后，元素的个数可能会低于最低限制（≥ ⌈m / 2⌉  - 1），这种情况下，可能会导致**下溢（underflow）**

##### 下溢问题的解决

由于B树的性质，我们可以知道，下溢节点的元素数量必然等于⌈m / 2⌉  - 2，如下图

![1570803622149](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570803622149.png)

如果下溢节点临近的兄弟节点，至少有⌈m / 2⌉ 个元素，可以向其借一个元素

将父节点的元素b插入到下溢节点的0位置（最小位置）

这种操作其实就是:**旋转**

![1570803863634](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570803863634.png)

如果下溢节点临近的兄弟节点，只有⌈m / 2⌉ - 1 个元素[下图]

![1570804035615](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570804035615.png)

此时就需要将付节点的元素b挪下来和左右子节点进行合并[下图]，合并后的节点元素个数等于⌈m / 2⌉ + ⌈m / 2⌉ - 2 ，不超过 m - 1

![1570804116101](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570804116101.png)

这个操作可能会导致父节点下溢，按照上述的方法解决，以下现象可能会一直往上传播

- #### 练习 

画出4阶B树（2-3-4树），将能更好的学习理解红黑树

4阶B树的性质

1. 所有节点能存储的元素个数 X : 1 ≤ X ≤ 3
2. 所有非叶子节点的子节点个数Y ： 2 ≤ Y ≤ 4

- 练习从1添加到22
- 练习从1删除到22

最终添加完成的结果为下图

![1570805085912](https://github.com/MSTGit/Algorithm/blob/master/BTreeNote/Resource/1570805085912.png)