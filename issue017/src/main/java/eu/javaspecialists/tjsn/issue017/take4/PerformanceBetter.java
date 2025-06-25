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
package eu.javaspecialists.tjsn.issue017.take4;

import java.util.*;

import static eu.javaspecialists.tjsn.issue017.take4.TransactionType.*;

public class PerformanceBetter {
    private static final TransactionType[] types =
            new TransactionType[16 * 1024];
    private static final int MASK = types.length - 1;
    protected static final int COUNT = 100 * 1000 * 1000;
    private static long countNone, countReadUncommitted, countReadCommitted, countRepeatableRead, countSerializable;

    static {
        Random random = new Random(0);
        for (int i = 0; i < types.length; i++) {
            switch (random.nextInt(5)) {
                case NONE:
                    types[i] = None.type;
                    break;
                case READ_UNCOMMITTED:
                    types[i] = ReadUncommitted.type;
                    break;
                case READ_COMMITTED:
                    types[i] = ReadCommitted.type;
                    break;
                case REPEATABLE_READ:
                    types[i] = RepeatableRead.type;
                    break;
                case SERIALIZABLE:
                    types[i] = Serializable.type;
                    break;
            }
        }
    }

    public static void switchOnObject(TransactionType transact) {
        try {
            throw transact;
        } catch (None type) {
            countNone++;
        } catch (ReadUncommitted type) {
            countReadUncommitted++;
        } catch (ReadCommitted type) {
            countReadCommitted++;
        } catch (RepeatableRead type) {
            countRepeatableRead++;
        } catch (TransactionType type) {
            countSerializable++;
        }
    }

    public static void switchOnInt(TransactionType transact) {
        switch (transact.getId()) {
            case NONE:
                countNone++;
                break;
            case READ_UNCOMMITTED:
                countReadUncommitted++;
                break;
            case READ_COMMITTED:
                countReadCommitted++;
                break;
            case REPEATABLE_READ:
                countRepeatableRead++;
                break;
            default:
                countSerializable++;
                break;
        }
    }

    public static void switchInstanceof(TransactionType transact) {
        if (transact instanceof None) {
            countNone++;
        } else if (transact instanceof ReadUncommitted) {
            countReadUncommitted++;
        } else if (transact instanceof ReadCommitted) {
            countReadCommitted++;
        } else if (transact instanceof RepeatableRead) {
            countRepeatableRead++;
        } else {
            countSerializable++;
        }
    }

    public abstract static class TransactionTypeStrategy {
        public abstract void process();
    }

    public static class NoneStrategy extends
            TransactionTypeStrategy {
        public void process() {
            countNone++;
        }
    }

    public static class ReadUncommittedStrategy extends
            TransactionTypeStrategy {
        public void process() {
            countReadUncommitted++;
        }
    }

    public static class ReadCommittedStrategy extends
            TransactionTypeStrategy {
        public void process() {
            countReadCommitted++;
        }
    }

    public static class RepeatableReadStrategy extends
            TransactionTypeStrategy {
        public void process() {
            countRepeatableRead++;
        }
    }

    public static class DefaultStrategy extends
            TransactionTypeStrategy {
        public void process() {
            countSerializable++;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test();
            System.out.println();
        }
    }

    private static void test() {
        resetCounts();
        long time = -System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            switchOnObject(types[i & MASK]);
        }
        time += System.currentTimeMillis();
        checkCounts();
        System.out.println("Switching " + COUNT + " times on objects " +
                time + "ms");

        resetCounts();
        time = -System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            switchOnInt(types[i & MASK]);
        }
        time += System.currentTimeMillis();
        checkCounts();
        System.out.println("Switching " + COUNT + " times on ints " +
                time + "ms");

        resetCounts();
        time = -System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            switchInstanceof(types[i & MASK]);
        }
        time += System.currentTimeMillis();
        checkCounts();
        System.out.println("Switching " + COUNT + " times using " +
                "instanceofs " + time + "ms");

        TransactionTypeStrategy[] strategies = new TransactionTypeStrategy[types.length];
        for (int i = 0; i < strategies.length; i++) {
            switch (types[i].getId()) {
                case NONE:
                    strategies[i] = new NoneStrategy();
                    break;
                case READ_UNCOMMITTED:
                    strategies[i] = new ReadUncommittedStrategy();
                    break;
                case READ_COMMITTED:
                    strategies[i] = new ReadCommittedStrategy();
                    break;
                case REPEATABLE_READ:
                    strategies[i] = new RepeatableReadStrategy();
                    break;
                case SERIALIZABLE:
                    strategies[i] = new DefaultStrategy();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown strategy id " + types[i].getId());
            }
        }
        resetCounts();
        time = -System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            strategies[i & MASK].process();
        }
        checkCounts();
        time += System.currentTimeMillis();
        System.out.println("Switching " + COUNT + " times using " +
                "Strategy Polymorphism pattern " + time + "ms");
    }

    private static void resetCounts() {
        countNone = countReadUncommitted = countReadCommitted = countRepeatableRead = countSerializable = 0;
    }

    private static void checkCounts() {
        if (countNone + countReadUncommitted + countReadCommitted + countRepeatableRead + countSerializable != COUNT) {
            throw new AssertionError();
        }
    }
}