- #### 哈夫曼编码（Huffman Coding）

哈夫曼编码，又称霍夫曼编码，它是现代压缩算法的基础。

假如我们现在有这样的需求，需要把字符串【ABBBCCCCCCCCDDDDDDEE】转为二进制编码进行传输，我们可能想到的是通过转为ASCII编码（65~69,1000001~1000101），但是有点冗长，如果希望编码更短呢？

我们可以约定上面5个字母对应的编码。例如

```
A -> 0
B -> 1
C -> 10
D -> 11
E -> 100
```

那么假设我们传递的数据是ABBB时，对应编码，我们传递的是0111。可是，0111根据上面约定的编码，却有不同的解读，可以解读成ADB，也可以解读成ABBB，也可以解读成ABD。所以我们发现，根据这份约定也是不靠谱的。所以我们还应该规定，大家的长度都是一致的，例如

![1572610764711](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572610764711.png)

所以假设我们还是传递的是字符串【ABBBCCCCCCCCDDDDDDEE】的话，对应的二进制编码为（每一种颜色对应一个字母）

![1572610805423](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572610805423.png)

我们成功将20个字母，转成了60个二进制

但是，如果我们使用哈夫曼编码的话，可以压缩至41个二进制位，约为原来长度的68.3%

- #### 哈夫曼树

先计算出每个字母的出现频率（权值，这里直接用出现次数），【ABBBCCCCCCCCDDDDDDEE】，经过统计，我们可以得出给字符串的出现频率

![1572611175250](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572611175250.png)

然后利用这些权值，构建一棵哈夫曼树（又称霍夫曼树，最优二叉树）

如何构建一棵哈夫曼树？（假设有n个权值）

1. 以权值作为根节点构建n棵二叉树，组成森林
2. 在森林中选出2个根节点最小的树合并，作为一棵新树的左右子树，且新树的根节点为其左右子树根节点之和
3. 从森林中删除刚才选取的2棵树，并将新树加入森林
4. 重复2,3步骤，知道森林只剩一棵树为止，该树纪委哈夫曼树

通过图形表示为

步骤一：下图的5个节点就是以权值为根节点，构建的5棵树

![1572611572696](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572611572696.png)

步骤二/步骤三：选出森林中最小的2个根节点进行合并成一颗新树，加入森林，删除刚刚选择的2棵子树

![1572611780667](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572611780667.png)

重复步骤二/步骤三：

![1572611877553](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572611877553.png)

重复步骤二/步骤三：

![1572611919389](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572611919389.png)

重复步骤二/步骤三：

![1572612152571](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572612152571.png)

最终，合并完成。这棵树，就叫做哈夫曼树。

##### 构建哈夫曼编码

根据规则，left为0，right为1，可以得出5个字母对应的哈夫曼编码

![1572612312935](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572612312935.png)

最终【ABBBCCCCCCCCDDDDDDEE】的哈夫曼编码为

![1572612421402](https://github.com/MSTGit/Algorithm/blob/master/HuffmanTreeNote/Resource/1572612421402.png)