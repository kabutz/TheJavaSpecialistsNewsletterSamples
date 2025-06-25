package eu.javaspecialists.tjsn.issue314;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class ChatGPTGeneratedCode {
    private static void copySymbolicLinks(Path source, Path target)
            throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs)
                    throws IOException {
                if (Files.isSymbolicLink(file)) {
                    Path targetLink = target.resolve(source.relativize(file));
                    Files.createDirectories(targetLink.getParent());
                    Files.copy(file, targetLink,
                            StandardCopyOption.REPLACE_EXISTING,
                            LinkOption.NOFOLLOW_LINKS);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
