package app.main;

import app.entity.Product;
import app.model.DBUtil;
import app.model.ProductModel;

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

    public void Reload() {
        try {
            ProductModel productModel = new ProductModel(DBUtil.GetConnection());
            Vector<Product> products = productModel.GetAll();

            Vector<String> Cod = new Vector<>();
            Vector<String> Name = new Vector<>();
            Vector<Integer> Quantity = new Vector<>();
            Vector<Float> Price = new Vector<>();

            products.forEach(product -> Cod.add(product.Cod));
            products.forEach(product -> Name.add(product.Name));
            products.forEach(product -> Quantity.add(product.Quantity));
            products.forEach(product -> Price.add(product.Price));

            list_cod.setListData(Cod);
            list_name.setListData(Name);
            list_quantity.setListData(Quantity);
            list_price.setListData(Price);
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
                Reload();
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
