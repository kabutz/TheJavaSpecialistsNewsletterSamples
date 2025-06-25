package eu.javaspecialists.tjsn.issue224;

public class DispLine {
    private final long disp;
    private final CharSequence line;

    public DispLine(long disp, CharSequence line) {
        this.disp = disp;
        this.line = line;
    }

    public CharSequence getLine() {
        return line;
    }

    public String toString() {
        return disp + ":" + line;
    }
}
