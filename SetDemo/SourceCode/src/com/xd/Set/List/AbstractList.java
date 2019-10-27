package com.xd.Set.List;

public abstract class AbstractList<E> implements List<E>{
    /*
     * 保存当前数组的元素个数
     * */
    protected int size;


    /*
     * 获取当前数组的元素个数
     *  @param 当前数组的元素个数
     * */
    public int size() {
        return size;
    }

    /*
     * 判断当前数组是否为空
     * @param 如果size == 0 则数组为空，否则不为空
     * */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
     * 判断数组中，是否包含某个元素，
     * @param 包含，则返回YES，不包含返回NO
     * */
    public boolean contains(E element) {
        return this.indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /*
     * 往数组中添加一个元素
     * @param 准备添加的元素
     * */
    public void add(E element) {
        //本质是往数组中最后一个元素后插入一个新的元素

        add(size,element);
    }
    //封装异常信息
    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("Index:" + index + ",Size:" + size);
    }

    //检查范围
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }
}
