package ministore;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class MiniStore extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Cart cart;
        if(session.isNew()) {
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
}