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

package eu.javaspecialists.tjsn.issue241;

import java.util.*;

public class MyArrayList {
    private final Object READ_LOCK = new Object();
    private final Object WRITE_LOCK = new Object();
    private int[] arr = new int[10];
    private int size = 0;

    public int size() {
        synchronized (READ_LOCK) {
            return size;
        }
    }

    public int get(int index) {
        synchronized (READ_LOCK) {
            rangeCheck(index);
            return arr[index];
        }
    }

    public boolean add(int e) {
        synchronized (WRITE_LOCK) {
            if (size + 1 > arr.length)
                arr = Arrays.copyOf(arr, size + 10);

            arr[size++] = e;
            return true;
        }
    }

    public int remove(int index) {
        synchronized (WRITE_LOCK) {
            rangeCheck(index);

            int oldValue = arr[index];

            int numMoved = size - index - 1;
            if (numMoved > 0)
                System.arraycopy(arr, index + 1,
                        arr, index, numMoved);
            arr[--size] = 0;

            return oldValue;
        }
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
    }

    public static void main(String... args) {
        for (int i = 0; i < 100000; i++) {
            MyArrayList list = new MyArrayList();
            new Thread(new Main(list, true)).start();
            new Thread(new Main(list, false)).start();
            new Thread(new Main(list, false)).start();
        }
    }

    static class Main implements Runnable {
        MyArrayList list;
        boolean update;

        public Main(MyArrayList list,
                    boolean update) {
            this.list = list;
            this.update = update;
        }

        @Override
        public void run() {
            if (update) {
                for (int i = 1; i < 1000; i++) {
                    list.add(i);
                }
                for (int i = 1; i < 250; i++) {
                    list.remove(7);
                }
            } else {
                // wait until we're certain
                // index 6 has a value
                while (list.size() < 7) {}
                for (int i = 1; i < 1000; i++) {
                    int x;
                    if ((x = list.get(6)) != 7) {
                        System.out.println(x +
                                " and " + list.size());
                    }
                }
            }
        }
    }
}
