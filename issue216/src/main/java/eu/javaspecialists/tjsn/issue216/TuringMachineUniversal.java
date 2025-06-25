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
