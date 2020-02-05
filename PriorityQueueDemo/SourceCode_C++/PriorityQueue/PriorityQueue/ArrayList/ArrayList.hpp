//
//  ArrayList.hpp
//  HashMap
//
//  Created by ducktobey on 7/2/2020.
//  Copyright © 2020 ducktobey. All rights reserved.
//

#ifndef ArrayList_hpp
#define ArrayList_hpp
#define DEFAULT_CAPACITY 1 << 4
#define ELEMENT_NOT_FOUND -1
#include <iostream>
#include <stdio.h>
#include <memory>
#include <string.h>
using namespace std;
template <typename E>
class ArrayList {
private:
//    friend ostream &operator<<(ostream &, const ArrayList<E> &);//设置友元函数
    int m_size = 0;
    E *m_elements = nullptr;
    int m_capacity = 0;
    void rangeCkeckForAdd(int index);//添加操作时,对索引进行判断
    void ensureCapacity(int size);//对数组进行扩容
    void rangeCheck(int index);//普通操作时,对索引进行判断
public:
    ArrayList();//无参的构造函数
    ArrayList(int capacity);//带容量的构造函数
    ~ArrayList();//析构函数
    int size();//获取元素的数量
    bool isEmpty();//判断数组是否为空
    bool contains(E element);//判断数组是否包含某个元素
    void add(E element); //往数组的最前面添加元素
    E get(int index); //获取数组中索引为index的元素
    E set(int index,E element); //往数组的第index位设置元素
    void add(int index,E element); //往数组的index位添加元素
    E remove(int index); //移除数组中索引为index的元素
    int indexOf(E element); //查看元素的位置
    void clear(); //删除数组中的所有元素
    void display();//打印所有元素
    E operator[](int index);//操作符重载
};
template <typename E>
ArrayList<E>::ArrayList(): ArrayList(DEFAULT_CAPACITY){
    m_capacity = DEFAULT_CAPACITY;
    //cout << "ArrayList()" <<endl;
}
template <typename E>
ArrayList<E>::ArrayList(int capacity){
    //cout << "ArrayList(int)" <<endl;
    m_elements = new E[capacity];//初始化容量
    //memset(&m_elements,'\0',sizeof(m_elements));
}
template <typename E>
ArrayList<E>::~ArrayList(){
    if (m_elements != nullptr) {
        delete m_elements;//释放申请的堆空间内存
    }
    //cout << "~ArrayList()" <<endl;
}
template <typename E>
int ArrayList<E>::size() {
    return m_size;
}
template <typename E>
bool ArrayList<E>::isEmpty(){
    return m_size == 0;
}
template <typename E>
bool ArrayList<E>::contains(E element){
    for (int i = 0; i < m_size; i++) {
        if (m_elements[i] == element) {
            return true;
        }
    }
    return false;
}
template <typename E>
void ArrayList<E>::add(E element){
    add(m_size, element);
}
template <typename E>
E ArrayList<E>::get(int index){
    
    return m_elements[index];
}
template <typename E>
E ArrayList<E>::set(int index,E element){
    rangeCheck(index);
    E oldValue = m_elements[index];
    m_elements[index] = element;
    return oldValue;
}
template <typename E>
void ArrayList<E>::add(int index,E element){
    rangeCkeckForAdd(index);
    ensureCapacity(m_size + 1);
    for (int i = m_size - 1; i >= index; i--) {
        m_elements[i + 1] = m_elements[i];//对数组中元素进行移动
    }
    m_elements[index] = element;
    m_size++;
}
template <typename E>
E ArrayList<E>::remove(int index){
    rangeCheck(index);
    int oldValue = m_elements[index];
    m_size--;
    return oldValue;
}
template <typename E>
int ArrayList<E>::indexOf(E element){
    for (int i = 0; i < m_size; i++) {
        if (m_elements[i] == element) {
            return i;
        }
    }
    return ELEMENT_NOT_FOUND;
}
template <typename E>
void ArrayList<E>::clear(){
    m_size = 0;
}
template <typename E>
void ArrayList<E>::display() {
    for (int i = 0; i < m_size; i++) {
        cout << m_elements[i] << endl;
    }
}

template <typename E>
void ArrayList<E>::rangeCkeckForAdd(int index) {
    if (index < 0 || index > m_size) {
        throw "数组下标越界";
    }
}
template <typename E>
void ArrayList<E>::ensureCapacity(int size) {
//    if (m_capacity < size) {//容量不够了
//        //定义一个更大空间的容量
//        m_capacity = m_capacity << 1;
//        //创建一个更大容量的数组
//        int *newElements = new int[m_capacity];
//        //将原来的数组中的元素,拷贝到新的数组中
//        for (int i = 0; i < m_size; i++) {
//            newElements[i] = m_elements[i];
//        }
//        delete m_elements;
//        m_elements = newElements;
//        cout << "数组扩容了..." << endl;
//    }
}
template <typename E>
void ArrayList<E>::rangeCheck(int index) {
    if (index < 0 || index > m_size) {
        //报错,抛异常
        throw "数组下标越界";
    }
}
template <typename E>
E ArrayList<E>::operator[](int index) {
    return get(index);
}

//template <typename E>
//ostream &operator<<(ostream &cout, const ArrayList<E> &array) {
//    cout << "[";
//    for (int i = 0 ; i < array.m_size; i++) {
//        if (i != 0) {
//            cout << ",";
//        }
//        cout << array.m_elements[i];
//    }
//   return cout << "]";
//}
#endif /* ArrayList_hpp */
