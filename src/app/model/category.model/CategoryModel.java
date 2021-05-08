package app.model.category.model;

import app.entity.category.entity.CategoryEntity;
import app.model.DBUtil;
import app.model.category.model.CategoryModel;

import javax.security.sasl.SaslException;
import java.sql.*;
import java.util.Vector;

public class CategoryModel {
    protected Connection db;
    private String sqlCategory = "SELECT name_category FROM category WHERE status_category=true";
    private String sqlGetAll = "SELECT cod_category, name_category FROM  category WHERE status_category=true;";
    private String sqlCreate = "INSERT INTO category VALUES (?, ?, ?) ";
    private String sqlUpdate = "UPDATE category SET name_category = ?, status_category = ? WHERE cod_category = ?";
    private String sqlDelete = "UPDATE category SET status_category = false WHERE cod_category = ?";

    public CategoryModel(Connection _db){
        this.db = _db;
    }

    public Boolean Create(CategoryEntity category){
        try{
            PreparedStatement pstm = this.db.prepareStatement(this.sqlCreate);
            pstm.setString(1, category.code_cat);
            pstm.setString(2, category.name_cat);
            pstm.setBoolean(3, category.status_cat);
            int rowsAffected = pstm.executeUpdate();
            if(rowsAffected != 1){
                return  false;
            }
        }catch(SQLException sqlException){
            DBUtil.ProcessException(sqlException);

        }
        return true;
    }

    public Boolean Update(CategoryEntity category){
        try{
            PreparedStatement pstm = this.db.prepareStatement(this.sqlUpdate);
            pstm.setString(1, category.name_cat);
            pstm.setBoolean(2, category.status_cat);
            pstm.setString(3, category.code_cat);
            int rowsAffected = pstm.executeUpdate();
            if(rowsAffected != 1){
                return  false;
            }
        }catch(SQLException sqlException){
            DBUtil.ProcessException(sqlException);

        }
        return true;
    }

    public Boolean Delete(CategoryEntity category){
        try{
            PreparedStatement pstm = this.db.prepareStatement(this.sqlDelete);
            pstm.setString(1, category.code_cat);
            int rowsAffected = pstm.executeUpdate();
            if(rowsAffected != 1){
                return  false;
            }
        }catch(SQLException sqlException){
            DBUtil.ProcessException(sqlException);

        }
        return true;
    }

    public Vector<CategoryEntity> GetAll(){
        Vector<CategoryEntity> categories = new Vector<>();
        Statement stmt = null;
        ResultSet rs = null;

        try{
            stmt = this.db.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(this.sqlGetAll);
            while(rs.next()){
                CategoryEntity cat = new CategoryEntity();
                cat.code_cat = rs.getString("cod_category");
                cat.name_cat = rs.getString("name_category");
                categories.add(cat);
            }
        }catch (SQLException sqlException){
            DBUtil.ProcessException(sqlException);
        }
        return categories;
    }




}
