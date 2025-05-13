package ministore;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class MiniStore extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) action = "default";
        switch (action) {
            case "addToCart":
                handleAddToCart(request, response);
            case "deleteFromCart":
                handleDeleteFromCart(request, response);
            case "purchase":
                handlePurchase(request, response);
            case "login":
                handleLogin(request, response);
            case "signup":
                handleSignup(request, response);
            case "seeAllPurchases":
                handleSeeAllPurchases(request, response);
        }

    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Cart cart;

        if (session.isNew()) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        } else {
            cart = (Cart) session.getAttribute("cart");
        }

        CD cd = new CD(request.getParameter("cd"));
        cart.addItem(cd, Integer.parseUnsignedInt(request.getParameter("quantity")));

        RequestDispatcher rd = request.getRequestDispatcher("cart.jsp");
        rd.forward(request, response);
    }

    private void handleDeleteFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Eliminar el elemento del carrito seleccionado
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            /**
             *
             * Código de eliminación: cart.deleteItem(new CD(request.getParameter("cd")));
             *
             */
        }

        /**
         *
         *
         * JSP
         *
         */
    }

    private void handlePurchase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Pasar a la pestaña de compra, donde está el inicio de sesión/registro
         */
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Inicio de sesión con usuario y contraseña
         */
    }

    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Registro con usuario, contraseña y nº tarjeta
         */
    }

    private void handleSeeAllPurchases(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Después de la compra, mostrar todas las compras realizadas por el usuario
         */
    }
}