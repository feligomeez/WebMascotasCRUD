/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let email = document.getElementById("email");
if (email) {
    email.addEventListener("change", email_duplicado);
}

function email_duplicado() {
    let xhr = new XMLHttpRequest();
    let url = "/home/usuario/checkemail";
    xhr.open("POST", url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let duplicado = document.getElementById("duplicado");
            let respuesta = xhr.responseText;
            let estilo;
            if (respuesta.includes("Ok"))
                estilo = "text-success";
            else
                estilo = "text-danger";
            let span = document.getElementById("mensaje");
            if (!span) {
                span = document.createElement("span");
                duplicado.appendChild(span);
            }
            span.setAttribute("class", estilo);
            span.setAttribute("id", "mensaje");
            span.innerHTML = respuesta;

        }
    };
    let email = document.getElementById("email");
    let datos = "email=" + encodeURIComponent(email.value);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(datos);
}

let prov = document.getElementById("provincias");
if (prov) {
    window.addEventListener("load", cargarProvincias, false);
    prov.addEventListener("change", cargarLocalidades, false);
}
function cargarProvincias() {
    let provincias = new Array("Álava", "Albacete", "Alicante", "Almería", "Ávila", "Badajoz",
            "Baleares", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Castellón", "Ciudad Real", "Córdoba",
            "Coruña", "Cuenca", "Girona", "Granada", "Guadalajara", "Guipuzcoa", "Huelva", "Huesca", "Jaén",
            "León", "Lleida", "Rioja", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Orense",
            "Asturias", "Palencia", "Las Palmas", "Pontevedra", "Salamanca", "Tenerife", "Cantabria",
            "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid",
            "Vizcaya", "Zamora", "Zaragoza", "Ceuta", "Melilla");
    provincias.forEach((provincia, p) => {
        prov.options[p + 1] = new Option(provincia, p + 1);
    });
}

function cargarLocalidades() {
    let provinciaSeleccionada = prov.options[prov.selectedIndex].text;

    // Validar que se ha seleccionado una provincia
    if (!provinciaSeleccionada || provinciaSeleccionada === "Seleccione una provincia") {
        return;
    }

    let xhr = new XMLHttpRequest();
    xhr.open("GET", `/home/usuario/getLocalidades?provincia=${encodeURIComponent(provinciaSeleccionada)}`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let localidades = JSON.parse(xhr.responseText); // Suponemos que el servidor responde con JSON
            let localidadSelect = document.getElementById("localidades");
            localidadSelect.innerHTML = ""; // Limpiar localidades previas

            // Agregar las nuevas localidades
            localidades.forEach((localidad) => {
                let option = new Option(localidad, localidad);
                localidadSelect.add(option);
            });
        }
    };
    xhr.send();
}