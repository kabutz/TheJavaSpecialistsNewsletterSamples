/*
 * Copyright 2000-2026 Heinz Max Kabutz
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

package eu.javaspecialists.tjsn.issue329;

import module java.base;

public final class SwitchPatternMatchingSanitizer
        implements InvocationTargetSantizier {
    public void sanitize(InvocationTargetException e) {
        switch (e.getCause()) {
            case Error error -> throw error;
            case RuntimeException re -> throw re;
            case IOException ioe ->
                    throw new UncheckedIOException(ioe);
            case Throwable t ->
                    throw new IllegalStateException(t);
        }
    }
}