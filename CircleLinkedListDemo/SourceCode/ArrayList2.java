package com.xd.LinkList;

import org.omg.CORBA.Object;

/**
 * 有动态数组缩容操作
 * */
@SuppressWarnings("unchecked")
public class ArrayList2<E> extends AbstractList<E>{

    /*
    * 创建一个数组，用来保存所有的元素
    * */
    private E[] elements;
    /*
    * 默认初始化时，数组的大小
    * */
    private static final int DEFAULT_CAPATICY = 10;

    ArrayList2(int capaticy) {
        capaticy = capaticy > DEFAULT_CAPATICY ? capaticy : DEFAULT_CAPATICY;
        //初始化数组大小
        elements = (E[]) new Object[capaticy];
    }
    ArrayList2() {
        this(DEFAULT_CAPATICY);
    }

    /*
     * 往数组中指定位置添加一个元素
     * @param 指定添加的位置
     * @param 准备添加的元素
     * */
    public void add(int index, E element) {
        //对索引进行判断
       rangeCheckForAdd(index);
        //对数组进行扩容
        ensureCapacity(size + 1);
        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        size++;
    }
    /*
     * 删除数组中某个下标的元素
     *  @param 被删除元素的下表
     *  @return 被删除元素的值
     * */
    public E remove(int index) {
        //对索引进行判断
        rangeCheck(index);
        E old = elements[index];
        for (int i = index + 1; i <= size - 1; i++) {
            elements[i - 1] = elements[i];
        }
        elements[--size] = null;
        trim();
    return old;
    }



    /*
    * 清除数组中的所有元素，把size设置为0，则外面就无法访问数组中原来的内容，对于外界来讲，是已经将数组清空了，但是实际不用删除原来内存上的数据，应为清除数据需要消耗额外的性能，并且这种操作几乎没有作用
    * */
    public void clear () {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
         size = 0;
        if (elements != null && elements.length > DEFAULT_CAPATICY) {
            elements = (E[]) new Object[DEFAULT_CAPATICY];
        }
    }

    /*
    * 获取数组中某个位置的元素
    * @return 查找到，则返回当前元素，否则抛出异常
    * */
    public E get(int index) {
        //对索引进行判断
        rangeCheck(index);
        return elements[index];
    }
    /*
     * 获取数组中某个位置设置元素
     * @param index 设置元素的位置
     * @param element 设置的元素
     * @return 被修改的元素
     * */
    public E set(int index,E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }
    /*
    * 查找数组中某个元素的位置
    * @return 如果查找到，则返回对应元素的位置，如果没有查找到，则返回-1
    * */
    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        } else  {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(elements[i])) return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /*
    * 自定义系统打印
    * */
    @Override
    public String toString() {
        //希望拼接的格式为 size = 9，[99,12,13]
        StringBuilder string = new StringBuilder();
        string.append("size = ").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                //推荐使用
                string.append(", ");
            }
            string.append(elements[i]);
            /*
            //这种方式不建议使用，因为会多一次减法运算
            if (i != size - 1) {
                string.append(", ");
            }
             */
        }
        string.append("]");
        return string.toString();
    }

    private  void trim() {
        int capacity = elements.length;
        if ((capacity <= (size << 1)) || capacity <= DEFAULT_CAPATICY) return;//不需要缩容
        //剩余空间还很多
        int newCapacity = capacity >> 1;
        E[] newElements = (E[]) new Object[newCapacity];
        //将原来数组中的元素，复制到新的数组中
        for (int i = 0; i < size; i++) {
            //系统提供了API来对数组进行挪动，效率更高，在这里用这种方式，是为了看起来更清晰
            newElements[i] = elements[i];
        }
        elements = newElements;
        System.out.println("缩容 旧容量：" + capacity + "新容量：" + newCapacity );
    }

    //数组扩容 保证要有capacity的容量
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity < capacity) {
            //确定新的容量  新容量为旧容量的1.5倍
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            //创建一个更大存储空间的数组
            E[] newElements = (E[]) new Object[newCapacity];
            //将原来数组中的元素，复制到新的数组中
            for (int i = 0; i < size; i++) {
                //系统提供了API来对数组进行挪动，效率更高，在这里用这种方式，是为了看起来更清晰
                newElements[i] = elements[i];
            }
            elements = newElements;
            System.out.println("扩容 旧容量：" + oldCapacity + "新容量：" + newCapacity );
        }
    }
}
