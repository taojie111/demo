package test;

public class Test1 {

    private int v1;

    public static void main(String[] args) {
        Test1 test1 = new Test1(1);
        test1.method2();
        test1.method4();
    }

    public Test1(int v1) {
        this.v1 = v1;
    }

    public static void method1() {
        System.out.println("test1 method1");
    }

    protected void method2() {
        System.out.println("test1 method2");
    }

    public void method3() {
        System.out.println("test1 method1");
    }

    void method4() {
        System.out.println("test1 method1");
    }
}

class Test2 extends Test1 {

    public Test2(int v1) {
        super(v1);
    }
}
