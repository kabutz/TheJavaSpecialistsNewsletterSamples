package eu.javaspecialists.tjsn.issue071;

public class OverloadingTest {
    public abstract static class Top {
        public String f(Object o) {
            String whoAmI = "Top.f(Object)";
            System.out.println(whoAmI);
            return whoAmI;
        }
    }

    public static class Sub extends Top {
        public String f(String s) {
            String whoAmI = "Middle.f(String)";
            System.out.println(whoAmI);
            return whoAmI;
        }
    }

    public static void main(String[] args) {
        Sub sub = new Sub();
        Top top = sub;

        String stringAsString = "someString";
        Object stringAsObject = stringAsString;

        if (top.f(stringAsObject) == sub.f(stringAsString))
        //if (top.f(stringAsObject) == sub.f(stringAsObject))
        //if (top.f(stringAsString) == sub.f(stringAsString))
        //if (top.f(stringAsString) == sub.f(stringAsObject))
        //if (sub.f(stringAsString) == sub.f(stringAsObject))
        //if (top.f(stringAsString) == top.f(stringAsObject))
        {
            System.out.println("Hey, life is great!");
        } else {
            System.out.println("Oh no!");
        }
    }
}
