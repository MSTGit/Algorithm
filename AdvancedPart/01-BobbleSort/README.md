#### 认识排序

什么叫排序？例如有下面的一串无序数字

- 排序前：3,1,6,9,2,5,8,4,7
- 排序后：1,2,3,4,5,6,7,8,9（升序）或者9,8,7,6,5,4,3,2,1（降序）

其实，排序的应用无处不在，例如，汽车销售根据销量排序

![1574772731672](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1574772731672.png)

再例如，游戏充值，根据金额进行排序

![1574772789705](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1574772789705.png)

认识了排序以后，接下来交接一下经典的**10大排序算法**

![1574774972273](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1574774972273.png)

以上表格中的结论是基于数组进行排序的一般性结论

其中：

冒泡，选择，插入，归并，快速，希尔，堆排序属于比较排序（Comparison Sorting）

#### 冒泡排序

接下来，先介绍一个冒泡排序，在这里所有的排序，统一以升序为例子。其冒泡排序的流程为

1. 从头开始比较每一对相邻元素，如果第一个比第二个大，就交换它们的位置。
   - 执行完一轮后，最末尾的哪个元素就是最大元素
2. 忽略1中曾经找到的最大元素，重复执行步骤1，直到全部元素有序

所以，通过上面的步骤，就可以得到以下的代码

```java
int[] array = {10,9,19,28,37,56,34};
for (int end = array.length - 1; end > 0; end--) {
    for (int begin = 1; begin <= end ; begin++) {
        if (array[begin] < array[begin - 1]) {
            int tmp = array[begin];
            array[begin] = array[begin - 1];
            array[begin - 1] = tmp;
        }
    }
}
```

##### 冒泡排序-优化1

通过上面这种代码，虽然实现了对元素的排序，但是还是有优化的地方，比如在某个时间节点，所有元素已经完全有序[下图]，则可以提前终止冒泡排序。通过上面这种实现方式，是不能达到这种要求的。

![1574775562512](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1574775562512.png)

由于在排序过程中，是通过一个元素一个元素扫描，来判断是否交换的，所以在第一轮扫描时，就可以知道该组元素是否已经有序。所以只要 `if (array[begin] < array[begin - 1])`成立，这说明当前组元素不完全有序，需要交换，否则就是所有元素都不用交换，则表示已经完全有序。所以可以通过下面方式进行优化

```java
int[] array = {10,9,19,28,37,56,34};
for (int end = array.length - 1; end > 0; end--) {
    boolean sorted = true;
    for (int begin = 1; begin <= end ; begin++) {
        if (array[begin] < array[begin - 1]) {
            int tmp = array[begin];
            array[begin] = array[begin - 1];
            array[begin - 1] = tmp;
            sorted = false;
        }
    }
    if (sorted == true) {
        break;
    }
}
```

通过这种优化，就可以提高效率。

但是这种优化，真的可以提高效率吗？为了测试真的可以提升效率，可以进行下面的测试。

1. 通过工具类，生成两组相同的数据
2. 计算两种不同方式，处理相同数据所消耗的时间

所以在这里随机生成了10000个随机数，然后通过两个不同的函数来计算最终所消耗的时间，函数如下

```java
public static void bubbleSort1(Integer[] array) {
    for (int end = array.length - 1; end > 0; end--) {
        for (int begin = 1; begin <= end ; begin++) {
            if (array[begin] < array[begin - 1]) {
                int tmp = array[begin];
                array[begin] = array[begin - 1];
                array[begin - 1] = tmp;
            }
        }
    }
}
public static void bubbleSort2(Integer[] array) {
    for (int end = array.length - 1; end > 0; end--) {
        boolean sorted = true;
        for (int begin = 1; begin <= end ; begin++) {
            if (array[begin] < array[begin - 1]) {
                int tmp = array[begin];
                array[begin] = array[begin - 1];
                array[begin - 1] = tmp;
                sorted = false;
            }
        }
        if (sorted == true) {
            break;
        }
    }
```

得到的结果如下

![1574776919514](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1574776919514.png)

通过测试时间，得到的结果，发现没有优化的代码，所消耗的时间更短。是不是觉得很奇怪，为什么优化后，反而更慢呢？那想一个问题，bubbleSort2之所以成为优化，在什么情况下，才能被优化。应该是经过冒泡排序后，数组中的数据提前排好序的情况下，可以被优化。假设冒泡排序的数据，数据量很大，而且有很随机的话，很难达到提前有序的情况。由于bubbleSort2相对于bubbleSort1多做了一些事情，所以所消耗的时间更长。

但是如果现在的数据是升序的，同样为10000个元素，最终得到的结果为

![1574777600618](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1574777600618.png)

通过结果可以发现，bubbleSort1无论数据是否提前有序，都要消耗很长的时间。但是bubbleSort2却可以大大的提高效率。不过需要注意的是，这种优化在某种前提下有效。

##### 冒泡排序-优化2

通过上面这种优化，提前有序的概念很低，那有没有更好的优化方案呢？是有的，可以这样做：

如果序列尾部已经局部有序，可以记录最后一次交换的位置，较少比较次数。例如现在得到的数据是这样的

![1574777850847](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1574777850847.png)

如果按照普通的冒泡排序算法，则是依次扫描所有的元素并进行比较。并不会理会后面数据是否有规律。但是上面这组数据，是有优化空间的，通过观察发现，使用冒泡排序的话， 最后面的几个元素的顺序是不会发生改变的，因为这些元素都已经有序，并且比前面的元素都大。也就意味着，交换操作，只需要堆前面未排序的元素进行就行了，没必要对后面已经有序的元素再次比较。所以，如果可以提前发现局部排序的元素，是可以提高效率的。所以可以这样实现

```java
public static void bubbleSort3(Integer[] array) {
    for (int end = array.length - 1; end > 0; end--) {
        int sortedIndex = 1;//该值在数组完全女友许的时候有用
        for (int begin = 1; begin <= end ; begin++) {
            if (array[begin] < array[begin - 1]) {
                int tmp = array[begin];
                array[begin] = array[begin - 1];
                array[begin - 1] = tmp;
                sortedIndex = begin;
            }
        }
        end = sortedIndex;
    }
}
```

通过这种方式优化后，现在来比较三种排序算法最终的结果。同样假设数据样本为10000个升序数据，最终得到的比较结果为

![1574779897219](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1574779897219.png)

可以看到，最后两种算法的效率是非常高的。

接下来，对数据进行优化，现在利用工具生成10000个数据，其中8000个数据已经排好序。最终运行程序，得到的结果为

![1574780216491](C:\Users\T\AppData\Roaming\Typora\typora-user-images\1574780216491.png)

可以看到，通过这种局部排序的数据，bubbleSort3是明显优于bubbleSort1和bubbleSort2的。

通过这种优化

1. 最坏，平均时间复杂度为：O(n^2)
2. 最好时间复杂度：O(n)
3. 空间复杂度为O(1)

#### 排序算法的稳定性（Stability）

如果相等的两个元素，在排序前后的相对位置保持不变，那么这是**稳定**的排序算法。例如，下列数据中，有两个值相等的元素

排序前:5,1,3(a),4,7,3(b)

稳定的排序:1,3(a),3(b),4,5,7

不稳定的排序：1,3(b),3(a),4,5,7

为什么要这样认为呢？因为在实际开发中，存在对自定义对象进行排序的情况，稳定性会影响最终的排序效果。

所以根据这个结论，可以知道冒泡排序是属于稳定的排序算法。但是，稍有不慎，稳定的排序算法也能被写成不稳定的排序算法，比如下面的冒泡排序代码就是不稳定的

```java
public static void bubbleSort4(Integer[] array) {
    for (int end = array.length - 1; end > 0; end--) {
        for (int begin = 1; begin <= end ; begin++) {
            if (array[begin] <= array[begin - 1]) {
                int tmp = array[begin];
                array[begin] = array[begin - 1];
                array[begin - 1] = tmp;
            }
        }
    }
}
```

所以在写排序算法时，需要特别的注意。

#### 原地算法（In-place Algorithm）

什么叫原地算法？

- 不依赖额外的资源或者依赖少数的额外资源，仅依靠输出来覆盖输入。

解读：

1. 不依赖额外的资源或者依赖少数的额外资源就意味着空间复杂度低；
2. 仅依靠输出来覆盖输入表示可以利用传进来的参数直接进行操作，不会利用其它资源

所以，空间复杂度为O(1)的算法，都可以认为是原地算法。

非原地算法，成为Not-in-place或者Out-of-place

前面的冒泡排序，属于In-place