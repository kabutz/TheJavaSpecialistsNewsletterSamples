package eu.javaspecialists.tjsn.issue014;

public class CachingHashcode {
    public static void main(String[] args) {
        java.util.Map map = new java.util.HashMap();
        map.put("hi there", "You found the value");
        new Warper();
        System.out.println(map.get("hi there"));
        System.out.println(map);
    }

    private static final String greeting = "hi there";
}