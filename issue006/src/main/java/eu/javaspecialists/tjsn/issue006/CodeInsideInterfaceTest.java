package eu.javaspecialists.tjsn.issue006;

/**
 * This test class demonstrates how to call the method on the interface.
 */
public class CodeInsideInterfaceTest implements CodeInsideInterface {
    public static void main(String[] args) {
        CodeInsideInterfaceTest test = new CodeInsideInterfaceTest();
        test.calculateIQ.run(new Result() {
            public void result(Object answer) {
                System.out.println("Your IQ is " + answer);
            }

            public void exception(Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}