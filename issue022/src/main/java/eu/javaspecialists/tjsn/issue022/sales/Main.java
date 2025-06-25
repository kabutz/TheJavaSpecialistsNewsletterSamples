package eu.javaspecialists.tjsn.issue022.sales;

/**
 * An example batch application
 */
public class Main {
    /**
     * starts the batch
     */
    public static void main(String[] args) throws Exception {
        // analyse command-line
        for (int count = 0; count < args.length; count++)
            // construct order and reserve
            new Order(args[count++],
                    new Integer(args[count]).intValue()).reserve();
    }
}