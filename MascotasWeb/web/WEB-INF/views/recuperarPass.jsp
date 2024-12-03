<%-- 
    Document   : recuperarPass
    Created on : Nov 27, 2024, 7:36:03 PM
    Author     : feligomeez
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recuperar contraseña</title>
        <link rel="stylesheet" href='${pageContext.servletContext.contextPath}/css/formularios.css'><!-- comment -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <h1>Recuperar contraseña</h1>        
        <form action="${pageContext.request.contextPath}/restablecer" method="POST">

            <label for="email">Introduce tu correo electrónico:</label>
            <input type="text" id="email" name="email" required>

            <label for="telefono">Introduce tu numero de telefono:</label>
            <input type="text" id="telefono" name="telefono" required>

            <c:if test="${not empty error}">
                <p style="color: red;">${error}</p>
            </c:if>
            <button class="btn btn-primary" type="submit">Restablecer</button>
        </form>


    </body>
</html>
