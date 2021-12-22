package test2;

import test.User;

import java.util.zip.Deflater;

public class Test3 {

    public final int i = 5;

    public final User user = new User("abx");

    public Test3() {
        System.out.println("Test3");
    }

    public Test3(Integer i, User user) {

    }

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("a").append("b");

    }

    public Test3 m1() {
       return new Test3();
    }
}

class Test7 extends Test3 {

    public int j;

    public Test7(int i, int j) {
        this.j = j;
    }

    public Test3 m1(Test7 t) {
        return t;
    }

}

