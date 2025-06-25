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