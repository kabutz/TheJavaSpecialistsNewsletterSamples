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

package eu.javaspecialists.tjsn.issue013;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.*;
// wouldn't be right for me to send
// you a newsletter that doesn't use reflection :)

class ComponentEncapsulator implements Serializable {
    private final Component comp;
    private IOException defaultWriteException;

    public ComponentEncapsulator(Component comp) {
        this.comp = comp;
    }

    public Component getComponent() {
        return comp;
    }

    private void writeObject(final ObjectOutputStream out)
            throws IOException {
        if (SwingUtilities.isEventDispatchThread()) {
            // This is all that is necessary if we are already in
            // the event dispatch thread, e.g. a user clicked a
            // button which caused the object to be written
            out.defaultWriteObject();
        } else {
            try {
                // we want to wait until the object has been written
                // before continuing.  If we called this from the
                // event dispatch thread we would get an exception
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        try {
                            // easiest way to indicate to the enclosing class
                            // that an exception occurred is to have a member
                            // which keeps the IOException
                            defaultWriteException = null;
                            // we call the actual write object method
                            out.defaultWriteObject();
                        } catch (IOException ex) {
                            // oops, an exception occurred, remember the
                            // exception object
                            defaultWriteException = ex;
                        }
                    }
                });
                if (defaultWriteException != null) {
                    // an exception occurred in the code above, throw it!
                    throw defaultWriteException;
                }
            } catch (InterruptedException ex) {
                // I'm not quite sure what do here, perhaps:
                Thread.currentThread().interrupt();
                return;
            } catch (InvocationTargetException ex) {
                // This can actually only be a RuntimeException or an
                // Error - in either case we want to rethrow them
                Throwable target = ex.getTargetException();
                if (target instanceof RuntimeException) {
                    throw (RuntimeException) target;
                } else if (target instanceof Error) {
                    throw (Error) target;
                }
                ex.printStackTrace(); // this should not happen!
                throw new RuntimeException(ex.toString());
            }
        }
    }
}