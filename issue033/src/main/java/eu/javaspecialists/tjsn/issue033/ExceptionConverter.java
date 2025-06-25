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

package eu.javaspecialists.tjsn.issue033;

/**
 * The ExceptionConverter changes a checked exception into an
 * unchecked exception.
 */
public class ExceptionConverter extends RuntimeException {
    /**
     * we keep a handle to the wrapped exception
     */
    private final Exception ex;

    public ExceptionConverter(Exception ex) {
        this.ex = ex;
    }

    /**
     * and allow the user of ExceptionConverter to get a handle to it.
     */
    public Exception getException() {
        return ex;
    }

    /**
     * We print the message of the checked exception
     */
    public String getMessage() {
        return ex.getMessage();
    }

    /**
     * and make sure we also produce a localized version
     */
    public String getLocalizedMessage() {
        return ex.getLocalizedMessage();
    }

    /**
     * The toString() is changed to be prefixed with ExceptionConverter
     */
    public String toString() {
        return "ExceptionConverter: " + ex;
    }

    /**
     * we have to override this as well
     */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /**
     * here we prefix, with s.print(), not s.println(), the stack
     * trace with "ExceptionConverter:"
     */
    public void printStackTrace(java.io.PrintStream s) {
        synchronized (s) {
            s.print("ExceptionConverter: ");
            ex.printStackTrace(s);
        }
    }

    /**
     * Again, we prefix the stack trace with "ExceptionConverter:"
     */
    public void printStackTrace(java.io.PrintWriter s) {
        synchronized (s) {
            s.print("ExceptionConverter: ");
            ex.printStackTrace(s);
        }
    }

    /**
     * requests to fill in the stack trace we will have to ignore
     * (I think)  We can't throw an exception here, as this method
     * is called by the constructor of Throwable
     */
    public Throwable fillInStackTrace() {
        return this;
    }
}
