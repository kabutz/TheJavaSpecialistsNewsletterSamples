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

package eu.javaspecialists.tjsn.issue034.ex5_downcasting_collections;

import java.util.*;

public class Pound {
    private Collection dogs;
    private Collection cats;

    public Pound(Dog[] dogs, Cat[] cats) {
        this.dogs = Arrays.asList(dogs);
        this.cats = Arrays.asList(cats);
    }

    public void makeNoise() {
        Iterator dog_it = dogs.iterator();
        while (dog_it.hasNext()) {
            ((Dog) dog_it.next()).bark(); // we have to downcast!
        }
        Iterator cat_it = cats.iterator();
        while (cat_it.hasNext()) {
            ((Cat) cat_it.next()).meow(); // we have to downcast!
        }
    }
}