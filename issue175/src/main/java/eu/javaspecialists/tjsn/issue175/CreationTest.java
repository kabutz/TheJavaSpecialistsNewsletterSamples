package eu.javaspecialists.tjsn.issue175;

public class CreationTest {
    public static void main(String... args) {
        // Creating MySerializable by calling NotSerializable no-args
        // constructor, but not the MySerializable constructor.
        MySerializable ms = SilentObjectCreator.create(
                MySerializable.class, NotSerializable.class);
        System.out.println("ms = " + ms);

        // Creating MySerializable by not calling any constructors.
        MySerializable ms2 = SilentObjectCreator.create(
                MySerializable.class
        );
        System.out.println("ms2 = " + ms2);
    }
}
