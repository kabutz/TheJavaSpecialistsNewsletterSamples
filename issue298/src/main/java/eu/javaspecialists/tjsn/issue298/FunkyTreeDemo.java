package eu.javaspecialists.tjsn.issue298;

public class FunkyTreeDemo {
    public static void main(String... args) {
        SealedClassPrinter.printTree(A.class);
    }

    sealed interface A {
    }

    sealed interface B extends A {
    }

    sealed interface C extends B {
    }

    static final class D implements A, B, C {
    }
}
