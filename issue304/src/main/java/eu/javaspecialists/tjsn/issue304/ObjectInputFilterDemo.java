package eu.javaspecialists.tjsn.issue304;

import java.io.*;
import java.util.*;

public class ObjectInputFilterDemo {
    public static void main(String... args) throws Exception {
        Company company = new Company();
        company.addEmployee("Chris");
        company.addEmployee("Tony");
        company.addEmployee("John");
        company.addEmployee("Parag");

        byte[] bytes = SerializationHelper.serialize(company);

        // With no filter
        test(bytes, null); // allowed
        // With our custom filter
        var filter1 = new NoLinkedListFilter();
        test(bytes, filter1); // rejected
        // With a generated filter rejecting LinkedList
        var filter2 = ObjectInputFilter.rejectFilter(
                clazz -> clazz == LinkedList.class,
                ObjectInputFilter.Status.UNDECIDED);
        test(bytes, filter2);  // rejected
        // With a generated filter allowing ArrayList
        var filter3 = ObjectInputFilter.allowFilter(
                clazz -> clazz == ArrayList.class,
                ObjectInputFilter.Status.UNDECIDED);
        test(bytes, filter3); // allowed
        // Merging filter1 and filter3
        var filter4 = ObjectInputFilter.merge(filter1, filter3);
        test(bytes, filter4); // rejected
        // This would reject even the Company class
        var filter5 = ObjectInputFilter.rejectUndecidedClass(filter3);
        test(bytes, filter5); // rejected
        // Create a filter from a text pattern that rejects LinkedList
        var filter6 = ObjectInputFilter.Config.createFilter(
                "!java.util.LinkedList");
        test(bytes, filter6); // rejected
        // Create a filter from a text pattern that accepts Company,
        // LinkedList and rejects all other classes
        var filter7 = ObjectInputFilter.Config.createFilter(
                "Company;java.util.LinkedList;!*");
        test(bytes, filter7); // allowed

        ObjectInputFilter.Config.setSerialFilter(filter1);
        test(bytes, null); // rejected
    }

    private static void test(byte[] bytes, ObjectInputFilter filter)
            throws IOException, ClassNotFoundException {
        var result = SerializationHelper.deserialize(filter, bytes);
        System.out.println(result); // rejected
    }
}
