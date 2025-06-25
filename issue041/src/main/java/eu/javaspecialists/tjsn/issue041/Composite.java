package eu.javaspecialists.tjsn.issue041;

public class Composite {
    private java.util.List children = new java.util.LinkedList();

    public void add(Component c) {
        children.add(c);
    }

    public void remove(Component c) {
        children.remove(c);
    }

    public void operation() {
        // or my cool VisitingIterator from last week ;-)
        java.util.Iterator it = children.iterator();
        while (it.hasNext()) {
            ((Component) it.next()).operation();
        }
    }
}