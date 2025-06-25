package eu.javaspecialists.tjsn.issue034.ex6_generic_collections;

public class PoundTest {
    public static void main(String[] clargs) throws ClassNotFoundException {
        Pound spca = new Pound(
                new Dog[]{
                        new Dog("Alsation"),
                        new Dog("Bulldog"),
                        new Dog("PavementSpecial")},
                new Cat[]{
                        new Cat("RussianBlue"),
                        new Cat("DeadBounce")});
        spca.makeNoise();
    }
}