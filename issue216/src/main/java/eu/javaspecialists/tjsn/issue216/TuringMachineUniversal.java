package eu.javaspecialists.tjsn.issue216;

import java.io.*;

public class TuringMachineUniversal {
  public static void main(String... args) throws IOException {
    String program = "" +
        "states { \"A\" \"B\" } initial \"A\"\n" +
        "alphabet { '0' '1' '2' } blank '0'\n" +
        "trans from \"A\" to \"B\" on ('0') write '1'" +
        " move right\n" +
        "trans from \"A\" to \"A\" on ('1') write '2'" +
        " move left\n" +
        "trans from \"A\" to \"A\" on ('2') write '1'" +
        " move left\n" +
        "trans from \"B\" to \"A\" on ('0') write '2'" +
        " move left\n" +
        "trans from \"B\" to \"B\" on ('1') write '2'" +
        " move right\n" +
        "trans from \"B\" to \"A\" on ('2') write '0'" +
        " move right\n" +
        "";
    System.out.println(program);

    TuringMachine tm = TuringMachine.create(program);
    test(tm, "012012");
  }

  private static void test(TuringMachine tm, CharSequence input) {
    System.out.println(input + " => " + tm.execute(input));
  }
}
