package eu.javaspecialists.tjsn.issue025;

public class Access2 {
    public void f() {
        for (int i = 0; i < 10; i++) {
            final int value = i;
            Runnable runnable = new Runnable() {
                public void run() {
                    System.out.println(value);
                }
            };
        }
    }
}
