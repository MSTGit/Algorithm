#### 递归（Recursion）

递归：函数（方法）直接或者间接调用自身。是一种常用的编程技巧

例如下列的函数，计算1 + 2 + 3 + ... + n的和。

```java
int sum(int n) {
 	if (n <= 1) return n;
 	return n + sum(n - 1);
}
```

在函数内部，直接调用函数自身，这就属于递归。

另外一种间接调用，例如

```java
void a(int v) {
	if (v < 0) return;
	b(--v);
}

void b(int v) {
	a(--v);
}
```

但是在一般开发中，常见的递归还是直接调用自身的这种情况。

#### 递归现象

说到递归，其实生活中也有递归的现象存在。例如

现象一：

> 从前有座山，山里有座庙，庙里有个老和尚，正在给小和尚将故事呢！故事的内容是什么呢？【从前有座山，山里有座庙，庙里有个老和尚，正在给小和尚将故事呢！故事的内容是什么呢？】[从前有座山，山里有座庙，庙里有个老和尚，正在给小和尚将故事呢！故事的内容是什么呢？]....

这就是一个递归。

现象二：

> GNU 是GNU is Not Unix的缩写
>
> GNU是一个开源组织，它的全称是GNU is Not Unix，可以发现GNU其实也是一个递归，因为
>
> GNU -> GNU is Not Unix -> GNU is Not Unix is Not Unix -> GNU is Not Unix is Not Unix is Not Unix -> GNU is Not Unix is Not Unix is Not Unix is Not Unix...

所以，GNU也是一个递归

现象三：

> 假设A在一个电影院，想知道自己坐在哪一排，但是前面人很多
>
> A懒得数，余数问前一排的人B【你坐在哪一排？】，只要把B的答案加一，就是A的排数
>
> B懒得数，余数问前一排的人C【你坐在哪一排？】，只要把C的答案加一，就是B的排数
>
> C懒得数，余数问前一排的人D【你坐在哪一排？】，只要把D的答案加一，就是C的排数
>
> 。。。
>
> 直到问道最前面的一排，最后，大家都知道自己在哪一排了。

这种，其实也是一种递归。

#### 函数的调用过程

由于递归的本质就是函数自己调用自己，所以现在来研究函数的调用过程，现有如下几个函数

```java
public static void main(String [] args) {
	test1(10);
	test2(20);
}

public static void test1(int v) {}

public static void test2(int v) {
	test3(30);
}

public static void test3(int v) {}
```

分别为main函数，test1，,test2,test3,函数，然后在main函数中调用了test1，test2函数，在test2函数中调用了test3函数，并且大家都知道，如果程序已启动，首先会执行main函数中的代码。所以main函数被调用以后，就会发生以下事情

1. 在栈空间为该函数分配一段连续的空间给该函数，用来保存该函数的实参和函数内部的局部变量
2. 所以调用main函数以后，系统就会为main函数在栈空间申请一块内存，用来保存main函数中的参数
   ![1577085475429](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577085475429.png)
3. 接下来，main函数内部会分别先后调用test1和test2函数
4. 调用test1函数，系统会为test1分配一段连续的栈空间，用来保存test1函数中的参数
   ![1577085603111](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577085603111.png)
5. 由于test1函数内部，没有做任何事情，所以test1函数立马就执行完毕了，所以test1(10)这句代码，一瞬间就执行完了
6. 一旦一个函数执行完以后，系统就会回收该函数的栈空间。所以test1(10)执行完毕，回收栈空间以后，内存中的结构如下
   
   ![1577085475429](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577085475429.png)
7. 接下来，main函数会继续调用test2(20)函数，调用test2(20)函数，系统又会为test2函数分配一段连续的栈空间，用来保存test2函数中的参数，所以调用test2函数以后，内存中的结构如下
   
   ![1577085949928](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577085949928.png)
8. 在test2函数内部，会执行test3(30)这段代码，所以就会调用test3函数
9. 一旦调用test3函数，系统又会为test3函数分配一段连续的栈空间，用来保存test3函数的参数，所以调用test3以后，内存中的结构如下
   
   ![1577086028989](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577086028989.png)
10. 由于test3里面，没有做任何事情，所以test2函数内部的test3(30)这句代码很快就执行完了
11. test3(30)函数一旦执行完毕，就会回收test3函数的栈空间，所以回收后的内存结构如下
    
    ![1577085949928](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577085949928.png)
12. 由于test3(30)这句代码执行完毕，也就意味着main函数中的test2(20)也就执行完毕，所以test2函数的栈空间也会被回收，所以回收后的内存结构如下
    
    ![1577085475429](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577085475429.png)
13. 由于test2(20)这句代码执行完毕，也就意味着main函数执行完毕，所以最终main函数的栈空间也会被回收，整个函数调用过程就结束了
    
    ![1577086366334](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577086366334.png)

以上就是函数的函数调用过程，而且在调用过程中，可以发现，新开辟的函数栈空间，是叠加到原来函数的栈空间之上的，函数调用完毕，系统又会回收该函数的栈空间。还可以发现，为函数开辟栈空间和回收栈空间内存，与栈这种数据结构很类似，开辟栈空间类似于栈的push操作，回收栈空间类似于栈的pop操作。

前面讨论的是函数调用的过程，如果现在是函数的递归调用，那应该有是怎样的情况呢？

##### 函数的递归调用过程

同样的，假设现在有如下的递归调用代码

```java
public static void main(String [] args) {
	sum(4);
}

int sum(int n) {
 	if (n <= 1) return n;
 	return n + sum(n - 1);
}
```

以上代码的函数调用，执行流程如下

1. 程序已启动，首先会调用main函数，所以系统会为main函数在栈空间开辟一段连续的栈空间，用来保存main函数的参数
   ![1577087146525](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577087146525.png)
2. 接下来，就会在main函数内部调用sum函数，所以会为sum函数开辟一段连续的栈空间，并且会将参数4传递到sum函数中的实参n中。最终的内存结构如下
   ![1577087319499](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577087319499.png)
3. 由于在main函数中传入的参数是4，所以在sum函数内部，又会调用sum(4 - 1)即sum(3)，所以又会为sum函数开辟一段连续的栈空间，push到栈空间中，需要注意，这一次调用sum函数传入的参数是3，所以最终的内存结构如下
   ![1577087602486](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577087602486.png)
4. 与第3不一样，在调用sum(3)时，sum函数又会调用sum(2)，所以又会为sum(2)开辟一段栈空间，并且传入的参数是2，所以内存结构如下
   ![1577087727851](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577087727851.png)
5. 同样的，sum(2)内部会继续调用sum(1)，所以又会继续为sum(1)开辟栈空间，并且传入的参数为1，所以内存结构如下
   ![1577087805531](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577087805531.png)
6. 由于在上一步中，传入的参数是1，根据条件判断，不会再调用sum函数，而是直接将值返回，所以认为找到了递归调用的出口。所以在这个时候，sum(1)函数的调用就结束了，那么sum(1)的栈空间就会被回收，所以内存结构如下
   ![1577087727851](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577087727851.png)
7. sum(1)的结果出来了，所以sum(2)的计算结果也出来了，所以就会执行sum(2)中的return操作，这一步完成后的内存结构如下
   ![1577087602486](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577087602486.png)
8. sum(2)的结果出来了，所以sum(3)的计算结果也出来了，所以就会执行sum(3)中的return操作，这一步完成后的内存结构如下
   ![1577087319499](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577087319499.png)
9. sum(3)的结果出来了，所以sum(4)的计算结果也出来了，所以就会执行sum(4)中的return操作，这一步完成后的内存结构如下
   ![1577087146525](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577087146525.png)
10. 由于sum(4)函数执行完，main函数中的代码就全部执行完了，所以最终main函数的栈空间也会被回收，这一步完成后的内存结构如下
    ![1577089114088](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577089114088.png)

上面流程转换为流程图，如下所示

![1577089222589](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577089222589.png)



由于一开始传入的参数是4，最终在栈空间开辟了4个sum函数的内存空间，所以可以认为sum函数的空间复杂度为O(n)

所以结合上面的递归调用流程的分析，可以知道，如果递归调用没有终止，就会一直消耗栈空间，为什么会一直消耗呢？原因是前面开辟函数空间的函数代码还没有执行完，所以就不会回收，只有函数执行完了以后，当前函数的栈空间才会回收。

递归调用一直消耗栈空间最终导致的结果是栈内存溢出（Stack Overflow）

所以在进行递归调用时，需要有一个明确的结束递归调用的条件。例如以上递归调用中`if (n <= 1) return n; `就明确了递归调用的结束条件。这种结束递归调用的条件，也叫做边界条件、递归基

#### 实例分析

实例一：

计算1 + 2 + 3 + ... + (n - 1) + n的和（n > 0 ）

如果采用递归的方式来的话，代码如下

```java
int sum(int n) {
 	if (n <= 1) return n;
 	return n + sum(n - 1);
}
```

通过这种方式来实现，总消耗时间T(n) = T( n - 1 ) + O( 1 )，

- 时间复杂度为O(n)
- 空间复杂度为O(n)

如果采用非递归调用的方式的话，代码如下

```java
int sum(int n) {
	int result = 0;
	for (int i = i; i <= n ; i++) {
		result += i;
	}
	return result;
}
```

如果是采用上面这种方式来计算的话，很容易可以分析出

- 时间复杂度为O(n)
- 空间复杂度为O(1)

所以相当于非递归调用这种方式是对递归调用方式的一种优化。

但是针对上面的这种非递归调用，其实还可以更优，因为可以利用数学公式来结合，所以优化后的代码如下

```java
int sum(int n) {
 	if (n <= 1) return n;
 	return (1 + n) * n >> 1;
}
```

通过这种优化，其复杂度分别为

- 时间复杂度O(1)
- 空间复杂度O(1)

注意：使用递归调用不是为了求得最优解，而是为了简化解决问题的思路，代码会更加简洁

#### 递归的基本思想

- 拆解问题
  1. 把规模大的问题变成规模较小的同类型问题
  2. 规模较小的问题有不断变成规模更小的问题
  3. 规模小到一定程度可以直接得出它的解

![1577090604155](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577090604155.png)

- 求解
  1. 由最小规模问题的解得出较大规模问题的解
  2. 较大规模问题的解不断得出规模更大问题的解
  3. 最后得出原来问题的解
- 凡是可以利用上述事项解决问题的，都可以尝试使用递归
  1. 很多链表，二叉树相关的问题，都可以使用递归来解决
     - 因为链表，二叉树本身就是一个递归结构（链表中包含链表，二叉树中包含二叉树）

##### 递归的使用套路

1. 明确函数的功能
   - 先不要去思考代码怎么写，要先搞清楚这个函数是干嘛用的，能完成什么功能
2. 明确原问题与子问题的关系
   - 寻找f(n)与f(n - 1)的关系（例如上面递归求和中，sum(n)与sum(n - 1)的关系）
3. 明确递归基（边界条件）
   - 递归的过程中，子问题的规模在不断减小，当小到一定程度时，可以直接得出它的解
   - 寻找递归基，相当于是思考：问题规模小到什么程度可以直接得出解？

#### 斐波那契数列

在前面复杂度章节就提到过斐波那契数列，但是这一次的斐波那契数列会比前面更详细。

斐波那契数列：1、1、2、3、5、8、13、21、34、....

斐波那契数列的特征：当前数的值，等于数列中前两个树的和，公式为F(1) = 1，F(2) = 2，F(n) = F(n-1) + F(n-2)（n >=3）

结合公式，很容易可以编写出一个计算前n项斐波那契数列的和

```java
int fib(int n) {
	if(n <= 2) return = 1;
	return fib(n - 1) + fib(n - 2);
}
```

根据递推式T(n) = T(n-1) + T(n-2) +O(1)，可得知时间复杂度为O(2^n)，空间复杂度为O(n)

递归调用的空间复杂度 = 递归深度 * 每次调用所需的辅助空间（即新开辟的内存空间）

##### fib函数的调用过程

如果在一开始，调用fib函数时，传入的参数为6。可以得到如下的拆解调用

![1577092361197](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577092361197.png)

通过拆解调用可以发现，在调用fib函数的过程中，出现了特别多的重复计算，所以属于一种自顶向下的调用过程

##### fib优化

用数组存放计算过的结果，避免重复计算

优化后的代码如下

```java
int fib(int n) {
	if (n <= 2) return 1;
	int[] array = new int[n + 1];
	array[1] = array[2] = 1;
	return fib(n,array);
}

int fib(int n , int[] array) {
	if (array[n] == 0) {
		array[n] = fib(n - 1, array) + fib(n - 2,array);
	}
	return array;
}
```

通过这样优化以后，fib的时间复杂度为O(n)，空间复杂度为O(n)

##### fib进一步优化1

这一次优化，是对上面优化的递归调用进行优化，优化后的代码如下

```java
int fib(int n) {
	if (n <= 2) return 1;
	int[] array = new int[n + 1];
	array[1] = array[2] = 1;
	for (int i = 3 ; i <= n; i++) {
		array[i] = array[i - 1] + array[i - 2];
	}
	return array[n];
}
```

但是，虽然对递归进行了优化，但是时间复杂度和空间复杂度没有得到改善，依然为O(n)，优化的地方在于递归调用时的空间消耗。

这种通过优化后的计算过程，属于自底向上的计算过程。

##### fib进一步优化2

在上一步优化时发现，数组元素在每一次计算时，其实只使用到了数组的2个元素，基于这个特点的话，可以使用**滚动数组**来进行优化

优化后的代码如下

```java
int fib(int n) {
	if (n <= 2) return 1;
	int[] array = new int[2];
	array[0] = array[1] = 1;
	for (int i = 3 ; i <= n; i++) {
		array[i % 2] = array[(i - 1) % 2] + array[(i - 2) % 2];
	}
	return array[n % 2];
}
```

通过这一次优化，成功的将空间复杂度优化到了O(1)。但是依然可以再次优化

因为在编程语言中，运算%，*，/的效率都很低，所以可以通过位运算来进行优化

```java
int fib(int n) {
	if (n <= 2) return 1;
	int[] array = new int[2];
	array[0] = array[1] = 1;
	for (int i = 3 ; i <= n; i++) {
		array[i % 2] = array[(i - 1)  & 1] + array[(i - 2)  & 1];
	}
	return array[n & 1];
}
```

##### fib进一步优化3

由于发现只用到了数组中的两个元素，所以可以进一步优化，不使用数组，直接定义两个变量来进行优化

化

```java
int fib(int n) {
	if (n <= 2) return 1;
    int first = 1;
    int second = 1;
	for (int i = 3 ; i <= n; i++) {
        second = first + second;
        first = second - first;
	}
	return second;
}
```

通过这样的优化，时间复杂度变为O(n)，空间复杂度变为了O(1)

由于斐波那契数列存在特征方程，所以可以利用线性代数的解法来解。通过这种方式来优化，可以将时间复杂度可空间复杂度优化到更低。

#### 上楼梯（跳台阶）

楼梯一共有n阶台阶，上楼可以一步上一阶，也可以一步上两阶，走完n阶台阶一共有多少种不同的走法？

问题分析：

假设下图就是一个有n阶台阶的台阶

![1577105644982](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577105644982.png)

现在要计算，n阶台阶，按照上面的要求，一共有多少种走法。

假设台阶只有1阶，可以知道只有一种走法；

假设台阶有2阶，可以知道有两种走法；

观察发现，当数据规模小到一定程度的时候，就可以直接得出问题的解，所以可以尝试，用递归的方式来解决。

1. 假设n阶台阶有f(n)种走法，第一步就有两种走法

   - 如果上1阶，那就剩下n - 1阶，共f(n - 1)种走法
   - 如果上2阶，那就剩下n - 2阶，共f(n - 2)种走法

   所以f(n) = f(n - 1) + f(n - 2)

所以，计算公式就出来了。可以发现，这个式子和前面的斐波那锲很像，只不过初始条件不一样。

所以计算n阶台阶走法的代码如下

```java
int climbStairs(int n) {
	if (n <= 2) return n;
    return climbStairs(n - 1) + climbStairs(n - 2);
}
```

由于算法与斐波那契数列计算方法几乎一样，所以优化的思路也是一致的。

#### 汉诺塔(Hanoi)

汉诺塔如下所示

![1577107121103](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107121103.png)

编程实现把A的n个盘子移动到C（盘子编号是[1,n]）

要求：

1. 每次只能移动一个盘子
2. 大盘子只能放在小盘子下面

挪完以后的最终结果

![1577107406913](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107406913.png)



问题分析：

假设**只有一个盘子**的情况

![1577107505568](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107505568.png)

非常简单，就一次就挪过去了

![1577107537802](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107537802.png)

**如果是两个盘子**

![1577107573205](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107573205.png)

也很容易想到，先将小的挪动到B，

![1577107753204](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107753204.png)

然后再将大的挪动到C，

![1577107772703](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107772703.png)

最终将小的从B挪动到C就可以了

![1577107794234](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107794234.png)

**如果是3个盘子**

![1577107833202](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107833202.png)

先将1挪动到C

![1577107919550](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107919550.png)

在将2挪动到B

![1577107957920](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577107957920.png)

再将1挪动到B

![1577108034949](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577108034949.png)

再将3挪动到C

![1577108065002](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577108065002.png)

再将1挪动到A

![1577108111816](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577108111816.png)

再将2挪动到C

![1577108129398](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577108129398.png)

最后将1挪动到C

![1577108158414](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577108158414.png)

解决思路

1. 当 n == 1时，直接将盘子从A移动到C

2. 当n > 1时，可以拆分为3大步骤

   1. 将n - 1个盘子从A移动到B
   2. 将编号为n的盘子从A移动到C
   3. 将n - 1个盘子从B挪动到C

   其中第一步和第三部明显是一个递归调用

所以，最终的实现代码如下

```java
void hanoi(int n ,String a, String b, String c) {
    if (n == 1) {
        move(n,a,c);
    }
    hanoi(n - 1,a,c,b);
    move(n - 1,a,c);
    hanoi(n - 1,b,a,c);
}
void move(int no,String from, String to) {
    System.out.println("将" + no + "号盘子从" + from + "移动到" + to );
}
```

#### 递归转非递归

现在有如下的一段递归调用代码

```java
public static void main(String[] args) {
    log(4);
}

static void log(int n) {
    if (n <= 1) return;
    log(n - 1);
    int v = n + 10;
    System.out.println(v);
}
```

前面已经知道了，函数递归调用的过程，所以在每一次递归调用的参数，局部变量都会保存在对应的栈帧（Stack Frame）中，下图所示的结构，开辟给每个函数的栈空间，就叫做栈帧，所以每一个函数都有自己的栈帧

![1577187697893](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/23-Recursion/Resource/1577187697893.png)

所以，递归可能存在以下一些问题

1. 如果递归调用深度较大，会占用比较多的栈空间，甚至会导致栈溢出。
2. 有些时候，递归会存在大量的重复计算，性能非常差。

所以，在某些时候，可能希望将递归转为非递归（递归100%可以转为非递归）

##### 递归转非递归的万能方法

自己维护一个栈，用来保存参数，局部变量。

但是空间复杂度依然没有得到优化。

将上面的递归调用转为非递归，实现如下

```java
static class Frame {
    int n;
    int v;
    Frame(int n, int v) {
        this.n = n;
        this.v = v;
    }
}

static void log(int n) {
    Stack<Frame> frames = new Stack<>();
    while (n > 0) {
        frames.push(new Frame(n,n +10));
        n--;
    }
    while (!frames.isEmpty()) {
        Frame frame = frames.pop();
        System.out.println(frame.v);
    }
}
```

可以发现，这段非递归的代码与前面递归调用的代码，完成的功能是一样的。所以，只要能模拟函数调用栈，递归就一定能转为非递归

再某些时候，也可以重复使用一组相同的变量，来保存每一帧的内容。例如，将上面的代码优化后，如下

```java
static void log(int n) {
    for (int i = 1; i <= n ; i++) {
        System.out.println(i + 10);
    }
}
```

这种方式，就是在重复利用变量i来保春耕原来栈帧中的参数。所以如果使用这种方法的话，在本例中的空间复杂度从O(n)降到了O(1)。