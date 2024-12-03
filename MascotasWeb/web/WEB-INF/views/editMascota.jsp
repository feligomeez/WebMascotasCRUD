<%-- 
    Document   : editMascota
    Created on : Oct 20, 2024, 6:31:29?PM
    Author     : feligomeez
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar mascota</title>
        <link rel="stylesheet" href='${pageContext.servletContext.contextPath}/css/formularios.css'>
    </head>
    <body>
        <h1>Editar mascota del sistema</h1>
        <form action="/home/mascota/update" method="POST">
            <input type="hidden" name="mascotaId" value="${mascota.mascotaID}" />

            <label for="name">Nombre:</label>
            <input id="name" type="text" name="name" value="${mascota.nombre}" required><br />

            <label for="peso">Peso:</label>
            <input id="peso" type="text" name="peso" value="${mascota.peso}" required><br />

            <label for="raza">Raza:</label>
            <input id="raza" type="text" name="raza" value="${mascota.raza}" required><br />

            <label for="especie">Especie:</label>
            <select id="especie" name="especie">
                <option value="Perro" ${mascota.especie == Especie.Perro ? 'selected' : ''}>Perro</option>
                <option value="Gato" ${mascota.especie == Especie.Gato ? 'selected' : ''}>Gato</option>
                <option value="Pez" ${mascota.especie == Especie.Pez ? 'selected' : ''}>Pez</option>
                <option value="Huron" ${mascota.especie == Especie.Huron ? 'selected' : ''}>Hurón</option>
                <option value="Ave" ${mascota.especie == Especie.Ave ? 'selected' : ''}>Ave</option>
            </select><br />

            <label for="genero">Sexo:</label>
            <select id="genero" name="genero">
                <option value="Macho" ${mascota.genero == Genero.Macho ? 'selected' : ''}>Macho</option>
                <option value="Hembra" ${mascota.genero == Genero.Hembra ? 'selected' : ''}>Hembra</option>
            </select><br />

            <label for="alergias">Alergias:</label>
            <input id="alergias" type="text" name="alergias" value="${mascota.alergias}"><br />

            <label for="img">Foto:</label>
            <input type="file" id="img" name="img" accept="image/*"><br />

            <input type="submit" value="Guardar Cambios">
        </form>
        <a href="/home/mascotas?usuarioId=${mascota.usuario.usuarioID}">Volver a la lista de mascotas</a>

       <script src='${pageContext.servletContext.contextPath}/js/validacionMascota.js'></script>
    </body>
</html>