<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%-- JSTL core --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  <%-- JSTL formatting --%>

<%-- Establece en_US para que fmt:formatNumber use punto --%>
<fmt:setLocale value="en_US" scope="page"/>

<html lang="es">
<head>
  <title>Confirmación de compra</title>
  <meta charset="utf-8">
</head>
<body>

<h1>Confirmación de compra</h1>
<p>
  Tu compra por valor de
  <fmt:formatNumber
          value="${total}"
          pattern="$#0.00"/>
  fue realizada con éxito.
</p>

<h2>Histórico de compras</h2>
<table>
  <thead>
  <tr>
    <th>Fecha</th>
    <th>Importe</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="purchase" items="${history}">
    <tr>
      <td><c:out value="${purchase.date}"/></td>
      <td>
        <fmt:formatNumber
                value="${purchase.cost}"
                pattern="$#0.00"/>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<a href="${pageContext.request.contextPath}/index.jsp">Volver al inicio</a>

</body>
</html>
