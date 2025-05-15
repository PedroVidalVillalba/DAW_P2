<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  <%-- JSTL core --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  <%-- JSTL formatting --%>

<%-- Establece en_US para que fmt:formatNumber use punto --%>
<fmt:setLocale value="en_US" scope="page"/>

<html lang="en">
<head>
    <title>Minitienda</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>

<p>Error</p>
<p> <c:out value="${sessionScope.error}"/>  </p>
<p>
    <a href="${pageContext.request.contextPath}/index.jsp">Volver al inicio</a>
</p>

</body>
</html>