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
