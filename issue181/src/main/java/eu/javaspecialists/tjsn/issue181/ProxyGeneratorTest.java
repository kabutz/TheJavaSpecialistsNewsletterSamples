package eu.javaspecialists.tjsn.issue181;

import javax.swing.*;
import javax.swing.table.*;
import java.util.concurrent.*;

public class ProxyGeneratorTest {
    public static void main(String... args) {
        for (Concurrency type : Concurrency.values()) {
            test(type);
        }
    }

    private static void test(Concurrency concurrency) {
        System.out.println();
        System.out.println("Concurrency: " + concurrency);
        MoralFibre mf = ProxyGenerator.make(MoralFibre.class,
                MoralFibreImpl.class, concurrency);
        System.out.println("Moral Fibre: " + mf.getClass());
        System.out.println(mf);
        mf.actSociallyResponsibly();

        System.out.println();

        ConcurrentMap<String, Integer> map =
                ProxyGenerator.make(ConcurrentMap.class,
                        ConcurrentHashMap.class, concurrency);

        System.out.println(map.getClass());
        map.put("Hello", 23);
        map.put("Hello2", 24);
        map.put("Hello3", 25);
        System.out.println(map);

        System.out.println();

        AbstractTableModel model =
                ProxyGenerator.make(AbstractTableModel.class,
                        DefaultTableModel.class, concurrency);
        System.out.println(model.getClass());
        JTable table = new JTable(model);

        System.out.println();
    }
}