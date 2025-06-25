package eu.javaspecialists.tjsn.issue230;

//@NotThreadSafe
public class SubbableString implements CharSequence {
    private final char[] value;
    private final int offset;
    private final int count;

    public SubbableString(char[] value) {
        this(value, 0, value.length);
    }

    private SubbableString(char[] value, int offset, int count) {
        this.value = value;
        this.offset = offset;
        this.count = count;
    }

    public int length() {
        return count;
    }

    public String toString() {
        return new String(value, offset, count);
    }

    public char charAt(int index) {
        if (index < 0 || index >= count)
            throw new StringIndexOutOfBoundsException(index);
        return value[index + offset];
    }

    public CharSequence subSequence(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > count) {
            throw new StringIndexOutOfBoundsException(end);
        }
        if (start > end) {
            throw new StringIndexOutOfBoundsException(end - start);
        }
        return (start == 0 && end == count) ? this :
                new SubbableString(value, offset + start, end - start);
    }
}
