package eu.javaspecialists.tjsn.issue300;

import eu.javaspecialists.tjsn.issue300.Colour.*;

public class EnhancedSwitchWithLabelledBreak {
    public static void main(String... args) {
        Colour[] colours = {new Red(), new Red(), new Green(),
                new Blue(), new Green(), new Orange()};
        out:
        for (Colour colour : colours) {
            switch (colour) {
                case Red r -> System.out.println("#FF0000 // Red");
                case Green g -> System.out.println("#008000 // Green");
                case Blue b -> {
                    System.out.println("#0000FF // Blue");
                    break out; // stop when we encounter the first Blue
                }
                case Orange o -> System.out.println("#FFA500 // Orange");
            }
        }
    }
}
