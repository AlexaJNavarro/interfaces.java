package app.controller;

import app.entity.category.entity.CategoryEntity;
import app.entity.product.entity.Product;
import app.model.DBUtil;
import app.model.category.model.CategoryModel;
import app.model.product.model.ProductModel;

import java.sql.SQLException;
import java.util.Vector;

public class CategoryController {
    public Vector<CategoryEntity> GetAll() {
        CategoryModel categoryModel = null;
        try {
            categoryModel = new CategoryModel(DBUtil.GetConnection());
        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
        }
         return categoryModel.GetAll();
    }
    public boolean Create(CategoryEntity categoryEntity) {
        if (categoryEntity.code_cat.length() != 5) return false;
        if (categoryEntity.name_cat.length() == 0) return false;
        try {
            CategoryModel categoryModel = new CategoryModel(DBUtil.GetConnection());
            if (!categoryModel.Create(categoryEntity)) {
                return false;
            }
        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
        }
        return true;
    }
    public boolean Update(CategoryEntity categoryEntity) {
        if (categoryEntity.code_cat.length() != 5) return false;
        if (categoryEntity.name_cat.length() == 0) return false;
        try {
            CategoryModel categoryModel = new CategoryModel(DBUtil.GetConnection());
            if (!categoryModel.Update(categoryEntity)) return false;
        } catch (SQLException sqlException) {
            return false;
        }
        return true;
    }

    public boolean Delete(CategoryEntity categoryEntity) {
        if (categoryEntity.code_cat.length() != 5) return false;
        try {
            CategoryModel categoryModel = new CategoryModel(DBUtil.GetConnection());
            if (!categoryModel.Delete(categoryEntity)) return false;
        } catch (SQLException sqlException) {
            return false;
        }
        return true;
    }
}
