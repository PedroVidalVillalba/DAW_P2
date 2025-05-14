package ministore;
import java.sql.Timestamp;

public record Purchase(String buyer, Timestamp date, float cost) {
    public Timestamp getDate() {
        return date;
    }
    public float getCost() {
        return cost;
    }
}