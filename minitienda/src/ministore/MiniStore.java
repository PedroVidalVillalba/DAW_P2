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
                CartHelper.addToCart(request, response);
                break;
            case "deleteFromCart":
                CartHelper.deleteFromCart(request, response);
                break;
            case "purchase":
                CartHelper.purchase(request, response);
                break;
            case "login":
                UserHelper.login(request, response);
                break;
            case "signup":
                UserHelper.signup(request, response);
                break;
        }
    }

    public static void changeView(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
    }
}