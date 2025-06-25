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
import java.util.*;

public class GermanSort implements Comparator<String> {
    private final RuleBasedCollator collator;

    public GermanSort() throws ParseException {
        collator = createCollator();
    }

    private RuleBasedCollator createCollator() throws ParseException {
        String german = "" +
                "= '-',''' " +
                "< A,a;ä,Ä< B,b< C,c< D,d< E,e< F,f< G,g< H,h< I,i< J,j" +
                "< K,k< L,l< M,m< N,n< O,o;Ö,ö< P,p< Q,q< R,r< S,s< T,t" +
                "< U,u;Ü,ü< V,v< W,w< X,x< Y,y< Z,z" +
                "& ss=ß";
        return new RuleBasedCollator(german);
    }

    public int compare(String s1, String s2) {
        return collator.compare(s1, s2);
    }

    public void sort(String[] strings) {
        Arrays.sort(strings, this);
    }
}
