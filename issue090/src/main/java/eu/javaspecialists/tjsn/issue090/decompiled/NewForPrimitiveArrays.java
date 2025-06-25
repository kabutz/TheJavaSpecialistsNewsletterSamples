package eu.javaspecialists.tjsn.issue090.decompiled;

public class NewForPrimitiveArrays {
    public static void main(String args[]) {
        int daysPerMonth[] = {
                31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };
        int totalDays = 0;
        int arr$[] = daysPerMonth;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            int days = arr$[i$];
            totalDays += days;
        }
        System.out.println("totalDays = " + totalDays);
    }
}
