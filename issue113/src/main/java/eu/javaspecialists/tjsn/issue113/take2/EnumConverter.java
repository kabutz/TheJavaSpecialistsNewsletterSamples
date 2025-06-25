package eu.javaspecialists.tjsn.issue113.take2;

public interface EnumConverter<E extends Enum<E> & EnumConverter<E>> {
    byte convert();

    E convert(byte val);
}