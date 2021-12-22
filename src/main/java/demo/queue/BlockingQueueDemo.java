package demo.queue;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueDemo {

    public static void main(String[] args) throws Exception {
        testArrayBlockingQueue();
    }

    public static void testArrayBlockingQueue() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
        new Thread(() -> {
            try {
                Integer poll = queue.take();
                System.out.println("阻塞结束: " + poll);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.offer(1);
            System.out.println("插入数据");
        }).start();
        System.out.println("main线程结束");
    }

}
