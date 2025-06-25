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

package eu.javaspecialists.tjsn.issue053;

/**
 * @author Mr M.O.Nument
 */

import java.util.*;

public class ListSearching {
    private List names = new LinkedList();

    public void f() {
        for (int i = 0; i < size(); i++) {
            if (names.get(i) == "Heinz")
                System.out.println("Found it");
        }
    }

    private int size() {
        int result = 0;
        Iterator it = names.iterator();
        while (it.hasNext()) {
            result++;
            it.next();
        }
        return result;
    }
}
