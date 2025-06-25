package eu.javaspecialists.tjsn.issue181;

import java.io.*;

class VirtualProxySourceGeneratorSomeDuplicates
        extends VirtualProxySourceGenerator {

    public VirtualProxySourceGeneratorSomeDuplicates(
            Class subject, Class realSubject) {
        super(subject, realSubject, Concurrency.SOME_DUPLICATES);
    }

    protected void addImports(PrintWriter out) {
        out.println("import java.util.concurrent.atomic.*;");
        out.println();
    }

    protected void addRealSubjectCreation(PrintWriter out,
                                          String name,
                                          String realName) {
        out.printf("  private final AtomicReference<%s> " +
                "ref = new AtomicReference<%1$s>();%n", name);
        out.println();
        out.printf("  private %s realSubject() {%n", name);
        out.printf("    %s result = ref.get()%n;", name);
        out.printf("    if (result == null) {%n");
        out.printf("      result = new %s();%n", realName);
        out.printf("      if (!ref.compareAndSet(null, result)) {%n");
        out.printf("        result = ref.get();%n");
        out.println("      }");
        out.println("    }");
        out.println("    return result;");
        out.println("  }");
    }
}
