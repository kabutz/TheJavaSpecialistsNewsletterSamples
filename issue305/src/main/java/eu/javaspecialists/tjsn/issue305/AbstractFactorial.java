package eu.javaspecialists.tjsn.issue305;

import java.math.*;
import java.util.function.*;

public abstract class AbstractFactorial implements Factorial {
    private final BinaryOperator<BigInteger> multiply;

    protected AbstractFactorial(BinaryOperator<BigInteger> multiply) {
        this.multiply = multiply;
    }

    protected final BigInteger multiply(BigInteger a, BigInteger b) {
        return multiply.apply(a, b);
    }
}
