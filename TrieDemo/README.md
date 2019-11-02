- #### 需求

如何判断一堆不重复的字符串，是否已某个前缀开头？

我们可以用Set/Map来存储字符串，然后遍历所有的字符串进行判断，通过这种方式实现，时间复杂度为O(n)

我们有没有更优的数据结构来实现前缀搜索？

Trie就可以。

- #### Trie

Trie也叫做字典树，前缀树（Prefix Tree），单词查找树

Trie搜索字符串的效率主要跟字符串的长度有关。

假设使用Trie存储cat,dog,doggy,does,cast,add六个单词，那它是怎么存储的呢？

首先，存储cat是将单词拆分为单个字符进行存储，如

![1572614226144](https://github.com/MSTGit/Algorithm/blob/master/TrieDemo/Resource/1572614226144.png)

如果要存储dog，怎么办呢？

![1572614294542](https://github.com/MSTGit/Algorithm/blob/master/TrieDemo/Resource/1572614294542.png)

然后存储doggy，在存储这个单词时，发现dog是当前单词的前缀，那只需要在原来dog的基础上，再加一个g，y即可

![1572614385595](https://github.com/MSTGit/Algorithm/blob/master/TrieDemo/Resource/1572614385595.png)

然后再存储does，同样的，利用已有的前缀进行存储，在已有的基础上，增加自己不同的字符

![1572614466754](https://github.com/MSTGit/Algorithm/blob/master/TrieDemo/Resource/1572614466754.png)

同理，存储cast

![1572614517876](https://github.com/MSTGit/Algorithm/blob/master/TrieDemo/Resource/1572614517876.png)

存储add

![1572614561528](https://github.com/MSTGit/Algorithm/blob/master/TrieDemo/Resource/1572614561528.png)

最终，所有的节点存储完成

当我们要搜索某个前缀时，我们只需要从根节点开始，一个字符一个字符的匹配即可。这样可以大大的提高搜索的效率。

- #### Trie的实现

##### 接口设计

第一种接口设计

```java
int size();
boolean isEmpty();
void add(String str);
void remove(String str);
boolean startsWith(String prefix);
boolean contains(String str);
void clear();
```

第二种接口设计

```java
int size();
boolean isEmpty();
V add(String str,V value);
V remove(String str);
V get(String str)
boolean startsWith(String prefix);
boolean contains(String str);
void clear();
```

第二种接口，在存储一个一个字符串的同时，可以存储一个值，由于第二种接口设计的实现包含了第一种接口设计，那么我们在这里就使用第二种方式来进行实现。具体实现可以查看demo中的源码

##### 删除的注意点

我们在删除节点时，需要注意，

1. 我们往上删除是，如果有某个单词是当前被删除单词的前缀时，就不要再往前删了，例如上面的单词中，我们删除dog单词，我们不能讲这三个节点从树中删除掉，因此我们需要判断我当前要删除的单词，最后一个节点，是否还有子节点，如果有，就只需要将最后的字符标记不为单词结尾就可以了。
   删除前：
   ![1572664978942](https://github.com/MSTGit/Algorithm/blob/master/TrieDemo/Resource/1572664978942.png)

   删除后：
   ![1572664831098](https://github.com/MSTGit/Algorithm/blob/master/TrieDemo/Resource/1572664831098.png)
   这样我们就把dog删除掉了

2. 如果没有子节点，例如d,t,t,y,s这些节点，这些节点如果没有子节点，就直接从后面往前删除。但是在往上删除的时候需要注意，如果当前节点删除完子节点以后，发现还有其他子节点，就不要再往上删除了；例如删除does的时候，当我们删除掉字符e时，就不能再往上删除了。即从最后开始往回删除，直到某节点删除子节点后，还有子节点时，就停止删除，单词的删除就结束了。

删除对应代码实现为：

```java
V remove(String str) {
    //找到最后一个节点
    Node<V> node = node(str);
    V oldValue = node.value;
    //如果不是单词结尾，不用做任何处理
    if (node == null || !node.word) return null;
    size--;
    //如果还有子节点
    if (node.children != null && !node.children.isEmpty()) {
        node.word = false;//把当前节点的单词结尾标记去掉
        node.value = null;
        return oldValue;
    }
    //如果没有子节点
    Node<V> parent = null;
    while ((parent = node.parent) != null) {
        parent.children.remove(node.character);
        if (parent.word || !parent.children.isEmpty()) {//删掉以后还有其他子节点，就不再往上删除,停止循环
            break;
        }
        node = parent;
    }
    return oldValue;
}
```

- #### 总结

Trie的优点：搜索前缀的效率主要跟前缀的长度有关

Trie的缺点：需要消耗大量的内存，因此还有待改进

> 更多Trie相关的数据结构和算法
>
> - Double-array Trie
> - Suffix Tree
> - Patricia Tree
> - Crit-bit Tree
> - AC自动机

