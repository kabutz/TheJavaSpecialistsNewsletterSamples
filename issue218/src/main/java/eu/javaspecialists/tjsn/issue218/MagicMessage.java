package eu.javaspecialists.tjsn.issue218;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

import static javax.swing.WindowConstants.*;

public class MagicMessage {
    private static final ThreadLocalRandom tlr =
            ThreadLocalRandom.current();

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                JLabel label = new JLabel(generateRandomString(),
                        SwingConstants.CENTER);
                frame.add(label, BorderLayout.NORTH);
                frame.setSize(300, 100);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }

    private static String generateRandomString() {
        char[] randomText = "HVTia\u000EDlciP".toCharArray();
        for (int i = 0; i < randomText.length; i++) {
            randomText[i] += tlr.nextInt(26);
        }
        return new String(randomText);
    }
}
