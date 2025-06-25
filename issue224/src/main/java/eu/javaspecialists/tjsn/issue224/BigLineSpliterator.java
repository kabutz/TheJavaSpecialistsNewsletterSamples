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

package eu.javaspecialists.tjsn.issue224;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;
import java.util.function.*;

import static java.nio.channels.FileChannel.MapMode.*;

/**
 * @author Dr Heinz M. Kabutz, Maurice Naftalin, based on the
 * LineSpliterator from the Mastering Lambdas book.
 */
public class BigLineSpliterator implements
        Spliterator<DispLine> {
    private static final int AVG_LINE_LENGTH = 40;
    private static final int CHUNK_SIZE = Integer.MAX_VALUE;
    private final ByteBuffer[] bbs;
    private long lo;
    private final long hi;
    private final long offset;

    public BigLineSpliterator(ByteBuffer bb) {
        this(bb, 0, bb.limit());
    }

    public BigLineSpliterator(ByteBuffer bb, int lo, int hi) {
        this(new ByteBuffer[]{bb}, lo, hi, 0);
    }

    public BigLineSpliterator(FileChannel fc) throws IOException {
        this(fc, 0, fc.size());
    }

    public BigLineSpliterator(FileChannel fc, long lo, long hi)
            throws IOException {
        this(split(fc, lo, hi), lo, hi, lo);
    }

    private BigLineSpliterator(ByteBuffer[] bbs,
                               long lo, long hi, long offset) {
        this.bbs = bbs;
        this.lo = lo;
        this.hi = hi;
        this.offset = offset;
    }

    private static ByteBuffer[] split(FileChannel fc,
                                      long lo, long hi)
            throws IOException {
        int numberOfChunks = (int) Math.ceil(
                ((double) (hi - lo)) / CHUNK_SIZE);
        long remainingBytes = (hi - lo);
        ByteBuffer[] bbs = new ByteBuffer[numberOfChunks];
        for (int i = 0; i < bbs.length; i++) {
            long position = i * (long) CHUNK_SIZE + lo;
            long size = i < bbs.length - 1 ?
                    CHUNK_SIZE : remainingBytes;
            remainingBytes -= CHUNK_SIZE;
            bbs[i] = fc.map(READ_ONLY, position, size);
        }

        long totalSize = 0;
        for (ByteBuffer bb : bbs) {
            totalSize += bb.limit();
        }
        if (totalSize != (hi - lo))
            throw new AssertionError("Split size does not match");
        return bbs;
    }

    public boolean tryAdvance(Consumer<? super DispLine> action) {
        long index = lo;
        StringBuilder sb = new StringBuilder();
        char next;
        while ((next = get(index++)) != '\n') {
            sb.append(next);
        }
        action.accept(new DispLine(lo, sb));
        lo = lo + sb.length() + 1;
        return lo < hi;
    }

    private char get(long pos) {
        long truePos = pos - offset;
        int chunk = (int) (truePos / CHUNK_SIZE);
        return (char) bbs[chunk].get((int) (truePos % CHUNK_SIZE));
    }

    private static final int SEQUENTIAL_THRESHOLD = 10000;

    @Override
    public Spliterator<DispLine> trySplit() {
        if (hi - lo < SEQUENTIAL_THRESHOLD) return null;
        long index = (lo + hi) >>> 1;
        while (get(index) != '\n') index++;
        BigLineSpliterator newSpliterator = null;
        if (index != hi) {
            newSpliterator = new BigLineSpliterator(
                    bbs, lo, index, offset);
            lo = index + 1;
        }
        return newSpliterator;
    }

    @Override
    public long estimateSize() {
        return (hi - lo) / AVG_LINE_LENGTH;
    }

    @Override
    public int characteristics() {
        return ORDERED | IMMUTABLE | NONNULL;
    }
}
