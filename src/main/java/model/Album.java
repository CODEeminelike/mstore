package model;

public class Album {
    private String name;
    private double price;
    private int releaseYear;

    // Constructors
    public Album() {}

    public Album(String name, double price, int releaseYear) {
        this.name = name;
        this.price = price;
        this.releaseYear = releaseYear;
    }

    // Getters và Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
