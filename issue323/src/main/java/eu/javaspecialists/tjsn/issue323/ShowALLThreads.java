package eu.javaspecialists.tjsn.issue323;

import jdk.internal.vm.*;

import java.util.stream.*;

public class ShowALLThreads {
    public static void main(String... args) {
        Thread.startVirtualThread(() -> {
            System.out.println("I'm a virtual thread");
            while (true) ;
        });
        // print ALL threads
        threads(ThreadContainers.root()).forEach(System.out::println);
    }

    public static Stream<Thread> threads(ThreadContainer container) {
        return Stream.concat(container.threads(),
                container.children().flatMap(ShowALLThreads::threads));
    }
}
