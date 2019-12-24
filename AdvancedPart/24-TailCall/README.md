#### 尾调用（Tail Call）

一个函数的最后一个动作是调用函数的话，就成为是尾调用。例如下面的代码

```java
void test1() {
	int a = 10;
	int b = a +20;
	test2(b);
}
```

上面test1函数的最后一个动作是调用test2函数，所以可以认为`test2(b);`是尾调用

如果最后一个动作是调用自身，称为尾递归（Tail Recursion）,是尾调用的特殊情况。例如下面的代码

```java
void test2(int n) {
	if (n < 0) return;
	test2(n - 1);
}
```

##### 尾调用优化

**一些编译器能对尾调用进行优化，以达到节省真空间的目的。**

例如上面的示例代码，如果调用test1函数，则会为test1开辟栈空间，如果按照传统的思路，接下来调用test2时，则会为test2开辟一段新的栈空间，如下图所示

![1577190120401](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/24-TailCall/Resource/1577190120401.png)

如果现在进行尾调用的优化的话，则是这样的：

1. 首先调用test1，则会为test1开辟一段栈空间
   ![1577190223599](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/24-TailCall/Resource/1577190223599.png)
2. 接下来，如果要继续调用test2，编译器发现是尾调用，则会重复利用test1的这段栈空间，如下图所示
   ![1577190321501](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/24-TailCall/Resource/1577190321501.png)
   将原来test1的栈空间，直接给test2使用

不过，在这个过程当中，有很多技术细节。例如

现在调用test1函数时，系统为test1开辟了10个字节的栈空间，但是尾调用test2时，test2函数需要20个自己的栈空间，为了达到栈空间复用的目的，所以在调用test2函数时，就需要对test1函数的栈空间进行扩容。这样才能达到重复利用的目的。所以尾调用优化还是有一定难度的。

需要注意：尾调用这种优化，并不是所有的编译器都支持。例如有些变成语言是无法动态去修改栈帧的大小，就不能对尾调用进行优化。

##### 尾调用优化原理

尾调用优化也叫做尾调用消除（Tail Call Elimination）

1. 如果当前栈帧上的局部变量等内容都不需要用了，当前栈帧经过过适当的改变后可以直接当做被为调用的函数的栈帧使用，然后程序可以jump到被尾调用的函数代码
2. 生成栈帧改变代码为jump的过程称作**尾调用消除**或**尾调用优化**
3. 尾调用优化让位于尾位置的函数调用跟goto语句性能一样高。

消除尾递归里的尾调用比消除一般的尾调用容易很多，因为例如Java虚拟机（JVM）会消除尾递归你的尾调用，但是不会消除一般的尾调用（因为改变不了栈帧），所以尾递归优化相对比较普遍，平时的递归代码可以考虑尽量使用尾递归的形式。

##### 尾调用优化前的汇编代码（C++）

例如现在有如下一段C++代码

```C++
void test(int n) {
	if (n < 0) return;
	printf("test - %d\n",n);
	test(n - 1);
}
```

可以发现，这一段代码是尾递归代码。在没有进行优化之前，生成的汇编代码如下

![1577193263848](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/24-TailCall/Resource/1577193263848.png)

从图中可以看到，在函数内部调用test函数时，使用的是call指令，又去调用test函数，在调用test函数时，有会开辟新的栈空间

如果开启了尾调用优化的话，最终生成的汇编代码如下

![1577193441749](https://github.com/MSTGit/Algorithm/blob/master/AdvancedPart/24-TailCall/Resource/1577193441749.png)



