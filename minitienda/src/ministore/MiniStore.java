package ministore;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.sql.Timestamp;
import java.util.List;

import static db.DataBase.getCurrentDB;

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

    private Cart retrieveCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    private void storeInSession(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(true);
        Object value = request.getParameter(key);

        if (value != null) {
            session.setAttribute(key, value);
        }
    }

    private void changeView(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = retrieveCart(request);

        CD cd = new CD(request.getParameter("cd"));
        cart.addItem(cd, Integer.parseUnsignedInt(request.getParameter("quantity")));

        changeView(request, response, "cart.jsp");
    }

    private void handleDeleteFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Eliminar el elemento del carrito seleccionado
        Cart cart = retrieveCart(request);

        String[] cds = request.getParameterValues("cd");
        if (cds != null) {
            for (String cd : cds) {
                cart.deleteItem(new CD(cd));
            }
        }

        changeView(request, response, "cart.jsp");
    }

    private void handlePurchase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Al añadir la pestaña de inicio de sesión, solo cambia de pestaña */
        changeView(request, response, "login.jsp");
    }

    /**
     * Inicio de sesión con usuario y contraseña
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            getCurrentDB().loginUser(email, new Password(email, password));
        } catch (Exception e) {
            storeInSession(request, "email");
            storeInSession(request, "password");
            changeView(request, response, "signup.jsp");
        }
        try {
            doPurchase(request, response);
            changeView(request, response, "confirmation.jsp");
        } catch (Exception e) {
            showError(request, response, e.getMessage());
        }
    }

    /**
     * Registro con usuario, contraseña y número de tarjeta
     */
    private void handleSignup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String cardType = request.getParameter("cardType");
            String cardNumber = request.getParameter("cardNumber");
            getCurrentDB().registerUser(email, new Password(email, password), cardType, cardNumber);

            doPurchase(request, response);
            changeView(request, response, "confirmation.jsp");
        } catch (Exception e) {
            showError(request, response, e.getMessage());
        }
    }

    private void handleSeeAllPurchases(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * Por si se añade una pestaña de ver todas las compras realizadas
         * Esta función está sin probar
         */
        try {
            List<Purchase> purchases = getCurrentDB().getPurchases(request.getParameter("email"));
            request.getSession().setAttribute("purchases", purchases);
        } catch (Exception e) {
            showError(request, response, e.getMessage());
        }


    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        /**
         * Función que muestra errores producidos, principalmente
         * errores de inicio de sesión/registro
         *
         *
         *
         * Se podría mejorar con un popup de error, en vez de una pestaña aparte
         *
         */
        request.getSession().setAttribute("error", message);
        changeView(request, response, "error.jsp");

    }

    private void doPurchase(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /* Guardar el correo y contraseña en la sesión para próximas compras consecutivas */
        storeInSession(request, "email");
        storeInSession(request, "password");

        /* Guardar la información de la compra en la base de datos y vaciar el carrito */
        Cart cart = retrieveCart(request);
        String email = request.getParameter("email");
        Purchase purchase = new Purchase(email, new Timestamp(System.currentTimeMillis()), (float) cart.getTotal());
        getCurrentDB().addPurchase(email, purchase);
        request.setAttribute("total", cart.getTotal());
        request.setAttribute("history", getCurrentDB().getPurchases(email));
        cart.clear();
    }
}