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

public class TransactionType extends Throwable {
    public static final int NONE = 0;
    public static final int READ_UNCOMMITTED = 1;
    public static final int READ_COMMITTED = 2;
    public static final int REPEATABLE_READ = 3;
    public static final int SERIALIZABLE = 4;
    private final int id;

    public final int getId() {
        return id;
    }

    private final String name;

    private TransactionType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static class None extends TransactionType {
        public static final TransactionType type = new None();

        private None() {
            super(NONE, "None");
        }
    }

    public static class ReadUncommitted extends TransactionType {
        public static final TransactionType type =
                new ReadUncommitted();

        private ReadUncommitted() {
            super(READ_UNCOMMITTED, "ReadUncommitted");
        }
    }

    public static class ReadCommitted extends TransactionType {
        public static final TransactionType type =
                new ReadCommitted();

        private ReadCommitted() {
            super(READ_COMMITTED, "ReadCommitted");
        }
    }

    public static class RepeatableRead extends TransactionType {
        public static final TransactionType type =
                new RepeatableRead();

        private RepeatableRead() {
            super(REPEATABLE_READ, "RepeatableRead");
        }
    }

    public static class Serializable extends TransactionType {
        public static final TransactionType type =
                new Serializable();

        private Serializable() {
            super(SERIALIZABLE, "Serializable");
        }
    }
}
