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

package eu.javaspecialists.tjsn.issue194;

import java.util.concurrent.*;

public class Thinker implements Callable<String> {
    private final int id;
    private final Krasi left, right;

    public Thinker(int id, Krasi left, Krasi right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    public String call() throws Exception {
        for (int i = 0; i < 1000; i++) {
            drink();
            think();
        }
        return "Java is fun";
    }

    public void drink() throws InterruptedException {
        while (true) {
            if (Thread.interrupted()) throw new InterruptedException();
            if (MonitorUtils.trySynchronize(left)) {
                try {
                    if (MonitorUtils.trySynchronize(right)) {
                        try {
                            System.out.printf("(%d) Drinking%n", id);
                            return;
                        } finally {
                            MonitorUtils.unsynchronize(right);
                        }
                    }
                } finally {
                    MonitorUtils.unsynchronize(left);
                }
            }
        }
    }
    // public void drink() {
    //     synchronized (left) {
    //         synchronized (right) {
    //             System.out.printf("(%d) Drinking%n", id);
    //         }
    //     }
    // }

    public void think() {
        System.out.printf("(%d) Thinking%n", id);
    }
}
