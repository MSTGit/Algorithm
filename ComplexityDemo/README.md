
- #### 什么是算法

①算法是用于解决特定问题的一系列执行步骤，如下列代码

```java
    /*
    * 计算 a + b的和
    * */
    public static int plus(int a,int b) {
        return a + b;
    }
    /*
    * 计算前n项的和
    * */
    public static int sum(int n) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            result += i;
        }
        return result;
    }
```

②使用不同的算法，解决同一问题，效率可能非常大，例如：求第n个斐波拉契数

> 通过递归的方式求第n个斐波拉契数,示例代码如下

```java
    /*
    * 通过递归的方式，计算斐波拉契数列的前N项
    * */

    public static int recursiveFib(int n) {
        if (n <= 1) return n;
        return recursiveFib(n- 1) + recursiveFib(n - 2);
    }
```

> 通过算法优化后的方式求第n个斐波拉契数,示例代码如下

```Java
    /*
    * 通过算法进行优化后的计算方法
    * */
    public static int optimizeFib(int n) {
        if (n <= 1) return n;
        int first = 0;
        int second = 1;
        for (int i = 0; i < n - 1; i++) {
            int sum = first + second;
            first = second;
            second = sum;
        }
        return second;
    }
```

这两段代码,从肉眼,感觉不到太大的差别,并且感觉第二段代码代码量更多,设计更加复杂,那我们通过一个工具类来简单测试一下两段代码的执行效率

> 当我们对两段代码传入相同的n值,测算两段代码的执行时间

![1566002110905](https://github.com/MSTGit/Algorithm/blob/master/ComplexityDemo/1566002110905.png)

可能现在看起来,感觉不是很明显,我们可以把n的值,适当的增加,再次运行测试

![1566002339295](https://github.com/MSTGit/Algorithm/blob/master/ComplexityDemo/1566002339295.png)

其实,从现在,已经可以稍微感觉到一点差异了,当n的值为45的时候,递归调用的算法使用了4.85秒的时间,而通过算法优化后的代码,消耗的时间几乎没有变化,不过,我们可以再把n增大1,就可以看到很明显的效果

![1566002546728](https://github.com/MSTGit/Algorithm/blob/master/ComplexityDemo/1566002546728.png)

我们看到很明显的变化,当n = 46时,递归调用的计算时间,已近增加到了7.743秒,但是,通过算法优化后的代码,计算消耗的时间几乎没有任何变化,因此现在我们可以粗略的看出两段代码的好坏.

- #### 如何评判一个算法的好坏

如果单从执行效率上进行评估，可能会想到则么一种方案

> 比较不同算法对同一组输入的执行处理时间；这种方案叫做事后统计法

不过这种方案有明显的缺点，如

> 执行时间严重依赖硬件以及运行时各种不确定的环境因素
>
> 必须编写相应的测试代码
>
> 测试数据的选择比较难保证公正性

一般从以下维度来评估算法的优劣

> 正确性、可读性、健壮性（对不合理输入的反应能力和处理能力）
>
> 时间复杂度：估算程序指令的执行次数（执行时间）
>
> 空间复杂度：估算所需占用的存储空间

- #### 大O表示法 （Big O）

①一般用大O表示法来描述复杂度,它表示的是数据规模n对应的复杂度,我们可以看下面几段代码,来说出他们对应代码粗略的执行次数

> 代码段1

```java
    public static void test1(int n) {

        /*
        * 一个简单的判断语句 执行次数为2次
        * */
        if (n > 10) {
            System.out.println("n > 10");
        } else if (n > 5) {
            System.out.println("n > 5");
        } else {
            System.out.println("n <= 5");
        }
        /*
        * int i = 0会执行一次
        * i < 4 会执行4次
        * i ++ 也会执行4次
        * System.out.println("test"); 也会执行4次
        * */
        for (int i = 0; i < 4; i++) {
            System.out.println("test");
        }
        //总共的执行次数大概是 4 + 4 + 4 +1 + 2 = 15
    }
```

> 代码段2

```java
    public static void test2(int n) {
        /*
         * int i = 0会执行一次
         * i < n 会执行n次
         * i ++ 也会执行n次
         * System.out.println("test"); 也会执行n次
         * */
        for (int i = 0; i < n; i++) {
            System.out.println("test");
        }
        //总共的执行次数大概是  n+ n + n +1  = 3n +1次
    }
```

> 代码段3

```java
    public static void test3(int n) {
        /*
         * int i = 0会执行一次
         * i < n 会执行n次
         * i ++ 也会执行n次
         * for (int j = 0; j < n; j++) 会执行n次
         * */
        for (int i = 0; i < n; i++) {
            /*
             * int j = 0会执行一次
             * j < n 会执行n次
             * j ++ 也会执行n次
             * System.out.println("test"); 也会执行n次
             * */
            for (int j = 0; j < n; j++) {
                System.out.println("test");
            }
        }
        //总共的执行次数大概是  2n + 1 + n * (3n + 1) = 3n^2 + 3n + 1次
    }
```

> 代码段4

```java
    public static void test4(int n) {
        /*
         * int i = 0会执行一次
         * i < n 会执行n次
         * i ++ 也会执行n次
         * for (int j = 0; j < 15; j++) 会执行n次
         * */
        for (int i = 0; i < n; i++) {
            /*
             * int j = 0会执行一次
             * j < n 会执行15次
             * j ++ 也会执行15次
             * System.out.println("test"); 会执行15次
             * */
            for (int j = 0; j < 15; j++) {
                System.out.println("test");
            }
        }
        //总共的执行次数大概是  2n + 1 + n * (3 *15 + 1) = 48n + 1次
    }

```

> 代码段5

```java
    public static void test5(int n) {

        //这个函数较之前的函数会稍微复杂一些，不过我们通过观察，发现如果n = 8 while的执行次数为3次，n = 16时 while的执行次数为4次,符合高数中的log2(n)计算结果
        /*
         * while ((n = n / 2) > 0)  会执行log2(n)次
         * System.out.println("test"); 会执行log2(n)次
         * */
        while ((n = n / 2) > 0) {
            System.out.println("test");
        }
        //总共的执行次数大概是  2 * log2(n)次
    }
```

> 代码段6

```java
    public static void test6(int n) {
        //通过上面的计算规律，我们可以知道，循环次数为log5(n)，因此
        /*
         * while ((n = n / 5) > 0)  会执行log5(n)次
         * System.out.println("test"); 会执行log5(n)次
         * */
        while ((n = n / 5) > 0) {
            System.out.println("test");
        }
        //总共的执行次数大概是  2 * log5(n)次
    }
```

> 代码段7

```java
    public static void test7(int n) {
        /*
         * int i = 0会执行一次
         * i < n 会执行log2(n)次
         * i ++ 也会执行log2(n)次
         * for (int j = 0; j < n; j++) 会执行log2(n)次
         * */
        for (int i = 1; i < n; i = i * 2) {
            /*
             * int j = 0会执行一次
             * j < n 会执行n次
             * j ++ 也会执行n次
             * System.out.println("test"); 会执行n次
             * */
            for (int j = 0; j < n; j++) {
                System.out.println("test");
            }
        }
        //总共的执行次数大概是  1 + log2(n)+ log2(n)+ log2(n) * (1 + 3n) = 3n * log2(n) + 3 * log2(n) + 1次
    }
```

②大O表示法的表示规则,忽略常数,系数,低阶数,应为当参数无限大时,常数,系数,低阶数接近于无限小,对结果影响不大,因此的一般表示规则如下:

> 9 >> O(1)
>
> 2n + 3 >> O(n)
>
> n^2 + 2n + 6 >> O(n^2)
>
> 4n^3 + 3n^2 + 33n + 99 >> O(n^3)

通过该规则,我们就可以计算出以上代码段通过大O表示法的时间复杂度

> 代码段1: 15 >> O(1)
>
> 代码段2: 3n +1 >> O(n)
>
> 代码段3:3n^2 + 3n + 1 >> O(n^2)
>
> 代码段4:48n + 1 >> O(n)
>
> 代码段5:2 * log2(n) >>O(logn)
>
> 代码段6:2 * log5(n) >>O(logn)
>
> 代码段7:3n * log2(n) + 3 * log2(n) + 1 >> O(n * logn)

⚠注意:大O表示法仅仅是一种粗略的分析模型,是一种估算,能帮助我们短时间内了解一个算法的执行效率

- #### 常见的复杂度

|       执行次数        |  复杂度  | 非正式术语 |
| :-------------------: | :------: | :--------: |
|          12           |   O(1)   |   常数阶   |
|        2n + 3         |   O(n)   |   线性阶   |
|    4n^2 + 2n + 32     |  O(n^2)  |   平方阶   |
|    4log2(n) + 1000    | O(logn)  |   对数阶   |
|  6n + 2nlog3(n) + 99  | O(nlogn) |  nlogn阶   |
| 4n^3 + 3n^2 + 22n + 1 |  O(n^3)  |   立方阶   |
|          2^n          |  O(2^n)  |   指数阶   |

> 常见复杂度效率排行

O(1) < O(logn) < O(n) < O(nlogn) < O(n^2) < O(n^3) < O(2^n) < O(n!) < O(n^n)

> 通过函数生成工具对比复杂度,[链接地址](https://zh.numberempire.com/graphingcalculator.php)

当数据规模较小时

![1566007940936](https://github.com/MSTGit/Algorithm/blob/master/ComplexityDemo/1566007940936.png)

当数据规模较大时

![1566008067840](https://github.com/MSTGit/Algorithm/blob/master/ComplexityDemo/1566008067840.png)

- #### 斐波拉契示例函数时间复杂度分析

>  recursiveFib函数的时间复杂度分析

![1566009065412](https://github.com/MSTGit/Algorithm/blob/master/ComplexityDemo/1566009065412.png)

一共执行的次数为 1 + 2 + 4 + 8 = 2^0 + 2^1 + 2^2 + 2^3 = @^4 - 1 = 2^(n-1) - 1 = 0.5 * 2^n - 1

所以recursiveFib函数的复杂度为O(2^n)

> optimizeFib函数的时间复杂度分析

通过前面的计算规则,我们容易计算出,optimizeFib函数的时间复杂度为O(n)

拓展:他们的差别有多大?如果有一台1GHz的普通计算机,运行熟读为10^9次每秒(n为64),O(n)大约消耗6.4 * 10 ^(-8)秒,O(2^n)大约耗时580年,因此可以看出,有时候算法之间的差距,往往比硬件方向的差距还要大



完!
