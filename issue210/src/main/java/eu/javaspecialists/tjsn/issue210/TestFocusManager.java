package eu.javaspecialists.tjsn.issue210;

import javax.swing.*;
import java.awt.*;

public class TestFocusManager {
    public final String greeting;

    public TestFocusManager(String greeting) {
        this.greeting = greeting;
    }

    public void test() {
        DefaultFocusManager dfm = new DefaultFocusManager() {
            public void setDefaultFocusTraversalPolicy(
                    FocusTraversalPolicy defaultPolicy) {
                if (greeting != null) {
                    System.out.println(greeting);
                }
                super.setDefaultFocusTraversalPolicy(defaultPolicy);
            }
        };
    }

    public static void main(String[]  args) {
        TestFocusManager tfm = new TestFocusManager("hello there");
        tfm.test();
    }
}
