package eu.javaspecialists.tjsn.issue004;

public class StackTrace2Test {
    public void f() {
        g();
    }

    public void g() {
        Log.it("where am I now?");
        System.out.println(StackTrace2.getCallStack(0));
        System.out.println(StackTrace2.getCallStack(1));
        System.out.println(StackTrace2.getCallStack(2));
        System.out.println(StackTrace2.getCallStack(3));
        System.out.println(StackTrace2.getCallStack(-1));
    }

    public static void main(String[] args) {
        new StackTrace2Test().f();
    }
}
