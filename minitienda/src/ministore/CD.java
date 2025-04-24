package ministore;

import java.util.Objects;

public class CD {

    private final String name;
    private final String author;
    private final String country;
    private final double price;

    public CD(String raw) {
        // Formato del archivo: "name";"author";"country";price
        String[] parts = raw.strip().split(" \\| ");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Formato incorrecto de línea: " + raw);
        }

        this.name = parts[0];
        this.author = parts[1];
        this.country = parts[2];
        this.price = Double.parseDouble(parts[3].substring(1));
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getCountry() {
        return country;
    }

    public double getPrice() {
        return price;
    }



    public String toString() {
        return String.format("%s|%s|%s|%.2f", name, author, country, price);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CD cd)) return false;
        return Double.compare(price, cd.price) == 0 && Objects.equals(name, cd.name) && Objects.equals(author, cd.author) && Objects.equals(country, cd.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, country, price);
    }
}
