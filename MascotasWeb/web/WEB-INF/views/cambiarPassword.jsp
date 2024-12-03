<%-- 
    Document   : cambiarPassword
    Created on : Nov 27, 2024, 8:00:27 PM
    Author     : feligomeez
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Restablecer contraseña</title>
        <link rel="stylesheet" href='${pageContext.servletContext.contextPath}/css/formularios.css'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <h1>Restablecer contraseña</h1>
        <form action="${pageContext.request.contextPath}/cambiarPassword" method="POST">
            <input type="hidden" name="email" value="${requestScope.email}">

            <label for="password">Nueva contraseña:</label>
            <input type="password" id="password" name="password" required>

            <label for="confirmPassword">Confirma la nueva contraseña:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>

            <c:if test="${not empty error}">
                <p style="color: red;">${error}</p>
            </c:if>
            <button class="btn btn-primary" type="submit">Cambiar contraseña</button>
        </form>

        <c:if test="${not empty errorMessage}">
            <p style="color: red;">${errorMessage}</p>
        </c:if>
    </body>
</html>