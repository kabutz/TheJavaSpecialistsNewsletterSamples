package eu.javaspecialists.tjsn.issue122;

import java.io.*;
import java.net.*;
import java.util.*;

public class Sucker {
    private final String outputFile;
    private final Stats stats;
    private final URL url;

    public Sucker(String path, String outputFile) throws IOException {
        this.outputFile = outputFile;
        System.out.println(new Date() + " Constructing Sucker");
        url = new URL(path);
        System.out.println(new Date() + " Connected to URL");
        stats = Stats.make(url);
    }

    public Sucker(String path) throws IOException {
        this(path, path.replaceAll(".*\\/", ""));
    }

    private void downloadFile() throws IOException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                stats.print();
            }
        }, 1000, 1000);

        try {
            System.out.println(new Date() + " Opening Streams");
            InputStream in = url.openStream();
            OutputStream out = new FileOutputStream(outputFile);
            System.out.println(new Date() + " Streams opened");

            byte[] buf = new byte[1024 * 1024];
            int length;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
                stats.bytes(length);
            }
            in.close();
            out.close();
        } finally {
            timer.cancel();
            stats.print();
        }
    }

    private static void usage() {
        System.out.println("Usage: java Sucker URL [targetfile]");
        System.out.println("\tThis will download the file at the URL " +
                "to the targetfile location");
        System.exit(1);
    }

    public static void main(String... args) throws IOException {
        Sucker sucker;
        switch (args.length) {
            case 1:
                sucker = new Sucker(args[0]);
                break;
            case 2:
                sucker = new Sucker(args[0], args[1]);
                break;
            default:
                usage();
                return;
        }
        sucker.downloadFile();
    }
}
