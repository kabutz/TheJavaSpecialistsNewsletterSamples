package eu.javaspecialists.tjsn.issue068;

public class StringAppendDiff {
    public static void main(String[] args) {
        System.out.println("String += 10000 additions");
        Timer.time(new Runnable() {
            public void run() {
                String s = "";
                for (int i = 0; i < 10000; i++) {
                    s += i;
                }
                // we have to use "s" in some way, otherwise a clever
                // compiler would optimise it away.  Not that I have
                // any such compiler, but just in case ;-)
                System.out.println("Length = " + s.length());
            }
        });

        System.out.println(
                "StringBuffer 300 * 10000 additions initial size wrong");
        Timer.time(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < (300 * 10000); i++) {
                    sb.append(i);
                }
                String s = sb.toString();
                System.out.println("Length = " + s.length());
            }
        });

        System.out.println(
                "StringBuffer 300 * 10000 additions initial size right");
        Timer.time(new Runnable() {
            public void run() {
                StringBuffer sb = new StringBuffer(19888890);
                for (int i = 0; i < (300 * 10000); i++) {
                    sb.append(i);
                }
                String s = sb.toString();
                System.out.println("Length = " + s.length());
            }
        });
    }
}
