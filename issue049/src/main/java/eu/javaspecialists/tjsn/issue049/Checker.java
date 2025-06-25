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

package eu.javaspecialists.tjsn.issue049;

import com.sun.javadoc.*;

/**
 * Abstract superclass for checking a code component.
 */
public abstract class Checker {
    private final Doc doc;

    public Checker(Doc doc) {
        this.doc = doc;
    }

    public abstract void check();

    protected abstract String getDescriptor();

    protected final boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public void checkComments() {
        if (isEmpty(doc.commentText()))
            error("misses comment");
    }

    protected void error(String msg) {
        System.err.println(getDescriptor() + ' ' + msg);
    }
}
