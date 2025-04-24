package ministore;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;


public class Cart {
    private final UUID id;
    private final HashMap<CD, Integer> cart;

    public Cart() {
        this.id = UUID.randomUUID();
        this.cart = new HashMap<CD, Integer>();
    }

    public UUID getId() {
        return id;
    }

    public Set<CD> getItems() {
        return cart.keySet();
    }

    public int getQuantity(CD item) {
        return cart.get(item);
    }

    public void addItem(CD cd, int quantity) {
        cart.put(cd, quantity);
    }

    public void deleteItem(CD cd) {
        cart.remove(cd);
    }

    public double computeTotal () {
        double total = 0.0;
        for (CD cd : cart.keySet()) {
            total += cd.getPrice() * cart.get(cd);
        }

        return total;
    }

    public double getCost (CD cd) {
        return cd.getPrice() * cart.get(cd);
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }

    public void emptyCart() {
        cart.clear();
    }
}
