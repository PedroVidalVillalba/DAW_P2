package ministore;
import java.sql.Timestamp;



public record Purchase(String buyer, Timestamp date, float cost) {
}
