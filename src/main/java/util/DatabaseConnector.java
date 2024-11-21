package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Nạp driver MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");

        // URL kết nối đến cơ sở dữ liệu
        String dbURL = "jdbc:mysql://localhost:3306/user";

        // Tên người dùng và mật khẩu
        String username = "root";
        String password = "123456";

        // Lấy Connection
        return DriverManager.getConnection(dbURL, username, password);
    }
}