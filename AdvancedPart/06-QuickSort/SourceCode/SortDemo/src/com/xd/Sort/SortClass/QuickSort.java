package com.xd.Sort.SortClass;

public class QuickSort<E  extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        quickSort(0,array.length);
    }

    /*
    * 对[begin,end)范围内的元素进行快速排序
    * */
    private void quickSort(int begin, int end) {
        if (end - begin < 2) return;
        //确定轴点位置
        int mid = pivotIndex(begin,end);
        //对子序列也进行快速排序
        quickSort(begin,mid);//左边子序列快速排序
        quickSort(mid + 1 ,end);//右边子序列快速排序
    }
    /*
    * 构造出[begin,end)范围内的轴点元素
    * @return 轴点元素的最终位置
    * */
    private int pivotIndex(int begin, int end) {
        //随机选择一个元素跟begin位置进行交换
        swap(begin,(int)(Math.random() * (end - begin)) + begin);
        //备份轴点元素
        E pivot = array[begin];
        //end指向最后一个元素
        end--;
        while (begin < end) {
            while (begin < end) {
                if (cmp(pivot,array[end]) < 0) {//右边元素大于轴点元素
                    end--;
                } else  {//右边元素小于等于轴点元素
                    array[begin++] = array[end];
                    break;
                }
            }
            while (begin < end) {
                if (cmp(pivot,array[begin]) > 0) {//左边元素小于轴点
                    begin++;
                } else {//左边元素大于等于轴点元素
                    array[end--] = array[begin];
                    break;
                }
            }
        }
        //将轴点元素放入最终的位置
        array[begin] = pivot;
        //返回轴点元素的位置
        return begin;
    }
}
