package app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    protected static final String MYSQL_DB = "jdbc:mysql://localhost:3306/bd_test_java";
    protected static final String MYSQL_USER = "root";
    protected static final String MYSQL_PASSWORD = "123456";

    public static Connection GetConnection() throws SQLException {
        return DriverManager.getConnection(MYSQL_DB, MYSQL_USER, MYSQL_PASSWORD);
    }

    public static void ProcessException(SQLException sqlE){
        System.err.println("Error: " + sqlE.getMessage());
        System.err.println("Code: " + sqlE.getErrorCode());
        System.err.println("SQL State: " + sqlE.getSQLState());
    }
}
