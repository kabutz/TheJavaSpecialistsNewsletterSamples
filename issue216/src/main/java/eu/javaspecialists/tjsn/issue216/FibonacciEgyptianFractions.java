package eu.javaspecialists.tjsn.issue216;

public class FibonacciEgyptianFractions {
  public static void main(String... args) {
    test(1, 3);
    test(2, 3);
    test(3, 4);
    test(4, 5);
    test(9, 31);
    test(21, 50);
    test(1023, 1024);
    test(5, 121);
  }

  private static void test(long num, long den) {
    Fraction fraction = new Fraction(num, den);
    System.out.print(fraction + " = ");
    Fraction[] fs = fraction.getEgyptianFractions();
    for (int i = 0; i < fs.length; i++) {
      System.out.print(fs[i]);
      if (i < fs.length - 1) System.out.print(" + ");
    }
    System.out.print(" = ");
    Fraction total = new Fraction(0, 1);
    for (Fraction f : fs) {
      total = total.add(f);
    }
    System.out.println(total);
  }
}
