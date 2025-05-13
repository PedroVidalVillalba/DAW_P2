<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%-- JSTL core --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  <%-- JSTL formatting --%>

<%-- Establece en_US para que fmt:formatNumber use punto --%>
<fmt:setLocale value="en_US" scope="page"/>

<html lang="es">
<head>
    <title>Caja</title>
    <meta charset="utf-8">
</head>
<body>

<h1>Caja</h1>
<c:choose>
    <c:when test="${empty cart or cart.isEmpty() }">
        <h3>El carrito está vacío</h3>
        <p>
            <a href="${pageContext.request.contextPath}/index.jsp">Volver a la página principal</a>
        </p>
    </c:when>
    <c:otherwise>
        <p>
            <strong>Total</strong>
            <fmt:formatNumber
                    value="${cart.total}"
                    pattern="$#0.00"/>
        </p>

        <form action="purchase" method="post">
            <input type="hidden" name="action" value="purchase"/>
            <input type="submit" value="Pagar y volver a la página principal"/>
        </form>
    </c:otherwise>
</c:choose>

</body>
</html>
