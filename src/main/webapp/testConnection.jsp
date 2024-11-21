<%@ page import="java.sql.Connection" %>
<%@ page import="murach.DatabaseConnector" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Kiểm tra kết nối MySQL</title>
</head>
<body>
    <h1>Kiểm tra kết nối MySQL</h1>
    <%
        try {
            Connection connection = DatabaseConnector.getConnection();
            if (connection != null) {
                out.println("<p style='color: green;'>Kết nối đến MySQL thành công!</p>");
                connection.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            out.println("<p style='color: red;'>Lỗi kết nối đến MySQL: " + e.getMessage() + "</p>");
        }
    %>
</body>
</html>