<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Album</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
            color: #333;
        }
        header {
            background-color: #007bff;
            color: white;
            padding: 10px 0;
            text-align: center;
        }
        header h1 {
            margin: 0;
            font-size: 24px;
        }
        .user-section {
            text-align: center;
            margin: 15px 0;
        }
        .user-section a, .user-section button {
            margin: 0 10px;
            padding: 8px 12px;
            font-size: 14px;
            border: none;
            cursor: pointer;
            text-decoration: none;
        }
        .user-section a {
            color: white;
            background-color: #28a745;
            border-radius: 5px;
        }
        .user-section button {
            color: white;
            background-color: #dc3545;
            border-radius: 5px;
        }
        .error-message {
            color: red;
            text-align: center;
        }
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        td button {
            padding: 5px 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        td button:hover {
            background-color: #0056b3;
        }
        footer {
            text-align: center;
            padding: 10px 0;
            background-color: #007bff;
            color: white;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>

<header>
    <h1>Danh Sách Album</h1>
</header>

<div class="user-section">
    <c:choose>
        <c:when test="${not empty user}">
            <p>Xin chào, ${user.name}!</p>
            <a href="CartServlet">Giỏ hàng</a>
            <form action="UserServlet" method="post" style="display: inline;">
                <button type="submit" name="action" value="logout">Đăng Xuất</button>
            </form>
        </c:when>
        <c:otherwise>
            <a href="UserServlet?action=register">Đăng Ký</a>
            <a href="UserServlet?action=login">Đăng Nhập</a>
        </c:otherwise>
    </c:choose>
</div>

<c:if test="${not empty error}">
    <p class="error-message">${error}</p>
</c:if>

<table>
    <thead>
        <tr>
            <th>Tên Album</th>
            <th>Giá</th>
            <th>Năm Phát Hành</th>
            <th>Hành Động</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="album" items="${albums}">
            <tr>
                <td>${album.name}</td>
                <td>${album.price}</td>
                <td>${album.releaseYear}</td>
                <td>
                    <form action="home" method="get">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="albumName" value="${album.name}">
                        <button type="submit">Thêm vào Giỏ</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<footer>
    &copy; 2024 - Hệ thống bán hàng Album
</footer>

</body>
</html>
