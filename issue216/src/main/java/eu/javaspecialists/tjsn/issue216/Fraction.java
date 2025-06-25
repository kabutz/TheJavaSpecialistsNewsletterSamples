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

public final class Fraction {
  private final BigInteger num;
  private final BigInteger den;

  public Fraction(BigInteger num, BigInteger den) {
    if (den.equals(BigInteger.ZERO)) {
      throw new ArithmeticException("divide by zero");
    }
    BigInteger gcd = gcd(num, den);
    this.num = num.divide(gcd);
    this.den = den.divide(gcd);
  }

  private BigInteger gcd(BigInteger a, BigInteger b) {
    if (b.equals(BigInteger.ZERO)) return a;
    return gcd(b, a.mod(b));
  }

  public Fraction(String num, String den) {
    this(new BigInteger(num), new BigInteger(den));
  }

  public Fraction(long num, long den) {
    this(Long.toString(num), Long.toString(den));
  }

  public Fraction add(Fraction other) {
    BigInteger newDen = other.den.multiply(den);
    BigInteger newNum = num.multiply(other.den).add(
        other.num.multiply(den)
    );
    return new Fraction(newNum, newDen);
  }

  public static Fraction addAll(Fraction... fractions) {
    Fraction total = new Fraction(0, 1);
    for (Fraction fraction : fractions) {
      total = total.add(fraction);
    }
    return total;
  }

  public Fraction[] getEgyptianFractions() {
    Collection<Fraction> fractions = new ArrayList<>();
    findEgyptianFractions(fractions, num, den);
    return fractions.toArray(new Fraction[fractions.size()]);
  }

  private static void findEgyptianFractions(
      Collection<Fraction> result,
      BigInteger x, BigInteger y) {
    if (x.equals(BigInteger.ZERO)) {
      return;
    }
    BigInteger den = y.divide(x).add(
        (y.mod(x).equals(BigInteger.ZERO)
            ? BigInteger.ZERO : BigInteger.ONE));
    result.add(new Fraction(BigInteger.ONE, den));
    BigInteger remx = y.negate().mod(x);
    BigInteger remy = y.multiply(den);
    findEgyptianFractions(result, remx, remy);
  }

  public String toString() {
    return num + "/" + den;
  }

  public boolean equals(Object o) {
    if (!(o instanceof Fraction)) return false;
    Fraction fraction = (Fraction) o;
    return den.equals(fraction.den) &&
        num.equals(fraction.num);
  }

  public int hashCode() {
    return (num.hashCode() << 16) ^ den.hashCode();
  }
}
