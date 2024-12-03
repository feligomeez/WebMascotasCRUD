<%-- 
    Document   : mascotasVenta
    Created on : Oct 27, 2024, 5:48:38?PM
    Author     : feligomeez
--%>

<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mascotas en Adopción</title>
    <link rel="stylesheet" href='${pageContext.servletContext.contextPath}/css/listados.css'>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg bg-body-white">
  <ul id="opciones" class="navbar-nav me-auto mb-2 mb-lg-0"></ul>
</nav>
    <h1>Mascotas en Adopción</h1>
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success d-flex align-items-center">${sessionScope.message}</div>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger d-flex align-items-center">${sessionScope.error}</div>
    </c:if>    
    <c:if test="${not empty mascotasEnAdopcion}">
        <table border="1">
            <tr>
                <th>Imagen</th>
                <th>Nombre</th>
                <th>Especie</th>
                <th>Raza</th>
                <th>Sexo</th>
                <th>Peso</th>
                <th>Acción</th>
            </tr>
            <c:forEach var="mascota" items="${mascotasEnAdopcion}">
                <tr>
                    <td><img src="${pageContext.servletContext.contextPath}/img/photos/${mascota.mascotaID}.jpg" alt="${mascota.nombre}" width="100"></td>
                    <td>${mascota.nombre}</td>
                    <td>${mascota.especie}</td>
                    <td>${mascota.raza}</td>
                    <td>${mascota.genero}</td>
                    <td>${mascota.peso} kg</td>
                    <td>
                        <form action="${pageContext.servletContext.contextPath}/cesta/add" method="POST">
                            <input type="hidden" name="mascotaId" value="${mascota.mascotaID}">
                            <button type="submit">Añadir a la Cesta</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <h2>Tu Cesta</h2>
    <c:if test="${empty sessionScope.cesta.mascotas}">
        <p>Tu cesta está vacía.</p>
    </c:if>
    <c:if test="${not empty sessionScope.cesta.mascotas}">
        <table border="1">
            <tr>
                <th>Nombre</th>
                <th>Especie</th>
                <th>Raza</th>
            </tr>
            <c:forEach var="mascota" items="${sessionScope.cesta.mascotas}">
                <tr>
                    <td>${mascota.nombre}</td>
                    <td>${mascota.especie}</td>
                    <td>${mascota.raza}</td>
                    <td>
                        <form action="${pageContext.servletContext.contextPath}/cesta/delete" method="POST">
                            <input type="hidden" name="mascotaId" value="${mascota.mascotaID}">
                            <button type="submit">Borrar de la Cesta</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <form action="${pageContext.servletContext.contextPath}/cesta/purchase" method="POST">
            <button type="submit">Adoptar Ahora</button>
        </form>
    </c:if>  
        <script>var id = ${requestScope.usuario};</script>
        <script src="${pageContext.servletContext.contextPath}/js/listado.js"></script>
</body>
</html>
