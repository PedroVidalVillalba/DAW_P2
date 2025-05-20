package ministore;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;

import static db.DataBase.getCurrentDB;

public class UserHelper {
    private static void storeInSession(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(true);
        Object value = request.getParameter(key);

        if (value != null) {
            session.setAttribute(key, value);
        }
    }

    /**
     * Inicio de sesión con usuario y contraseña
     */
    public static void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            getCurrentDB().loginUser(email, new Password(email, password));
        } catch (Exception e) {
            storeInSession(request, "email");
            storeInSession(request, "password");
            MiniStore.changeView(request, response, "signup.jsp");
        }
        try {
            doPurchase(request, response);
            MiniStore.changeView(request, response, "confirmation.jsp");
        } catch (Exception e) {
            showError(request, response, e.getMessage());
        }
    }

    /**
     * Registro con usuario, contraseña y número de tarjeta
     */
    public static void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String cardType = request.getParameter("cardType");
            String cardNumber = request.getParameter("cardNumber");

            User user = new User(email, new Password(email, password), cardType, cardNumber);
            getCurrentDB().registerUser(user);

            doPurchase(request, response);
            MiniStore.changeView(request, response, "confirmation.jsp");
        } catch (Exception e) {
            showError(request, response, e.getMessage());
        }
    }

    /**
     * Función que muestra errores producidos, principalmente
     * errores de inicio de sesión/registro
     */
    private static void showError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.getSession().setAttribute("error", message);
        MiniStore.changeView(request, response, "error.jsp");
    }

    private static void doPurchase(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /* Guardar el correo y contraseña en la sesión para próximas compras consecutivas */
        storeInSession(request, "email");
        storeInSession(request, "password");

        /* Guardar la información de la compra en la base de datos y vaciar el carrito */
        Cart cart = CartHelper.retrieveCart(request);
        String email = request.getParameter("email");
        Purchase purchase = new Purchase(email, new Timestamp(System.currentTimeMillis()), (float) cart.getTotal());
        getCurrentDB().addPurchase(email, purchase);
        request.setAttribute("total", cart.getTotal());
        request.setAttribute("history", getCurrentDB().getPurchases(email));
        cart.clear();
    }
}
