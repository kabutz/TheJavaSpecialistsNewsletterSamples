package eu.javaspecialists.tjsn.issue216;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class TuringMachineSubtract {
  public static void main(String... args) throws IOException {
    String program = "" +
        "states { \"scan\" \"erase1\" \"subtract1\" \"skip\" }" +
        " initial \"scan\"\n" +
        "alphabet { '1' ' ' '=' '-' } blank ' '\n" +
        "trans from \"scan\" to \"scan\" on (' ') write ' '" +
        " move right\n" +
        "trans from \"scan\" to \"scan\" on ('1') write '1'" +
        " move right\n" +
        "trans from \"scan\" to \"scan\" on ('-') write '-'" +
        " move right\n" +
        "trans from \"scan\" to \"erase1\" on ('=') write ' '" +
        " move left\n" +
        "trans from \"erase1\" to \"subtract1\" on ('1') write" +
        " '=' move left\n" +
        "trans from \"erase1\" to \"Halt\" on ('-') write ' '" +
        " move left\n" +
        "trans from \"subtract1\" to \"subtract1\" on ('1')" +
        " write '1' move left\n" +
        "trans from \"subtract1\" to \"skip\" on ('-')" +
        " write '-' move left\n" +
        "trans from \"skip\" to \"skip\" on (' ') write ' '" +
        " move left\n" +
        "trans from \"skip\" to \"scan\" on ('1') write ' '" +
        " move right\n";
    System.out.println(program);

    TuringMachine tm = TuringMachine.create(program);
    test(tm, "11111111-11111=");
    test(tm, "1111111-11111=");
    test(tm, "11111-11111=");
    test(tm, "11111-11=");
    test(tm, "11111-1=");
  }

  private static void test(TuringMachine tm, CharSequence input) {
    System.out.println(input + " => " + tm.execute(input));
  }
}
