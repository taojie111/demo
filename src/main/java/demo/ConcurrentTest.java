package demo;

public class ConcurrentTest {

    private static boolean flag = true;

    private static int x,y,a,b=0;

    private static int count = 0;

    public static void main(String[] args) throws Exception  {
        // join测试
        /*Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainThread.interrupt();
                    Thread.sleep(2000);
                    System.out.println("线程执行结束");
                } catch (InterruptedException e) {
                    System.out.println("线程中断");
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("主线程中断");
            thread.interrupt();
        }
        System.out.println("主线程执行结束");*/
        // wait测试
        /*Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    try {
                        Thread.sleep(1000); //  使当前线阻塞 1 s，确保主程序的 t1.wait(); 执行之后再执行 notify()
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" call notify()");
                    // 唤醒当前的wait线程
                    this.notify();

                    long i = 1000000000;
                    while (i > 0) {
                        i--;
                    }
                    System.out.println("t1执行");
                }
            }
        });
        synchronized(t1) {
            try {
                // 启动“线程t1”
                System.out.println(Thread.currentThread().getName()+" start t1");
                t1.start();
                // 主线程等待t1通过notify()唤醒。
                System.out.println(Thread.currentThread().getName()+" wait()");
                t1.wait();  //  不是使t1线程等待，而是当前执行wait的线程等待
                System.out.println(Thread.currentThread().getName()+" continue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        // volatile可见性测试
        /*Thread threadA = new Thread(() -> {
            while (flag){
                //注意在这里不能有输出
            };
            System.out.println("threadA over");
        });
        threadA.start();
        //休眠100毫秒，让线程A先执行
        Thread.sleep(100);
        //主线程设置共享变量flag等于false
        flag = false;*/
        // synchronized可见性测试
        /*Thread threadA = new Thread(() -> {
            while (flag){
                synchronized (Test7.class){
                }
            };
            System.out.println("threadA over");
        });
        threadA.start();
        //休眠100毫秒，让线程A先执行
        Thread.sleep(100);
        //主线程设置共享变量flag等于false
        flag = false;*/
        // volatile非原子性
        /*Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<10000; i++) {
                    count++;
                }
            }
        });
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<10000; i++) {
                    count++;
                }
            }
        });
        threadA.start();
        threadB.start();
        //主线程等待AB执行完毕！
        threadA.join();
        threadB.join();
        System.out.println("累加count="+count);*/
        // synchronized 原子性
        /*Runnable task = new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < 10000; i++) {
                        count++;
                    }
                }
            }
        };
        Thread threadA = new Thread(task);
        Thread threadB = new Thread(task);
        threadA.start();
        threadB.start();
        //主线程等待AB执行完毕！
        threadA.join();
        threadB.join();
        System.out.println("累加count="+count);*/
        // 有序性
        /*while (true) {
            //初始化4个变量
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread threadA = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 3;
                    x = b;
                }
            });
            Thread threadB = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 3;
                    y = a;
                }
            });
            threadA.start();
            threadB.start();
            threadA.join();
            threadB.join();
            count++;
            if (x == 0 && y==0) {
                System.out.println("执行次数:"+count);
                break;
            } else {
                System.out.println("执行次数:"+count+","+"x:"+x +" y:"+y);
            }
        }*/
    }

    public static void testLock() {

    }
}
