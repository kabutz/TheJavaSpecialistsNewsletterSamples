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

public class CustomerView4 extends FormView1 {
    private static final ThreadLocal tempType = new ThreadLocal();
    private Integer type;

    public CustomerView4(JFrame owner, int type) {
        super(hackToPieces(owner, type));
    }

    private static JFrame hackToPieces(JFrame owner, int type) {
        tempType.set(new Integer(type));
        return owner;
    }

    private void init() {
        type = (Integer) tempType.get();
    }

    public JComponent makeUI() {
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
        CustomerView4 view = new CustomerView4(null, 1);
        System.out.println(view.getMainUIComponent().getClass());
    }
}