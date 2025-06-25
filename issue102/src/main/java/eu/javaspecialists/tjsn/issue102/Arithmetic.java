package eu.javaspecialists.tjsn.issue102;

// --add-opens java.base/java.lang=ALL-UNNAMED
public class Arithmetic {
    public static void main(String... args) {
        CoolClass.makeExcellentCuppaCoffee(); // hmmm - we all need that!
        int upto = 1000;

        // using Integer
        Integer total1 = 0;
        for (int i = 1; i <= upto; i++) {
            total1 += i;
        }
        System.out.println("total1 = " + total1);

        // using int
        int total2 = 0;
        for (int i = 1; i <= upto; i++) {
            total2 += i;
        }
        System.out.println("total2 = " + total2);

        System.out.println("Should be " + ((upto + 1) * upto) / 2);
    }
}
