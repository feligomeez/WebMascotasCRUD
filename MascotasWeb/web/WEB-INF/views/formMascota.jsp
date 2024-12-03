<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Nueva mascota</title>
        <link rel="stylesheet" href='${pageContext.servletContext.contextPath}/css/formularios.css'>
    </head>
    <body>
        <h1>Añadir mascota al sistema</h1>

        <form action="${pageContext.servletContext.contextPath}/mascota/save" method="POST" enctype="multipart/form-data">           
            <input type="hidden" name="userid" value="${requestScope.userid}">

            
            <label for="name">Nombre:</label>
            <input id="name" type="text" name="name"><br />
            <span id = 'errorName'></span>

            <label for="peso">Peso:</label>
            <input id="peso" type="number" name="peso" step="0.1" min="0"><br />
            <span id = 'errorPeso'></span>
            
            <label for="raza">Raza:</label>
            <input id="raza" type="text" name="raza"><br />
            <span id = 'errorRaza'></span>
            
            <label for="especie">Especie:</label>
            <select id="especie" name="especie">
                <option value="Perro">Perro</option>
                <option value="Gato">Gato</option>
                <option value="Pez">Pez</option>
                <option value="Huron">Hurón</option>
                <option value="Ave">Ave</option>
            </select><br />
            <span id = 'errorEspecie'></span>
            
            
            <label for="sexo">Sexo:</label>
            <select id="sexo" name="sexo" >
                <option value="Hembra">Hembra</option>
                <option value="Macho">Macho</option>
            </select><br />
            <span id = 'errorGenero'></span>
            
            
            <label for="alergias">Alergias:</label>
            <input id="alergias" type="text" name="alergias" ><br />
            <span id = 'errorAlergias'></span>
            
            <label for="img">Foto: </label>
            <input type="file" id="img" name="img" /> <br /><br />

            <input type="submit" value="Guardar">
        </form>
        <script src='${pageContext.servletContext.contextPath}/js/validacionMascota.js'></script>
        
        <a href="/home/usuarios">Inicio</a>
        
    </body>
</html>