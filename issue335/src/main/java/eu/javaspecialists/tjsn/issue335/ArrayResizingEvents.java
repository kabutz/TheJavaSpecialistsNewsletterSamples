package eu.javaspecialists.tjsn.issue335;

public class ArrayResizingEvents {
    public void main() {
        var targetSize = 100_000_000;
        var size = 10;
        var resizes = 0;
        var junk = 0;
        while (size < targetSize) {
            System.out.println("size = " + size);
            junk += size;
            size *= 1.5;
            resizes++;
        }
        System.out.println("final size = " + size);
        System.out.println("resizes = " + resizes);
        System.out.println("junk = " + junk);
    }
}