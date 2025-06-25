package eu.javaspecialists.tjsn.issue034.ex2_decorator;

class ConcreteDecorator extends Decorator {
    ConcreteDecorator(Component component) {
        super(component);
    }

    public void anotherOperation() {
    /* decorate the other operation in some way, then call the
     other operation() */
        operation();
    }
}
