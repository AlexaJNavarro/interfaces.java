package app.views.category;

import javax.swing.*;

public class Category {
    private JPanel container;
    private JButton btn_create;
    private JButton btn_update;
    private JButton btn_delete;
    private JButton btn_clear;
    private JList list_code;
    private JList list_name;
    private JTextField textField1;
    private JTextField textField2;
    private JRadioButton rdb_status;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Category");
        frame.setContentPane(new Category().container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
