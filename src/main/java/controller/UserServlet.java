package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("register".equals(action)) {
            // Chuyển hướng đến trang đăng ký
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } else if ("login".equals(action)) {
            // Chuyển hướng đến trang đăng nhập
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        UserDAO userDAO = new UserDAO();

        if ("register1".equalsIgnoreCase(action)) {
            String email = req.getParameter("email");
            String name = req.getParameter("name");
            String password = req.getParameter("password");

            try {
                // Kiểm tra xem email đã tồn tại chưa
                if (userDAO.getUserByEmail(email) != null) {
                    req.setAttribute("error", "Email đã tồn tại!");
                    req.getRequestDispatcher("/register.jsp").forward(req, resp);
                    return;
                }

                User user = new User(email, name, password);
                userDAO.addUser(user);

                // Lưu cookies để đăng nhập ngay sau khi đăng ký
                Cookie cookie = new Cookie("email", email);
                cookie.setMaxAge(24 * 60 * 60); // 1 ngày
                resp.addCookie(cookie);

                // Chuyển hướng về home
                resp.sendRedirect("index.html");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                req.setAttribute("error", "Không thể đăng ký người dùng. Vui lòng thử lại.");
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
            }
        } else if ("login1".equalsIgnoreCase(action)) { 
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            try {
                User user = userDAO.getUserByEmail(email);

                if (user != null && user.getPassword().equals(password)) {
                    // Đăng nhập thành công
                    Cookie cookie = new Cookie("email", email);
                    cookie.setMaxAge(24 * 60 * 60); // 1 ngày
                    resp.addCookie(cookie);
                    resp.sendRedirect("index.html"); 
                } else {
                    // Đăng nhập thất bại
                    req.setAttribute("error", "Email hoặc mật khẩu không đúng!");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                req.setAttribute("error", "Lỗi đăng nhập. Vui lòng thử lại.");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } else if ("logout".equalsIgnoreCase(action)) {
            // Xóa cookies
            Cookie cookie = new Cookie("email", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);

            // Chuyển hướng về index.html
            resp.sendRedirect("index.html");
        }
    }
}