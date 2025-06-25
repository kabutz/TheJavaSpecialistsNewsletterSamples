package eu.javaspecialists.tjsn.issue216;

import java.io.*;

public class TuringMachineAdd {
  public static void main(String... args) throws IOException {
    String program = "" +
        "states { \"scan\" \"erase1\" \"add\" \"clean\" }" +
        " initial \"scan\"\n" +
        "alphabet { '1' ' ' '=' '+' } blank ' '\n" +
        "trans from \"scan\" to \"scan\" on (' ') write ' '" +
        " move right\n" +
        "trans from \"scan\" to \"scan\" on ('1') write '1'" +
        " move right\n" +
        "trans from \"scan\" to \"add\" on ('+') write '+'" +
        " move right\n" +
        "trans from \"add\" to \"erase1\" on ('1') write '+'" +
        " move left\n" +
        "trans from \"add\" to \"clean\" on ('=') write ' '" +
        " move left\n" +
        "trans from \"clean\" to \"Halt\" on ('+') write ' '" +
        " move left\n" +
        "trans from \"erase1\" to \"scan\" on ('+') write '1'" +
        " move right\n";
    System.out.println(program);

    TuringMachine tm = TuringMachine.create(program);
    test(tm, "11111111+11111=");
    test(tm, "1111111+11111=");
    test(tm, "11111+11111=");
    test(tm, "11111+11=");
    test(tm, "111+11=");
    test(tm, "1+1=");
  }

  private static void test(TuringMachine tm, CharSequence input) {
    System.out.println(input + " => " + tm.execute(input));
  }
}
