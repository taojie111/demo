package test;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Test8 {

    public static void main(String[] args) throws Exception {
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

    public static void m1() throws IOException {
        FileOutputStream fos = new FileOutputStream("D:\\data\\work\\devops\\1.txt");
        ObjectOutput out = new ObjectOutputStream(fos);
        out.writeUTF("test");
    }

    public static String byteToHex(byte[] bt){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<bt.length;i++){
            String tmpStr = Integer.toHexString(bt[i]);
            if(tmpStr.length()<2)
                sb.append("0");
            sb.append(tmpStr);
        }
        return sb.toString().toUpperCase();
    }

}
