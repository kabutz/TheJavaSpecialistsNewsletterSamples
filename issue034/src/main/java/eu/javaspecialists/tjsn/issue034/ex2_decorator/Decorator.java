package eu.javaspecialists.tjsn.issue034.ex2_decorator;

import eu.javaspecialists.tjsn.issue034.ex1_proxy.*;

class Decorator implements Component {
    private Component component;

    Decorator(Component component) {
        this.component = component;
    }

    public void operation() {
        /* do something, then */
        component.operation();
    }
}

