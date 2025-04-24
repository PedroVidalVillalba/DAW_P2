<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" session="false" %>
<%@ page import="ministore.Cart" import="ministore.CD" %>

<html>
<head>
    <title>Carrito de la compra</title>
</head>
<body>
<%
    HttpSession session = request.getSession(false);
    Cart cart = (session == null) ? null : (Cart) session.getAttribute("cart");
//    Cart cart = (Cart) request.getAttribute("cart");
%>

<h1>Carrito de la compra</h1>

<table>
    <thead>
        <tr>
            <th>Título del CD</th>
            <th>Cantidad</th>
            <th>Importe</th>
        </tr>
    </thead>
    <tbody>
        <% if (cart != null && !cart.isEmpty()) {
            for (CD cd : cart.getItems()) {
        %>
        <tr>
            <td><%= cd.toString() %></td>
            <td><%= cart.getQuantity(cd) %></td>
            <td><%= cart.getCost(cd) %></td>
        </tr>
        <% } %>
        <tr>
            <td></td>
            <td>Total</td>
            <td><%= cart.computeTotal() %></td>
        </tr>
        <% } else { %>
            <h3>Carrito vacío</h3>
        <% } %>
    </tbody>
</table>

<p>
    <a href="${pageContext.request.contextPath}/index.html">Seguir comprando</a>
</p>

</body>
</html>