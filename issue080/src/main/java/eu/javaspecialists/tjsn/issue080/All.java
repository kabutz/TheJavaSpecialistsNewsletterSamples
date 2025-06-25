package eu.javaspecialists.tjsn.issue080;

public class All {
    public static class A {
        public void f() {
        }
    }

    public static class B {
        public void g() {
        }
    }

    public static class C {
        public void h() {
        }
    }

    public static class D extends A {
        public void f() {
        }
    }

    public static class E {
        public void jump() {
        }
    }

    public static class F {
        private final E e = new E();

        public void skip() {
            e.jump();
        }
    }
}
