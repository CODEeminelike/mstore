<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Giỏ Hàng</title>
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
            padding: 15px 0;
            text-align: center;
        }
        header h1 {
            margin: 0;
            font-size: 24px;
        }
        .cart-container {
            width: 80%;
            margin: 30px auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            text-align: center;
            padding: 12px;
        }
        th {
            background-color: #f2f2f2;
            font-size: 16px;
        }
        td {
            font-size: 14px;
        }
        .empty-cart {
            text-align: center;
            font-size: 18px;
            color: #666;
            margin: 20px 0;
        }
        .checkout-button {
            display: block;
            width: 200px;
            margin: 20px auto;
            padding: 10px 15px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            text-align: center;
            cursor: pointer;
        }
        .checkout-button:hover {
            background-color: #218838;
        }
        .continue-shopping {
            text-align: center;
            margin-top: 20px;
        }
        .continue-shopping a {
            text-decoration: none;
            color: #007bff;
            font-size: 16px;
        }
        .continue-shopping a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <header>
        <h1>Giỏ Hàng Của Bạn</h1>
    </header>

    <div class="cart-container">
        <c:if test="${not empty cart.albums}">
            <table>
                <thead>
                <tr>
                    <th>Tên Album</th>
                    <th>Giá</th>
                    <th>Năm Phát Hành</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="album" items="${cart.albums}">
                    <tr>
                        <td>${album.name}</td>
                        <td>${album.price}</td>
                        <td>${album.releaseYear}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form action="CartServlet" method="post">
                <input type="hidden" name="action" value="checkout">
                <button type="submit" class="checkout-button">Thanh Toán</button>
            </form>
        </c:if>

        <c:if test="${empty cart.albums}">
            <p class="empty-cart">Giỏ hàng của bạn trống!</p>
        </c:if>

        <div class="continue-shopping">
            <a href="home">Tiếp tục mua sắm</a>
        </div>
    </div>
</body>
</html>
