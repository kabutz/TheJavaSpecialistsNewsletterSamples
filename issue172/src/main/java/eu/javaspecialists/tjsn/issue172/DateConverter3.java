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

import java.lang.ref.SoftReference;
import java.text.*;
import java.util.Date;

public class DateConverter3 {
  private static final ThreadLocal<SoftReference<DateFormat>> tl
      = new ThreadLocal<SoftReference<DateFormat>>();

  private static DateFormat getDateFormat() {
    SoftReference<DateFormat> ref = tl.get();
    if (ref != null) {
      DateFormat result = ref.get();
      if (result != null) {
        return result;
      }
    }
    DateFormat result = new SimpleDateFormat("yyyy/MM/dd");
    ref = new SoftReference<DateFormat>(result);
    tl.set(ref);
    return result;
  }

  public void testConvert(String date) {
    try {
      DateFormat formatter = getDateFormat();
      Date d = formatter.parse(date);
      String newDate = formatter.format(d);
      if (!date.equals(newDate)) {
        System.out.println(date + " converted to " + newDate);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
