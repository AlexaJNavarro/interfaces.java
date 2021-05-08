package app.controller;

import app.entity.product.entity.Product;
import app.model.DBUtil;
import app.model.product.model.ProductModel;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Vector;

public class ProductController {
    public Vector<app.entity.product.entity.Product>  GetAll() {
        ProductModel productModel = null;
        try {
            productModel = new ProductModel(DBUtil.GetConnection());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return productModel.GetAll();
    }

    public boolean Create (Product product){
        if (product.Cod.length() != 5) return false;
        if (product.Price == 0) return false;
        if (product.Quantity == 0) return false;
        if (product.name_category.length() == 0) return false;
        try {
            ProductModel productModel = new ProductModel(DBUtil.GetConnection());
            if (!productModel.Create(product)) return false;
        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
            return false;
        }
        return true;
    }

    public boolean Delete(String cod) {
        if (cod.length() != 5) return false;
        try {
            ProductModel productModel = new ProductModel(DBUtil.GetConnection());
            if (!productModel.Delete(cod)) return false;
        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
            return false;
        }
        return true;
    }

    public boolean Update(Product product) {
        if (product.Cod.length() != 5) return false;
        if (product.Price == 0) return false;
        if (product.Quantity == 0) return false;
        if (product.name_category.length() == 0) return false;
        try {
            ProductModel productModel = new ProductModel(DBUtil.GetConnection());
            if (!productModel.Update(product)) return false;
        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
            return false;
        }
        return true;
    }
}
