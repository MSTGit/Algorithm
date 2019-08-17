import java.sql.Time;


public class ComplexityDemo {

    /*
    * 计算 a + b的和
    * */
    public static int plus(int a,int b) {
        return a + b;
    }
    /*
    * 计算前n项的和
    * */
    public static int sum(int n) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            result += i;
        }
        return result;
    }


    /*
    * 通过递归的方式，计算斐波拉契数列的前N项
    * */

    public static int recursiveFib(int n) {
        if (n <= 1) return n;
        return recursiveFib(n- 1) + recursiveFib(n - 2);
    }

    /*
    * 通过算法进行优化后的计算方法
    * */
    public static int optimizeFib(int n) {
        if (n <= 1) return n;
        int first = 0;
        int second = 1;
        for (int i = 0; i < n - 1; i++) {
            int sum = first + second;
            first = second;
            second = sum;
        }
        return second;
    }

    public static void main(String[] args) {
        System.out.println("ComplexityDemo.main");
        int n = 46;

        Times.test("recursiveFib", new Times.Task() {
            @Override
            public void execute() {
                System.out.println(recursiveFib(n));
            }
        });

        Times.test("optimizeFib", new Times.Task() {
            @Override
            public void execute() {
                System.out.println(optimizeFib(n));
            }
        });
    }

    public static void test1(int n) {

        /*
        * 一个简单的判断语句 执行次数为2次
        * */
        if (n > 10) {
            System.out.println("n > 10");
        } else if (n > 5) {
            System.out.println("n > 5");
        } else {
            System.out.println("n <= 5");
        }
        /*
        * int i = 0会执行一次
        * i < 4 会执行4次
        * i ++ 也会执行4次
        * System.out.println("test"); 也会执行4次
        * */
        for (int i = 0; i < 4; i++) {
            System.out.println("test");
        }
        //总共的执行次数大概是 4 + 4 + 4 +1 + 2 = 15
    }

    public static void test2(int n) {
        /*
         * int i = 0会执行一次
         * i < n 会执行n次
         * i ++ 也会执行n次
         * System.out.println("test"); 也会执行n次
         * */
        for (int i = 0; i < n; i++) {
            System.out.println("test");
        }
        //总共的执行次数大概是  n+ n + n +1  = 3n +1次
    }

    public static void test3(int n) {
        /*
         * int i = 0会执行一次
         * i < n 会执行n次
         * i ++ 也会执行n次
         * for (int j = 0; j < n; j++) 会执行n次
         * */
        for (int i = 0; i < n; i++) {
            /*
             * int j = 0会执行一次
             * j < n 会执行n次
             * j ++ 也会执行n次
             * System.out.println("test"); 也会执行n次
             * */
            for (int j = 0; j < n; j++) {
                System.out.println("test");
            }
        }
        //总共的执行次数大概是  2n + 1 + n * (3n + 1) = 3n^2 + 3n + 1次
    }

    public static void test4(int n) {
        /*
         * int i = 0会执行一次
         * i < n 会执行n次
         * i ++ 也会执行n次
         * for (int j = 0; j < 15; j++) 会执行n次
         * */
        for (int i = 0; i < n; i++) {
            /*
             * int j = 0会执行一次
             * j < n 会执行15次
             * j ++ 也会执行15次
             * System.out.println("test"); 会执行15次
             * */
            for (int j = 0; j < 15; j++) {
                System.out.println("test");
            }
        }
        //总共的执行次数大概是  2n + 1 + n * (3 *15 + 1) = 48n + 1次
    }

    public static void test5(int n) {

        //这个函数较之前的函数会稍微复杂一些，不过我们通过观察，发现如果n = 8 while的执行次数为3次，n = 16时 while的执行次数为4次,符合高数中的log2(n)计算结果
        /*
         * while ((n = n / 2) > 0)  会执行log2(n)次
         * System.out.println("test"); 会执行log2(n)次
         * */
        while ((n = n / 2) > 0) {
            System.out.println("test");
        }
        //总共的执行次数大概是  2 * log2(n)次
    }

    public static void test6(int n) {
        //通过上面的计算规律，我们可以知道，循环次数为log5(n)，因此
        /*
         * while ((n = n / 5) > 0)  会执行log5(n)次
         * System.out.println("test"); 会执行log5(n)次
         * */
        while ((n = n / 5) > 0) {
            System.out.println("test");
        }
        //总共的执行次数大概是  2 * log5(n)次
    }

    public static void test7(int n) {
        /*
         * int i = 0会执行一次
         * i < n 会执行log2(n)次
         * i ++ 也会执行log2(n)次
         * for (int j = 0; j < n; j++) 会执行log2(n)次
         * */
        for (int i = 1; i < n; i = i * 2) {
            /*
             * int j = 0会执行一次
             * j < n 会执行n次
             * j ++ 也会执行n次
             * System.out.println("test"); 会执行n次
             * */
            for (int j = 0; j < n; j++) {
                System.out.println("test");
            }
        }
        //总共的执行次数大概是  1 + log2(n)+ log2(n)+ log2(n) * (1 + 3n) = 3n * log2(n) + 3 * log2(n) + 1次
    }

    public static void test10(int n) {
        int a = 10;
        int b = 20;
        int c = a + b;
        int[] array = new int[n];
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i] + c);
        }
    }
}
