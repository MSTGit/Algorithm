### 希尔排序（Shell Sort）

希尔排序是唐纳德·希尔（Donald Shell）在0959年提出的。希尔排序与其他的排序算法不一样，非常有意思。

> 希尔排序是把序列看做是一个矩阵，分成m列，逐列进行排序。
>
> - m从某个整数逐渐减为1
> - 当m为1时，整个序列完全有序

你现在看到这些，可能还是很迷糊的，不过不要紧，你现在只需要知道，希尔排序这种算法非常特殊，是将序列分为m列进行逐列排序即可。

由于希尔排序的特性，所以也被称为递减增量排序（Diminishing Increment sort）

另外需要注意，一个矩阵最终会被分为多少列，最终由步长序列决定（step sequence），步长序列简介如下

1. 如果步长序列为{1,5,19,41,109，...}，则代表着最终的矩阵会被分成109列，41列，19列，5列，1列进行排序
2. 不同的步长序列，执行效率不同

所以希尔排序有意思的地方也在于，每个人都可以给出不同的步长序列，并且不同的步长序列，执行的效率也是不同的，所以现在也有很多科学家在研究，能不能有更好的步长序列，来提高排序的性能呢？

#### 实例

希尔本人给出的步长序列是n/2^k;例如n为16时，步长序列是{1,2,4,8}，如果是按照这种情况，根据下图的序列，数据规模n的值就位16

![1575284729864](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575284729864.png)

根据希尔给出的步长，首先会将序列分为8列进行排序，所以会将序列分为下图所示的8列

![1575284831618](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575284831618.png)

进行逐列进行排序后的结果为

![1575284910344](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575284910344.png)

由于进行8列排序时，上面一排是原序列中前面8个元素，下面一排是原序列中后面8个元素，进行逐列排序后，在原序列中的结果其实就变为下图这样了

![1575285024810](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285024810.png)

由于步长序列是{1,2,4,8}，刚是按照8列进行排序，所以现在再将排序后的序列分为4列进行排序，4列拆分的结果为



![1575285172948](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285172948.png)

拆分后，再将每一列进行排序，最终每一列排序后的结果为

![1575285219763](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285219763.png)

最终，实际在原序列中的结果就是下图这种排列方式

![1575285283027](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285283027.png)

这样之后，再将原序列分为2列进行拆分，最终拆分后的结果为

![1575285353049](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285353049.png)

拆分后，在将每一列进行排序， 最终排序后的结果为

![1575285405160](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285405160.png)

所以，最终实际在原序列中的结果就是下图这种排列方式

![1575285443991](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285443991.png)

最后，再将原序列分为1列进行排序，最终拆分后的结果如下

![1575285503781](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285503781.png)

拆分后，再将该列进行排序，最终排序后的结果为

![1575285558083](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285558083.png)

最终，对应到原序列中的结果如下图

![1575285585815](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575285585815.png)

看到这里，可能会想，前面将序列拆分为8,4,2列到底有什么意义呢？当拿到一个序列，我一开始就将序列分为1列不就好了吗？如果最开始就分为一列，那这一列排好了，最终整个序列不就排好了吗。其实横向的一列和纵向的一列，元素排列顺序都是一样的，一开始就将序列分为纵向的一列，其实是没有任何意义的。而且前面分割序列，其实是有意义的，你有没有发现一个特点，每次按照步长，排完一次序以后，逆序对的数量在减少呢？所以，每次根据步长排完一次序后，逆序对的数量都是在逐渐减少的，所以整个流程是有意义的。

因此希尔排序底层一般使用插入排序对每一列进行排序，有很多资料也认为希尔排序是插入排序的改进版。

为什么要使用插入排序呢？在前面介绍[插入排序](https://github.com/MSTGit/Algorithm/tree/master/AdvancedPart/04-InsertionSort)时有介绍到，插入排序是有最好情况的，即这个序列本来就是完全有序时，并且也在插入排序部分有讲到，插入排序的时间复杂度与逆序对的数量成正比关系，即逆序对数量越少，插入排序的时间复杂度越低。

并且从希尔排序可以看出，在不断排序的过程当中，逆序对的数量是在逐渐减少的，所以在这种情况下，就非常适合使用插入排序来对每一列进行排序。

#### 实例2

由于刚刚的示例非常特殊，根据步长，最终得到的每一列元素数量都是一样的，所以现在再举一个不一样的例子。

假设现在有下列的一个序列，其中包含11个元素，步长序列假设为{1,2,5}

![1575286502152](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575286502152.png)

所以，在最开始的时候，序列会被分成5列进行排序，所以最终拆分后的结果为

![1575286574666](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575286574666.png)

排完序以后的结果为

![1575286622699](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575286622699.png)

所以拆分的话就可以进行上图这种拆分，并不需要每一列的元素数量都一样。

那么问题来了，如何对每一列的元素进行排序呢？现在有如下的规律

假设元素在第col列，第row行，步长（总列数）是step

- 那么这个元素在数组中的索引是col + row * step
  - 例如上图中的9，现在排在第二列，第0行，那么它排列前的索引是2 + 0 * 5 = 2
  - 例如4在排序前处于第二列，第一行，所以它在排序前的索引是2 + 1 * 5 = 7

根据这个结论，最终得到通过步长对每一列进行排序的代码

```java
private void sort(int step) {
    //col :表示第几列
    for (int col = 0; col < step; col++) {//对第col列进行排序
        //col,col + step, col + 2 * step ...
        for (int begin = col + step; begin < array.length; begin += step) {
            int cur = begin;
            while (cur >col && cmp(cur,cur - step) < 0) {
                swap(cur,cur - step);
                cur -= step;
            }
        }
    }
}
```

计算步长的代码为

```java
private List<Integer> shellStepSequence() {
    List<Integer> stepSequence = new ArrayList<>();
    int step = array.length;
    while ((step >>= 1) > 0) {
        stepSequence.add(step);
    }
    return stepSequence;
}
```

最终，遍历步长数组中的每一个元素，就可以对序列进行排序了。那现在可以利用编写好的ShellSort排序算法利用3万组数据与原来的几个排序算法进行比较。

![1575289055867](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575289055867.png)

可以发现，ShellSort表现非常优秀，甚至超过了堆排序算法，而且也看到原来的插入排序使用了509毫秒的时间，在ShellSort中，最终只消耗了21毫秒。前面也提到，很多人认为希尔排序是对插入排序的一种改进，是的，从结果中明显可以看出，希尔排序的性能是比插入排序的性能要好的。

#### 步长序列

前面也将到，希尔本人给出的步长序列是n/2^k，但是这个步长序列的最坏时间复杂度为O(n^2)，但是在后面，有科学家研究出了很多优秀的步长序列，到目前已知的最好步长序列，最坏情况的时间复杂度为O(n(4/3))，是在1986年由Robert Sedgewick提出的[下图]。所以将希尔优化后的话，可以将最坏时间复杂度降低到O(n(4/3))

![1575290136386](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/07-ShellSort/Resource/1575290136386.png)

所以最终优化后的计算步长序列代码为

```java
private List<Integer> sedgewickStepSequence() {
    int count = array.length;
    List<Integer> stepSequence = new ArrayList<>();
    int k = 0,step = 0;
    while (true) {
        if (k % 2 == 0) {
            int pow = (int)Math.pow(2, k>> 1);
            step = 1 + 9 * (pow * pow - pow);
        } else {
            int pow1 = (int)Math.pow(2,(k - 1) >> 1);
            int pow2 = (int)Math.pow(2, (k + 1) >> 1);
            step = 1 + 8 * pow1 * pow2 - 6 * pow2;
        }

        if (step >= count) break;
        stepSequence.add(0,step);
        k++;
    }
    return stepSequence;
}
```

#### 复杂度总结

> 最好时间复杂度：O(n)；因为希尔排序底层是使用的插入排序，插入排序的最好时间复杂度为O(n)，所以希尔排序也是O(n)
>
> 最坏时间复杂度：介于O(n^2)到O(n(4/3))之间
>
> 平均时间复杂度：取决于步长；在前面说过，不同的步长序列，执行效率不同，所以平均复杂度依然由步长决定。
>
> 空间复杂度：O(1)；由于使用的原地排序，没有依赖于额外的存储空间，也没有递归调用，所以空间复杂度为O(1)

最后，希尔排序属于不稳定排序，由于现在的希尔排序是逐列进行排序，所以在demo中的判断方法，是不准确的，只有通过举例的方式来证明。