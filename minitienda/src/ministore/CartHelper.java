package ministore;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class CartHelper {
    public static Cart retrieveCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    public static void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = retrieveCart(request);

        CD cd = new CD(request.getParameter("cd"));
        cart.addItem(cd, Integer.parseUnsignedInt(request.getParameter("quantity")));

        MiniStore.changeView(request, response, "cart.jsp");
    }

    public static void deleteFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Eliminar el elemento del carrito seleccionado
        Cart cart = retrieveCart(request);

        String[] cds = request.getParameterValues("cd");
        if (cds != null) {
            for (String cd : cds) {
                cart.deleteItem(new CD(cd));
            }
        }

        MiniStore.changeView(request, response, "cart.jsp");
    }

    public static void purchase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Al añadir la pestaña de inicio de sesión, solo cambia de pestaña */
        MiniStore.changeView(request, response, "login.jsp");
    }
}
