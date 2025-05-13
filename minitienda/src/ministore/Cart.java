package ministore;

import java.util.HashMap;
import java.util.Set;

public class Cart {
    private final HashMap<CD, Integer> cart;

    public Cart() {
        this.cart = new HashMap<CD, Integer>();
    }

    public Set<CD> getItems() {
        return cart.keySet();
    }

    public double getTotal () {
        double total = 0.0;
        for (CD cd : cart.keySet()) {
            total += cd.getPrice() * cart.get(cd);
        }

        return total;
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

    public double getCost (CD cd) {
        return cd.getPrice() * cart.get(cd);
    }

    public boolean isEmpty() {
        return cart.isEmpty();
    }

    public void clear() {
        cart.clear();
    }
}
