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

package eu.javaspecialists.tjsn.issue079;

/**
 * Here we use the Chain-of-Responsibility design pattern that is
 * very similar to the Strategy pattern, except that we pass the
 * request on if we cannot deal with it.  It helps us to reduce
 * the number of multi-conditional if-else-if statements that we
 * need in our program to decide which strategy to use.
 */
public abstract class ToStringConverter {
    private final ToStringConverter successor;

    /**
     * We need to know the successor, if this handler cannot cope
     * with the request.
     */
    protected ToStringConverter(ToStringConverter successor) {
        this.successor = successor;
    }

    /**
     * handle() decides whether this current object can handle the
     * request; otherwise it is passed onto the next in the sequence
     */
    protected final StringBuffer handle(Object o, StringBuffer buf) {
        if (!isHandler(o)) {
            assert successor != null;
            return successor.handle(o, buf);
        }
        return toString(o, buf);
    }

    /**
     * Subclasses can specify whether they can handle the current
     * object.
     */
    protected abstract boolean isHandler(Object o);

    /**
     * The toString() method is the main method that is called from
     * within the handle() method, once it is established which object
     * should handle the request.
     */
    protected StringBuffer toString(Object o, StringBuffer buf) {
        buf.append('(');
        appendName(o, buf);
        buf.append(getSeparator());
        appendValues(o, buf);
        buf.append(')');
        return buf;
    }

    /**
     * The separator between name and value can be different for
     * different types of objects.
     */
    protected char getSeparator() {
        return '=';
    }

    /**
     * This method will determine an identifier for the current object.
     */
    protected void appendName(Object o, StringBuffer buf) {
    }

    /**
     * This method will determine the values for the current object.
     */
    protected void appendValues(Object o, StringBuffer buf) {
    }
}
