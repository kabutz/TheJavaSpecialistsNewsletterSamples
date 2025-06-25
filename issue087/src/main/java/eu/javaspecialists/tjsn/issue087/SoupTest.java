package eu.javaspecialists.tjsn.issue087;

public class SoupTest {
    public static void main(String[] args) {
        Soup soup = new Soup();
        soup.add(new Potato(1));
        soup.add(new Potato(2));
        soup.add(new Potato(3));
        Potato p4 = new Potato(4);
        soup.add(p4);
        p4.setSoup(soup); // redundant code
        Potato p5 = new Potato(5);
        p5.setSoup(soup);
        System.out.println("soup = " + soup);
    }
}
