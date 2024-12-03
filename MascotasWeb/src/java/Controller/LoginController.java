/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Usuario;
import jakarta.annotation.Resource;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author feligomeez
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login", "/login/auntenticar", "/logout", "/restablecer", "/cambiarPassword"})
public class LoginController extends HttpServlet {

    @PersistenceContext(unitName = "MascotasPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(Controlador.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entro a login");
        boolean logout = false;
        String vista = "";
        String accion = request.getServletPath();
        switch (accion) {
            case "/login":
                vista = "login";
                break;
            case "/logout":
                HttpSession session = request.getSession();
                if (session != null) {
                    session.removeAttribute("usuario"); // Cerrar sesión
                    request.setAttribute("mensaje", "Has iniciado sesión correctamente.");
                    logout = true;
                }
                break;
            case "/restablecer":
                vista = "recuperarPass";
        }
        if (logout == true) {
            response.sendRedirect("http://localhost:8080/home/cesta");
            return;
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/" + vista + ".jsp");
        rd.forward(request, response);

    }

    @Override
    @SuppressWarnings("empty-statement")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email;
        String password;
        Usuario usuario;
        HttpSession session = request.getSession();
        String accion = request.getServletPath();
        switch (accion) {
            case "/login":
                email = request.getParameter("email");
                password = request.getParameter("password");
                String passwordEncriptada = encriptarPassword(password);
                usuario = autenticarUsuario(email, passwordEncriptada);
                String captchaToken = request.getParameter("g-recaptcha-response");
                if (captchaToken == null || !verificarCaptcha(captchaToken)) {
                    request.setAttribute("error", "Captcha inválido. Por favor, inténtalo de nuevo.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
                    dispatcher.forward(request, response);
                    return;
                }

                if (usuario != null) {
                    // Iniciar sesión y guardar usuario en la sesión
                    session.setAttribute("usuario", usuario.getUsuarioID());
                    request.setAttribute("mensaje", "Has iniciado sesión correctamente.");
                    response.sendRedirect("/home/cesta"); // Redireccionar a la página principal después de iniciar sesión
                } else {
                    response.sendRedirect("/home/login"); // Redirigir a la página de inicio de sesión con mensaje de error
                }
                break;
            case "/logout":
                if (session != null) {
                    session.removeAttribute("usuario"); // Cerrar sesión
                }
                response.sendRedirect("/home/cesta");
                break;
            case "/restablecer":
                email = request.getParameter("email");
                String tlf = request.getParameter("telefono");
                usuario = buscarUsuarioPorEmail(email);

                if (usuario != null && usuario.getTelefono().equals(tlf)) {
                    // Redirige al formulario de cambio de contraseña
                    request.setAttribute("email", email);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cambiarPassword.jsp");
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("error", "Correo o Telefono incorrecto.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/recuperarPass.jsp");
                    dispatcher.forward(request, response);
                }
                break;
            case "/cambiarPassword":
                email = request.getParameter("email");
                password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");

                if (password.equals(confirmPassword)) {
                    try {
                        utx.begin();
                        usuario = buscarUsuarioPorEmail(email);
                        if (usuario != null) {
                            usuario.setPassword(encriptarPassword(password));
                            em.merge(usuario);
                        }
                        utx.commit();
                    } catch (Exception e) {
                    }
                    session.setAttribute("message", "Contraseña actualizada");
                    response.sendRedirect("http://localhost:8080/home/cesta");
                } else {
                    request.setAttribute("error", "Las contraseñas no coinciden.");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cambiarPassword.jsp");
                    dispatcher.forward(request, response);
                }
                break;
        }

    }

    private Usuario autenticarUsuario(String username, String password) {
        try {
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.password = :password");
            query.setParameter("email", username);
            query.setParameter("password", password);
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private String encriptarPassword(String password) {
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

    private boolean verificarCaptcha(String token) throws IOException {
        String secretKey = "6LeFdpAqAAAAANVl9R5DR6IwA23rN85icsRx6IX6";
        String url = "https://www.google.com/recaptcha/api/siteverify";

        URL obj = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Enviar parámetros
        String postParams = "secret=" + secretKey + "&response=" + token;
        try (OutputStream os = connection.getOutputStream()) {
            os.write(postParams.getBytes());
            os.flush();
        }

        // Leer respuesta
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parsear respuesta JSON
        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        return jsonResponse.get("success").getAsBoolean();
    }

}
