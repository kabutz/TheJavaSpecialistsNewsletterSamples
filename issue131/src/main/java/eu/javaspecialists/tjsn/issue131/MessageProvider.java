package eu.javaspecialists.tjsn.issue131;

import java.io.*;
import java.util.*;

public class MessageProvider {
    private final String subject;
    private final String content;

    public MessageProvider(String filename) throws IOException {
        Iterator<String> lines = new FileCollection(filename).iterator();
        subject = lines.next();
        StringBuilder cb = new StringBuilder();
        while (lines.hasNext()) {
            cb.append(lines.next());
            cb.append('\n');
        }
        content = cb.toString();
    }

    public final String getSubject() {
        return subject;
    }

    public final String getContent() {
        return content;
    }
}
