<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <!-- JSTL core -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  <!-- JSTL formatting -->

<html>
<head>
    <title>Carrito de la compra</title>
</head>
<body>

<h1>Carrito de la compra</h1>

<c:choose>
    <%-- Si no hay carrito o está vacío --%>
    <c:when test="${empty cart or cart.isEmpty()}">
        <h3>Carrito vacío</h3>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
            <tr>
                <th>Título del CD</th>
                <th>Cantidad</th>
                <th>Importe</th>
            </tr>
            </thead>
            <tbody>
            <%-- Iteramos sobre la lista de CDs en el carrito --%>
            <c:forEach var="cd" items="${sessionScope.cart.items}">
                <tr>
                    <%-- toString() del CD --%>
                    <td><c:out value="${cd}"/></td>
                    <%-- Llamada al método getQuantity(cd) vía EL --%>
                    <td><c:out value="${cart.getQuantity(cd)}"/></td>
                    <%-- Formato con 2 decimales --%>
                    <td>
                        <fmt:formatNumber
                                value="${cart.getCost(cd)}"
                                pattern="#0.00"/>
                    </td>
                    <td>
                        <input type="radio" name="remove" value="${cd}"/>
                    </td>
                </tr>
            </c:forEach>
            <%-- Fila de total --%>
            <tr>
                <td></td>
                <td><strong>Total</strong></td>
                <td>
                    <fmt:formatNumber
                            value="${cart.total}"
                            pattern="#0.00"/>
                </td>
                <td>
                    <input type="button" value="Eliminar"/>
                </td>
            </tr>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<p>
    <a href="${pageContext.request.contextPath}/index.html">Seguir comprando</a>
</p>

</body>
</html>