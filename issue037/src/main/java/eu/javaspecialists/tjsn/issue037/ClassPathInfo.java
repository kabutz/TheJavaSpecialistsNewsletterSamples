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

package eu.javaspecialists.tjsn.issue037;

import java.io.*;
import java.net.*;

public class ClassPathInfo {
    private final ClassLoader classLoader;

    public ClassPathInfo(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassPathInfo() {
        this(ClassLoader.getSystemClassLoader());
    }

    /**
     * validates classpath, throws an LinkageError if invalid
     * classpath was specified
     */
    public void validateClassPath() {
        try {
            URL[] urls = ((URLClassLoader) classLoader).getURLs();
            for (int i = 0; i < urls.length; i++) {
                try {
                    urls[i].openStream();
                } catch (IllegalArgumentException iae) {
                    throw new LinkageError(
                            "malformed class path url:\n " + urls[i]);
                } catch (IOException ioe) {
                    throw new LinkageError(
                            "invalid class path url:\n " + urls[i]);
                }
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(
                    "The current VM's System classloader is not a "
                            + "subclass of java.net.URLClassLoader");
        }
    }

    public static void main(String[] args) {
        ClassPathInfo info = new ClassPathInfo();
        info.validateClassPath();
    }
}
