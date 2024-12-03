/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.getElementById("usuarioForm").addEventListener("submit", function (event) {
        const name = document.getElementById("name").value.trim();
        const surname = document.getElementById("surname").value.trim();
        const email = document.getElementById("email").value.trim();
        const phone = document.getElementById("phone").value.trim();
        const dir = document.getElementById("dir").value.trim();
        let r = true;


if (!name){
        let mensaje = document.getElementById("errorNombre");
        mensaje.innerText = "ERROR: Introduce nombre";
        name.setAttribute("class", "form-control error");
        r = false;
}

if (!surname){
        let mensaje = document.getElementById("errorSurname");
        mensaje.innerText = "ERROR: Introduce apellido";
        surname.setAttribute("class", "form-control error");
        r = false;
}


if(!dir){
        let mensaje = document.getElementById("errorDireccion");
        mensaje.innerText = "ERROR: Introduce direccion";
        dir.setAttribute("class", "form-control error");
        r = false;
    }

if (!email) {
    document.getElementById("errorEmail").innerText = "ERROR: Introduce email";
    email.setAttribute("class", "form-control error");
    r = false;
} else {
    // Validación de formato de correo electrónico
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(email)) {
        document.getElementById("errorEmail").innerText = "ERROR: Ingrese un correo electrónico válido.";
        email.setAttribute("class", "form-control error");
        r = false;
    }
}

if (!phone) {
    document.getElementById("errorPhone").innerText = "ERROR: Introduce teléfono";
    phone.setAttribute("class", "form-control error");
    r = false;
} else {
    // Validación de formato de teléfono: 9 dígitos y empieza con 6, 7, o 9
    const phonePattern = /^[679]\d{8}$/;
    if (!phonePattern.test(phone)) {
        document.getElementById("errorPhone").innerText = "ERROR: Ingrese un número de teléfono válido.";
        phone.setAttribute("class", "form-control error");
        r = false;
    }
}


if (!r) {
    event.preventDefault();    
}
return r;

});
