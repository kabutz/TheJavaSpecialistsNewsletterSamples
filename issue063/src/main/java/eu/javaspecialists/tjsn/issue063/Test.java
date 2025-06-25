package eu.javaspecialists.tjsn.issue063;

public class Test {
    private CallDetective saps =
            CallDetective.Factory.makeCallDetective();

    {
        System.out.println("Using " + saps.getClass().getName());
    }

    public void f() {
        g();
    }

    public void g() {
        Log.it("where am I now?");
        System.out.println(saps.findCaller(0));
        System.out.println(saps.findCaller(1));
        System.out.println(saps.findCaller(2));
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        new Test().f();
    }
}
