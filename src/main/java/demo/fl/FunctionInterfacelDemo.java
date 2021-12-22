package demo.fl;

import demo.common.entity.User;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FunctionInterfacelDemo {

    public static void main(String[] args) {
        FunctionInterfacelDemo f = new FunctionInterfacelDemo();
//        f.m1();
        f.m2();
    }

    // 4大函数式接口
    public void m1() {
        Function<String, String> function = (str) -> {
            return str;
        };
        System.out.println(function.apply("function"));

        Predicate<String> predicate = (str) -> {
            return true;
        };
        System.out.println(predicate.test("predicate"));

        Consumer<String> consumer = (str) -> {
            System.out.println(str);
        };
        consumer.accept("consumer");

        Supplier<String> supplier = () -> {
            return "supplier";
        };
        System.out.println(supplier.get());
    }

    public void m2() {
        User u1 = new User(1, "a", 21);
        User u2 = new User(2, "b", 22);
        User u3 = new User(3, "c", 23);
        User u4 = new User(4, "d", 24);
        User u5 = new User(5, "e", 25);

        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);

//        list.stream().filter(u -> {return u.getId()%2 == 0;}).forEach(System.out::println);

        list.stream()
                .filter(u -> { return u.getId() % 2 == 0;})
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

}
