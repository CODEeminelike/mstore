package model;

import java.util.List;

public class Cart {
    private String email; // Liên kết đến User qua email
    private List<Album> albums;

    // Constructors
    public Cart() {}

    public Cart(String email, List<Album> albums) {
        this.email = email;
        this.albums = albums;
    }

    // Getters và Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    // Thêm Album vào giỏ hàng
    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    // Xóa Album khỏi giỏ hàng
    public void removeAlbum(Album album) {
        this.albums.remove(album);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "email='" + email + '\'' +
                ", albums=" + albums +
                '}';
    }
}
