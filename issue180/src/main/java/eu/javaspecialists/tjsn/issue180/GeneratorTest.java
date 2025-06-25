package eu.javaspecialists.tjsn.issue180;

public class GeneratorTest {
    public static void main(String... args) throws Exception {
        Class testClass = Generator.make(
                null, "WatchThis",
                "" +
                        "package coolthings;\n" +
                        "\n" +
                        "public class WatchThis implements Runnable {\n" +
                        "  public WatchThis() {\n" +
                        "    System.out.println(\"Hey this works!\");\n" +
                        "  }\n" +
                        "\n" +
                        "  public void run() {\n" +
                        "    System.out.println(Thread.currentThread());\n" +
                        "    while(Math.random() < 0.95) {\n" +
                        "      System.out.println(\"Cool stuff!\");\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n"
        );
        Runnable r = (Runnable) testClass.newInstance();
        Class<? extends Runnable> clazz = r.getClass();
        System.out.println("Our class: " + clazz.getName());
        System.out.println("Classloader: " + clazz.getClassLoader());
        Thread t = new Thread(r, "Cool Thread");
        t.start();
    }
}
