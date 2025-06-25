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

package eu.javaspecialists.tjsn.issue211;

import java.text.*;

public class Normalize {
    public boolean normeq(String w1, String w2) {
        if (w1.length() != w2.length()) {
            w1 = Normalizer.normalize(w1, Normalizer.Form.NFD);
            w2 = Normalizer.normalize(w2, Normalizer.Form.NFD);
        }
        return w1.equals(w2);
    }

    public void testEquals(String w1, String w2) {
        System.out.println(w1 + " equals " + w2 + " " + w1.equals(w2));
        System.out.println(w1 + " normeq " + w2 + " " + normeq(w1, w2));
    }
}
