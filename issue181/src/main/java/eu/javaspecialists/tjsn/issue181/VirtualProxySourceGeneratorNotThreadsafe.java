package eu.javaspecialists.tjsn.issue181;

import java.io.*;

class VirtualProxySourceGeneratorNotThreadsafe
        extends VirtualProxySourceGenerator {

    public VirtualProxySourceGeneratorNotThreadsafe(
            Class subject, Class realSubject) {
        super(subject, realSubject, Concurrency.NONE);
    }

    protected void addRealSubjectCreation(PrintWriter out,
                                          String name,
                                          String realName) {
        out.printf("  private %s realSubject;%n", name);
        out.println();
        out.printf("  private %s realSubject() {%n", name);
        out.printf("    if (realSubject == null) {%n");
        out.printf("      realSubject = new %s();%n", realName);
        out.println("    }");
        out.println("    return realSubject;");
        out.println("  }");
    }
}
