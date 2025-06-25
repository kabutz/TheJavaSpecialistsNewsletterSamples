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
package eu.javaspecialists.tjsn.issue017.take3;

public class TransactionType extends Throwable {
    private final String name;

    private TransactionType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public static class None extends TransactionType {
        public static final TransactionType type = new None();

        private None() {
            super("None");
        }
    }

    public static class ReadUncommitted extends TransactionType {
        public static final TransactionType type =
                new ReadUncommitted();

        private ReadUncommitted() {
            super("ReadUncommitted");
        }
    }

    public static class ReadCommitted extends TransactionType {
        public static final TransactionType type =
                new ReadCommitted();

        private ReadCommitted() {
            super("ReadCommitted");
        }
    }

    public static class RepeatableRead extends TransactionType {
        public static final TransactionType type =
                new RepeatableRead();

        private RepeatableRead() {
            super("RepeatableRead");
        }
    }

    public static class Serializable extends TransactionType {
        public static final TransactionType type =
                new Serializable();

        private Serializable() {
            super("Serializable");
        }
    }
}
