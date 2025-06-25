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

package eu.javaspecialists.tjsn.issue109;

public class PersonWithStrategy
        implements ValueBasedEqualityStrategy.ValueSupplier {
    private final String name;
    private final String address;
    private final int age;
    private final EqualityStrategy strategy;

    public PersonWithStrategy(String name, String address,
                              int age, EqualityStrategy strategy) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.strategy = strategy;
        this.strategy.setOwner(this);
    }

    /**
     * This is an example of a constructor that uses a
     * NullEqualityStrategy as default.
     */
    public PersonWithStrategy(String name, String address, int age) {
        this(name, address, age, new NullEqualityStrategy());
    }

    public int hashCode() {
        return strategy.hashCode();
    }

    public boolean equals(Object obj) {
        return strategy.equals(obj);
    }

    public Object[] getValues() {
        return new Object[]{name, address, age};
    }
}
