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

package eu.javaspecialists.tjsn.issue081;

import javax.swing.*;

public class BetterGui {
    public static void main(String[] args) {
        ThreadGroup exceptionThreadGroup = new ExceptionGroup();
        new Thread(exceptionThreadGroup, "Init thread") {
            public void run() {
                Gui gui = new Gui();
                gui.pack();
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui.show();
            }
        }.start();
    }
}
