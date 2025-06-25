package eu.javaspecialists.tjsn.issue131;

import java.io.*;
import java.util.*;

public class FileCollection extends ArrayList<String> {
    public FileCollection(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s;
        while ((s = in.readLine()) != null) {
            add(s);
        }
        in.close();
    }
}
