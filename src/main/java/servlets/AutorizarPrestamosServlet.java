package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entidades.Prestamo;
import negocioImplementacion.PrestamoNegocioImplementacion;
import negocioImplementacion.Seguridad;

@WebServlet("/AutorizarPrestamosServlet")
public class AutorizarPrestamosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AutorizarPrestamosServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        Object user = (session != null) ? session.getAttribute("usuario") : null;
        
        if (!Seguridad.sesionActiva(user)) {
            response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
            return;
        }
        
        if (!Seguridad.esAdministrador(user)) {
            response.sendRedirect(request.getContextPath() + "/vistas/Login.jsp");
            return;
        }
		
		PrestamoNegocioImplementacion pni= new PrestamoNegocioImplementacion();
		List<Prestamo> listaPrestamos= pni.listarPrestamos();
		
		request.setAttribute("listaPrestamos", listaPrestamos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/AdministrarPrestamos/AutorizarPrestamos.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
