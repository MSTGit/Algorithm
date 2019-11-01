- #### 优先级队列（Priority Queue）

优先级队列也是一个队列，与我们前面介绍的队列类似，因此如果我们设计一个优先级队列的话，也是提供以下接口

```java
int size();//元素的数量
boolean isEmpty();//是否为空
void enQueue(E element);//入队
E deQueue();//出队
E front();//获取队列的头元素
void clear();//清空
```

上面的这些接口，与我们前面介绍的队列一样。

那么优先级队列与我们前面介绍的队列有什么不同呢？首先优先级队列也是一个队列，因此与前面介绍的队列一样，同样有队头，队尾。并且入队这个元素，都是从队尾入队，出队都是从队头出队。

![1572606989545](https://github.com/MSTGit/Algorithm/blob/master/PriorityQueueDemo/Resource/1572606989545.png)

普通的队列是FIFO原则，也就是先进先出；优先级队列则是按照**优先级高低**进行出队，比如将**优先级最高**的元素作为**队头**元素优先出队。

##### 优先级队列的应用场景

1. 医院的夜间门诊，队列元素是病人。根据病情的轻重，优先选择病情最重的病人进行看病。因此优先级是病情的严重情况，挂号时间来决定
2. 操作系统的多任务调度，其中队列中的元素是需要执行的任务，其调度的优先级是根据任务的类型来决定

- #### 优先级队列的底层实现

根据优先级队列的特点，很容易想到：可以直接利用二叉堆作为优先级队列的底层实现

当我们往队列当中添加元素时，只需要定义好元素的比较规则就可以了。最终就可以根据元素的优先级进行出队。

例如我们往队列中添加了5个Person对象

```java
PriorityQueue<Person> queue = new PriorityQueue<>();
queue.enQueue(new Person("Jack",2));
queue.enQueue(new Person("Rose",10));
queue.enQueue(new Person("Jake",5));
queue.enQueue(new Person("Hames",15));
while (!queue.isEmpty()) {
    System.out.println(queue.deQueue());
}
```

当我们最终出队打印对象时的结果为：

```java
Person{name='Hames', boneBreak=15}
Person{name='Rose', boneBreak=10}
Person{name='Jake', boneBreak=5}
Person{name='Jack', boneBreak=2}
```

我们看到，最终出队的顺序并不是添加的顺序，而是我们自己定义的优先级规则。