package eu.javaspecialists.tjsn.issue064;

public class Increment {
    public int preIncrement() {
        int i = 0;
        ++i;
        return i;
    }

    public int postIncrement() {
        int i = 0;
        i++;
        return i;
    }

    public int negative() {
        int i = 0;
        i -= -1;
        return i;
    }

    public int plusEquals() {
        int i = 0;
        i += 1;
        return i;
    }
}
