/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
