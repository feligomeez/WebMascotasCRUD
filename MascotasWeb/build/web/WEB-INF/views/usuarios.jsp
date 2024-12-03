<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Dueños de mascotas</title>
        <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/listados.css">
    </head>
    <body>
        <nav> | <a href="/home/usuario/new">Crear Nueva Entrada</a> | </nav>
        <h1>Dueños de mascotas</h1>
        <c:if test="${!empty requestScope.users}">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Email</th>
                    <th>Teléfono</th>
                    <th>Direccion</th>
                    <th>Acciones</th>
                </tr>
                <c:forEach var="user" items="${requestScope.users}" >
                    <tr>
                        <td>${user.usuarioID}</td>
                        <td>${user.nombre}</td>
                        <td>${user.apellidos}</td>
                        <td>${user.email}</td>
                        <td>${user.telefono}</td>
                        <td>${user.direccion}</td>
                        <td>
                            <form action="/home/usuario/edit" method="GET">
                                <input type="hidden" name="usuarioId" value="${user.usuarioID}">
                                <button type="submit">Edit</button>
                            </form>
                        </td>
                        <td>
                            <form action="/home/usuario/delete" method="POST">
                                <input type="hidden" name="usuarioId" value="${user.usuarioID}">
                                <button type="submit" onclick="return confirm_delete('${user.nombre}')">Delete</button>
                            </form>
                        </td>
                        <td>
                            <form action="/home/mascotas" method="GET">
                                <input type="hidden" name="usuarioId" value="${user.usuarioID}">
                                <button type="submit">Ver Mascotas</button>
                            </form>
                        </td>
                    </tr>
                </p>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty requestScope.users}">
        <p>Oops! No hay Usuarios todavía!</p>
    </c:if>
    <script src='${pageContext.servletContext.contextPath}/js/listado.js'></script>
</body>
</html>
