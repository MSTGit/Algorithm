package com.xd;

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
     * 判断第 row行 第col列是否可以摆放皇后 优化前
     * */
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
