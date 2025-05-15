<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%-- JSTL core --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  <%-- JSTL formatting --%>

<%-- Establece en_US para que fmt:formatNumber use punto --%>
<fmt:setLocale value="en_US" scope="page"/>

<html lang="es">
<head>
    <title>Registro</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>

<form action="signup" method="post">
    <input type="hidden" name="action" value="signup">
    <h1>Registráte</h1>
    <label>
        Correo electrónico:
        <input type="email" name="email" value="${sessionScope.email}" required>
    </label>
    <label>
        Contraseña:
        <input type="password" name="password" value="${sessionScope.password}" required>
    </label>
    <label>
        Tipo de tarjeta:
        <select name="cardType" required>
            <option value="VISA">VISA</option>
            <option value="MASTERCARD">MASTERCARD</option>
        </select>
    </label>
    <label>
        Número de tarjeta:
        <input type="text" name="cardNumber" required>
    </label>
    <input type="submit" value="Registrarse">
</form>

</body>
</html>
