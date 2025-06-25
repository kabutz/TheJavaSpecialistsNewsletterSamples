package eu.javaspecialists.tjsn.issue049;

public class BadTest {
    public BadTest(int i) {
    }

    /**
     * @param someone means nothing
     * @return always true
     * @throws bla if something bad happens
     */
    public BadTest() {
    }

    /**
     * @return nothing at all!
     * @return nothing at all!
     * @throws Exception if nothing happens
     * @throws Exception if something happens
     */
    public void method1() throws NullPointerException, Exception {
    }

    private boolean bad;
}
