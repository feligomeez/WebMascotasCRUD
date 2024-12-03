/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function onCaptchaSuccess() {
    document.getElementById("submitButton").disabled = false;
}

function onCaptchaExpired() {
    document.getElementById("submitButton").disabled = true; 
}


function confirm_delete(mas) {
    let borrar = confirm(`¿Seguro que desea eliminar a ${mas}?`);
    return borrar;
}
window.addEventListener("load", MenuBootstrap, false);
function MenuBootstrap() {
    let menu = [{name: "Adopcion", url: "/home/cesta", options: []},
        
    ];
    // Agregar Login solo si el id es 0
    if (id === 0) {
        menu.push({name: "Login", url: "/home/login", options: []});
        menu.push({name: "Registrarse", url: "/home/usuario/new", options: []});
    }
// Agregar Logout solo si el id no es 0
    if (id !== 0) {
        if (id === 14) {            
            menu.push({name: "Gestión usuarios", url: "/home/usuarios", options: []});
            menu.push({name: "Editar Perfil", url: "/home/usuario/edit?usuarioId=" + id, options: []});
            menu.push({name: "Logout", url: "/home/logout", options: []});
        } else {
            menu.push({name: "Mis Mascotas", url: "/home/mascotas?usuarioId=" + id, options: []});
            menu.push({name: "Editar Perfil", url: "/home/usuario/edit?usuarioId=" + id, options: []});
            menu.push({name: "Logout", url: "/home/logout", options: []});
        }

    }

    let strmenu = '';
    let opciones = document.getElementById("opciones");
    for (let op of menu) {
        strmenu += '<li class="nav-item">\n';
        strmenu += `<a class="--bs-body-color" href="${op.url}"> ${op.name} </a>\n`;
        strmenu += '</li>\n';
    }
    opciones.innerHTML = strmenu;
}