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

package eu.javaspecialists.tjsn.issue221;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

/**
 * To avoid a bottleneck at the head and tail of the
 * ConcurrentLinkedQueue, we create an array of queues.
 * Since the queues are created at startup based on the
 * desired concurrencyLevel, we do not need any additional
 * synchronization to protect the elements of the array.
 * The number of queues is always a power of 2, so that we
 * can use bit masking instead of remainder operations to
 * find our queue.
 */
public class TransformerObjectPoolArray<T> extends
        Transformer<T> {
    private static final int MAXIMUM_CAPACITY = 1 << 16;
    private final Queue<T>[] objects;
    private final Supplier<T> supplier;
    private final int mask;

    public TransformerObjectPoolArray(Supplier<T> supplier) {
        this(supplier, 16);
    }

    @SuppressWarnings("unchecked")
    public TransformerObjectPoolArray(Supplier<T> supplier,
                                      int concurrencyLevel) {
        this.supplier = supplier;
        objects = new Queue[sizeFor(concurrencyLevel)];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = new ConcurrentLinkedQueue<>();
        }
        mask = objects.length - 1;
    }

    private static int sizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ?
                MAXIMUM_CAPACITY : n + 1;
    }

    protected T takeTransformer() {
        Queue<T> q = getMyQueue();
        T t = q.poll();
        if (t == null) {
            t = supplier.get();
        }
        return t;
    }

    private Queue<T> getMyQueue() {
        long id = Thread.currentThread().getId();
        return objects[((int) (id & mask))];
    }

    protected void putTransformer(T t) {
        getMyQueue().add(t);
    }
}