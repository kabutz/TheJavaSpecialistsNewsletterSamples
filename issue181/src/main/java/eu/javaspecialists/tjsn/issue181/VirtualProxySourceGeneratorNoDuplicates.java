package eu.javaspecialists.tjsn.issue181;

import java.io.*;

class VirtualProxySourceGeneratorNoDuplicates
        extends VirtualProxySourceGenerator {

    public VirtualProxySourceGeneratorNoDuplicates(
            Class subject, Class realSubject) {
        super(subject, realSubject, Concurrency.NO_DUPLICATES);
    }

    protected void addImports(PrintWriter out) {
        out.println("import java.util.concurrent.locks.*;");
        out.println();
    }

    protected void addRealSubjectCreation(PrintWriter out,
                                          String name,
                                          String realName) {
        out.printf("  private volatile %s realSubject;%n", name);
        out.println("  private final Lock initializationLock = " +
                "new ReentrantLock();");
        out.println();
        out.printf("  private %s realSubject() {%n", name);
        out.printf("    %s result = realSubject;%n", name);
        out.printf("    if (result == null) {%n");
        out.printf("      initializationLock.lock();%n");
        out.printf("      try {%n");
        out.printf("        result = realSubject;%n");
        out.printf("        if (result == null) {%n");
        out.printf("          result = realSubject = new %s();%n",
                realName);
        out.printf("        }%n");
        out.printf("      } finally {%n");
        out.printf("        initializationLock.unlock();%n");
        out.printf("      }%n");
        out.printf("    }%n");
        out.printf("    return result;%n");
        out.println("  }");
    }
}
