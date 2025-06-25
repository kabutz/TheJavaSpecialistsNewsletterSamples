package eu.javaspecialists.tjsn.issue086;

import javax.swing.*;

public class CustomerView2 extends FormView2 {
    private Integer type;

    public CustomerView2(JFrame owner, int type) {
        super(owner, new Integer(type));
    }

    public JComponent makeUI(Object params) {
        this.type = (Integer) params;
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
        CustomerView2 view1 = new CustomerView2(null, 1);
        System.out.println(view1.getMainUIComponent().getClass());
    }
}
