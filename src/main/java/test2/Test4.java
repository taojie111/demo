package test2;

public abstract class Test4 {

    public int v1;

    public Test4(int v1) {
        this.v1 = v1;
    }

    static {
        int v2 = 1;
    }

    public void test1() {
        System.out.println("test1");
    }

    public static void main(String[] args) {

    }
}

class Test5 extends Test4 {

    public Test5(int v1) {
        super(v1);
    }

    public static void main(String[] args) {
        Test5 test5 = new Test5(2);
        test5.notify();
    }
}
