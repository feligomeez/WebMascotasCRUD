package Controller;

import Model.Cesta;
import Model.Mascota;
import Model.Usuario;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "AdopcionController", urlPatterns = {"/cesta", "/cesta/*"})
public class AdopcionController extends HttpServlet {

    private List<Mascota> mascotasEnAdopcion;
    @PersistenceContext(unitName = "MascotasPU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    private static final Logger Log = Logger.getLogger(Controlador.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        HttpSession session = request.getSession();
        if(session.getAttribute("usuario")!=null){
        request.setAttribute("usuario", session.getAttribute("usuario"));
        } else{
            long id = 0;
            request.setAttribute("usuario", id);
            session.setAttribute("usuario", id);
            
        }  
        mascotasEnAdopcion = new ArrayList<>();
        Query consulta = em.createQuery("SELECT m FROM Mascota m WHERE m.usuario = null", Mascota.class);
        mascotasEnAdopcion = consulta.getResultList();
        
        request.setAttribute("mascotasEnAdopcion", mascotasEnAdopcion);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/adopcion.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String accion = request.getPathInfo();
        session.removeAttribute("message");
        session.removeAttribute("error");

        // Obtener o crear la cesta en sesión
        Cesta cesta = (Cesta) session.getAttribute("cesta");
        if (cesta == null) {
            cesta = new Cesta();
            session.setAttribute("cesta", cesta);
        }

        // Determinar la acción a ejecutar
        if (accion != null) {
            switch (accion) {
                case "/add":
                    long mascotaId = Long.parseLong(request.getParameter("mascotaId"));
                    // Verificar si la mascota está en la lista de venta
                    for (Mascota mascota : mascotasEnAdopcion) {  // Método ficticio que representa la obtención de mascotas en venta
                        if (mascota.getMascotaID() == mascotaId) {
                            cesta.addMascota(mascota);
                            break;
                        }
                    }
                    break;
                case "/delete":
                    mascotaId = Long.parseLong(request.getParameter("mascotaId"));
                    // Verificar si la mascota está en la lista de venta
                    for (Mascota mascota : mascotasEnAdopcion) {  // Método ficticio que representa la obtención de mascotas en venta
                        if (mascota.getMascotaID() == mascotaId) {
                            cesta.deleteMascota(mascota);
                            break;
                        }
                    }
                    break;

                case "/purchase":
                    Long cliente = (Long)session.getAttribute("usuario");
                    if(cliente != 0){
                    Usuario user = em.find(Usuario.class, cliente);
                    if (user == null) {
                        // Redirigir al inicio de sesión si no hay cliente
                        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
                        rd.forward(request, response);
                        return;
                    }

                    // Asociar cada mascota de la cesta con el cliente en la base de datos
                    for (Mascota mascota : cesta.getMascotas()) {
                        mascota.setUsuario(user);  // Asocia la mascota al cliente
                        try{
                        utx.begin();  // Método para actualizar en la base de datos
                        em.merge(mascota);
                        utx.commit();
                        }catch(Exception e){
                            
                        }
                    }

                    // Vaciar la cesta después de la compra
                    cesta.clear();
                    session.setAttribute("message", "¡Compra realizada con éxito!");
                    }
                    session.setAttribute("error", "Antes de comprar, Inicia Sesión");
                    break;
                default:
                    System.out.println("Acción no reconocida: " + accion);
                    break;
            }
        }

        response.sendRedirect(request.getContextPath() + "/cesta");
    }
}
