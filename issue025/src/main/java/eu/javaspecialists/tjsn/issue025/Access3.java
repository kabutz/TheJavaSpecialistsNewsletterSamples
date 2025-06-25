package eu.javaspecialists.tjsn.issue025;

public class Access3 {
    public void f() {
        Runnable[] runners = new Runnable[10];
        for (final int[] i = {0}; i[0] < runners.length; i[0]++) {
            runners[i[0]] = new Runnable() {
                private int counter = i[0];

                public void run() {
                    System.out.println(counter);
                }
            };
        }
        for (int i = 0; i < runners.length; i++)
            runners[i].run();
    }

    public static void main(String[] args) {
        new Access3().f();
    }
}
