package eu.javaspecialists.tjsn.issue184;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class VectorSerializationDeadlock {
    public static void main(String... args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        final Vector[] vecs = {
                new Vector(),
                new Vector(),
        };
        vecs[0].add(vecs[1]);
        vecs[1].add(vecs[0]);

        for (int i = 0; i < 2; i++) {
            final int threadNumber = i;
            pool.submit(new Callable() {
                public Object call() throws Exception {
                    for (int i = 0; i < 1000 * 1000; i++) {
                        ObjectOutputStream out = new ObjectOutputStream(
                                new NullOutputStream()
                        );
                        out.writeObject(vecs[threadNumber]);
                        out.close();
                    }
                    System.out.println("done");
                    return null;
                }
            });
        }
        pool.shutdown();
    }
}

