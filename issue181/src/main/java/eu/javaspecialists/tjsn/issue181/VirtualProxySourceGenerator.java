package eu.javaspecialists.tjsn.issue181;

import java.io.*;
import java.lang.reflect.*;

public abstract class VirtualProxySourceGenerator {
    protected final Class subject;
    protected final Class realSubject;
    private final String proxy;
    private CharSequence charSequence;

    public VirtualProxySourceGenerator(
            Class subject, Class realSubject, Concurrency type) {
        this.subject = subject;
        this.realSubject = realSubject;
        this.proxy = makeProxyName(subject, type);
    }

    private static String makeProxyName(Class subject,
                                        Concurrency type) {
        return "$$_" + subject.getName().replace('.', '_') +
                "Proxy_" + Integer.toHexString(System.identityHashCode(
                subject.getClassLoader())) + "_" + type;
    }

    public String getProxyName() {
        return proxy;
    }

    public CharSequence getCharSequence() {
        if (charSequence == null) {
            StringWriter sw = new StringWriter();
            generateProxyClass(new PrintWriter(sw));
            charSequence = sw.getBuffer();
        }
        return charSequence;
    }

    private void generateProxyClass(PrintWriter out) {
        addClassDefinition(out);
        addProxyBody(out);
        out.close();
    }

    private void addProxyBody(PrintWriter out) {
        addRealSubjectCreation(out, subject.getName(),
                realSubject.getName());
        addProxiedMethods(out);
        out.println("}");
    }

    protected abstract void addRealSubjectCreation(
            PrintWriter out, String name, String realName);

    private void addClassDefinition(PrintWriter out) {
        addImports(out);
        out.printf("public class %s %s %s {%n",
                proxy, getInheritanceType(subject), subject.getName());
    }

    private String getInheritanceType(Class subject) {
        return subject.isInterface() ? "implements" : "extends";
    }

    protected void addImports(PrintWriter out) {
    }

    private void addToStringIfInterface(PrintWriter out) {
        if (subject.isInterface()) {
            out.println();
            out.println("  public String toString() {");
            out.println("    return realSubject().toString();");
            out.println("  }");
        }
    }

    private void addProxiedMethods(PrintWriter out) {
        for (Method m : subject.getMethods()) {
            addProxiedMethod(out, m);
        }
        addToStringIfInterface(out);
    }

    private void addProxiedMethod(PrintWriter out, Method m) {
        if (Modifier.isFinal(m.getModifiers())) return;
        addMethodSignature(out, m);
        addMethodBody(out, m);
        out.printf(");%n  }%n");
    }

    private void addMethodSignature(PrintWriter out, Method m) {
        out.printf("%n  public %s", Util.prettyPrint(m.getReturnType()));
        out.printf(" %s(", m.getName());
        addParameterList(out, m);
        out.printf(") {%n    ");
    }

    private void addParameterList(PrintWriter out, Method m) {
        Class<?>[] types = m.getParameterTypes();
        for (int i = 0; i < types.length; i++) {
            String next = i == types.length - 1 ? "" : ", ";
            out.printf("%s p%d%s", Util.prettyPrint(types[i]), i, next);
        }
    }

    private void addMethodBody(PrintWriter out, Method m) {
        addReturnKeyword(out, m);
        addMethodBodyDelegatingToRealSubject(out, m);
    }

    private void addReturnKeyword(PrintWriter out, Method m) {
        if (m.getReturnType() != void.class) {
            out.print("return ");
        }
    }

    private void addMethodBodyDelegatingToRealSubject(
            PrintWriter out, Method m) {
        out.printf("realSubject().%s(", m.getName());
        addMethodCall(out, m);
    }

    private void addMethodCall(PrintWriter out, Method m) {
        Class<?>[] types = m.getParameterTypes();
        for (int i = 0; i < types.length; i++) {
            String next = i == types.length - 1 ? "" : ", ";
            out.printf("p%d%s", i, next);
        }
    }
}
