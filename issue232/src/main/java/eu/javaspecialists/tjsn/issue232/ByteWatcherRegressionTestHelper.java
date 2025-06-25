package eu.javaspecialists.tjsn.issue232;
/*
 * Copyright (C) 2015 Daniel Shaya and Heinz Max Kabutz
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.  Heinz Max Kabutz licenses
 * this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.junit.Assert.*;

public class ByteWatcherRegressionTestHelper {
    private final ByteWatcherSingleThread bw;

    public ByteWatcherRegressionTestHelper(Thread thread) {
        bw = new ByteWatcherSingleThread(thread);
    }

    public ByteWatcherRegressionTestHelper() {
        this(Thread.currentThread());
    }

    public void testAllocationNotExceeded(
            Runnable job, long limit) {
        bw.reset();
        job.run();
        long size = bw.calculateAllocations();
        assertTrue(String.format("exceeded limit: %d using: %d%n",
                limit, size), size <= limit);
    }
}