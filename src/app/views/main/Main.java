package app.views.main;

import app.entity.product.entity.Product;
import app.model.DBUtil;
import app.model.product.model.ProductModel;
import app.views.category.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class Main {

    private JPanel container;
    private JButton btn_clear;
    private JButton btn_create;
    private JButton btn_delete;
    private JButton btn_update;
    private JTable tbl_products;
    private JTextField txt_cod;
    private JTextField txt_name;
    private JTextField txt_quantity;
    private JTextField txt_price;
    private JLabel lbl_name;
    private JLabel lbl_quantity;
    private JLabel lbl_price;
    private JList list;
    private JList list_name;
    private JList list_quantity;
    private JList list_price;
    private JList list_cod;
    private JComboBox cbx_category;
    private JList list_category_name;
    private JRadioButton rbt_status;
    private JButton btn_openCategory;

    public void Reload() {
        try {
            ProductModel productModel = new ProductModel(DBUtil.GetConnection());
            Vector<Product> products = productModel.GetAll();

            Vector<String> Cod = new Vector<>();
            Vector<String> Name = new Vector<>();
            Vector<Integer> Quantity = new Vector<>();
            Vector<Float> Price = new Vector<>();
            Vector<String> Category = new Vector<>();

            products.forEach(product -> Cod.add(product.Cod));
            products.forEach(product -> Name.add(product.Name));
            products.forEach(product -> Quantity.add(product.Quantity));
            products.forEach(product -> Price.add(product.Price));
            products.forEach(product -> Category.add(product.name_category));
            products.forEach(product -> cbx_category.addItem(product.name_category));

            list_cod.setListData(Cod);
            list_name.setListData(Name);
            list_quantity.setListData(Quantity);
            list_price.setListData(Price);
            list_category_name.setListData(Category);

        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
        }
    }

    public Main() {

        btn_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Product product = new Product();
                product.Cod = txt_cod.getText();
                product.Name = txt_name.getText();
                product.Quantity = Integer.parseInt(txt_quantity.getText());
                product.Price = Float.parseFloat(txt_price.getText());
                product.Status= rbt_status.isSelected();
                product.name_category=cbx_category.getSelectedItem().toString();
                try {
                    ProductModel productModel = new ProductModel(DBUtil.GetConnection());
                    boolean response = productModel.Create(product);
                    if (!response) {
                        JOptionPane.showMessageDialog(container, "Not product created", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(container, "Created", "OK", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (SQLException sqlException) {
                    DBUtil.ProcessException(sqlException);
                }
                cbx_category.removeAllItems();
                Reload();
            }
        });
        btn_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_cod.setText("");
                txt_name.setText("");
                txt_quantity.setText("");
                txt_price.setText("");
                rbt_status.setSelected(false);
                cbx_category.removeAllItems();
                Reload();
            }
        });
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProductModel productModel = new ProductModel(DBUtil.GetConnection());
                    boolean response = productModel.Delete(txt_cod.getText());
                    if (!response) {
                        JOptionPane.showMessageDialog(container, "Not product deleted", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(container, "Deleted", "OK", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException sqlException) {
                    DBUtil.ProcessException(sqlException);
                }
                cbx_category.removeAllItems();
                Reload();
            }
        });
        btn_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Product product = new Product();
                    product.Cod = txt_cod.getText();
                    product.Name = txt_name.getText();
                    product.Quantity = Integer.parseInt(txt_quantity.getText());
                    product.Price = Float.parseFloat(txt_price.getText());
                    product.Status= rbt_status.isSelected();
                    product.name_category=cbx_category.getSelectedItem().toString();

                    ProductModel productModel = new ProductModel(DBUtil.GetConnection());
                    boolean response = productModel.Update(product);
                    if (!response) {
                        JOptionPane.showMessageDialog(container, "Not product updated", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(container, "Updated", "OK", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (SQLException sqlException) {
                    DBUtil.ProcessException(sqlException);
                }
                cbx_category.removeAllItems();
                Reload();
            }
        });
        btn_openCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
