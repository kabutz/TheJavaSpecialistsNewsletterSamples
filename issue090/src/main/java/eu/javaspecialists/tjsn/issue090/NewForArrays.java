package eu.javaspecialists.tjsn.issue090;

public class NewForArrays {
    public static void main(String... args) {
        String[] names = {"Maxi", "Connie", "Helene", "Heinz"};
        for (String name : names) {
            System.out.println("name = " + name);
        }
    }
}
