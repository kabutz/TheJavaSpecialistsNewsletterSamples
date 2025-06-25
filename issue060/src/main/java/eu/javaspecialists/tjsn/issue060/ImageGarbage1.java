/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
