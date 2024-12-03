/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Usuario;
import com.google.gson.Gson;
import jakarta.annotation.Resource;
import java.util.logging.Logger;
import java.util.logging.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author feligomeez
 */
@WebServlet(name = "Controlador", urlPatterns = {"/usuarios", "/usuario/*"})
public class Controlador extends HttpServlet {

    @PersistenceContext(unitName = "MascotasPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(Controlador.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vista = "";
        String accion = "/usuarios";
        if (request.getServletPath().equals("/usuario")) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            } else {
                accion = "error";
            }
        }
        switch (accion) {
            case "/usuarios" -> {
                HttpSession session = request.getSession();
                Long id = (Long) session.getAttribute("usuario");
                if (id == 14) {
                    List<Usuario> lb;
                    TypedQuery<Usuario> q = em.createNamedQuery("Usuario.findAll", Usuario.class);
                    lb = q.getResultList();
                    request.setAttribute("users", lb);
                    vista = "usuarios";
                }

            }
            case "/new" -> {
                vista = "formUsuario";
            }
            case "/edit" -> {
                Long usuarioId = Long.valueOf(request.getParameter("usuarioId"));

                // Buscar la mascota en la base de datos usando JPA
                Usuario usuario = em.find(Usuario.class, usuarioId);

                if (usuario != null) {
                    // Pasar los datos de la mascota al formulario
                    request.setAttribute("usuario", usuario);
                    vista = "editUsuario";
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "La mascota no fue encontrada.");
                }
            }

            case "/getLocalidades" -> {
                String provincia = request.getParameter("provincia");
                List<String> localidades = getLocalidadesPorProvincia(provincia); 
                response.setContentType("application/json");
                try (PrintWriter out = response.getWriter()) {
                    Gson gson = new Gson(); 
                    out.print(gson.toJson(localidades));
                    out.flush();
                }
            }
            default -> {
                vista = "error";
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getPathInfo();
        switch (accion) {
            case "/save" -> {
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String dir = request.getParameter("dir");
                String pass = request.getParameter("pass");
                String passwordEncriptada = encriptarPassword(pass);
                String provincia = request.getParameter("provincias");
                String localidad = request.getParameter("localidades");
                try {
                    if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                        throw new NullPointerException();
                    }
                    Usuario u = new Usuario(name, surname, email, phone, dir, passwordEncriptada, provincia, localidad);
                    save(u);
                    response.sendRedirect("http://localhost:8080/home/cesta");
                } catch (Exception e) {
                    request.setAttribute("msg", "Error: datos no válidos");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
                    rd.forward(request, response);
                }
                break;
            }
            case "/checkemail" -> {
                String email = request.getParameter("email");
                List<Usuario> usuarios;

                String respuesta;

                TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
                query.setParameter("email", email);
                usuarios = query.getResultList();

                if (!usuarios.isEmpty()) {
                    respuesta = "Email no válido: ya está registrado.";
                } else {
                    respuesta = "Ok";
                }

                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println(respuesta);
                }
                break;
            }

            case "/update" -> {
                Long usuarioId = Long.valueOf(request.getParameter("usuarioId"));
                String name = request.getParameter("name");
                String surname = request.getParameter("surname");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String dir = request.getParameter("dir");
                String pass = request.getParameter("pass");
                System.out.println(pass);
                String passwordEncriptada = encriptarPassword(pass);
                System.out.println(passwordEncriptada);
                String provincia = request.getParameter("provincias");
                String localidad = request.getParameter("localidades");
                try {
                    // Buscar el usuario existente en la base de datos
                    Usuario usuario = em.find(Usuario.class, usuarioId);

                    if (usuario != null) {
                        // Actualizar los campos de la mascota
                        usuario.setNombre(name);
                        usuario.setApellidos(surname);
                        usuario.setEmail(email);
                        usuario.setTelefono(phone);
                        usuario.setDireccion(dir);
                        usuario.setPassword(passwordEncriptada);
                        usuario.setProvincia(provincia);
                        usuario.setLocalidad(localidad);

                        // Guardar los cambios
                        utx.begin();
                        em.merge(usuario);
                        utx.commit();

                        // Redirigir a la lista de mascotas del usuario
                        response.sendRedirect("/home/usuarios");
                        return;
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Mascota no encontrada.");
                    }
                } catch (Exception e) {
                    request.setAttribute("msg", "Error al actualizar la mascota.");
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
                    rd.forward(request, response);
                }
                break;
            }
            case "/delete" -> {
                Long usuarioID = Long.valueOf(request.getParameter("usuarioId"));
                try {
                    Usuario usuario = em.find(Usuario.class, usuarioID);
                    if (usuario != null) {
                        // Eliminar la mascota de la base de datos
                        utx.begin();
                        em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
                        utx.commit();
                        System.out.println("Usuario eliminado con exito");
                        // Redirigir a la vista de mascotas del usuario
                        response.sendRedirect("/home/usuarios");
                        return;
                    } else {
                        // Si no se encontró la mascota
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "La mascota no fue encontrada.");
                    }

                } catch (Exception e) {
                    em.getTransaction().rollback();
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
                    rd.forward(request, response);
                }
            }

        }

    }

    public void save(Usuario u) {
        Long id = u.getUsuarioID();
        try {
            utx.begin();
            if (id == null) {
                em.persist(u);
                Log.log(Level.INFO, "New User saved");
            } else {
                Log.log(Level.INFO, "User {0} updated", id);
                em.merge(u);
            }
            utx.commit();
        } catch (Exception e) {
            Log.log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    public String encriptarPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    private List<String> getLocalidadesPorProvincia(String provincia) {
    Map<String, List<String>> localidades = new HashMap<>();
    
    localidades.put("Álava", Arrays.asList("Vitoria-Gasteiz", "Llodio", "Amurrio"));
    localidades.put("Albacete", Arrays.asList("Albacete", "Hellín", "Villarrobledo"));
    localidades.put("Alicante", Arrays.asList("Alicante", "Elche", "Benidorm"));
    localidades.put("Almería", Arrays.asList("Almería", "Roquetas de Mar", "El Ejido"));
    localidades.put("Ávila", Arrays.asList("Ávila", "Arévalo", "Cebreros"));
    localidades.put("Badajoz", Arrays.asList("Badajoz", "Mérida", "Don Benito"));
    localidades.put("Baleares", Arrays.asList("Palma", "Manacor", "Inca"));
    localidades.put("Barcelona", Arrays.asList("Barcelona", "Badalona", "Terrassa"));
    localidades.put("Burgos", Arrays.asList("Burgos", "Aranda de Duero", "Miranda de Ebro"));
    localidades.put("Cáceres", Arrays.asList("Cáceres", "Plasencia", "Navalmoral de la Mata"));
    localidades.put("Cádiz", Arrays.asList("Cádiz", "Jerez de la Frontera", "Algeciras"));
    localidades.put("Castellón", Arrays.asList("Castellón de la Plana", "Villarreal", "Burriana"));
    localidades.put("Ciudad Real", Arrays.asList("Ciudad Real", "Puertollano", "Tomelloso"));
    localidades.put("Córdoba", Arrays.asList("Córdoba", "Lucena", "Puente Genil"));
    localidades.put("Coruña", Arrays.asList("A Coruña", "Santiago de Compostela", "Ferrol"));
    localidades.put("Cuenca", Arrays.asList("Cuenca", "Tarancón", "Motilla del Palancar"));
    localidades.put("Girona", Arrays.asList("Girona", "Figueres", "Blanes"));
    localidades.put("Granada", Arrays.asList("Granada", "Motril", "Almuñécar"));
    localidades.put("Guadalajara", Arrays.asList("Guadalajara", "Azuqueca de Henares", "Sigüenza"));
    localidades.put("Guipuzcoa", Arrays.asList("San Sebastián", "Eibar", "Irun"));
    localidades.put("Huelva", Arrays.asList("Huelva", "Lepe", "Almonte"));
    localidades.put("Huesca", Arrays.asList("Huesca", "Jaca", "Monzón"));
    localidades.put("Jaén", Arrays.asList("Jaén", "Linares", "Úbeda"));
    localidades.put("León", Arrays.asList("León", "Ponferrada", "Astorga"));
    localidades.put("Lleida", Arrays.asList("Lleida", "Balaguer", "Tàrrega"));
    localidades.put("Rioja", Arrays.asList("Logroño", "Calahorra", "Haro"));
    localidades.put("Lugo", Arrays.asList("Lugo", "Monforte de Lemos", "Vilalba"));
    localidades.put("Madrid", Arrays.asList("Madrid", "Alcobendas", "Getafe"));
    localidades.put("Málaga", Arrays.asList("Málaga", "Marbella", "Antequera"));
    localidades.put("Murcia", Arrays.asList("Murcia", "Cartagena", "Lorca"));
    localidades.put("Navarra", Arrays.asList("Pamplona", "Tudela", "Estella"));
    localidades.put("Orense", Arrays.asList("Ourense", "Verín", "O Barco de Valdeorras"));
    localidades.put("Asturias", Arrays.asList("Oviedo", "Gijón", "Avilés"));
    localidades.put("Palencia", Arrays.asList("Palencia", "Aguilar de Campoo", "Guardo"));
    localidades.put("Las Palmas", Arrays.asList("Las Palmas de Gran Canaria", "Telde", "Arucas"));
    localidades.put("Pontevedra", Arrays.asList("Pontevedra", "Vigo", "Vilagarcía de Arousa"));
    localidades.put("Salamanca", Arrays.asList("Salamanca", "Béjar", "Ciudad Rodrigo"));
    localidades.put("Tenerife", Arrays.asList("Santa Cruz de Tenerife", "La Laguna", "Arona"));
    localidades.put("Cantabria", Arrays.asList("Santander", "Torrelavega", "Castro Urdiales"));
    localidades.put("Segovia", Arrays.asList("Segovia", "Cuéllar", "El Espinar"));
    localidades.put("Sevilla", Arrays.asList("Sevilla", "Dos Hermanas", "Alcalá de Guadaíra"));
    localidades.put("Soria", Arrays.asList("Soria", "Almazán", "El Burgo de Osma"));
    localidades.put("Tarragona", Arrays.asList("Tarragona", "Reus", "Valls"));
    localidades.put("Teruel", Arrays.asList("Teruel", "Alcañiz", "Andorra"));
    localidades.put("Toledo", Arrays.asList("Toledo", "Talavera de la Reina", "Illescas"));
    localidades.put("Valencia", Arrays.asList("Valencia", "Gandía", "Torrent"));
    localidades.put("Valladolid", Arrays.asList("Valladolid", "Medina del Campo", "Tordesillas"));
    localidades.put("Vizcaya", Arrays.asList("Bilbao", "Barakaldo", "Getxo"));
    localidades.put("Zamora", Arrays.asList("Zamora", "Benavente", "Toro"));
    localidades.put("Zaragoza", Arrays.asList("Zaragoza", "Calatayud", "Utebo"));
    localidades.put("Ceuta", Arrays.asList("Ceuta"));
    localidades.put("Melilla", Arrays.asList("Melilla"));

    return localidades.getOrDefault(provincia, Collections.emptyList());
} 
}
