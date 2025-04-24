import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class MiniStore extends HttpServlet {
    /* Atributos */
//    private Cart cart;


    /* Métodos */
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Mini Store</h1>");
        out.println("<p> CD: " + request.getParameter("cd") + "</p>");
        out.println("<p> Cantidad: " + request.getParameter("quantity") + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}