#### 动态规划（Dynamic Programming）

动态规划，简称DP，它是求解最优化问题的一种常见策略。例如前面章节中提到的找零钱问题，要求找的硬币个数最少；或者最大连续子序列和问题。所以，很多时候，有带最子的问题，都可以使用动态规划的方式来解决。

动态规划的使用套路：一步一步进行优化。也就是说，当对动态规划不够熟悉时，可能并不能直接就能想到使用动态规划的解法来解决一个问题，很有可能是一步一步进行优化，例如进行如下的方式

1. 暴力递归（自顶向下，出现了重叠子问题）
2. 记忆化搜索（自顶向下）
3. 递推（自底向上）

另外，动态规划还有其他的基本理论，这里先不着急进行介绍。先通过一个示例场景问题，对动态规划有一个初步的了解。

##### 场景一：找零钱[[LeetCode](https://leetcode-cn.com/problems/coin-change/)]

> 假设有25分，20分，5分，1分的硬币，现在要找给客户41分零钱，如何办到硬币个数最少

在前面，这个问题曾经使用过贪心策略来进行求解，但是并没有得到最优解（贪心得到的解是5枚），但是使用动态规划来解决这个问题的话，就能得到最优解。

使用动态规划解该问题，其中假设dp(n)是凑到n分需要的最少硬币数量，存在以下几种情况：

- 如果第一次选择了25分的硬币，那么dp(n) = dp(n - 25)  + 1;
- 如果第一次选择了20分的硬币，那么dp(n) = dp(n - 20)  + 1;
- 如果第一次选择了5分的硬币，那么dp(n) = dp(n - 5)  + 1;
- 如果第一次选择了1分的硬币，那么dp(n) = dp(n - 1)  + 1;

所以现在dp(n)存在以上4中情况

那么，最小dp(n)的值为 dp(n) = min{dp(n - 25),dp(n - 20),dp(n - 5),dp(n - 1)} +1，也就是说从dp(n - 25),dp(n - 20),dp(n - 5),dp(n - 1)4中情况中选出硬币数量最少的一种，就是dp(n)硬币数量最少的情况

所以，现在相当于把dp(n)的所有情况都考虑到了。

通过上面的分析，最终得到的代码如下

```java
static int coins(int n) {
    if (n < 1) return Integer.MAX_VALUE;
    if (n == 25 || n == 20 || n == 5 || n ==1) return 1;
    int min1 = Math.min(coins(n - 25),coins(n - 20));
    int min2 = Math.min(coins(n - 5),coins(n - 1));
    return Math.min(min1,min2) + 1;
}
```

通过，这种方法，再次对41分零钱进行求解的话，最终得到的结果为3枚。是最终想要的结果

###### 代码优化

仔细观察可以发现，上面这种计算硬币最少数量的解法，与前面介绍的斐波那锲数列的解法非常相似，存在非常多的重复计算问题，所以可以使用记忆搜索来进行优化，即计算过的值，进行记录后面直接使用，不在计算，所以，通过优化，得到代码如下

```java
static int coins(int n) {
    if (n < 1) return -1;
    int[] dp = new int[n + 1];
    int[] faces = {1,5,20,25};
    for (int face : faces) {
        if (n < face) break;
        dp[face] = 1;
    }
    dp[1] = dp[5] = dp[20] = dp[25] = 1;
    return coins(n,dp);
}
static int coins(int n, int[] dp) {
    if (n < 1)return Integer.MAX_VALUE;
    if (dp[n] == 0) {
        int min1 = Math.min(coins(n - 25,dp),coins(n - 20,dp));
        int min2 = Math.min(coins(n - 5,dp),coins(n - 1,dp));
        dp[n] = Math.min(min1,min2) + 1;
    }
    return dp[n];
}
```

这种优化方式依然是采用自顶向下的方式来计算的，并且依然是递归调用， 所以还存在栈空间的开销，所以现在可以考虑采用另外的方式进行优化，结合前面的介绍，使用记忆化搜索以后，可以使用递推的方式来进行进行的优化，所以接下来使用递推的方式来进行优化

###### 递推

递推采用的是自底向上的方式 来进行计算的。所以可以将上面的代码修改为如下方式

```java
static int coins(int n){
    if (n < 1) return -1;
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        int min = Integer.MAX_VALUE
            
            ;
        if (i >= 1) min = Math.min(dp[i - 1], min);
        if (i >= 5) min = Math.min(dp[i - 5], min);
        if (i >= 20) min = Math.min(dp[i - 20], min);
        if (i >= 25) min = Math.min(dp[i - 25], min);
        dp[i] = min + 1;
    }
    return dp[n];
}
```

通过递推的方式来进行求解的话，其时间复杂度与空间复杂度均为O(n)

现在，通过递推的方式，将上面求解的代码进行整合，变得更加通用，结果如下

```java
static int generalCoins(int n,int[] faces){
    if (n < 1 || faces == null || faces.length == 0) return -1;
    int [] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        int min = Integer.MAX_VALUE;
        for (int face : faces) {
            if (i < face) continue;
            min = Math.min(dp[i - face],min);
        }
        dp[i] = min + 1;
    }
    return dp[n];
}
```

为什么这种解法，叫做动态规划？

现在来回顾一下解决该问题，经过的步骤。

1. 暴力递归
2. 记忆化搜索
3. 递推

所以，在最开始的时候，是使用暴力递归的方式，来对问题进行求解，对计算找n分零钱，最少需要多少枚硬币，转换为计算n - 1, n - 5 , n - 20, n - 25,这4中情况中中最少的一种情况再+1，就是找n分零钱最少的硬币数量。

后来，发现在进行暴力递归时，有很多重复的计算，所以就将暴力递归的方式进行了优化，改为了记忆化搜索的方式，记录某一面值，需要硬币数量的情况。但是记忆化搜索仅仅是解决了重复计算的问题， 它依然是自上而下的一种调用方式，并没有解决递归调用，消耗栈空间的问题。

最终，经过进一步的观察，发现可以将自顶向下的方式改为自底向上地方方式来进行计算，通过计算出较小的值，然后利用较小的值推导出较大值的这种方式，这样的话，就将递归调用给去掉了，所以最终的实现，就变为了上面最后的方式。

以上的整个过程，就可以理解为在执行动态规划的过程。结合上面的场景问题，接下来将对动态规划的常规步骤进行研究。

#### 动态规划的常规步骤

动态规划中的“动态”可以理解为“会变化的状态”，其步骤如下

1. 定义状态（何为状态：状态就是原问题，子问题的解）

   - 例如定义dp(i)的含义

     > 在解决零钱兑换问题时，就定义了dp(n)的含义，即凑到n分需要的硬币个数，所以dp(41)就表示凑够41分，需要的硬币个数；dp(4)就问凑够4分，需要的硬币个数

     可以发现，定义的状态中的dp(n)就位最终要得到的解，同时也是子问题的解

2. 设置边界条件（边界条件）

   - 例如设置一些可以确定的值

     > 在解决零钱兑换问题是，就定义了dp(1),dp(5),dp(20),dp(25)的值，这些值都是可以直接确定的

     确定了初始状态以后，就可以进行下一步了

3. 确定状态转移方程

   - 比如确定dp(i)与dp(i - 1)之间的关系(利用旧的状态，推导出新的状态，比如现一直dp(i - 1)的值，利用dp(i - 1)推导出dp(i)的值)

#### 动态规划的相关概念

来自维基百科的解释

> 动态规划背后的基本思想非常简单。大致上，若要解一个给定问题，我们需要解其不同部分（即子问题），再根据子问题的解以得出原问题的解。

根据上面的解释，可以得到以下结论

1. 将复杂的原问题，拆解成若干个简单的子问题
2. 每个子问题仅仅解决一次，并保存它们的解
3. 最后推导出原问题的解

一般来讲，若可以利用动态规划来解决的问题，通常具备2个特点

1. 最优子结构（最优化原理）：通过求解子问题的最优解，可以获得原问题的最优解

   - 上面的零钱兑换，是满足这个条件的

2. 无后效性（也就意味着，该问题仅仅拥有最优子机构是不够的，还需要有无后效性）

   - 即某阶段的状态一旦确定，则此后过程的演变，不会受到此前各状态及决策的影响（未来与过去无关）

     > 例如，在零钱兑换时，是通过零钱比较少的状态，推导出零钱比较多的状态。在零钱少时确定的状态，并不会随着后面零钱边多后，最终的结果发生变化，而且后面零钱变多后的推导过程，也不会受前面零钱少时状态的影响

   - 在推导后面阶段的状态时，只关心前面阶段的具体状态值，不关心这个状态是怎么一步步推导出来的

     > 在零钱兑换时，兑换dp(41)的零钱，只关心dp(40),dp(36),dp(16),dp(21)的值是多少，并不关心这些值是怎么计算出来的

##### 无后效性举例

现在有如下图所示的5*5格子

![1577712283155](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1577712283155.png)

现在要从起点(0,0)走到终点(4,4)，要计算一共有多少种走法（规定只能往右，往下走）

利用动态规划来解决的话，首先第一步是定义状态，定义状态如下

假设dp(i,j)是从(0,0)走到(i,j)的走法

> 可以发现，定义的这个状态，既是原问题的解，也是子问题的解

接下来设置边界条件，如下所示

dp(i,0) = dp(0,j) = 1

确定好边界条件以后，就可以设置状态转移方程了，通过规定可以知道，某个点dp(i,j)只可能从两个方向走过来，一个是从dp(i - 1,j)向右走一步到达dp(i,j),或者是从dp(i,j-1)往下走一步，到达dp(i,j)，所以可以知道

dp(i,j) = dp(i - 1,j) + dp(i,j-1)

通过动态规划的步骤，最终就可以算出到达(4,4)一共的走法。

在这里，无后效性体现在:

推导dp(i,j) =时，只需要用到dp(i - 1,j) 与 dp(i,j-1)的结果，并不需要关心dp(i - 1,j) 与 dp(i,j-1)的值是怎么计算出来的

##### 有后效性举例

现在同样有如下的格子

![1577712283155](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1577712283155.png)

要从起点(0,0)走到终点(4,4)，要计算一共有多少种走法。但是现在规定可以向左，向右，向上，向下走，并且同一格子不能走两次

有后效性体现在

现在假设要走到(i,j)这个点，要推导出下一个点dp(i + 1,j) 或 dp(i,j+1)，由于规定同一个格子不能走两次，所以前面格子的走法就决定了后面点的走法，才能保证下一步的走法不会导致同一个点走两次

对动态规划的概念和步骤有一定的了解以后，接下来利用一些场景问题，对动态规划进行深入的理解。

##### 场景二：最大连续子序列和

最大连续子序列和，这个问题，在分治策略部分章节曾有接触过，当时是通过分治的方法来进行求解，分别计算左边部分的最大连续子序列和/右边部分的最大连续子序列和/左右种均存在的最大子序列和，在计算出这三个值中最大的值，这是采用分治策略的一种解法。虽然通过这种方法计算出了最终的结果，但是分治策略来计算并不是最优的，接下来，就使用动态规划的方式来计算。

> 给定一个长度为n的整数序列，求它的最大连续子序列和
>
> - 比如-2,1,-3,4,-1,2,1,-5,4的最大连续子序列和是4 + (-1) + 2 + 1 = 6

定义状态

采用动态规划的方式来进行求解的话，首先要做的就是定义状态dp(i),但是问题在于，dp(i)代表着什么含义呢？

> 在这里，期望得到的结果为计算出当前序列的最大连续子序列和。其实，等价于计算当前序列的最大连续子序列，只不过在计算出最大连续子序列以后，将这些序列的值加起来，就可以得到最大连续子序列了
>
> 但是，并不是任意一个子序列都是最终期望的解，所以可以先排除一些不必要的结果，也就是说，最大连续子序列可能是以序列中的某个元素结尾的序列，例如最大子序列可能为(1,-3)，则是以-3结尾的序列，也可能是(4,-1,2,1)，则是以1结尾的序列，也可能是(-1,2,1,-5)，则是以-5结尾的序列

通过上面的分析，可以先计算出以序列中元素结尾的最大子序列出来，然后再选择这些最大子序列中，最大的序列，所以可以通过如下的方式来定义dp(i)的状态

dp(i)是以nums[i]结尾的最大连续子序列和（nums是整个序列）

所以，结合上面的序列，可以得到以下的结论:

1. dp(0)表示以nums(0)结尾的最大连续子序列和，所以dp(0)的序列为（-2），那么要计算的序列就是以-2结尾的最大连续子序列，所以dp(0) = -2，最大子序列为（-2）
2. 以nums(1)结尾的最大连续子序列和，所以dp(1)的序列为（-2,1），那么要计算的序列就是以1结尾的最大连续子序列，所以dp(1) = 1，最大子序列为（1）
3. 以nums(2)结尾的最大连续子序列和，所以dp(2)的序列为（-2,1,-3），那么要计算的序列就是以-3结尾的最大连续子序列，所以dp(2) = -2(该序列必须包含-3，所以结果不能是1)，最大子序列为（1,-3）
4. 以nums(3)结尾的最大连续子序列和，所以dp(3)的序列为（-2,1,-3,4），那么要计算的序列就是以4结尾的最大连续子序列，所以dp(3) = 4，最大子序列为（4）
5. 以nums(4)结尾的最大连续子序列和，所以dp(4)的序列为（-2,1,-3,4,-1），那么要计算的序列就是以-1结尾的最大连续子序列，所以dp(4) = 3，最大子序列为（4,-1）
6. 以nums(5)结尾的最大连续子序列和，所以dp(5)的序列为（-2,1,-3,4,-1,2），那么要计算的序列就是以2结尾的最大连续子序列，所以dp(5) = 5，最大子序列为（4,-1,2）
7. 以nums(6)结尾的最大连续子序列和，所以dp(6)的序列为（-2,1,-3,4,-1,2,1），那么要计算的序列就是以1结尾的最大连续子序列，所以dp(6) = 6，最大子序列为（4,-1,2,1）
8. 以nums(7)结尾的最大连续子序列和，所以dp(7)的序列为（-2,1,-3,4,-1,2,1,-5），那么要计算的序列就是以-5结尾的最大连续子序列，所以dp(7) = 1，最大子序列为（4,-1,2,1,-5）
9. 以nums(8)结尾的最大连续子序列和，所以dp(8)的序列为（-2,1,-3,4,-1,2,1,-5,4），那么要计算的序列就是以4结尾的最大连续子序列，所以dp(8) = 5，最大子序列为（4,-1,2,1,-5,4）

这样，就计算出了以每一个元素结尾的最大连续子序列和，最终要计算的整一个序列的最大连续子序列和，就是所有dp(i)中的最大值，即在本示例中的dp(0)至dp(8)的最大值，就为最终要得到的结果

规律：以nums(1)为例，要计算当前序列的最大连续子序列，就要参考前面的nums(0)的最大连续子序列的值，如果nums(0)的最大连续子序列和的结果小于等于0，那么nums(1)的最大连续子序列和就不需要加上nums(0)的值，所以，dp(i - 1) <= 0，那么dp(i)的值就位nums(i)，否则dp(i) = dp(i - 1) + nums(i)

通过以上的规律，则可以得到以下的状态转移方程

- 如果dp(i - 1) <= 0，那么dp(i) = nums(i)
- 如果dp(i - 1) > 0，那么dp(i) = dp(i - 1) + nums(i)

初始状态dp(0)的值为nums(0)

最终，可以求的的解为：最大连续子序列和是所有dp(i)中的最大值max{dp(i)},i∈[0,nums.length)

实现代码如下

```java
static int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int[] dp = new int[nums.length];
    dp[0] = nums[0];
    int max = dp[0];
    System.out.println("dp[0] = " + dp[0]);
    for (int i = 1; i < nums.length; i++) {
        if (dp[i - 1] < 0) {
            dp[i] = nums[i];
        } else {
            dp[i] = dp[i - 1] + nums[i];
        }
        max = Math.max(dp[i],max);
    }
    return max;
}
```

通过这种方式，不但计算出了当前序列的最大子序列和，并且还把以每一个元素结尾的最大连续子序列和也计算出来了。

通过这种方式的实现，空间复杂度为O(n)，时间复杂度为O(n)

###### 优化

由于现在的这种实现方式，空间复杂度比较高，通过对前面流程的分析，可以知道，在计算dp(i)的值时，只需要使用到dp(i - 1)的值，所以可以考虑将数组中的元素数量进行优化，优化后的结果如下

```java
static int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int dp = nums[0];
    int max = dp;
    System.out.println("dp[0] = " + dp);
    for (int i = 1; i < nums.length; i++) {
        if (dp < 0) {
            dp = nums[i];
        } else {
            dp +=  nums[i];
        }
        max = Math.max(dp,max);
        System.out.println("dp[" + i + "] = " + dp);
    }
    return max;
}
```

通过这种方式进行优化以后，空间复杂度变为了O(1)

##### 场景三:最长上升子序列(LIS)[[LeetCode](https://leetcode-cn.com/problems/Longest-Increasing-Subsequence/)]

最长上升子序列(最长递增子序列，Longest Increasing Subsequence,LIS)

给定一个无序的整数序列，求出它最长上升子序列的长度（要求严格上升）

> 比如[10,2,2,5,1,7,101,18]的最长上升子序列是[2,5,7,101],[2,5,7,18],长度为4

定义状态：

上面要求求出最长上升子序列的长度，也就意味着，需要知道最长上升子序列的结果是什么。其实结合场景二的问题可以发现，最长上升的子序列，依然可以利用计算以nums(i - 1)时元素的最长上升子序列，进而推导出nums(i)时元素的最长上升子序列，所以通过上面的序列，可以得到以下的计算过程

1. 以nums[0],即10结尾的最长上升子序列是10，即dp(0) = 1
2. 以nums[1],即2结尾的最长上升子序列是(2)，即dp(1) = 1
3. 以nums[2],即2结尾的最长上升子序列是(2)，即dp(2) = 1
4. 以nums[3],即5结尾的最长上升子序列是(2,5)，即dp(3) = 2
5. 以nums[4],即1结尾的最长上升子序列是(2,5)，即dp(4) = 2
6. 以nums[5],即7结尾的最长上升子序列是(2,5,7)，即dp(5) = 3
7. 以nums[6],即101结尾的最长上升子序列是(2,5,7,101)，即dp(6) = 4
8. 以nums[7],即18结尾的最长上升子序列是(2,5,7,18)，即dp(7) = 4

所以，最长上升子序列的长度是所有dp(i)中的最大值max{dp(i)},i∈[0,nums.length)

状态转移方程

设定另外一个变量j，其中j属于[0,j)

遍历j中的所有dp

- 当nums[i] > nums[j]时
  - 说明nums[i]可以接在nums[j]后面， 形成一个比dp[j]更长的上升子序列，长度为dp(j) + 1
  - dp(i) = max(dp(i),dp(j) + 1)
- 当nums[i] <= nums[j]时
  - nums[i]不能接在nums[j]后面，需要跳过此次遍历

其中，初始状态dp[0] = 1;

所以，结合前面的分析，可以得到如下的代码

```java
static int lengthOfLongestIncreasingSubsequence(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int[] dp = new int[nums.length];
    dp[0] = 1;
    int max = 1;
    for (int i = 1; i < dp.length; i++) {
        dp[i] = 1;
        for (int j = 0; j < i; j++) {
            if (nums[i] <= nums[j]) continue;
            dp[i] = Math.max(dp[i],dp[j] + 1);
        }
        max = Math.max(dp[i],max);
    }
    return max;
}
```

通过这种方式实现，其空间复杂度为O(n)，时间复杂度为O(n^2)。需要注意，这里的空间复杂度不能像前面场景二的方式进行优化，因为每当要计算i为的dp值时，需要i前面所有的dp值，每一次都要用到前面的dp值，所以每一个dp值都需要存储，因此不能优化

不过，最长上升子序列还有更优的解法。可以利用二分搜索的思路来解决。由于在本节中主要是对动态规划进行讨论，所以二分搜索这种思路，在后面再进行介绍。接下来，继续对动态规划这种策略进行深入探讨。

##### 场景四：最长公共子序列(LCS)[[LeetCode](https://leetcode-cn.com/problems/Longest-Common-Subsequence/)]

最长公共子序列（Longest Common Subsequence,LCS）

即表达的意思为：求两个序列的最长公共子序列长度，例如

- [1,3,5,9,10]和[1,4,9,10]的最长公共子序列是[1,9,10]，长度为3
- ABCBDAB和BDCABA的最长公共子序列长度是4
  - ABCBDAB和BDCABA >BDAB
  - ABC**BDAB**和**BD**CA**BA** > BDAB
  - A**BC**BD**AB**和**B**D**CAB**A > BCAB
  - A**BCB**D**A**B和**B**D**CAB**A >  BCBA

可以发现，这个问题与前面的最大连续子序列/最长上升子序列非常相似，所以依然可以利用动态规划的方式来进行解决。

前面在利用动态规划来解决问题时，首先要做的就是定义状态。但是在该场景下，不同的地方在于，这里有2个序列，所以现在的状态，就会与前面的不一样了

###### 思路

现假设2个序列分别是nums1，nums2；并定义如下两个变量

- i ∈ [0,nums1.length]
- j ∈ [0,nums2.length]

然后，利用这两个参数来定义状态

假设dp(i,j)是【nums1前i个元素】与【nums2的前j个元素】的最长公共子序列，可以得到以下的信息

1. dp(i,0),dp(0,j)初始值均为0

2. 并且如果nums1[i-1] (即nums1中的第i位)位的元素与nums2[j - 1] (即nums2中的第i位)位的元素相等的话，则有d(i,j) = dp(i - 1, j - 1) +1(只比较最后一位的元素是否相等即可，然后利用小的子问题，推导出大的子问题)

3. 如果nums1[i-1] (即nums1中的第i位)位的元素与nums2[j - 1] (即nums2中的第i位)位的元素不相等的话，则存在两种情况

   - 查看nums1的nums1[i - 2]与nums2[i - 1]的最长公共子序列是多少。因为可能nums2新增的这个元素，虽然不与nums1新增的这个元素相等， 但是可能与nums1[i - 2]序列中的元素相等
   - 查看nums2的nums2[i - 2]与nums1[i - 1]的最长公共子序列是多少。原因同上

   所以，如果nums1[i - 1] ≠ nums2[j - 1],那么dp(i,j) = max{dp(i - 1,j),dp(i,j - 1)}

   这样，就确定了该问题的状态转移方程。

利用前面的分析，通过递归的方式实现如下：

```java
static int longestCommonSubsequence(int[] nums1, int[] nums2) {
    if (nums1 == null || nums1.length == 0) return 0;
    if (nums2 == null || nums2.length == 0) return 0;
    return longestCommonSubsequence(nums1,nums1.length,nums2,nums2.length);
}

/*
* 求nums1前i个元素与nums2前j个元素的最长公共子序列
* */
static int longestCommonSubsequence(int[] nums1,int i,int[] nums2, int j) {
    if (i == 0 || j == 0) return 0;
    if (nums1[i - 1] == nums2[j - 1]) {
        return longestCommonSubsequence(nums1,i - 1,nums2,j - 1) +1;
    }
    //最后一个元素不相等
    return Math.max(longestCommonSubsequence(nums1,i,nums2,j - 1),longestCommonSubsequence(nums1,i - 1,nums2,j ));
}
```

这种方式实现的空间复杂度为：O(k)，其中k = min{n,m},n,m是2个序列的长度；

由于这里的空间复杂度主要取决于递归的深度，所以，可以发现，当n,m中有一个值递减为0时，递归就会结束，所以取决于两个序列中长度更短的序列。

时间复杂度为：O(2^n)，当n = m 时

将上面的递归实现改为非递归实现，结果如下

```java
static int longestCommonSubsequence(int[] nums1, int[] nums2) {
    if (nums1 == null || nums1.length == 0) return 0;
    if (nums2 == null || nums2.length == 0) return 0;
    int[][] dp = new int[nums1.length + 1][nums2.length + 1];
    for (int i = 1; i <= nums1.length; i++) {
        for (int j = 1; j <= nums2.length; j++) {
            if (nums1[i - 1] == nums2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i - 1][j],dp[i][j - 1] );
            }
        }
    }
    return dp[nums1.length][nums2.length];
}
```

这种方式实现

- 空间复杂度为：O(n*m)
- 时间复杂度为：O(n*m)

通过观察可以发现，dp(i,j)值的来源有三个地方，分别是dp[i - 1] [j - 1]，dp[i - 1] [j] ，dp[i] [j - 1]。所以前面定义的二维数组，当计算到后面(i增大以后)的值时，前面存储的一些值，其实永远也用不上了，使用到的值可能是第i行的数据与i - 1行的数据，所以可以通过这种方式，利用滚动数组对上面的实现进行优化。

优化后的结果如下：

```java
static int longestCommonSubsequence(int[] nums1, int[] nums2) {
    if (nums1 == null || nums1.length == 0) return 0;
    if (nums2 == null || nums2.length == 0) return 0;
    int[][] dp = new int[2][nums2.length + 1];
    for (int i = 1; i <= nums1.length; i++) {
        int row = i & 1;
        int prevRow = (i - 1) & 1;
        for (int j = 1; j <= nums2.length; j++) {
            if (nums1[i - 1] == nums2[j - 1]) {
                dp[row][j] = dp[prevRow][j - 1] + 1;
            } else {
                dp[row][j] = Math.max(dp[prevRow][j],dp[row][j - 1] );
            }
        }
    }
    return dp[nums1.length & 1][nums2.length];
}
```

将二维数组进一步优化为一维数组

```java
static int longestCommonSubsequence(int[] nums1, int[] nums2) {
    if (nums1 == null || nums1.length == 0) return 0;
    if (nums2 == null || nums2.length == 0) return 0;
    int[] dp = new int[nums2.length + 1];
    for (int i = 1; i <= nums1.length; i++) {
        int cur = 0;//保存左上角的值
        for (int j = 1; j <= nums2.length; j++) {
            int leftTop = cur;
            cur = dp[j];
            if (nums1[i - 1] == nums2[j - 1]) {
                dp[j] = leftTop + 1;
            } else {
                dp[j] = Math.max(dp[j],dp[j - 1] );
            }
        }
    }
    return dp[nums2.length];
}
```

由于发现，nums1和nums2，在循环操作是，哪个数组作为i变量，哪个数组作为j变量，其实是没有影响的，通过两个循环，都能考虑到所有的情况，所以基于这种情况，可以进一步将dp数组的空间进行优化。可以选择length更小的数组来作为dp数组的长度

优化后的结果如下

```java
static int longestCommonSubsequence(int[] nums1, int[] nums2) {
    if (nums1 == null || nums1.length == 0) return 0;
    if (nums2 == null || nums2.length == 0) return 0;
    int[] rowsNums = nums1, colsNums = nums2;
    if (nums1.length < nums2.length) {
        rowsNums = nums2;
        colsNums = nums1;
    }
    int[] dp = new int[colsNums.length + 1];
    for (int i = 1; i <= rowsNums.length; i++) {
        int cur = 0;//保存左上角的值
        for (int j = 1; j <= colsNums.length; j++) {
            int leftTop = cur;
            cur = dp[j];
            if (rowsNums[i - 1] == colsNums[j - 1]) {
                dp[j] = leftTop + 1;
            } else {
                dp[j] = Math.max(dp[j],dp[j - 1] );
            }
        }
    }
    return dp[colsNums.length];
}
```

通过这种优化，不管从空间复杂度还是时间复杂度，都已经达到了最优。

##### 场景五：最长公共子串

最长公共子串（Longest Common Substring）

- 子串是连续的子序列

例如：ABCBA和BABCA的最长公共子串是ABC,长度为3

###### 思路

假设两个字符串分别为str1,str2，并定义如下两个变量

- i ∈ [0,str1.length]
- j ∈ [0,str2.length]

假设状态dp(i,j)是以str1[i - 1],str2[j - 1]结尾的最长公共子串长度
其中规定dp(i,0),dp(0,j)的初始值均为0

所以，结合前面最长公共子序列的经验可以知道有如下转移方程情况

- str1[i - 1] = str2[j - 1],那么dp(i,j) = dp(i - 1,j - 1) + 1
- str1[i - 1] ≠ str2[j - 1],那么dp(i,j) = 0

最终，期望得到的值就是所有dp(i,j)中最大的值，即max{dp(i,j)}

通过上面的思路，最终得到的代码如下

```java
static int longestCommonSubstring(String str1,String str2) {
    if (str1 == null || str2 == null) return 0;
    if (str1.length() == 0 || str2.length() == 0) return 0;
    int[][] dp = new int[str1.length() + 1][str2.length() + 1];
    char[] chars1 = str1.toCharArray();
    char[] chars2 = str2.toCharArray();
    int max = 0;
    for (int i = 1; i <= chars1.length; i++) {
        for (int j = 1; j <= chars2.length; j++) {
            if (chars1[i - 1] == chars2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
                max = Math.max(max,dp[i][j]);
            }
//                else {//值默认就是0 所以不用赋值
//                    dp[i][j] = 0;
//                }
        }
    }
    return max;
}
```

通过这种实现的空间复杂度与时间复杂度为：O(n*m)。结合前面最长公共子序列的优化经验可以知道，这里的二维数组同样可以进行优化，因为计算dp[i] [j]的值时，只会用到dp[i - 1] [j - 1]的值，所以优化思路和最长公共子串是一样的。

所以，优化后的结果如下：

```java
static int longestCommonSubstring(String str1,String str2) {
    if (str1 == null || str2 == null) return 0;
    if (str1.length() == 0 || str2.length() == 0) return 0;
    char[] chars1 = str1.toCharArray();
    char[] chars2 = str2.toCharArray();
    char[] rowsChars = chars1,colsChars = chars2;
    if (chars1.length < chars2.length) {
        rowsChars = chars2;
        colsChars = chars1;
    }
    int[] dp = new int[colsChars.length + 1];
    int max = 0;
    for (int row = 1; row <= rowsChars.length; row++) {
        int cur = 0;
        for (int col = 1; col <= colsChars.length; col++) {
            int leftTop = cur;
            cur = dp[col];
            if (chars1[row - 1] == chars2[col - 1]) {
                dp[col] = leftTop + 1;
                max = Math.max(max,dp[col]);
            }
            else {
                dp[col] = 0;
            }
        }
    }
    return max;
}
```

通过优化后，空间复杂度为：O(k)，k = min(m,n);

时间复杂度为：O(n*m)

##### 场景六：0-1背包问题

0-1背包问题，在讨论贪心策略时，曾经介绍过该问题，但是当时也说到，利用贪心策略来解决该问题时，并不靠谱，因为根据不同的优先策略最终得到结果并不一样，所以并不靠谱，但是，如果利用动态规划的策略来解决该问题的话，最终的结果，就很固定了。

首先来回顾一下0-1背包问题的场景

> 有n件物品和一个最大承重为W的背包，没见物品的重量是w，价值是v
>
> 在保证总重量不超过W的前提下，将哪几件物品装入背包，可使得背包的总价值最大？

所以，现在就考虑使用动态规划来解决该问题。

定义状态：

现假设values是价值数组，weights是重量数组

- 编号为k的物品，价值是values[k]，重量为weights[k]，其中k∈[0,n)(n为物品的数量)

定义如下状态
dp(i,j)表示**最大承重为j时，有前i件物品可选**时的最大总价值，其中i ∈ [0,n],j ∈ [0,W]

> 例如：dp(3,7)
>
> 现有10件物品，但是只能选择前面的3件物品
>
> 最大承重量为7

初始值：

dp(i,0),dp(0,j)初始值为0；因为分别表示最大承重量为0时和可选的物品为0时的状态

那么，dp{i,j}如何才能通过子问题推导出来呢？

当面临i件物品时，有两种选择，

- 如果不选择第i件物品时，dp(i,j) = dp(i - 1, j)，因为在第i件物品时，没有选，所以背包的称重量没有发生变换，只是现在的情况下，可选的物品比原来多了一件，但是最终的价值是一样的
- 如果选择第i件物品时，dp(i,j) = dp(i - 1, j - weights[i - 1]) +  values[i - 1]

最终决定是否选择该件物品由最终的价值决定，所以取这两种情况中的最大值dp(i,j) =  max(dp(i - 1, j),dp(i - 1, j - weights[i - 1]) +  valuess[i - 1])

不过需要注意，除了上面容易考虑到的两种情况以外，还有另外一种情况，即当前最大称重量j小于第i件物品的重量时，该件物品就不可能选择，所以这个时候，有i件物品可选与i-1件物品可选时的结果是一样的，所以这个时候的结果为dp(i,j) = dp(i - 1, j)

所以，最终的状态转移方程如下

- j < weights[i - 1]，那么dp(i,j) = dp(i - 1, j)
- j ≥ weights[i - 1]，那么dp(i,j) =  max(dp(i - 1, j),dp(i - 1, j - weights[i - 1]) +  valuess[i - 1])

通过上面的分析思路，最终得到的代码如下

```java
static int maxValue(int[] values,int[] weights, int capacity) {
    if (
            values == null || 
            weights == null || 
            values.length == 0 || 
            weights.length == 0 || 
            values.length != weights.length || 
            capacity <= 0
    ) return 0;
    int[][] dp = new int[values.length + 1][capacity + 1];
    for (int i = 1; i <= values.length; i++) {
        for (int j = 1; j <= capacity; j++) {
            if (j < weights[i - 1]) {//当前最大称重量j小于第i件物品的重量时
                dp[i][j] = dp[i - 1][j];
            } else  {
                dp[i][j] = Math.max(dp[i - 1][j],values[i - 1] + dp[i - 1][j - weights[i - 1]]);
            }
        }
    }
    return dp[values.length][capacity];
}
```

结合前面的经验，可以知道，计算第i行数据时，只会用到i-1行的数据，所以可以想到，dp数组是可以进行优化的。而且在利用前面的值计算后面的值时，用到了dp[i - 1] [j - weights[i - 1]]的值进行计算，所以如果还是用前面的方法来进行优化的话，[j - weights[i - 1]]的值是拿不到的，因为会被覆盖，所以为了保证能拿到[j - weights[i - 1]]的数据，现在考虑利用重量大的情况，推导出重量小的情况。这样就不会覆盖[j - weights[i - 1]]的值

最终，优化后的结果如下

```java
static int maxValue(int[] values,int[] weights, int capacity) {
    if (
            values == null ||
                    weights == null ||
                    values.length == 0 ||
                    weights.length == 0 ||
                    values.length != weights.length ||
                    capacity <= 0
    ) return 0;
    int[] dp = new int[capacity + 1];
    for (int i = 1; i <= values.length; i++) {
        for (int j = capacity; j >= 1; j--) {
            if (j < weights[i - 1]) continue;
               dp[j] = Math.max(dp[j],values[i - 1] + dp[j - weights[i - 1]]);
        }
    }
    return dp[capacity];
}
```

#### 扩展

##### 最长上升子序列-二分搜索实现

在前面，利用动态规划遍历序列来进行查找，所以，通过动态规划的方式来查找，其时间复杂度为O(n^2)，如果使用二分搜索的方式实现，则会更优。其思路如下

假设序列如下图所示

![1578483790149](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1578483790149.png)

为了能更好的理解，现在请将每一个数组都看做为一张扑克牌，然后从左往右按顺序处理每一张扑克牌

- 将正在处理的牌，放在第一张大于等于当前牌的牌堆之上
- 如果找不到第一张牌大于等于当前正在处理的牌的牌堆时，就将它放入一个新的牌堆中

例如，

1. 在最开始的时候，拿到的是10这张牌，但是这时候没有牌堆，所以这个时候10会成立一个新的牌堆

   ![1578484199146](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1578484199146.png)

2. 接下来，会拿到2这张牌，通过从左往右寻找牌堆，所以先找到了10这个牌堆，这个牌堆的牌顶是10，10这张牌是大于2这张牌的，所以会将2这张牌压在10这张牌的上面，结果如下

   ![1578484334731](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1578484334731.png)

3. 接下来，继续出列下一张牌2，通过寻找牌堆，发现现在有一个牌堆的牌顶是2，所以就会将正在处理的这张牌，压到牌顶为2的牌堆上，结果如下

   ![1578484444822](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1578484444822.png)

4. 接下来，会拿到5这张牌，然后从左往右查找，并没有发现牌顶大于等于5的牌堆，所以会新建一个牌堆

   ![1578484521081](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1578484521081.png)

5. 然后拿到下一章牌1，并且从左往右开始寻找牌堆，发现当前有一个牌堆的牌顶为2，大于等于当前的牌，所以就会将1这张牌，放在牌顶为2的牌堆中

   ![1578484628410](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1578484628410.png)

6. 拿到下一章牌7，发现所有牌堆的牌顶都不大于当前的牌，所以会新建一个牌堆，最终的结果如下

   ![1578484703691](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1578484703691.png)

7. 继续拿到下一张牌101，依然发现所有牌堆的牌顶都不大于当前的牌，所以又会新建一个牌堆，结果如下

   ![1578484779288](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1578484779288.png)

8. 接下来，会拿到最后一张牌18，从左往右查找发现，前面3个牌堆的牌顶都不大于当前的牌，所以最终18会放到最后一个牌堆上

   ![1578484851045](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/28-DynamicProgramming/Resource/1578484851045.png)

现在，所有牌都已经排好了，并且发现，现在的最长上升子序列已经产生了，为2,5,7,101或者2,5,7,18.并且最终牌堆的数量，就是最长上升子序列的长度，且组成的元素来自于4个牌堆，通过这种思路，得到的代码如下

```java
static int lengthOfLongestIncreasingSubsequence(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int len = 0;//牌堆的数量
    int[] top = new int[nums.length];//牌顶数组
    //遍历所有的牌
    for (int num : nums) {
        int i = 0;
        for (; i < len; i++) {
            if (top[i] >= num) {//找到了一个大于等于num的牌顶
                top[i] = num;
                break;
            }
        }
        if (i == len) {
            //所有牌顶小于当前的牌,新建牌堆
            len++;
            top[i] = num;
        }
    }
    return len;
}
```

通过这种方式实现，可以发现如果是最坏的情况的话，时间复杂度依然是O(n^2)，所以对比动态规划，也没有太多的提升。所以在这种情况下，可以利用二分搜索来进行优化。

由于发现牌堆从左往右的牌顶，是升序的。当有一个新的牌要放在合适的位置时，就可以利用二分搜索的方式来找到其对应的位置，然后找到位置后，直接覆盖牌顶即可，所以通过二分搜索优化后的结果如下

```java
static int lengthOfLongestIncreasingSubsequence(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int len = 0;//牌堆的数量
    int[] top = new int[nums.length];//牌顶数组
    //遍历所有的牌
    for (int num : nums) {
        int begin = 0;
        int end = len;
        while (begin < end) {
            int mid = (begin + end) >> 1;
            if (num <= top[mid]) {
                end = mid;
            } else {
                begin = mid + 1;
            }
        }
        //覆盖牌顶
        top[begin] = num;
        //检查是否要新建牌堆
        if (begin == len) len++;
    }
    return len;
}
```

