package eu.javaspecialists.tjsn.issue163;


public class NutitionFactsDemo {
    public static void main(String... args) {
        NutritionFacts sodaDrink = new NutritionFacts(240, 8, 100, 0, 35, 27);
        NutritionFacts2 sodaDrink2 = new NutritionFacts2.Builder(240, 8)
                .calories(100).sodium(35).carbohydrate(27).build();
    }
}