package app.model.product.model;
import app.entity.product.entity.Product;
import app.model.DBUtil;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class ProductModel {
    protected Connection db;
    private String sqlCreate = "INSERT INTO products VALUES (?, ?, ?, ?, ?, ?)";
    private String sqlDelete = "UPDATE products SET status = false WHERE cod = ?";
    private String sqlGetAll = "SELECT cod,name,quantity,price,tbp.name_category\n" +
                               "FROM products tbp INNER JOIN category tbc\n" +
                               "ON tbp.name_category = tbc.name_category\n" +
                               "WHERE tbp.status = true";
    private String sqlUpdate = "UPDATE products SET name = ?, quantity = ?, price = ?, status = ?, name_category = ? WHERE cod = ?";

    public ProductModel(Connection _db) {
        this.db = _db;
    }

    public boolean Create(Product product) {
        try {
            PreparedStatement pstm = this.db.prepareStatement(this.sqlCreate);
            pstm.setString(1, product.Cod);
            pstm.setString(2, product.Name);
            pstm.setInt(3, product.Quantity);
            pstm.setFloat(4, product.Price);
            pstm.setBoolean(5,product.Status);
            pstm.setString(6,product.name_category);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected != 1) {
                return false;
            }
        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
        }
        return true;
    }

    public boolean Delete(String _cod) {
        try {
            PreparedStatement pstm = this.db.prepareStatement(this.sqlDelete);
            pstm.setString(1, _cod);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected != 1) {
                return false;
            }
        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
        }
        return true;
    }

    public Vector<Product> GetAll() {
        Vector<Product> products = new Vector<Product>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.db.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(this.sqlGetAll);
            while (rs.next()) {
                Product product = new Product();
                product.Cod = rs.getString("cod");
                product.Name = rs.getString("name");
                product.Quantity = rs.getInt("quantity");
                product.Price = rs.getFloat("price");
                product.name_category = rs.getString("tbp.name_category");
                products.add(product);
            }
        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
        }
        return products;
    }

    public boolean Update(Product product) {
        try {
            PreparedStatement pstm = db.prepareStatement(this.sqlUpdate);
            pstm.setString(1, product.Name);
            pstm.setInt(2, product.Quantity);
            pstm.setFloat(3, product.Price);
            pstm.setString(4, product.Cod);
            pstm.setBoolean(5,product.Status);
            pstm.setString(6,product.name_category);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected != 1) {
                return false;
            }
        } catch (SQLException sqlException) {
            DBUtil.ProcessException(sqlException);
        }
        return true;
    }
}
