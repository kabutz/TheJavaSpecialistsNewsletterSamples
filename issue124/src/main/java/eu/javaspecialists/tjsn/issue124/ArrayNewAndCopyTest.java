package eu.javaspecialists.tjsn.issue124;

import java.util.*;

public class ArrayNewAndCopyTest implements Runnable {
    private final byte[] byteValue;

    public ArrayNewAndCopyTest(int length) {
        byteValue = new byte[length];
        // always the same set of bytes...
        new Random(0).nextBytes(byteValue);
    }

    private static final boolean WITH_CHECK = false;

    public void run() {
        if (WITH_CHECK) {
            Class cls = byteValue.getClass();
            if (!cls.isArray() || cls.getComponentType().isAssignableFrom(Object.class)) {
                throw new RuntimeException();
            }
        }
        byte[] b = new byte[byteValue.length];
        System.arraycopy(byteValue, 0, b, 0, byteValue.length);
    }
}

/*
WITH_CHECK = true:

Length=1
Clone 18803930	3405720
Copy  22143695	2111816
Diff  1.18x

Length=10
Clone 20848983	668453
Copy  19656724	361072
Diff  0.94x

Length=100
Clone 13147184	1621505
Copy  13344319	499902
Diff  1.01x

Length=1000
Clone 2191867	212936
Copy  1951354	282157
Diff  0.89x

Length=10000
Clone 117547	25605
Copy  99131	13591
Diff  0.84x

Length=100000
Clone 19507	3418
Copy  20014	1481
Diff  1.03x


WITH_CHECK = false:

Length=1
Clone 19025156	2232921
Copy  22237702	2001575
Diff  1.17x

Length=10
Clone 21106380	497336
Copy  19925428	435150
Diff  0.94x

Length=100
Clone 14109530	222192
Copy  13470486	332256
Diff  0.95x

Length=1000
Clone 1899846	290450
Copy  1857375	176583
Diff  0.98x

Length=10000
Clone 102663	3448
Copy  113228	23681
Diff  1.10x

Length=100000
Clone 18442	4286
Copy  18334	3983
Diff  0.99x
*/