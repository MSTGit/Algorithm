package com.xd;

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
