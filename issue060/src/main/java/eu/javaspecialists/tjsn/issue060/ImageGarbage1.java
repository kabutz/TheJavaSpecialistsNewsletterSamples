package eu.javaspecialists.tjsn.issue060;

import java.awt.*;
import java.awt.image.*;

public class ImageGarbage1 {
    private static Image img; // IMPORTANT 1

    public static void main(String args[]) {
        // Get one numerical argument which specifies size of image
        int imageSize = Integer.parseInt(args[0]);
        long startTime = System.currentTimeMillis();
        int imgIndex = 0;
        long loopIndex = 0;
        while (true) {
            //img = null; // IMPORTANT 2
            // Create an image object
            img = new BufferedImage(imageSize, imageSize,
                    BufferedImage.TYPE_INT_RGB);
            long endTime = System.currentTimeMillis();
            ++loopIndex;
            // We print stats for every two seconds
            if (endTime - startTime >= 2000) {
                // Images created and disposed if per second
                long ips = (loopIndex / ((endTime - startTime) / 1000));
                System.out.println("IPS = " + ips);
                startTime = endTime;
                loopIndex = 0;
            }
        }
    }
}
