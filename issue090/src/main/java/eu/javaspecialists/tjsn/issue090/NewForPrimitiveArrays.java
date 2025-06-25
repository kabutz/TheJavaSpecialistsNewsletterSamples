package eu.javaspecialists.tjsn.issue090;

public class NewForPrimitiveArrays {
    public static void main(String... args) {
        int[] daysPerMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int totalDays = 0;
        for (int days : daysPerMonth) {
            totalDays += days;
        }
        System.out.println("totalDays = " + totalDays);
    }
}
