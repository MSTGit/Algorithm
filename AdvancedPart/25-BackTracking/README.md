#### 回溯（Back Tracking）

回溯可以理解为：通过不同的岔路口来通往目的地

- 每一步都选择一条路出发，能进则进，不能进则退回上一步（回溯），换一条路再试。

所以，树，图的深度优先搜索（DFS）就是典型的回溯应用。[下图]

![1577195878838](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577195878838.png)

所以，很容易看出来，回溯非常适合使用递归。

##### 八皇后问题（Eight Queens）

八皇后问题，是一个古老而著名的问题

在 8 * 8的国际象棋上摆放八个皇后，使其不能相互共计：任意两个皇后都不能处于同一行，同一列，斜线上，现要计算一共有多少种摆法。

下图是国际象棋的棋盘，摆放了一个皇后以后的结果，蓝色当前皇后的共计区域

![1577196227200](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577196227200.png)

摆放了2个皇后以后的结果

![1577196572497](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577196572497.png)

下图是网上公布的一个答案

![1577196598622](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577196598622.png)

###### 八皇后问题的解决思路

1. 暴力出奇迹
   列举所有的可能， 从64个格子里面，选出任意8个格子摆放皇后，检查每一种摆法的可行性，但是这种方法计算量太恐怖了，这种方式，大概有44亿种不同的摆法
2. 根据前面的条件，较小暴力程度
   很显然，每一行只能放8个皇后，所以共有8^8种摆法，检查每一种摆法的可行性
3. 回溯法

#### 回溯法

##### 四皇后

再计算八皇后之前，可以尝试先缩小数据规模，看看如何计算解决四皇后问题

下图的不同颜色，表示不同的状态

- 蓝色：可摆放
- 蓝色：已摆放
- 黑色：不能摆放

利用回溯法来解决这类问题时，步骤如下

1. 最开始时，棋盘上没有摆放任何皇后，所以棋牌是空的
   ![1577197878333](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577197878333.png)

2. 摆放第一个皇后，由于回溯法都是依次尝试，所以第一个皇后会选择第一行的第一个位置进行摆放
   ![1577197950660](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577197950660.png)
   摆放完以后，不能摆放和可摆放的区域如图所示

3. 摆放第二个皇后时，就会选择剩下的蓝色位置进行摆放，所以这一次回溯法会选择第二行中第一个能摆放的位置进行摆放，摆放后的结果如下
   ![1577198175748](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577198175748.png)

4. 摆放完第二个皇后以后，发现，第三行没有位置可以选，所以就回退到摆放Q2的上一步，回退后的结果如下
   ![1577197950660](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577197950660.png)

5. 根据回溯的特性，所以会选择第二行中的下一个位置进行尝试，所以选择第二行中第二个可摆放的位置进行摆放，结果如下
   ![1577198380933](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577198380933.png)

6. 由于第三行只剩下一个位置可以选择，所以摆放第三个皇后时，会选择第三行中可以摆放的位置进行摆放，结果如下
   ![1577198690647](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577198690647.png)

7. 由于现在没有位置可以选择，所以就会回溯到上一步，回溯后的结果如下
   ![1577198380933](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577198380933.png)

8. 由于第三行的这条路已经尝试过了，第三行没有其他路可以尝试，所以会继续回溯，回溯后的结果如下
   ![1577197950660](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577197950660.png)

9. 同样的，第二行的两个位置都尝试过了，所以，又会继续回溯，所以最终回溯的结果如下
   ![1577197878333](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577197878333.png)

10. 由于第一次尝试，选择的是第一个位置，所以这一次会选择第一行的第二个位置，结果如下
    ![1577198722573](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577198722573.png)

11. 在第二行中的唯一可摆放位置中摆放下一个皇后，结果如下
    ![1577198774754](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577198774754.png)

12. 在第三行中唯一可选的位置中摆放下一个皇后，结果如下
    ![1577198822056](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577198822056.png)

13. 最后，在第四行唯一可摆放的位置中摆放下一个皇后，结果如下
    ![1577198871638](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577198871638.png)

    这样，就把四皇后摆好了。利用回溯法，发现没有路可以走的时候，就回溯到上一步，找另外一条路继续尝试。

    但是，上面的例子中，在默认的回溯算法中，并不是直接就选择了可以选择的位置，而是当当前行选择完毕以后，下一行仍然有4个位置可以选择，并不会排除不能选择的位置。所以在这个时候，就需要对不符合条件的位置进行处理。在回溯中，成为剪枝。

    #### 剪枝（Pruning）
    
    结合上面的例子，假设在第一行选择位置的时候，选择的是0号位置的格子进行摆放皇后，一旦选择了第一行的0号位置摆放皇后以后，就会选择下一行的位置来摆放下一个皇后，在正常情况下， 下一行同样是可以选择4个格子中的任意一个格子来摆放皇后，默认是从左到右每个格子都进行尝试，并一直往下一层进行尝试，直到不符合条件时，再进行回溯。但是实际上，大可不必这样每一个格子都去尝试，因为国际象棋中有一条规则，一旦在某位置放上一个皇后以后的话， 与该皇后同一行，同一列，同一对角线的位置，均不允许进行摆放其他皇后，所以当第一行选择了0号位置以后，就可以立即排除两个不符合条件的格子[下图]
    
    ![1577275826664](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577275826664.png)
    
    这种根据一定的条件，可以马上排除掉的分支，就可以省去不符合条件分支继续尝试带来的开销；这种操作就称为剪枝处理。一旦进行了剪枝处理的话， 就可以直接。后面再选择位置的时候，就直接选择符合条件的位置即可。这样就省去了很多情况的处理，对比把所有情况都列举出来的情况，效率就会大大得到提升。
    
    ![1577276021067](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577276021067.png)

所以，准确来讲，四皇后问题的处理，就会采用回溯+剪枝的策略。

最终，将8皇后问题转换为n皇后问题后，得到的代码如下

```java
public class Queen1 {
    /*
    * 数组索引是行号，数组元素是列号
    * */
    int[] cols;
    /*
    * 一共有多少种摆法
    * */
    int ways;

    void placeQuueens(int n) {
        if (n < 1)return;
        cols = new int[n];
        place(0);
        System.out.println(n + "皇后一共有" + ways + "种摆法" );
    }

    /*
    * 从第 row行开始摆放皇后
    * */
    void place(int row) {
        if (row == cols.length) {
            //行号为列数 + 1时，说明皇后已经全部找到位置了
            ways++;
            show();
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if (isVaild(row,col)) {
                //在第row行第col列摆放皇后
                cols[row] = col;
                //继续摆下一行
                place(row + 1);
                //代码能来到这里，就会自动回溯
            } //else {}就相当于是剪枝处理
        }
    }

    /*
    * 判断第 row行 第col列是否可以摆放皇后
    * */
    boolean isVaild(int row, int col) {
        for (int i = 0; i < row; i++) {
            //第col列已经有皇后
            if (cols[i] == col) return false;
            //判断斜线上是否有皇后
            //通过斜率计算。（row - i） / (col - cols[i]  == 1/-1)，表示在同一对角线上
            if ((row - i) == Math.abs(col - cols[i])) return false;
        }
        return true;
    }

    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (cols[row] == col) {
                    System.out.print("1  ");
                } else {
                    System.out.print("0  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
```

这样，通过参数，可以计算任意数量皇后的问题。

接下来继续研究回溯与剪枝的问题。

可以发现，上面代码中，回溯和剪枝的核心代码是place函数，在place函数中，将回溯和剪枝都使用上了。剪枝是在调用isVaild()函数的条件判断中，利用条件判断，将很多不必要的分支，都过滤掉了，这个操作其实就是剪枝操作。回溯的话，则是在place函数的递归调用后面，一旦place函数中的递归调用执行完毕，就会自动执行回溯操作。

为了能跟踪回溯算法的执行过程， 现在在isVaild()函数执行的过程中，进行查看，现在在该函数中增加打印信息

```java
boolean isVaild(int row, int col) {
    for (int i = 0; i < row; i++) {
        //第col列已经有皇后
        if (cols[i] == col)
        {
            System.out.println("[" + row + "][" + col + "] == false");
            return false;
        }
        //判断斜线上是否有皇后
        //通过斜率计算。（row - i） / (col - cols[i]  == 1/-1)，表示在同一对角线上
        if ((row - i) == Math.abs(col - cols[i])) {
            System.out.println("[" + row + "][" + col + "] == false");
            return false;
        }
    }
    System.out.println("[" + row + "][" + col + "] == true");
    return true;
}
```

对4皇后进行测试以后，得到如下的打印信息，

![1577282058135](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/25-BackTracking/Resource/1577282058135.png)

通过打印信息，就能清楚的看到在回溯过程中的整个判断过程。

##### 性能优化

现在发现，在进行调用isVaild()函数进行判断的时候，其实是进行了很多重复的判断，并且每一次判断，都会进行for循环，效率比较低，所以现在考虑将这部分内容进行优化。

优化思路

1. 使用Boolean数组记录当前哪一列摆放了皇后，摆放了皇后的列，后面都不能摆放皇后
2. 使用另外一个Boolean数组记录当前从左到右的对角线，哪条对角线摆放了皇后，如果该对角线摆放了皇后，后面就不能摆放皇后
3. 再使用另外一个Boolean数组记录当前从右到左的对角线，哪条对角线摆放了皇后，如果该对角线摆放了皇后，后面就不能摆放皇后

所有，优化后的代码为

```java
public class Queen2 {

    int queen[];
    /*
     * 数组索引是列号，为true表示当前列有皇后
     * */
    boolean[] cols;
    /*
    *   表示从左上→右下的对角线，如果为true，表示当前对角线有皇后
    * */
    boolean[] leftTop;
    /*
    *   表示从右上→左下的对角线，如果为true，表示当前对角线有皇后
    * */
    boolean[] rightTop;
    /*
     * 一共有多少种摆法
     * */
    int ways;

    void placeQuueens(int n) {
        if (n < 1)return;
        queen = new int[n];
        cols = new boolean[n];
        leftTop = new boolean[(n << 1) - 1];
        rightTop = new boolean[leftTop.length];
        place(0);
        System.out.println(n + "皇后一共有" + ways + "种摆法" );
    }

    /*
     * 从第 row行开始摆放皇后
     * */
    void place(int row) {
        if (row == cols.length) {
            //行号为列数 + 1时，说明皇后已经全部找到位置了
            ways++;
            show();
            return;
        }
        for (int col = 0; col < cols.length; col++) {
            if (cols[col])continue;
            int ltIndex = row - col + cols.length - 1;
            if (leftTop[ltIndex]) continue;
            int rtIndex = col + row;
            if (rightTop[rtIndex])continue;
            queen[row] = col;
                cols[col] = true;
                leftTop[ltIndex] = true;
                rightTop[rtIndex] = true;
                place(row + 1);
            cols[col] = false;
            leftTop[ltIndex] = false;
            rightTop[rtIndex] = false;
        }
    }
    void show() {
        for (int row = 0; row < cols.length; row++) {
            for (int col = 0; col < cols.length; col++) {
                if (queen[row] == col) {
                    System.out.print("1  ");
                } else {
                    System.out.print("0  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
```

以上，就是对N皇后的一种优化。

其实，针对n小于等于8 的皇后问题，还可以进一步优化，因为现在判断某个位置是否可以摆放皇后，是通过Boolean值来进行保存的，所以完全可以使用位运算来进行优化，这样可以空间复杂度能进一步提升。