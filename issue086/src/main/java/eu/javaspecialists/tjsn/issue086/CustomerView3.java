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

package eu.javaspecialists.tjsn.issue086;

import javax.swing.*;

public class CustomerView3 extends FormView1 {
    private static final Object lock = new Object();
    private static Integer tempType;
    private Integer type;

    /**
     * The problem with the superclass is that it makes a callback
     * to method makeUI().  We however want to set a variable in
     * this object before that method is called.  The way we do it
     * is to set a static variable, then at the beginning of the
     * makeUI() method we initialise our non-static variable.
     */
    public CustomerView3(JFrame owner, int type) {
        super(hackToPieces(owner, type));
    }

    private static JFrame hackToPieces(JFrame owner, int type) {
        synchronized (lock) {
            /** We want to prevent several threads overwriting the
             * tempType static variable. */
            while (tempType != null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    // someone wants to shut us down, let's return and keep
                    // the thread interrupted
                    Thread.currentThread().interrupt();
                    return owner;
                }
            }
            tempType = new Integer(type);
            return owner;
        }
    }

    /**
     * We initialise the variables and set the temporary static
     * fields to null.
     */
    private void init() {
        synchronized (lock) {
            type = tempType;
            tempType = null;
            lock.notifyAll();
        }
    }

    public JComponent makeUI() {
        // Make sure that init() is called first.  This assumes that
        // makeUI only gets called once, by the superclass'
        // constructor.  If that is a false assumption, you will have
        // to be a bit cleverer here.  Probably test whether type
        // is null or something.  Exercise for the reader.
        init();
        switch (type.intValue()) {
            case 0:
                return new JTextArea();
            case 1:
                return new JTextField();
            default:
                return new JComboBox();
        }
    }

    public static void main(String[] args) {
        CustomerView3 view1 = new CustomerView3(null, 1);
        System.out.println(view1.getMainUIComponent().getClass());
    }
}
