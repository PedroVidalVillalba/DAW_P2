package ministore;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class MiniStore extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) action = "default";
        switch (action) {
            case "addToCart":
                handleAddToCart(request, response);
                break;
            case "deleteFromCart":
                handleDeleteFromCart(request, response);
                break;
            case "purchase":
                handlePurchase(request, response);
                break;
            case "login":
                handleLogin(request, response);
                break;
            case "signup":
                handleSignup(request, response);
                break;
            case "seeAllPurchases":
                handleSeeAllPurchases(request, response);
                break;
        }
    }

    private Cart retrieveCart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    private void storeInSession(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        Object value = session.getAttribute(key);

        if (value != null) {
            session.setAttribute(key, value);
        }
    }

    private void changeView(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = retrieveCart(request, response);

        CD cd = new CD(request.getParameter("cd"));
        cart.addItem(cd, Integer.parseUnsignedInt(request.getParameter("quantity")));

        changeView(request, response, "cart.jsp");
    }

    private void handleDeleteFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Eliminar el elemento del carrito seleccionado
        Cart cart = retrieveCart(request, response);

        String[] cds = request.getParameterValues("cd");
        if (cds != null) {
            for (String cd : cds) {
                cart.deleteItem(new CD(cd));
            }
        }

        changeView(request, response, "cart.jsp");
    }

    private void handlePurchase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = retrieveCart(request, response);

        cart.clear();

        /*
         * Añadir lógica para pasar a la pestaña de inicio de sesión/registro
         */
        changeView(request, response, "login.jsp");
    }

    /**
     * Inicio de sesión con usuario y contraseña
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Guardar el correo y contraseña en la sesión para
        * próximas compras consecutivas */
        storeInSession(request, "email");
        storeInSession(request, "password");

       /* Aquí habría que hacer una movida con la base de datos */

        changeView(request, response, "index.jsp");
    }

    /**
     * Registro con usuario, contraseña y número de tarjeta
     */
    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Guardar el correo y contraseña en la sesión para
         * próximas compras consecutivas */
        storeInSession(request, "email");
        storeInSession(request, "password");

        /* Registrarlo de verdad y esas cosas */

        changeView(request, response, "index.jsp");
    }

    private void handleSeeAllPurchases(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Después de la compra, mostrar todas las compras realizadas por el usuario
         */
    }
}