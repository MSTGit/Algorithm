- #### TreeMap分析

1. 时间复杂度（平均）
   - 添加，删除，搜索：O(logn)
2. 特点
   - Key必须具备可比较性
   - 元素的分布是有序的
3. 实际应用中，很多时候的需求
   - Map中存储的元素不需要讲究顺序
   - Map中的Key不具备可比较性

**不考虑顺序，不考虑Key的可比较性，Map有更好的实现方案，平均时间复杂度可达到O(1)**，那就是采用**哈希表**来实现Map

- #### 需求

> 设计一个写字楼通讯录，存放所有公司的通讯信息，其要求如下
>
> 1. 座机号码作为key（假设座机号码最长是8位），公司详情（名称，地址等）作为value
> 2. 添加，删除，搜索的时间复杂度要求是O(1)

首先，我们可能想到的是使用TreeMap来实现，但是存在一个问题，TreeMap的添加，删除，搜索复杂度为O(logn)，因此不符合要求。那我们可以这么做

```java
private Company[] companies = new Company[100000000];
public void add(int phone,Company company){
    companies[phone] = company;
}
public void remove(int phone) {
    companies[phone] = null;
}
public Company get(int phone) {
    return companies[phone];
}
```

那么，最终，这些公司信息存储到数组里面，在内存中的一个什么样的情况呢？即如下图这样进行存储的

![1572078785267](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572078785267.png)

那么，通过这种方式，存在什么问题呢？

1. 空间复杂度，非常大
2. 空间使用率极其低，非常浪费内存空间

其实，上面的数组companies就是一个哈希表，一种典型的【空间换时间】

- #### 哈希表(Hash Table)

> 哈希表也叫做散列表（hash有“剁碎”的意思）

它是如何实现高效处理数据的？

假设我们用哈希表来存储以下三对数据

```java
put(“Jack”,666);
put("Rose",777);
put("Kate",888);
```

那么，哈希表在内存中是怎样存储这三对数据的呢？

![1572079627569](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572079627569.png)

哈希表添加，搜索，删除的流程

1. 利用哈希函数生产key对应的index，复杂度为O(1)
2. 根据index操作定位数组元素，复杂度为O(1)

> - 哈希表是空间换时间的典型应用
> - 哈希函数，也叫做散列函数
> - 哈希表内部的数组元素，很多地方也叫Bucket（桶），整个数组叫Buckets或者Bucket Array

- #### 哈希冲突(Hash Collision)

哈希冲突也叫做哈希碰撞

解释：2个不同的key，经过哈希函数计算出相同的结果，即key1 ≠ key2，但是hash(key1) = hash(key2)

例如可能会出现以下这种情况

![1572080197839](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572080197839.png)

解决哈希冲突的常见方法

1. 开放定址法（Open Addressing）
   - 按照一定的规则，向其他地址探测，知道遇到空桶
2. 再哈希法(Re-Hashing)
   - 设计多个哈希函数
3. 链地址发(Separate Chaining)
   - 比如通过链表，将同一index的元素串起来[下图]

![1572080832425](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572080832425.png)

- #### JDK1.8的哈希冲突解决方案

1. java官方是默认使用**单向链表**将元素串起来
2. 在添加元素时，可能会由**单向链表**转为**红黑树**来存储
   - 比如当哈希表容量≥64且**单向链表**的节点数量＞8的时候[下图]
3. 当红黑树节点数量少到一定程度时，又会转为**单向链表**

![1572081172719](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572081172719.png)

所以在JDK1.8中的哈希表是使用**链表**+**红黑树**来解决哈希冲突的

🤔这里为什么使用单项链表？

1. 每次都要从头还是遍历，看当前链表中元素的key是否已经存在，如果存在，就覆盖，如果不存在，才添加
2. 单向链表比双向链表少一个指针，可以节省内存空间

- #### 哈希函数

在哈希表中，哈希函数的实现步骤大概如下

1. 先生成**key的哈希值**（必须是**整数**）
2. 再让**key的哈希值**与**数组的大小**进行相关运算，生成一个**索引值**，如下示例代码所示

```java
public int hash(Object key){
	return hash_code(key) % table.length;
}
```

为了提高效率，可以使用&位运算取代%运算【前提：将数组的长度设计为2的幂（2^n）】

```java
public int hash(Object key){
	return hash_code(key) & (table.length - 1);
}
```

我们来回顾一下&运算的特点：相同为上，两个都为1时，结果才为1，如下

```
 1001010
&1101101
---------
 1001000
```

那么我们来思考一下，为什么数组的长度要设计为2^n，那我们来看以下二进制数与2的关系

```
1              2^0
10             2^1
100            2^3
1000           2^4
10000          2^5
```

同样的，我们来看看二进制数与2的幂次方-1的关系

```
0              2^0 - 1
01             2^1 - 1
011            2^3 - 1
0111           2^4 - 1
01111          2^5 - 1
```

你发现规律了吗？

2^n - 1的二进制结果，全部都是1（n = 0 除外）

那么通过这个方法，来计算索引的话，是这样的

```
 1001010
&1111111
---------
 1001010
 
 1001010
&   1111
---------
 0001010
```

我们发现，最终计算出来的结果就是原来的hash值的结果，但是需要注意的是，通过&运算后，计算出来的结果，必然小于数组长度 - 1

##### 良好的哈希哈数是怎么样的？

1. 让哈希值更加均匀分布→减少哈希冲突次数→提升哈希表的性能

- #### 如何生成key的哈希值

key的常见种类可能有：整数，浮点数，字符串，自定义对象

不同种类的key，哈希值的生成方式不一样，但目标是一直的

1. 尽量让每一个key的哈希值都是唯一的
2. 尽量让key的所有信息都参与运算

注意：在Java中，HashMap的key必须实现hashCode，equals方法，也允许key为null

接下来，看看常见类型生成哈希值的方式

##### 整数

直接使用该整数当哈希值，比如10的哈希值就是10

##### 浮点数

将浮点数存储的二进制格式转换为整数后，当做哈希值

##### Long和Double的哈希值

在Java官方，哈希值只能是int类型，因此Long类型与Double类型的长度，都超过了int类型的长度，因此需要转换

官方对Long是这样处理的

```java
public  static int hashCode(long value) {
    return (int)(value ^ (value >>> 32));
}
```

对Double类型是这样处理的

```java
public  static int hashCode(double value) {
    long bits = doubleToLongBits(value);
    return (int)(bits ^ (bits >>> 32));
}
```

其中>>> 与^的作用：将高32bit和低32bit混合计算出32bit的哈希值，可以充分利用所用信息计算出哈希值

> 符号>>>表示无符号右移
>
> 符号^表示异或（相同为0，不同为1）

![1572085514121](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572085514121.png)

##### 字符串的哈希值

首先我们来思考一个问题！

整数5489是如何算出来的？

其实5489是通过 5 * 10^3 + 4 * 10^2 + 8 * 10^1 + 9 * 10^0，这样算出来的。那么同样的，字符串也可以通过类似于这种的方式计算

**字符串也是由若干个字符组成的**

例如：现在有字符串jack，它是由字符j,a,c,k组成的，并且我们知道，字符的本质就是一个整数，那么jack的哈希值可以表示为j * n^3 +a * n^2 + c * n^1 + k * n^0,通过等式变换为[(j * n + a) * n + c] * n + k。

在JDK中，乘数n的值为31，为什么使用31？因为31是一个奇素数，JVM会将31 * i优化为(i << 5) - i

那么，字符串的哈希值可以通过如下的示例进行计算

```java
String string = "jack";
int len = string.length();
int hashCode = 0;
for (int i = 0; i < len; i++) {
    char c = string.charAt(i);
    hashCode = hashCode * 31 + c;
}
```

到这里，我们都知道整数，浮点数，字符串的哈希值该如何计算了。但是，好在这些都不用我们自己去计算，在Java官方，都已经给我们计算好了，直接对应的对象类型，调用hashCode()方法就可以了，计算结果与我们自己计算的一致。

但是，我们还有一种类型的哈希值没有计算，那就是自定义类型。

##### 自定义类型的哈希值

如果我们对自定义对象求哈希值的话，在Java官方，同样是右默认实现的，但是需要注意的是，在默认实现中，其哈希值与该自定义对象在内存中的位置有关，因此就会有以下的问题

```java
Person p1 = new Person(18,1.85l,"jack");
Person p2 = new Person(18,1.85l,"jack");
```

最终p1,p2计算出来的哈希值是不一样的。因此假设我们现在有需求，一个Person，如果它的age，height，name都相同的话，则认为是同一个Person对象。此时我们就需要手动计算自定义对象的哈希值，因此可以通过如下的示例进行计算

```java
public int hashCode(){
    int hashCode = Integer.hashCode(age);
    hashCode = hashCode * 31 + Float.hashCode(height);
    hashCode = hashCode * 31 + (name != null ? name.hashCode() : 0);
    return hashCode;
}
```

通过自己实现计算自定义对象的哈希值以后，以上两个Person对象的哈希值相同了。

但是需要注意，hashCode相等的两个对象，不一定是同一个对象，因为参数的值不同，最终计算出来的值是一样的，所以不能认为hashCode相同的对象是同一个对象，如果要判断是否为同一个对象，我们还需要实现以下的示例方法

```java
public boolean equals(Object obj) {
    //内存地址相等
    if (this == obj) return true;
    //obj为空或者不是同一种类型
    if (obj == null || !(obj instanceof Person)) return false;
    //最后比较成员变量
    Person person = (Person)obj;
    return person.age == age
            && person.height == height
            && (person.name == null ? name == null : person.name.equals(name));
}
```

只有这样，最终才能判断出两个对象是否相等。

> 总结：
>
> - 重写hashCode方法的作用是计算索引
> - 重写equals方法的作用是当哈希值冲突的时候，判断两个对象是否相等

##### 自定义对象作为key

自定义对象作为key，最好是同时重写hashCode,equals方法

- equals：用来判断2个key是否为同一个key
  - 自反性：对于任何非null的x，x.equals(x)必须返回true
  - 对称性：对于任何非null的x,y,如果y.equals(x)返回true，x.equals(y)必须返回true
  - 传递性：对于任何非null的x,y,z,如果x.equals(y),y.equals(z)返回true，那么x.equals(z)必须返回true
  - 一致性：对于任何非null的x,y，只要equals的比较操作在对象中所用的信息没有被修改，对此调用x.equals(y)就会一致的返回true，或者一致的返回false
  - 对于任何非null的x,x.equals(null)必须返回false
- hashCode：必须保证equals为true的两个key的哈希值一样
- 反过来，如果hashCode相等的key，不一定equals为true

那么问题来了，在我们计算自定义对象的哈希值时，如果哈希值太大，整型溢出了怎么办？

这种情况我们不用做任何处理，我们只需要计算出一个整型的值就可以了。溢出了也没有关系

另外，如果不重写hashCode方法，只重写equals，会有什么后果？

可能会导致2个equals为true的key同时存在哈希表中



- #### 关于31的讨论

31 * i = (2^5 - 1) * i = i * 2^5 - 1 = (i << 5) - 1

1. 因为31不仅仅是符合2^n - 1,并且它还是一个奇素数（既是奇数，又是素数，也叫做质数）
2. 素数与其他数相乘的结果，比其他方式更容易产生唯一性，减少哈希冲突
3. 最终选择31是经过观测分布结果后的选择

- #### 哈希表的扩容

在哈希表的扩容过程中，会涉及到一个装填因子（Load Factor）的概念

> 装填因子（Load Factor）：节点总数量/哈希表数组长度，也叫做负载因子

在JDK1.8的HashMap中，如果装填因子超过0.75，桶数量就会扩容为原来的2倍

我们扩容的方案是遍历旧的表对象，然后将表对象中的所有元素，移动到新的表中。其中将桶数量扩容以后，不仅可以提高哈希表的性能，而且还能减少哈希碰撞的次数。

关于扩容的实现，可以参考demo中的`void resize()`函数

通过对比，我们来看以下处理相同数量的内容，扩容前与扩容后的对比

**扩容前处理900万条数据所花的时间**

![1572352586841](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572352586841.png)

**扩容后处理900万条数据所花的时间**

![1572352683901](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572352683901.png)

我们看到，处理900万条数据，扩容前后相差了接近2秒钟是时间。可见提升是多么的明显。、

所以虽然我们在扩容的那一刻，消耗的性能稍微大一点，但是从整体来讲，效率是提升了的。

- #### HashMap VS TreeMap

我们来对比一下哈希表与红黑树映射表之间，性能的差异

我们还是处理900万条数据，我们得到以下的结果

![1572353523099](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572353523099.png)

最终我们看到，哈希表的性能明显优于红黑树映射表的性能。

那关于HashMap和TreeMap应该如何选择呢？

- 如果元素具备可比较性，并且要求升序遍历（按照元素从小到大），此时应该选择TreeMap
- 遍历是无序的，选择HashMap

- #### LinkedHashMap

在HashMap的技术上，维护元素的添加顺序，使得遍历的结果是遵从添加顺序的

下图是我们前面的HashMap，假设索引03中的元素添加顺序为37,21,31,41,97,52,42,83

![1572356049219](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572356049219.png)

如何维护每个元素的添加顺序呢？我们可以通过在LinkedHashMap中增加一个指针，该指针指向下一个添加的元素，如下图所示

![1572356329650](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572356329650.png)

因此我们只需要在原来红黑树节点的基础上，增加两个字段，一个用来指向当前节点的上一个节点，一个用来指向当前节点的下一个节点，就可以将所有节点，通过链表的形式串起来。

##### LinkedHashMap删除注意点

由于我们知道，在红黑树中删除节点时，最终真正被删除掉的节点是叶子节点，此时，如果我们删除的节点不是叶子节点，红黑树会使用前驱节点或者后继节点来替换当前的节点，然后删除后，再修复红黑树的性质。

因此在删除红黑树节点时，有三种情况

1. 删除的是叶子节点
2. 删除的是度为1的节点
3. 删除的是度为2的节点

如果删除的是叶子节点(如节点97)

![1572437187123](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572437187123.png)

站在红黑树的角度，直接删除掉就好了，因此直接去掉父节点指向当前节点的线就好了

![1572437298724](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572437298724.png)

然后再更新链表的线，最终结果为

![1572437378672](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572437378672.png)



由我们前面红黑树部分知识可以知道，在删除红黑树节点时，只有被删除的节点有左右子树的时候，期望被删除的节点才与实际被删除的节点不符，因此最终被删除的是当前节点的前驱或者后继。所以我们在删除的时候，需要特别注意。

因此在删除以上节点（如31）时，需要注意更换node与前驱/后继节点的链接位置，在理解这句话之前，我们先来看看下面这种删除的情况。

站在链表的角度，删除31之前，我们的遍历顺序为（部分）52 -> 37 - > 21 -> 31 -> 41，删除之后的遍历顺序应该为（部分）52 -> 37 - > 21 -> 41

![1572437378672](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572437378672.png)

按照我们一般的逻辑来讲，我们直接将31删除掉就好了，因此从红黑树的角度讲，删除后最终的结果为

![1572438140825](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572438140825.png)

好了，此时我们再看看从链表角度的遍历顺序，发现变为了 52 -> 21 -> 37 -> -> 41，遍历顺序与我们期望的不符，因此问题就产生了。因此我们需要注意更换node与前驱/后继节点的链接位置，来保证删除后的正确遍历顺序。

因此，同样的，假设有如下的一棵红黑树

![1572438953491](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572438953491.png)

删除是节点31,31节点的后继为37，我们在删除之前，先调换两个节点在链表中的位置，注意，不交换链表在红黑树种的位置，位置交换后的结果为

![1572438882591](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572438882591.png)

然后最终删除后的结果为

![1572439001628](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572439001628.png)

通过这种方式，先交换位置后，再删除的话，就能顺利的解决链表遍历顺序不符的问题

##### LinkedHashMap更换节点的链接位置

下图是链表原始的顺序

![1572439583570](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572439583570.png)

调换顺序后的期望位置

![1572439631211](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572439631211.png)

因此我们可以通过将被删除的节点与正在被删除的节点通过以下的方式进行位置的调换

```java
//1.交换prev
LinkedNode<K,V> tmp = linkedWillNode.prev;
linkedWillNode.prev = linkedRemoveNode.prev;
linkedRemoveNode.prev = tmp;
if (linkedWillNode.prev == null) {
    first = linkedWillNode;
} else  {
    linkedWillNode.prev.next = linkedWillNode;
}

if (linkedRemoveNode.prev == null) {
    first = linkedRemoveNode;
} else {
    linkedRemoveNode.prev.next = linkedRemoveNode;
}

//2.交换next
tmp = linkedWillNode.next;
linkedWillNode.next = linkedRemoveNode.next;
linkedRemoveNode.next = tmp;
if (linkedWillNode.next == null) {
    last = linkedWillNode;
} else  {
    linkedWillNode.next.prev = linkedWillNode;
}

if (linkedRemoveNode.next == null) {
    last = linkedRemoveNode;
} else  {
    linkedRemoveNode.next.prev = linkedRemoveNode;
}
```

通过以上方法，就可以成功交换两个链表中的位置

- #### 关于使用%来计算索引

在前面，我们使用&来计算元素的索引，可以提高计算性能，如果我们要使用%好计算索引的话，有以下的建议

**建议把哈希表的长度设计为质数，这样可以减少哈希冲突**，如下例所示

```
10 % 8 = 2		10 % 7 = 3
20 % 8 = 4		20 % 7 = 6
30 % 8 = 6		30 % 7 = 2
40 % 8 = 0		40 % 7 = 5
50 % 8 = 2		50 % 7 = 1
60 % 8 = 4		60 % 7 = 4
70 % 8 = 6		70 % 7 = 0
```

通过两组数据，我们看到，质数的余数可以大大的减少哈希冲突。

下图是科学家整理出来的不同数据规模对应的最佳素数，在数据规模介于上界与下界之间时，选择对应的素数，哈希表的性能最优，哈希冲突可以降低到最少。

![1572355159058](https://github.com/MSTGit/Algorithm/blob/master/HashTableDemo/Resource/1572355159058.png)

以上素数还有以下两个特点：

1. 每个素数略小于前一个素数的2倍
2. 每个素数尽可能接近2的幂（2^n）