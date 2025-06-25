package eu.javaspecialists.tjsn.issue174;

public class JavaMemoryPuzzlePolite {
    private final int dataSize =
            (int) (Runtime.getRuntime().maxMemory() * 0.6);

    public void f() {
        {
            byte[] data = new byte[dataSize];
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("Please be so kind and release memory");
        }
        byte[] data2 = new byte[dataSize];
    }

    public static void main(String[] args) {
        JavaMemoryPuzzlePolite jmp = new JavaMemoryPuzzlePolite();
        jmp.f();
        System.out.println("No OutOfMemoryError");
    }
}
