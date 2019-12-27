#### 贪心（Greedy）

贪心策略：也称为贪婪差略

使用贪心策略，在执行每一步的过程中，都会选择当前状态下的最优解（局部最优解），从而希望推导出全局最优解

贪心的应用

- 哈夫曼树
- 最小生成树：Prim，Kruskal
- 最短路径算法：Dijkstra

这些算法，在前面文章中也有介绍，为什么说这些算法都使用到了贪心策略呢？通过阅读完本章节内容，就会明白为什么这些算法使用到了贪心策略。

##### 场景一：最优装载问题（加勒比海盗）

> 在北美东南部，有一片神秘的海域，是海盗最活跃的加勒比海
>
> 有一天，海盗们截获了一艘装满各种各样古董的货船，每一件古董都价值连城，一旦打碎，就失去了价值
>
> 现在海盗船的载重量为W，每一件古董的重量为w，海盗们该如何把尽可能多数量的古董装上海盗船？

结合上面的情景，可以理解出：每一都应该选择重量最小的古董，因为一旦选择的重量最小的古董，则海盗船剩下的载重量就会更大，就可以装载更多的古董。所以，利用贪心策略的话，应该每一次都选择重量最小的古董

根据上面的要求，现在假设海盗船的载重量W为30，w分别为3,5,4,10,7,14,2,11，利用贪心策略，每一步选择的古董重量如下

1. 选择重量为2的古董，剩余载重量为28
2. 选择重量为3的古董，剩余载重量为25
3. 选择重量为4的古董，剩余重量为21
4. 选择重量为5的古董，剩余载重量为16
5. 选择重量为7的古董，剩余载重量为9

现在还分别剩下重量为10,11,14的古董，剩余的载重量不足以装载这些古董，所以，利用贪心算法最多能装载5个古董

前面提到，贪心策略在执行每一步的过程中，都会选择当前状态下的最优解，所以在当前问题中，就每一步都选择重量最小的古董。这就是贪心算法的一个典型应用

所以，这个问题，很容易就解出来了

```java
public static void main(String[] args) {
    int[] weights = {3,5,4,10,7,14,2,11};
    Arrays.sort(weights);
    int capacity = 30, weight = 0, count = 0;
    for (int i = 0; i < weights.length; i++) {
        int newWeight = weight + weights[i];
        if (newWeight > capacity) break;
        weight = newWeight;
        count++;
        System.out.println(weights[i]);
    }
    System.out.println("一共选择了" + count + "件古董");
}
```

##### 场景二：零钱兑换

> 假设有25分，10分，5分，1分的硬币，现要找给客户41分的零钱，如何办到硬币个数最少？

这里，同样可以使用到贪心的策略，在这里，优先选择面值较大的硬币，这样的话， 按道理就能做到，硬币的数量更少。所以，使用贪心策略来解决这类问题的话，就每一次都选择面值最大的硬币。所以，每一步的选择步骤如下

1. 选择25分的硬币，剩余16分
2. 选择10分的硬币，剩余6分
3. 选择5分的硬币，剩余1分
4. 选择1分的硬币，剩余0分

所以，通过上面的执行步骤，可以发现，找给客户41分零钱的话， 最少需要4个硬币。25分，10分，5分，1分各1枚。

转换为代码，结果如下

```java
public static void main(String[] args) {
    Integer[] faces = {25,10,5,1};
    Arrays.sort(faces,(Integer f1, Integer f2) -> f2 - f1);
    int money = 41,coins = 0,i = 0;
    while (i < faces.length) {
        if (money < faces[i]) {
            i++;
            continue;
        }
        money -= faces[i];
        System.out.println(faces[i]);
        coins++;
    }
    System.out.println(coins);
}
```

如果，现在的零钱变为有25分，20分，5分，1分的硬币，同样要找给客户41分的零钱，上面的解决方案，每一步选择结果如下

1. 选择25分的硬币，剩16分
2. 选择5分，剩11分
3. 选择5分，剩6分
4. 选择5分，剩1分
5. 选择1分，剩0分

可以发现，如果将零钱面值进行变化，依然按照前面的贪心策略进行解决的话，最终的解是1枚25分，3枚5分，1枚1分，一共5枚硬币。通过肉眼的观察，发现，最优的解并不是这个，可以观察出的最优解是2枚20分，1枚1分的硬币，一共3枚硬币。

所以可以发现，贪心策略在这样的问题中，是有问题的。所以存在以下问题

贪心策略并不一定能得到全局最优解

1. 因为一般没有测试所有可能的解，容易过早做决定，所以没法达到最佳解
2. 贪图眼前局部的利益最大化，看不到长远未来，走一步看一步

优点：简单，高效，不需要穷举所有可能，通常作为其他算法的辅助算法来使用。

缺点：鼠目寸光，不从整体上考虑其他可能，每次都采取局部的最优解，不会再回溯，因此很少情况会得到最优解

所以，现在这个问题，如果要解决的话，则需要利用到其他知识，例如动态规划。

##### 情景三：0-1背包问题

> 有n件物品和一个最大承重为W的背包，没见物品的重量是w，价值是v
>
> 在保证总重量不超过W的前提下，将哪几件物品装入背包，可使得背包的总价值最大？

请注意：每一件物品只有1件，所以，每一件物品只能选择1件或者0件，因此成为0-1背包问题

在该问题中，如果采用贪心策略来解决的话，可以有3个不同的方案

1. 价值主导：优选择价值最高的物品放进背包（这样可以使得背包的物品价值的最大化）
2. 重量主导：优先选择重量最轻的物品放进背包（这样可以放进更多的物品）
3. 价值密度主导：优先选择价值密度最高的物品放进背包（价值密度 =  价值 ÷ 重量）

可以发现，这三种策略都有一定的道理，那到底哪一种方案才是正确的呢？不过先不用考虑哪一种方案是正确的，首先从这里就可以观察出，贪心策略是不靠谱的，因为感觉利用贪心策略，都可以达到目的。所以再一次证明了，贪心策略是贪图眼前的利益最大化，不会去考虑未来长远的问题。

###### 背包问题实例

假设现在背包的最大承重量为150,7个物品如表格所示

![1577453596686](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/26-Greedy/Resource/1577453596686.png)

现在就结合物品的信息，来对前面的3种不同方案进行检测

1. 价值主导：背包中放入的物品编号为4,2,6,5，总重量为130，总价值为165
2. 重量主导：背包中放入的物品编号为6,7,2,1,5，总重量为140，总价值为155
3. 价值密度主导：放入背包中的物品编号为6,2,7,4,1，总重量为150，总价值为170

所以，这个问题，转化为代码如下

```java
public static void main(String[] args) {

    //价值主导
    select("价值主导",(Article a1,Article a2) -> {
        return a2.value - a1.value;
    });
    //重量主导
    select("重量主导",(Article a1,Article a2) -> {
        return a1.weight - a2.weight;
    });
    //价值密度主导
    select("价值密度主导",(Article a1,Article a2) -> {
        return Double.compare(a2.valueDensity,a1.valueDensity);
    });

}

static void select(String title, Comparator<Article> cmp) {
    Article[] articles = new Article[] {
            new Article(35,10),new Article(30,40),
            new Article(60,30),new Article(50,50),
            new Article(40,35),new Article(10,40),
            new Article(25,30)
    };
    Arrays.sort(articles,cmp);

    int capacity = 150, weight = 0, value = 0;
    List<Article> selectedArticles = new LinkedList<>();
    for (int i = 0; i < articles.length && weight < capacity; i++) {
        int newWeight = weight + articles[i].weight;
        if (newWeight <= capacity) {
            weight = newWeight;
            value += articles[i].value;
            selectedArticles.add(articles[i]);
        }
    }
    System.out.println("【" + title + "】");
    System.out.println("【总价值】 ： " + value);
    for (int i = 0; i < selectedArticles.size();i++) {
        System.out.println(selectedArticles.get(i));
    }
}
```

所以，通过以上的几个场景，就知道了贪心策略的一些特性，贪图眼前局部的利益最大化，每一步都会选择当前状态下的最优解，这就是贪心。