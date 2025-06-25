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