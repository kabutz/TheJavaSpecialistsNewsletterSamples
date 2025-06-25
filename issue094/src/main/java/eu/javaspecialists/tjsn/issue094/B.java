package eu.javaspecialists.tjsn.issue094;

public class B extends A {
    private int size = 0;

    public B(int size) {
        super(size);
    }

    protected void build(int size) {
        this.size = size;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        B test = new B(1);
        System.out.println("Size: " + test.size());
    }
}
