package eu.javaspecialists.tjsn.issue216;

import javax.xml.transform.*;
import java.math.*;
import java.util.*;

public class ContinuedFractionTest {
  public static void main(String... args) {
    ContinuedFraction cf1 = // 2.3456
        new ContinuedFraction(2, 2, 1, 8, 2, 1, 1, 4);
    System.out.println(cf1.doubleValue());
    System.out.println(cf1.inverse().doubleValue());
    System.out.println(1.0 / 2.3456);


    int[] ones = new int[250];
    Arrays.fill(ones, 1);
    ContinuedFraction phi = new ContinuedFraction(1, ones);
    System.out.println(phi.calculate(100));
    System.out.println("1.6180339887498948482045868343656381" +
        "177203091798057628621354486227052604628189024497072" +
        "07204189391137484754");

    ContinuedFraction pi = new ContinuedFraction(
        3, 7, 15, 1, 292, 1
    );
    System.out.println(pi.calculate(9));
    System.out.println("3.141592653589");
    System.out.println(Math.PI);
  }
}
