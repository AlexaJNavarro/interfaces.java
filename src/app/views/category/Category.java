package app.views.category;

import app.entity.category.entity.CategoryEntity;
import app.model.DBUtil;
import app.model.category.model.CategoryModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class Category {
    private JPanel container;
    private JButton btn_create;
    private JButton btn_update;
    private JButton btn_delete;
    private JButton btn_clear;
    private JList list_code;
    private JList list_name;
    private JTextField txt_code;
    private JTextField txt_name;
    private JRadioButton rdb_status;

    public void Reload(){
        try{
            CategoryModel categoryModel = new CategoryModel(DBUtil.GetConnection());
            Vector<CategoryEntity> categories = categoryModel.GetAll();

            Vector<String> Cod = new Vector<>();
            Vector<String> Name = new Vector<>();
            Vector<Boolean> Statu = new Vector<>();

            categories.forEach(cat -> Cod.add(cat.code_cat));
            categories.forEach(cat -> Name.add(cat.name_cat));
            categories.forEach(cat -> Statu.add(cat.status_cat));

            list_code.setListData(Cod);
            list_name.setListData(Name);


        }catch(SQLException sqlException){
            DBUtil.ProcessException(sqlException);
        }

    }

    public Category() {
        btn_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_code.setText("");
                txt_name.setText("");
                rdb_status.setSelected(false);
                Reload();
            }
        });
        btn_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_code.setText("");
                txt_name.setText("");
                rdb_status.setSelected(false);
                Reload();
            }
        });
    }


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
