<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" session="false" %>

<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Minitienda</title>
</head>
<body>

<form action="addToCart" method="post" >
  <input type="hidden" name="action" value="addToCart">
  <p>
    <b>CD:</b>
    <select name="cd">
      <option>Yuan | The Guo Brothers | China | $14.95</option>
      <option>Drums of Passion | Babatunde Olatunji | Nigeria | $16.95</option>
      <option>Kaira | Tounami Diabate | Mali | $16.95</option>
      <option>The Lion is Loose | Eliades Ochoa | Cuba | $13.95</option>
      <option>Dance the Devil Away | Outback | Australia | $14.95</option>
      <option>Record of Changes | Samulnori | Korea | $12.95</option>
      <option>Djelika | Tounami Diabate | Mali | $14.95</option>
      <option>Rapture | Nusrat Fateh Ali Khan | Pakistan | $12.95</option>
      <option>Cesaria Evora | Cesaria Evora | Cape Verde | $16.95</option>
      <option>DAA | GSTIC | Spain | $50.00</option>
    </select>
  </p>
  <p>
    <b>Cantidad:</b>
    <input type="text" name="quantity" value="1">
  </p>
  <p>
    <input type="submit" value="Selecciona Producto">
  </p>
</form>

<a href="${pageContext.request.contextPath}/cart.jsp">Ver carrito</a>

</body>
</html>