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

package eu.javaspecialists.tjsn.issue035;

import com.sun.javadoc.*;

/**
 * This class is used as a Doclet and will find any non-private
 * data members in your classes.
 */
public class CodeChecker {
    /**
     * This is the entry point for the doclet engine into your
     * class.
     */
    public static boolean start(RootDoc root) {
        System.out.println("Non-private data members:");
        checkClasses(root.classes());
        return true;
    }

    /**
     * We will call the checkClasses() method recursively so that
     * we can also check the inner classes (for what it's worth).
     */
    private static void checkClasses(ClassDoc[] cds) {
        for (int i = 0; i < cds.length; i++) {
            checkDataMembersArePrivate(cds[i]);
        }
    }

    /**
     * This method prints out any data members that are not private
     * together with their access.  If the field is package access
     * we print out no modifiers.
     */
    private static void checkDataMembersArePrivate(ClassDoc cd) {
        System.out.println(cd.modifiers() + " " + cd.qualifiedName() + ":");
        FieldDoc[] fields = cd.fields();
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].isPrivate()) {
                System.out.print("\t");
                if (!fields[i].isPackagePrivate())
                    System.out.print(fields[i].modifiers() + " ");
                System.out.println(fields[i].type() + " " + fields[i].name());
            }
        }
        checkClasses(cd.innerClasses());
    }
}