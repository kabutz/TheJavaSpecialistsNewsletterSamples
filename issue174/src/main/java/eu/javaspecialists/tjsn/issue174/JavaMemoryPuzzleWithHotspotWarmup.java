package eu.javaspecialists.tjsn.issue174;

public class JavaMemoryPuzzleWithHotspotWarmup {
    private int dataSize = 0;

    public void f() {
        {
            byte[] data = new byte[dataSize];
        }

        byte[] data2 = new byte[dataSize];
    }

    public static void main(String[] args) {
        JavaMemoryPuzzleWithHotspotWarmup jmp =
                new JavaMemoryPuzzleWithHotspotWarmup();
        jmp.dataSize = 10;
        for (int i = 0; i < 1000 * 1000; i++) {
            jmp.f();

        }
        jmp.dataSize = (int) (Runtime.getRuntime().maxMemory() * 0.6);

        jmp.f(); // probably no OutOfMemoryError
    }
}
