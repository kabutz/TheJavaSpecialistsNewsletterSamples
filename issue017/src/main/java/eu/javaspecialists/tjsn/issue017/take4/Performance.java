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


import eu.javaspecialists.tjsn.issue017.take4.TransactionType.*;

import static eu.javaspecialists.tjsn.issue017.take4.TransactionType.*;

public class Performance {
    public static void switchOnObject(TransactionType transact) {
        try {
            throw transact;
        } catch (TransactionType.None type) {
        } catch (ReadUncommitted type) {
        } catch (ReadCommitted type) {
        } catch (RepeatableRead type) {
        } catch (TransactionType type) {
        }
    }

    public static void switchOnInt(TransactionType transact) {
        switch (transact.getId()) {
            case NONE:
                break;
            case READ_UNCOMMITTED:
                break;
            case READ_COMMITTED:
                break;
            case REPEATABLE_READ:
                break;
            case SERIALIZABLE:
                break;
            default:
                break;
        }
    }

    public static void switchInstanceof(TransactionType transact) {
        if (transact instanceof None) {
        } else if (transact instanceof ReadUncommitted) {
        } else if (transact instanceof ReadCommitted) {
        } else if (transact instanceof RepeatableRead) {
        }
    }

    public abstract static class TransactionTypeStrategy {
        public abstract void doSomething();
    }

    public static class RepeatableReadStrategy extends
            TransactionTypeStrategy {
        public void doSomething() {
        }
    }

    public static void main(String[] args) {
        long time = -System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            switchOnObject(RepeatableRead.type);
        }
        time += System.currentTimeMillis();
        System.out.println("Switching 1000000 times on objects " +
                time + "ms");

        time = -System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            switchOnInt(RepeatableRead.type);
        }
        time += System.currentTimeMillis();
        System.out.println("Switching 1000000 times on ints " +
                time + "ms");

        time = -System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            switchInstanceof(RepeatableRead.type);
        }
        time += System.currentTimeMillis();
        System.out.println("Switching 1000000 times using " +
                "instanceofs " + time + "ms");

        time = -System.currentTimeMillis();
        TransactionTypeStrategy strategy =
                new RepeatableReadStrategy();
        for (int i = 0; i < 1000000; i++) {
            strategy.doSomething();
        }
        time += System.currentTimeMillis();
        System.out.println("Switching 1000000 times using " +
                "Strategy Polymorphism pattern " + time + "ms");
    }
}
