<%-- 
    Document   : login
    Created on : Oct 28, 2024, 7:00:45 PM
    Author     : feligomeez
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar Sesión</title>
        <link rel="stylesheet" href='${pageContext.servletContext.contextPath}/css/formularios.css'>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <h1>Iniciar Sesión</h1>
        <form action="${pageContext.request.contextPath}/login" method="POST">
            <label for="email">Correo electrónico:</label>
            <input type="text" id="email" name="email" required><br>

            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required><br>
            
            <div class="g-recaptcha" 
                 data-sitekey="6LeFdpAqAAAAAPKbGFMPbtfD83GSc4ZFTputlFIz" 
                 data-callback="onCaptchaSuccess" 
                 data-expired-callback="onCaptchaExpired"></div>
            
            <a href="/home/restablecer"> Se te ha olvidado la contraseña? </a>
            <input type="submit" value="Iniciar Sesión">
        </form>

    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>
</body>
</html>
