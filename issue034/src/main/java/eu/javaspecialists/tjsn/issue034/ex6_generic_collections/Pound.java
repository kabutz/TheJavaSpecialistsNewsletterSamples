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

package eu.javaspecialists.tjsn.issue034.ex6_generic_collections;

import java.util.*;

public class Pound {
    private Dog.Collection dogs;
    private Cat.Collection cats;

    public Pound(Dog[] dogs, Cat[] cats) {
        this.dogs = (Dog.Collection) GenericFactory.makeCollection(
                new LinkedList(Arrays.asList(dogs)), Dog.class);
        this.cats = (Cat.Collection) GenericFactory.makeCollection(
                new ArrayList(Arrays.asList(cats)), Cat.class);
    }

    public void makeNoise() {
        Dog.Iterator dog_it = dogs.dogIterator();
        while (dog_it.hasNext()) {
            /* no more downcasting! */
            dog_it.nextDog().bark();
        }
        Cat.Iterator cat_it = cats.catIterator();
        while (cat_it.hasNext()) {
            /* no more downcasting! */
            cat_it.nextCat().meow();
        }
    }
}