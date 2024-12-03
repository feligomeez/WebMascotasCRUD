<%-- 
    Document   : Mascotas
    Created on : Oct 7, 2024, 12:27:16?AM
    Author     : feligomeez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Mascotas</title>
        <link rel="stylesheet" href='${pageContext.servletContext.contextPath}/css/listados.css'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg bg-body-white">
  <ul id="opciones" class="navbar-nav me-auto mb-2 mb-lg-0"></ul>
</nav>
        <div class="text-center">
        <form action="/home/mascota/new" method="GET" style="vertical-align: middle;">
                <input type="hidden" name="usuarioId" value="${requestScope.userid}">
                <button class="btn btn-primary" type="submit">Añadir Mascota</button>
            </form>
        </div>
        <h2>Mascotas de ${requestScope.name}</h2>
        <c:if test="${!empty requestScope.mascotas}">
            <table>
                <tr>
                    <th>Imagen</th>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Genero</th>
                    <th>Especie</th>
                    <th>Raza</th>                    
                    <th>Peso</th>
                    <th>Alergias</th>
                    <th>Acciones</th>


                </tr>
                <c:forEach var="mas" items="${requestScope.mascotas}" >
                    <tr>
                        <td><img src="${pageContext.servletContext.contextPath}/img/photos/${mas.mascotaID}.jpg" alt="img-${mas.mascotaID}" class='pet-image'></td>
                        <td>${mas.mascotaID}</td>
                        <td>${mas.nombre}</td>
                        <td>${mas.genero}</td>
                        <td>${mas.especie}</td>
                        <td>${mas.raza}</td>
                        <td>${mas.peso}</td>
                        <td>${mas.alergias}</td>
                        <td>
                            <form action="/home/mascota/edit" method="GET">
                                <input type="hidden" name="mascotaId" value="${mas.mascotaID}">
                                <button class="btn btn-warning" type="submit">Edit</button>
                            </form>                                
                        </td>
                        <td>
                            <form action="/home/mascota/delete" method="POST">
                                 <input type="hidden" name="usuarioId" value="${requestScope.userid}">
                                 <input type="hidden" name="mascotaId" value="${mas.mascotaID}">
                                 <button class="btn btn-danger" type="submit" onclick="return confirm_delete('${mas.nombre}')">Delete</button>
                             </form>   
                        </td>
                    </tr>
                </p>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty requestScope.mascotas}">
        <p>Oops! No hay Mascotas de este usuario!</p>
    </c:if>
    <script>var id = ${sessionScope.usuario};</script>
    <script src="${pageContext.servletContext.contextPath}/js/listado.js"></script>
</body>

</html>
