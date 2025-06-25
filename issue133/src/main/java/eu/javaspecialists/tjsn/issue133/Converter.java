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

package eu.javaspecialists.tjsn.issue133;

import java.util.*;

public abstract class Converter {
    public static final Converter TO_ARRAY = new ToArrayConverter();
    public static final Converter WITH_COPY = new WithCopyConverter();
    public static final Converter WITH_LOOP = new WithLoopConverter();
    public static final Converter UNSAFE = new UnsafeConverter();

    public abstract <T> Collection<T> convert(Class<T> dest,
                                              Collection<?> objects);

    @SuppressWarnings("unchecked")
    public final <T> List<T> convert(Class<T> dest, List<?> objects) {
        return (List<T>) convert(dest, (Collection) objects);
    }
}
