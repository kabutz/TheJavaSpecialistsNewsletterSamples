package eu.javaspecialists.tjsn.issue126;

import java.lang.reflect.*;

public class ProxyEquality {
    public static void main(String[] args) {
        DelegationHandler dh = new DelegationHandler(
                new ForestImpl("Black"));
        Forest i0 = make(dh);
        Forest i1 = make(dh);
        System.out.println(i0 == i1); // should be false
        System.out.println(i0.equals(i1)); // should be true
    }

    private static Forest make(DelegationHandler dh) {
        return (Forest) Proxy.newProxyInstance(
                Forest.class.getClassLoader(),
                new Class[]{Forest.class}, dh);
    }
}
