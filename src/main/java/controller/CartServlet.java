package controller;

import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cart;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String email = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    email = cookie.getValue();
                    break;
                }
            }
        }

        if (email != null) {
            try {
                // Lấy giỏ hàng của người dùng
                CartDAO cartDAO = new CartDAO();
                Cart cart = cartDAO.getCartByUser(email);
                req.setAttribute("cart", cart);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                req.setAttribute("error", "Không thể tải giỏ hàng.");
            }
        } else {
            req.setAttribute("error", "Vui lòng đăng nhập để xem giỏ hàng.");
        }

        req.getRequestDispatcher("/Cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Cookie[] cookies = req.getCookies();
        String email = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    email = cookie.getValue();
                    break;
                }
            }
        }

        if ("checkout".equalsIgnoreCase(action) && email != null) {
            try {
                // Xóa toàn bộ sản phẩm trong giỏ hàng
                CartDAO cartDAO = new CartDAO();
                cartDAO.clearCart(email);

                // Hiển thị thông báo thanh toán thành công
                req.setAttribute("message", "Thanh toán thành công!");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                req.setAttribute("error", "Lỗi khi thanh toán.");
            }
        }

        req.getRequestDispatcher("/Cart.jsp").forward(req, resp);
    }
}
