/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function confirmDelete(person){
   let borrar = confirm
}


document.getElementById("mascotaForm").addEventListener("submit", function (event) {
    const name = document.getElementById("name").value.trim();
    const peso = document.getElementById("peso").value;
    const raza = document.getElementById("raza").value.trim();
    const especie = document.getElementById("especie").value;
    const sexo = document.getElementById("sexo").value;
    const alergias = document.getElementById("alergias").value.trim();
    const img = document.getElementById("img").files[0];

    // Validación de campos obligatorios
    if (!name) {
        document.getElementById("errorNombre").innerText = "ERROR: Introduce nombre";
        r = false;
    }
    if (!raza) {
        document.getElementById("errorRaza").innerText = "ERROR: Introduce raza";
        r = false;
    }
    if (!especie) {
        document.getElementById("errorEspecie").innerText = "ERROR: Introduce especie";
        r = false;
    }
    if (!sexo) {
        document.getElementById("errorGenero").innerText = "ERROR: Introduce sexo";
        r = false;
    }

// Validación de peso
    if (isNaN(peso) || peso <= 0) {
        document.getElementById("errorPeso").innerText = "ERROR: El peso debe ser un número mayor que cero.";
        r = false;
    }

// Validación de alergias (opcional)
    if (alergias.length > 100) {
        document.getElementById("errorAlergias").innerText = "ERROR: Las alergias no pueden superar los 100 caracteres.";
        r = false;
    }

// Validación de imagen (opcional)
    if (img && img.size > 2 * 1024 * 1024) { // Limitar a 2MB
        document.getElementById("errorImagen").innerText = "ERROR: La imagen no puede ser mayor de 2MB.";
        r = false;
    }

// Prevenir envío del formulario si hay errores
    if (!r) {
        event.preventDefault();
    }
    return r;
});
