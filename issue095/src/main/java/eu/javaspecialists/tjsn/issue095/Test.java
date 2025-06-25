package eu.javaspecialists.tjsn.issue095;

import java.beans.*;
import java.io.*;
import java.util.*;

public class Test {
    public static void main(String... args) throws Exception {
        String filename = "test.xml";
        Properties defaults = new Properties();
        defaults.setProperty("name", "Heinz");
        defaults.setProperty("yahooid", "heinzkabutz");
        defaults.setProperty("age", "32");
        Configuration cfg = new XMLFileConfiguration(defaults, filename, 300);
        cfg.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Property " + evt.getPropertyName() +
                        " has now changed from <" + evt.getOldValue() +
                        "> to <" + evt.getNewValue() + ">");
            }
        });
        Thread.sleep(1000);
        defaults.setProperty("age", "33");
        FileOutputStream fos = new FileOutputStream(filename);
        defaults.storeToXML(fos, "");
        fos.close();

        Thread.sleep(1000);
        defaults.setProperty("age", "32");
        fos = new FileOutputStream(filename);
        defaults.storeToXML(fos, "");
        fos.close();

        Thread.sleep(1000);
        defaults.setProperty("age", "2 ^ 4");
        fos = new FileOutputStream(filename);
        defaults.storeToXML(fos, "");
        fos.close();

        Thread.sleep(1000);
    }
}
