package eu.javaspecialists.tjsn.issue323;

public class PrintingThreadableVisitor implements ThreadableVisitor {
    private int indentLevel = 0;

    public void visit(ThreadableComposite composite) {
        printIndent();
        System.out.println(composite + ": {");
        indentLevel++;
        try {
            ThreadableVisitor.super.visit(composite);
        } finally {
            indentLevel--;
        }
        printIndent();
        System.out.println("}");
    }

    public void visitVirtualThread(Thread thread) {
        printIndent();
        System.out.println("- Virtual thread: " + thread);
    }

    public void visitPlatformThread(Thread thread) {
        printIndent();
        System.out.println("- Platform thread: " + thread);
    }

    private void printIndent() {
        System.out.print(" ".repeat(indentLevel * 4));
    }
}
