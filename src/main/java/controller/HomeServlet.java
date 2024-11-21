package controller;

import dao.AlbumDAO;
import dao.CartDAO;
import dao.UserDAO;
import model.Album;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy action từ request
        String action = req.getParameter("action");

        // Kiểm tra trạng thái đăng nhập thông qua cookies
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

        User user = null;
        if (email != null) {
            try {
                // Kiểm tra email trong cơ sở dữ liệu
                UserDAO userDAO = new UserDAO();
                user = userDAO.getUserByEmail(email);
                req.setAttribute("user", user);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                req.setAttribute("error", "Lỗi khi tải thông tin người dùng.");
            }
        }

        // Xử lý các action
        if ("add".equalsIgnoreCase(action)) {
            if (user == null) {
                // Nếu chưa đăng nhập, chuyển hướng tới trang đăng ký
                resp.sendRedirect("register.jsp");
                return;
            } else {
                // Lấy tên album từ request
                String albumName = req.getParameter("albumName");

                try {
                    // Thêm album vào giỏ hàng của user
                    CartDAO cartDAO = new CartDAO();
                    cartDAO.addAlbumToCart(user.getEmail(), albumName);

                    // Hiển thị thông báo thành công
                    req.setAttribute("message", "Đã thêm album vào giỏ hàng.");
                } catch (SQLException | ClassNotFoundException e) {
                    // Xử lý ngoại lệ, ví dụ: hiển thị thông báo lỗi
                    e.printStackTrace();
                    req.setAttribute("error", "Lỗi khi thêm album vào giỏ hàng.");
                }
            }
        } else if ("buy".equalsIgnoreCase(action)) {
            if (user == null) {
                // Nếu chưa đăng nhập, chuyển hướng tới trang đăng ký
                resp.sendRedirect("register.jsp");
                return;
            } else {
                // Xử lý logic mua album (tạm thời chưa triển khai)
                System.out.println("Chưa xử lý logic mua album.");
            }
        }

        // Luôn tải lại danh sách album từ cơ sở dữ liệu
        try {
            AlbumDAO albumDAO = new AlbumDAO();
            List<Album> albums = albumDAO.getAllAlbums();
            req.setAttribute("albums", albums);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            req.setAttribute("error", "Không thể tải danh sách album: " + e.getMessage());
        }

        // Forward tới Home.jsp
        req.getRequestDispatcher("Home.jsp").forward(req, resp);
    }
}
