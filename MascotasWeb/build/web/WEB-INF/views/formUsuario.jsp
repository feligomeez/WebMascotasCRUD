<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo usuario</title>
        <link rel="stylesheet" href='${pageContext.servletContext.contextPath}/css/formularios.css'>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <h1>Añadir usuario al sistema</h1>

        <form id="usuarioForm" action="/home/usuario/save" method="POST">
            <label for="name">Nombre:</label>
            <input id="name" type="text" class="form-control" name="name" />
            <span id="errorNombre"  class="text-danger"></span>

            <label for="surname">Apellidos:</label>
            <input id="surname" type="text"  class="form-control" name="surname" />
            <span id="errorSurname"  class="text-danger"></span>

            <label for="email">Correo:</label>
            <input id="email" type="text" class="form-control" name="email" />
            <div id="duplicado" class="mt-2"></div>
            <span id="errorEmail"  class="text-danger"></span>

            <label for="pass">Contraseña:</label>
            <input id="pass" type="password" class="form-control" name="pass" />
            <span id="errorEmail"  class="text-danger"></span>

            <label for="phone">Teléfono: </label>
            <input id="phone" type="text" class="form-control" name="phone" />
            <span id="errorPhone"  class="text-danger"></span>

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
`   `       </div>
            
            <label for="dir">Direccion: </label>
            <input id="dir" type="text" class="form-control" name="dir" />
            <span id="errorDireccion"  class="text-danger"></span>

            <input type="submit" value="Guardar" />
        </form>
        <script src="${pageContext.servletContext.contextPath}/js/validacionUsuario.js"></script>
        <script src="${pageContext.servletContext.contextPath}/js/emailAJAX.js"></script>
        <a href="/home/cesta">Inicio</a>


    </body>
</html>