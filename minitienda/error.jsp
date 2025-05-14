<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%-- JSTL core --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  <%-- JSTL formatting --%>

<%-- Establece en_US para que fmt:formatNumber use punto --%>
<fmt:setLocale value="en_US" scope="page"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Minitienda</title>
</head>
<body>

<p>Error</p>
<p> <c:out value="${sessionScope.error}"/>  </p>
<p>
    <a href="${pageContext.request.contextPath}/purchase.jsp">Volver atrás</a>
</p>

</body>
</html>