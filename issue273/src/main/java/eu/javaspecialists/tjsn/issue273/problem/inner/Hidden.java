package eu.javaspecialists.tjsn.issue273.problem.inner;

import eu.javaspecialists.tjsn.issue273.problem.*;

public class Hidden {
    public static A getPrivateInnerClass() {
        return new C();
    }

    private static class C implements A, B {
        public String foo() {
            return "Hello World";
        }

        public String bar() {
            return "Should not be visible";
        }
    }

    public static A getMethodClass() {
        class D implements A {
            public CharSequence foo() {
                return "inside method";
            }
        }
        return new D();
    }

    public static A getLambda() {
        return () -> "Hello Lambert";
    }
}
