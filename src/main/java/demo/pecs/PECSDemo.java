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
        pecsDemo.m3();
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

    // pecs
    public void m2() {
        List<Apple> pList = new ArrayList<>();
        pList.add(new Apple(1));
        List<? extends Fruits> producer = pList;
        Fruits fruits = producer.get(0);
        System.out.println(fruits.getWeight());

        List<Fruits> cList = new ArrayList<>();
        cList.add(new Fruits(2));
        List<? super Fruits> consumer = cList;
        consumer.add(new Apple(3));
        System.out.println(consumer);
    }

    // List、List<Object>、List<?>的区别
    public void m3() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);

        // List
        List list1 = new ArrayList();
        list1.add(new Apple(1));
        list1.add(1);
        list1.add("a");
        System.out.println(list1.get(2));

        // List<Object>
        List<Object> list2 = new ArrayList<>();
        // List赋值List<Object>，可以赋值，不会报错
        list2 = list1;
        // 其他泛型类型赋值，会报错
        // list2 = integerList;

        // List<?>
        List<?> list3 = new ArrayList<>();
        // 添加元素报错
        // list3.add(new Apple(1));
        // 添加null不会报错
        list3.add(null);
        // 只能通过赋值引用
        list3 = list1;
    }
}
