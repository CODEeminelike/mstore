package dao;

import model.Album;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO {
    public void addAlbum(Album album) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Album (name, price, release_year) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, album.getName());
            pstmt.setDouble(2, album.getPrice());
            pstmt.setInt(3, album.getReleaseYear());
            pstmt.executeUpdate();
        }
    }

    public void updateAlbum(Album album) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Album SET price = ?, release_year = ? WHERE name = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, album.getPrice());
            pstmt.setInt(2, album.getReleaseYear());
            pstmt.setString(3, album.getName());
            pstmt.executeUpdate();
        }
    }

    public void deleteAlbum(String name) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Album WHERE name = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public Album getAlbumByName(String name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Album WHERE name = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Album(rs.getString("name"), rs.getDouble("price"), rs.getInt("release_year"));
            }
        }
        return null;
    }

    public List<Album> getAllAlbums() throws SQLException, ClassNotFoundException {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM Album";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                albums.add(new Album(rs.getString("name"), rs.getDouble("price"), rs.getInt("release_year")));
            }
        }
        return albums;
    }
}
