package app.views.category;

import app.controller.CategoryController;
import app.entity.category.entity.CategoryEntity;
import app.model.DBUtil;
import app.model.category.model.CategoryModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class CategoryView {
    private JButton btn_create;
    private JPanel container;
    private JList list_code;
    private JList list_name;
    private JButton btn_update;
    private JButton btn_delete;
    private JButton btn_clean;
    private JTextField txt_code;
    private JTextField txt_name;
    private JRadioButton rbx_status;
    public void Call(){
        JFrame frame = new JFrame("CategoryView");
        frame.setContentPane(new CategoryView().container);
        frame.pack();
        frame.setVisible(true);
    }
    public void Reload(){
        CategoryController categoryController = new CategoryController();
        Vector<CategoryEntity> categories = categoryController.GetAll();

        Vector<String> Cod = new Vector<>();
        Vector<String> Name = new Vector<>();
        Vector<Boolean> Statu = new Vector<>();

        categories.forEach(cat -> Cod.add(cat.code_cat));
        categories.forEach(cat -> Name.add(cat.name_cat));
        categories.forEach(cat -> Statu.add(cat.status_cat));

        list_code.setListData(Cod);
        list_name.setListData(Name);
    }

    public CategoryView() {
        btn_create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoryEntity cat = new CategoryEntity();
                cat.code_cat = txt_code.getText();
                cat.name_cat = txt_name.getText();
                cat.status_cat = rbx_status.isSelected();
                CategoryController categoryController = new CategoryController();
                if (!categoryController.Create(cat)) {
                    JOptionPane.showMessageDialog(container, "Not category Deleted", "Error",JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(container,"Created","OK",JOptionPane.INFORMATION_MESSAGE);
                }
                Reload();
            }
        });
        btn_clean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txt_code.setText("");
                txt_name.setText("");
                rbx_status.setSelected(false);
                Reload();
            }
        });
        btn_update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.code_cat = txt_code.getText();
                categoryEntity.name_cat = txt_name.getText();
                categoryEntity.status_cat = rbx_status.isSelected();
                CategoryController categoryController = new CategoryController();
                if (!categoryController.Update(categoryEntity)) {
                    JOptionPane.showMessageDialog(container, "Not category Deleted", "Error",JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(container,"Update","OK",JOptionPane.INFORMATION_MESSAGE);
                }
                Reload();
            }
        });
        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoryEntity cat = new CategoryEntity();
                cat.code_cat = txt_code.getText();
                try{
                    CategoryModel catModel = new CategoryModel(DBUtil.GetConnection());
                    boolean response = catModel.Delete(cat);
                    if(!response){
                        JOptionPane.showMessageDialog(container, "Not category Deleted", "Error",JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(container,"Deleted","OK",JOptionPane.INFORMATION_MESSAGE);
                    }
                }catch(SQLException sqlException){
                    DBUtil.ProcessException(sqlException);
                }
                Reload();
            }
        });
    }
}
