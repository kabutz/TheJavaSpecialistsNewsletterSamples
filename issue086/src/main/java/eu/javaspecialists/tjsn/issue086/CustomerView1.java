package eu.javaspecialists.tjsn.issue086;

import javax.swing.*;

public class CustomerView1 extends FormView1 {
    private final Integer type;

    public CustomerView1(JFrame owner, int type) {
        super(owner);
        this.type = new Integer(type);
    }

    public JComponent makeUI() {
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
        CustomerView1 view1 = new CustomerView1(null, 1);
        System.out.println(view1.getMainUIComponent().getClass());
    }
}
