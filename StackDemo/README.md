- #### 栈

> 栈也是一种特殊的线性表，只能在**一端**进行操作

![1569033075086](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569033075086.png)

往栈中**添加**元素的操作，一般叫做**push**，**入栈**

![1569033204902](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569033204902.png)

从栈中**移除**元素的操作，一般叫做**pop**，**出栈**（只能移除栈顶元素，也叫做：弹出栈顶元素）

![1569033323870](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569033323870.png)

栈中元素，出入栈的原则：后进先出的原则，Last In First Out，LIFO

入栈操作：

![1569033416192](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569033416192.png)

出栈操作：

![1569033437239](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569033437239.png)

注意：这里说的“栈“与内存中的”栈空间“是两个不同的概念

- #### 栈的接口设计

元素的数量

```java
int size();
```

是否为空

```java
boolean isEmpty();
```

入栈

```java
void push(E element);
```

出栈

```java
E pop();
```

获取栈顶元素

```java
E top();
```

思考：栈的内部实现是否可以用前面所了解的数据结构呢？

我们对栈中的元素进行编号以后，发现其结构与动态数组，链表非常相似，因此可以使用动态数组，链表的方式实现

![1569034202114](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569034202114.png)

- #### 栈的实现

实现方式一：使用继承自ArrayList或者LinkedList的方式实现

使用继承的方式实现后，我们只需要重写push,pop,top方法即可，size，isEmpty可以直接继承父类的实现，不过这种方式有问题，栈同时也继承了父类中的其他方法，因此虽然实现了功能，但是也暴露的更多的接口给外界，因此不合理。

实现方式二：使用组合的方式实现

使用组合的方式，可以在Stack中创建一个私有的ArrayList或者LinkedList对象成员变量，在对Stack对象进行操作的时候，间接的对私有的ArrayList或者LinkedList对象成员变量进行操作，这样屏蔽了ArrayList或者LinkedList对外暴露的方法，Stack只能访问Stack对外提供的方法，这样使得对外暴露的方法更加合理。具体设计如下：

```java
public class Stack<E>  {
    private ArrayList<E> list = new ArrayList<>();
    public int size(){
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(E element) {
        list.add(element);
    }

    public E pop() {
        return list.remove(list.size() - 1);
    }

    public E top() {
        return list.get(list.size() - 1);
    }
}
```

- #### 栈的应用

浏览器的前进和后退

输入jd.com

![1569037096293](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569037096293.png)

输入qq.com

![1569037122976](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569037122976.png)

输入baidu.com

![1569037147346](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569037147346.png)

后退，把弹出的baidu.com存入另外一个栈中，现在访问到的是qq.com

![1569037230936](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569037230936.png)

后退，把弹出的qq.com存入另外一个栈中，现在访问到的是jd.com

![1569037298806](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569037298806.png)

前进，将另外一个栈中的元素弹出，放入第一个栈中，现在访问到的是qq.com

![1569037385058](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569037385058.png)

输入taobao.com，清空第二个栈中的内容，将taobao.com入第一个栈

![1569037497417](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569037497417.png)

##### 其他类似的应用场景

- 软件的撤销(Undo)，恢复(Redo)功能

![1569037637493](https://github.com/MSTGit/Algorithm/blob/master/StackDemo/Resource/1569037637493.png)

- #### 练习-有效的括号

leetcode地址- [点击访问](https://leetcode-cn.com/problems/valid-parentheses/solution/)

解题思路

1.遇见左字符，将左字符入栈

2.遇见右字符

2.1 如果栈是空的，说明括号无效

2.2 如果栈不为空，将栈顶字符出栈，与右字符匹配

2.2.1如果左右字符不匹配，说明括号无效

2.2.2 如果左右字符匹配，继续扫描下一个字符

3.所有字符扫描完毕后

3.1栈为空，说明括号有效

3.2 栈不为空，说明括号无效

解题源码

方式1：通过hashmap与栈的方式实现

```java
public boolean isValid(String s) {
    int len = s.length();
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < len; i++) {
        char c = s.charAt(i);
        if (map.containsKey(c)) {
            //左括号
            stack.push(c);
        } else {
            //右括号
            if (stack.isEmpty()) return false;
            if (stack.pop() != map.get(c)) return  false;
        }
    }
    return stack.isEmpty();
}
```

方式二：通过栈的方式实现

```java
public boolean isValid1(String s) {
    int len = s.length();
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < len; i++) {
        char c = s.charAt(i);
        if (c == '(' || c == '{' || c == '[') {
            //左括号
            stack.push(c);
        } else {
            //右括号
            if (stack.isEmpty()) return false;
            char left = stack.pop();
            if (left == '(' && c != ')') return false;
            if (left == '{' && c != '}') return false;
            if (left == '[' && c != ']') return false;
        }
    }
    return stack.isEmpty();
}
```

方式三：使用低效率的方式来判断

```java
public boolean isValid2(String s) {
    while (s.contains("{}")|| s.contains("()") || s.contains("[]")) {
        s = s.replace("{}","");
        s = s.replace("[]","");
        s = s.replace("()","");
    }
    return s.isEmpty();
}
```

