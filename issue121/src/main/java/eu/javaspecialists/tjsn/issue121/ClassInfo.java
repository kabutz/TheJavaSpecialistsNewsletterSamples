package eu.javaspecialists.tjsn.issue121;

public class ClassInfo implements Comparable<ClassInfo> {
    private final String className;
    private final int depth;

    public ClassInfo(String className, int depth) {
        this.className = className;
        this.depth = depth;
    }

    public int compareTo(ClassInfo o) {
        if (depth < o.depth) return -1;
        if (depth > o.depth) return 1;
        return className.compareTo(o.className);
    }

    public String toString() {
        return depth + "\t" + className;
    }
}



