<%-- 
    Document   : editUsuario
    Created on : Oct 27, 2024, 1:14:10?PM
    Author     : feligomeez
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar usuario</title>
        <link rel="stylesheet" href='${pageContext.servletContext.contextPath}/css/formularios.css'>
    </head>
    <body>
        <h1>Editar Usuario</h1>

        <form id="editUsuarioForm" action="/home/usuario/update" method="POST"">
            <input type="hidden" name="usuarioId" value="${usuario.usuarioID}"> <!-- ID oculto para la actualización -->
            <label for="name">Nombre:</label>
            <input id="name" type="text" name="name" value="${usuario.nombre}" required><br />

            <label for="surname">Apellidos:</label>
            <input id="surname" type="text" name="surname" value="${usuario.apellidos}" required><br />

            <label for="email">Correo:</label>
            <input id="email" type="text" name="email" value="${usuario.email}" required><br />

            <label for="pass">Contraseña:</label>
            <input id="pass" type="password" name="pass" value="${usuario.password}" required><br />

            <label for="phone">Teléfono:</label>
            <input id="phone" type="text" name="phone" value="${usuario.telefono}" required><br />

            <div class="mb-3">
                <label for="provincia" class="form-label">Provincia</label>
                <select id="provincias" class="form-select" >
                    <option selected>Elige provincia ...</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="localidades">Localidad:</label>
                <select id="localidades">
                    <option value="" selected>Seleccione una localidad</option>
                </select>
            </div>

            <label for="dir">Dirección:</label>
            <input id="dir" type="text" name="dir" value="${usuario.direccion}" required><br />

            <input type="submit" value="Guardar Cambios">
        </form>
        <script src="${pageContext.servletContext.contextPath}/js/validacionUsuario.js"></script>
        <script src="${pageContext.servletContext.contextPath}/js/emailAJAX.js"></script>

    </body>
</html>
