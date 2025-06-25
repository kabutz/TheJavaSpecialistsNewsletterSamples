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

import java.math.*;
import java.util.*;

public class ContinuedFraction {
  private final int first;
  private final int[] remain;

  public ContinuedFraction(int first, int... remain) {
    this.first = first;
    this.remain = remain;
  }

  public double doubleValue() {
    double result = first;
    double factor = 0;
    for (int i = remain.length - 1; i >= 0; i--) {
      factor = 1.0 / (factor + remain[i]);
    }
    return factor + first;
  }

  public BigDecimal calculate(int scale) {
    BigDecimal result = new BigDecimal(first);
    BigDecimal factor = BigDecimal.ZERO;
    for (int i = remain.length - 1; i >= 0; i--) {
      factor = BigDecimal.ONE.divide(factor.add(
          new BigDecimal(remain[i])), scale,
          RoundingMode.HALF_EVEN);
    }
    return factor.add(new BigDecimal(first));
  }

  public ContinuedFraction inverse() {
    if (first == 0) {
      return new ContinuedFraction(remain[0],
          Arrays.copyOfRange(remain, 1, remain.length));
    } else {
      int[] newRemain = new int[remain.length + 1];
      newRemain[0] = first;
      System.arraycopy(remain, 0, newRemain, 1, remain.length);
      return new ContinuedFraction(0, newRemain);
    }
  }
}
