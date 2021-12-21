package demo.pecs;

import demo.common.entity.Apple;
import demo.common.entity.Fruits;
import demo.common.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PECSDemo {

    public static void main(String[] args) {
        PECSDemo pecsDemo = new PECSDemo();
        pecsDemo.m2();
    }

    // Collections.copy方法测试
    public void m1() {
        User u1 = new User(1, "a", 21);
        User u2 = new User(2, "b", 22);
        User u3 = new User(3, "c", 23);
        List<User> list1 = Arrays.asList(u1, u2, u3);
        List<User> list2 = Arrays.asList(u1, u1, u1);
        Collections.copy(list2, list1);
        System.out.println(list2);
    }

    public void m2() {
        List<? super Fruits> consumer = new ArrayList<>();
        List<? extends Fruits> producer = new ArrayList<>();
        consumer.add(new Apple(1));
        Fruits fruits = producer.get(0);
//        Fruits f = consumer.get(0);
//        producer.add(new Apple(1));
    }
}
