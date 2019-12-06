package com.xd.Sort.SortClass.Cmp;

import java.util.ArrayList;
import java.util.List;

public class ShellSort<E  extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        List<Integer> stepSequence = shellStepSequence();
        for (Integer step :
                stepSequence) {
            sort(step);
        }
    }

    /*
    * 分成step列进行排序
    * */
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

    private List<Integer> shellStepSequence() {
        List<Integer> stepSequence = new ArrayList<>();
        int step = array.length;
        while ((step >>= 1) > 0) {
            stepSequence.add(step);
        }
        return stepSequence;
    }

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
}
