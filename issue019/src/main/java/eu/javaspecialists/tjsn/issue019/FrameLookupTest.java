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

package eu.javaspecialists.tjsn.issue019;

import javax.swing.*;

public class FrameLookupTest {
    private static final String SWING_FRAME_TITLE = "Swing Frame";
    private static final String AWT_FRAME_TITLE = "AWT Frame";
    private static final String SWING_DIALOG_TITLE = "Swing Dialog";

    public static void main(String[] args) {
        FrameLookup framer = FrameLookup.getInstance();
        makeFrame();

        // we can find visible frame by using our FrameLookup utility
        System.out.println("Frame is " +
                FrameLookup.getInstance().lookupFrame(SWING_FRAME_TITLE));

        makeDialog();
    }

    private static void makeFrame() {
        JFrame frame = new JFrame(SWING_FRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.show();
    }

    private static void makeDialog() {
        // we can bind our JDialog to the Frame
        JDialog dialog = new JDialog(
                FrameLookup.getInstance().lookupFrame(SWING_FRAME_TITLE),
                SWING_DIALOG_TITLE, true);
        dialog.setSize(400, 100);
        dialog.show();
    }
}
