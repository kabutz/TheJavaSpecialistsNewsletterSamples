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

package eu.javaspecialists.tjsn.issue056;

public class NestedLoops extends Thread {
    private static boolean correct = true;

    public void run() {
        while (true) {
            System.out.print(".");
            System.out.flush();
            for (int i = 0; i < 10; i++) {
                System.out.print("#");
                System.out.flush();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    if (correct) Thread.currentThread().interrupt();
                    System.out.println();
                    System.out.println("Shut down inner loop");
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                if (correct) Thread.currentThread().interrupt();
                System.out.println();
                System.out.println("Shut down outer loop");
                break;
            }
        }
        System.out.println("Shutting down thread");
    }

    private static void test() throws InterruptedException {
        Thread t = new NestedLoops();
        t.start();
        Thread.sleep(6500);
        t.interrupt();
        t.join();
        System.out.println("Shutdown the thread correctly");
    }

    public static void main(String[] args)
            throws InterruptedException {
        test();
        correct = false;
        test();
    }
}
