/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Mascota;
import Model.Mascota.Especie;
import Model.Mascota.Genero;
import Model.Usuario;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.transaction.UserTransaction;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author feligomeez
 */
@WebServlet(name = "MascotaController", urlPatterns = {"/mascotas", "/mascota/*"})
@MultipartConfig
public class MascotaController extends HttpServlet {

    @PersistenceContext(unitName = "MascotasPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(Controlador.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vista = "";
        String accion = "/mascotas";
        if (request.getServletPath().equals("/mascota")) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            } else {
                accion = "error";
            }
        }

        switch (accion) {
            case "/mascotas" -> {
                String usuarioIdParam = request.getParameter("usuarioId");
                if(usuarioIdParam == "0") response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro usuarioID es requerido.");
                // Verificar si se recibió el parámetro
                if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
                    // Convertir el parámetro a entero (si es un número)
                    Long usuarioID = Long.valueOf(usuarioIdParam);

                    // Obtener el usuario y sus mascotas (en un caso real se haría una consulta a la BD)
                    //Usuario usuario = obtenerUsuarioPorId(usuarioID);
                    Usuario usuario = em.find(Usuario.class, usuarioID);

                    // Agregar la lista de mascotas al request
                    request.setAttribute("name", usuario.getNombre());
                    request.setAttribute("userid", usuario.getUsuarioID());
                    request.setAttribute("mascotas", usuario.getMascotas());

                    // Vista JSP donde se mostrarán las mascotas
                    vista = "Mascotas";

                } else {
                    // Si no se pasa un usuarioID válido, redirigir a una página de error o mostrar un mensaje
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro usuarioID es requerido.");
                }
            }
            case "/new" -> {
                String usuarioIdParam = request.getParameter("usuarioId");

                if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
                    // Convertir el parámetro a entero (si es un número)
                    Long usuarioID = Long.valueOf(usuarioIdParam);
                    request.setAttribute("userid", usuarioID);
                    vista = "formMascota";

                } else {
                    // Si no se pasa un usuarioID válido, redirigir a una página de error o mostrar un mensaje
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El parámetro usuarioID es requerido.");
                }

            }
            case "/edit" -> {
                String mascotaIdParam = request.getParameter("mascotaId");
                Long mascotaId = Long.valueOf(mascotaIdParam);

                // Buscar la mascota en la base de datos usando JPA
                Mascota mascota = em.find(Mascota.class, mascotaId);

                if (mascota != null) {
                    // Pasar los datos de la mascota al formulario
                    request.setAttribute("mascota", mascota);
                    vista = "editMascota";
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "La mascota no fue encontrada.");
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
    @SuppressWarnings("empty-statement")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = "/mascotas";
        if (request.getServletPath().equals("/mascota")) {
            if (request.getPathInfo() != null) {
                accion = request.getPathInfo();
            } else {
                accion = "error";
            }
        }
        switch (accion) {
            case "/save" -> {
                Long userid = Long.valueOf(request.getParameter("userid"));
                Usuario user = em.find(Usuario.class, userid);;

                String name = request.getParameter("name");
                float peso = Float.parseFloat(request.getParameter("peso"));
                String raza = request.getParameter("raza");
                Especie especie = Especie.valueOf(request.getParameter("especie"));
                Genero sexo = Genero.valueOf(request.getParameter("sexo"));
                String alergias = request.getParameter("alergias");
                // Crear instancia de Mascota
                Mascota mascota = new Mascota(name, especie, raza, sexo, peso, alergias, user);
                save(mascota);

                // Procesar la imagen
                Part imgPart = request.getPart("img");
                if (imgPart != null && imgPart.getSize() > 0) {
                    String relativePathFolder = "/img/photos";
                    String absolutePathFolder = getServletContext().getRealPath(relativePathFolder);

                    // Guardar la imagen con el ID de la mascota
                    String fileName = mascota.getMascotaID().toString() + ".jpg";
                    File file = new File(absolutePathFolder + File.separator + fileName);

                    try (InputStream fileContent = imgPart.getInputStream(); FileOutputStream fos = new FileOutputStream(file)) {

                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = fileContent.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }

                        response.sendRedirect("http://localhost:8080/home/mascotas?usuarioId=" + user.getUsuarioID());
                        return;
                    } catch (Exception e) {
                        request.setAttribute("msg", "Error: datos no válidos");
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
                        rd.forward(request, response);
                    }

                }
                break;
            }

            case "/update" -> {
                Long mascotaId = Long.valueOf(request.getParameter("mascotaId"));
                String name = request.getParameter("name");
                float peso = Float.parseFloat(request.getParameter("peso"));
                String raza = request.getParameter("raza");
                Especie especie = Especie.valueOf(request.getParameter("especie"));
                Genero sexo = Genero.valueOf(request.getParameter("genero"));
                String alergias = request.getParameter("alergias");

                try {
                    // Buscar la mascota existente en la base de datos
                    Mascota mascota = em.find(Mascota.class, mascotaId);

                    if (mascota != null) {
                        // Actualizar los campos de la mascota
                        mascota.setNombre(name);
                        mascota.setPeso(peso);
                        mascota.setRaza(raza);
                        mascota.setEspecie(especie);
                        mascota.setGenero(sexo);
                        mascota.setAlergias(alergias);

                        // Guardar los cambios
                        utx.begin();
                        em.merge(mascota);
                        utx.commit();

                        // Redirigir a la lista de mascotas del usuario
                        response.sendRedirect("/home/mascotas?usuarioId=" + mascota.getUsuario().getUsuarioID());
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
                Long mascotaID = Long.valueOf(request.getParameter("mascotaId"));
                try {
                    // Obtener el usuario desde la base de datos
                    Usuario usuario = em.find(Usuario.class, usuarioID);
                    Mascota mascotaAEliminar = null;
                    for (Mascota mascota : usuario.getMascotas()) {
                        if (mascota.getMascotaID().equals(mascotaID)) {
                            mascotaAEliminar = mascota;
                            break;
                        }
                    }

                    // Si se encontró la mascota, eliminarla de la lista
                    if (mascotaAEliminar != null) {
                        // Eliminar la mascota de la base de datos
                        utx.begin();
                        em.remove(em.contains(mascotaAEliminar) ? mascotaAEliminar : em.merge(mascotaAEliminar));
                        utx.commit();
                        System.out.println("Mascota eliminada con exito");
                        // Redirigir a la vista de mascotas del usuario
                        response.sendRedirect("/home/mascotas?usuarioId=" + usuarioID);
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

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/error.jsp");
        rd.forward(request, response);

    }

    public void save(Mascota m) {
        Long id = m.getMascotaID();
        try {
            utx.begin();
            if (id == null) {
                em.persist(m);
                Log.log(Level.INFO, "New Pet saved");
            } else {
                Log.log(Level.INFO, "User {0} updated", id);
                em.merge(m);
            }
            utx.commit();
        } catch (Exception e) {
            Log.log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

}
