package eu.javaspecialists.tjsn.issue030;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.prefs.*;

public class PreferencesEditor extends JDialog {
    JTree prefTree;
    JTable editTable;

    /**
     * Creates PreferencesEditor dialog that show all System and
     * User preferences.
     *
     * @param owner owner JFrame
     * @param title title of dialog
     */
    public PreferencesEditor(JFrame owner, String title) {
        this(owner, title, null, true, null, true);
    }

    /**
     * @param owner           owner JFrame
     * @param title           title of dialog
     * @param userObj         the package to which this object belongs is
     *                        used as the root-node of the User preferences tree (if
     *                        userObj is null, then the rootnode of all user preferences
     *                        will be used)
     * @param systemObj       the package to which this object belongs is
     *                        used as the root-node of the System preferences tree (if
     *                        systemObj is null, then the rootnode of all system
     *                        preferences will be used)
     * @param showSystemPrefs if true, then show system preferences
     * @boolean showUserPrefs if true, then show user preferences
     */
    public PreferencesEditor(JFrame owner, String title,
                             Object userObj, boolean showUserPrefs, Object systemObj,
                             boolean showSystemPrefs) {
        super(owner, title);
        getContentPane().setLayout(new BorderLayout(5, 5));
        setSize(640, 480);
        createTree(userObj, showUserPrefs, systemObj, showSystemPrefs);
        editTable = new JTable();
        createSplitPane();
        createButtonPanel();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void createTree(Object userObj, boolean showUserPrefs, Object systemObj, boolean showSystemPrefs) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Preferences");
        if (showUserPrefs) {
            rootNode.add(createUserRootNode(userObj));
        }
        if (showSystemPrefs) {
            rootNode.add(createSystemRootNode(systemObj));
        }
        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        prefTree = new JTree(model);
        prefTree.addTreeSelectionListener(new PrefTreeSelectionListener());
    }

    private MutableTreeNode createSystemRootNode(Object obj) {
        try {
            PrefTreeNode systemRoot;
            if (obj == null) {
                systemRoot = new PrefTreeNode(Preferences.systemRoot());
            } else {
                systemRoot = new PrefTreeNode(Preferences.systemNodeForPackage(obj.getClass()));
            }
            return systemRoot;
        } catch (BackingStoreException e) {
            e.printStackTrace();
            return new DefaultMutableTreeNode("No System Preferences!");
        }
    }

    private MutableTreeNode createUserRootNode(Object obj) {
        try {
            PrefTreeNode userRoot;
            if (obj == null) {
                userRoot = new PrefTreeNode(Preferences.userRoot());
            } else {
                userRoot = new PrefTreeNode(Preferences.userNodeForPackage(obj.getClass()));
            }
            return userRoot;
        } catch (BackingStoreException e) {
            e.printStackTrace();
            return new DefaultMutableTreeNode("No User Preferences!");
        }
    }

    class PrefTreeNode extends DefaultMutableTreeNode {
        Preferences pref;
        String nodeName;
        String[] childrenNames;

        public PrefTreeNode(Preferences pref) throws BackingStoreException {
            this.pref = pref;
            childrenNames = pref.childrenNames();
        }

        public Preferences getPrefObject() {
            return pref;
        }

        public boolean isLeaf() {
            return ((childrenNames == null) || (childrenNames.length == 0));
        }

        public int getChildCount() {
            return childrenNames.length;
        }

        public TreeNode getChildAt(int childIndex) {
            if (childIndex < childrenNames.length) {
                try {
                    PrefTreeNode child = new PrefTreeNode(pref.node(childrenNames[childIndex]));
                    return child;
                } catch (BackingStoreException e) {
                    e.printStackTrace();
                    return new DefaultMutableTreeNode("Problem Child!");
                }
            }
            return null;
        }

        public String toString() {
            String name = pref.name();
            if ((name == null) || ("".equals(name))) { //if root node
                name = "System Preferences";
                if (pref.isUserNode()) name = "User Preferences";
            }
            return name;
        }
    }

    class PrefTableModel extends AbstractTableModel {
        Preferences pref;
        String[] keys;

        public PrefTableModel(Preferences pref) {
            this.pref = pref;
            try {
                keys = pref.keys();
            } catch (BackingStoreException e) {
                System.out.println("Could not get keys for Preference node: " + pref.name());
                e.printStackTrace();
                keys = new String[0];
            }
        }

        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Key";
                case 1:
                    return "Value";
                default:
                    return "-";
            }
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    return false;
            }
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            pref.put(keys[rowIndex], aValue.toString());
            try {
                pref.sync(); //make sure the backing store is synchronized with latest update
            } catch (BackingStoreException e) {
                System.out.println("Error synchronizing backStore with updated value");
                e.printStackTrace();
            }
        }

        public Object getValueAt(int row, int column) {
            String key = keys[row];
            if (column == 0) return key;
            Object value = pref.get(key, "(Unknown)");
            return value;
        }

        public int getColumnCount() {
            return 2;
        }

        public int getRowCount() {
            return keys.length;
        }
    }

    class PrefTreeSelectionListener implements TreeSelectionListener {
        public void valueChanged(TreeSelectionEvent e) {
            try {
                PrefTreeNode node = (PrefTreeNode) e.getPath().getLastPathComponent();
                Preferences pref = node.getPrefObject();
                editTable.setModel(new PrefTableModel(pref));
            } catch (ClassCastException ce) {
                System.out.println("Node not PrefTreeNode!");
                editTable.setModel(new DefaultTableModel());
            }
        }
    }

    private void createSplitPane() {
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setOneTouchExpandable(true);
        splitPane.setLeftComponent(new JScrollPane(prefTree));
        splitPane.setRightComponent(new JScrollPane(editTable));
        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout(5, 5));
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(closeButton, BorderLayout.EAST);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
} //end of PreferencesEditor