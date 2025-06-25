/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.javaspecialists.tjsn.issue234;

import java.util.concurrent.*;

public class PXMLTag {
    public final String m_type;
    private static String[] m_primitiveTypes = {
            "java.lang.String",
            "boolean",
            "int",
            "long",
            "float",
            "short"};

    private static String[] m_allTypes = {
            "java.lang.String",
            "boolean",
            "int",
            "long",
            "float",
            "java.util.Vector",
            "java.util.Map",
            "short"};

    public PXMLTag(String type) {
        this.m_type = type;
    }

    public boolean isPrimitiveType() {
        for (int i = 0; i < m_primitiveTypes.length; i++) {
            String type = m_type;
            String primitiveType = m_primitiveTypes[i];
            if (type.compareTo(primitiveType) == 0
                    && notReallyPrimitiveType(type)) {
                System.out.println("This odd case");

                System.out.println("type '" + type + "'");
                System.out.println("currentPrimitiveType '" +
                        primitiveType + "'");
                System.out.println("They are equal? " +
                        type.equals(primitiveType));
                System.out.println("They are compared " +
                        type.compareTo(primitiveType));

                System.out.println("m_type '" + m_type + "'");
                System.out.println("m_primitiveType[i] '" +
                        m_primitiveTypes[i] + "'");
                System.out.println("They are equal? " +
                        m_type.equals(m_primitiveTypes[i]));
                System.out.println("They are compared " +
                        m_type.compareTo(m_primitiveTypes[i]));
                return true;
            }
        }
        return false;
    }

    public static boolean notReallyPrimitiveType(String m_type) {
        return m_type.contains("Vector") || m_type.contains("Map");
    }

    public static String getRandomType() {
        return m_allTypes[
                ThreadLocalRandom.current().nextInt(m_allTypes.length)];
    }

    public static void main(String... args) {
        int threads = 1;
        if (args.length == 1) {
            threads = Integer.parseInt(args[0]);
        }
        for (int i = 0; i < threads; i++) {
            Thread t = createThread();
            t.start();
        }
    }

    private static Thread createThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    PXMLTag tag = new PXMLTag(getRandomType());
                    if (tag.isPrimitiveType()
                            && notReallyPrimitiveType(tag.m_type)) {
                        System.out.println(tag.m_type +
                                " not really primitive!");
                        System.exit(1);
                    }
                    try {
                        Thread.sleep(
                                ThreadLocalRandom.current().nextInt(100));
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });
    }
}
