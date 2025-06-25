package eu.javaspecialists.tjsn.issue300;

import eu.javaspecialists.tjsn.issue300.Colour.*;

public class IfElseInstanceof {
    public static void main(String... args) {
        Colour[] colours = {new Red(), new Red(), new Green(),
                new Blue(), new Green(), new Orange()};
        for (Colour colour : colours) {
            if (colour instanceof Red) {
                System.out.println("#FF0000 // Red");
            } else if (colour instanceof Green) {
                System.out.println("#008000 // Green");
            } else if (colour instanceof Blue) {
                System.out.println("#0000FF // Blue");
                break; // stop when we encounter the first Blue
            } else if (colour instanceof Orange) {
                System.out.println("#FFA500 // Orange");
            } else {
                throw new AssertionError(
                        "Unknown Colour: " + colour.getClass());
            }
        }
    }
}
