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

package eu.javaspecialists.tjsn.issue172;

import java.util.concurrent.*;

public class DateConverterTest {
  public static void main(String... args) {
    ExecutorService pool = Executors.newCachedThreadPool();
    convert(pool, "1971/12/04");
    convert(pool, "2001/09/02");
  }

  private static void convert(ExecutorService pool, final String date) {
    pool.submit(new Runnable() {
      public void run() {
        DateConverter1 dc = new DateConverter1();
        while (true) {
          dc.testConvert(date);
        }
      }
    });
  }
}
