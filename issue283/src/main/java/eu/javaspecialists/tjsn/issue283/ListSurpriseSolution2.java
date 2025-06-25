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

package eu.javaspecialists.tjsn.issue283;

import java.util.*;
import java.util.concurrent.*;

public class ListSurpriseSolution2 extends ListSurprise {
    private static void doSomething(List<Integer> list) {
        // Set item 5 to 9; block main thread as we remove last item
        list.set(5, 9);
        Phaser phaser = new Phaser(2);
        Thread main = Thread.currentThread();
        new Thread(() -> {
            synchronized (System.out) {
                phaser.arriveAndDeregister();
                while (main.getState() != Thread.State.BLOCKED)
                    Thread.onSpinWait();
                list.remove(6);
            }
        }).start();
        phaser.arriveAndAwaitAdvance();
    }
}
