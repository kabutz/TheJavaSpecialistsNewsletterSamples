package eu.javaspecialists.tjsn.issue207;

public class Foo2 {
    public void baz() {
        class Bar {
            public String toString() {
                return "I am a bar!";
            }
        }
        Bar bar = new Bar();
        System.out.println(bar);
    }

    public static void main(String[] args) {
        Foo2 foo = new Foo2();
        foo.baz();
    }
}
