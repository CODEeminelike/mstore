package dao;

import model.Album;
import model.Cart;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
   public void addAlbumToCart(String email, String albumName) throws SQLException, ClassNotFoundException {
    String sqlCheck = "SELECT quantity FROM Cart WHERE email = ? AND album_name = ?";
    String sqlUpdate = "UPDATE Cart SET quantity = quantity + 1 WHERE email = ? AND album_name = ?";
    String sqlInsert = "INSERT INTO Cart (email, album_name, quantity) VALUES (?, ?, 1)";

    try (Connection conn = DatabaseConnector.getConnection()) {
        try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
            pstmtCheck.setString(1, email);
            pstmtCheck.setString(2, albumName);
            ResultSet rs = pstmtCheck.executeQuery();

            if (rs.next()) {
                // Nếu sản phẩm đã tồn tại, cập nhật số lượng
                try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                    pstmtUpdate.setString(1, email);
                    pstmtUpdate.setString(2, albumName);
                    pstmtUpdate.executeUpdate();
                }
            } else {
                // Nếu chưa tồn tại, thêm mới
                try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                    pstmtInsert.setString(1, email);
                    pstmtInsert.setString(2, albumName);
                    pstmtInsert.executeUpdate();
                }
            }
        }
    }
}


    public void removeAlbumFromCart(String email, String albumName) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Cart WHERE email = ? AND album_name = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, albumName);
            pstmt.executeUpdate();
        }
    }

    public Cart getCartByUser(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT Album.name, Album.price, Album.release_year " +
                     "FROM Cart JOIN Album ON Cart.album_name = Album.name " +
                     "WHERE Cart.email = ?";
        List<Album> albums = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                albums.add(new Album(rs.getString("name"), rs.getDouble("price"), rs.getInt("release_year")));
            }
        }
        return new Cart(email, albums);
    }

    public void clearCart(String email) throws SQLException, ClassNotFoundException {
    String sql = "DELETE FROM Cart WHERE email = ?";
    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, email);
        pstmt.executeUpdate();
    }
}

}
