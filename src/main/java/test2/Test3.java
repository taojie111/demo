package test2;

import test.User;

import java.util.zip.Deflater;

public class Test3 {

    public final int i = 5;

    public final User user = new User("abx");

    protected Test3() {
        System.out.println("Test3");
    }

    public Test3(Integer i, User user) {

    }

    public static void main(String[] args) {
        String s1 = "zhuge";
        String s2 = s1.intern();
        System.out.println(s1 == s2);
    }

    public void m1(int i) {
        int s = 1;
        i = 6;
        System.out.println(i);
        i = 6;
    }

    public void m2(final User user) {
        user.setName("b");
        System.out.println(user.getName());
    }

    public void m3(String s) {
        System.out.println(s);
        s = new String("b");
        System.out.println(s);
    }

    public void m4() {
        int i;
        String s;
    }
}

class Test7 extends Test3 {

    public int j;

    public Test7(int i, int j) {
        this.j = j;
    }
}

