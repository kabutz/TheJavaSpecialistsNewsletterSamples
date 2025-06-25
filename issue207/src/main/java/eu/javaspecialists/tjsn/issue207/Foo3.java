package eu.javaspecialists.tjsn.issue207;

public class Foo3 {
    public void baz() {
        Object bar = new Object() {
            public String toString() {
                return "I am an anonymous bar!";
            }
        };
        System.out.println(bar);
    }

    public static void main(String[] args) {
        Foo3 foo = new Foo3();
        foo.baz();
    }
}
