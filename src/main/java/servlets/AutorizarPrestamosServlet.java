package servlets;

import java.io.IOException;
import java.math.BigDecimal;
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
		listaPrestamos.sort((p1, p2) -> Integer.compare(p1.getEstado(), p2.getEstado()));
		
		request.setAttribute("listaPrestamos", listaPrestamos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/AdministrarPrestamos/AutorizarPrestamos.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	PrestamoNegocioImplementacion pni= new PrestamoNegocioImplementacion();
	
	String idPrestamoParam = request.getParameter("idPrestamo");
    String mensaje="";
    
    if (idPrestamoParam != null) {
        int idPrestamo = Integer.parseInt(idPrestamoParam);

        if (request.getParameter("btnReportes") != null) {              	
        	 HttpSession session = request.getSession(); 
        	 String dniParam= request.getParameter("DniCliente");
        	    session.setAttribute("dniBuscado", dniParam);
        	    response.sendRedirect(request.getContextPath() + "/ReporteDeClienteServlet");
        	    return;
        }

        if (request.getParameter("btnAprobar") != null) {
            boolean exito= pni.autorizarPrestamo(idPrestamo);
            mensaje = exito ? "Préstamo ID " + idPrestamo + " aprobado con éxito"
                    : "No se pudo aprobar el préstamo ID " + idPrestamo;
        }

        if (request.getParameter("btnRechazar") != null) {
            boolean exito= pni.rechazarPrestamo(idPrestamo);
            mensaje = exito ? "Préstamo ID " + idPrestamo + " rechazado con éxito"
                    : "No se pudo rechazar el préstamo ID " + idPrestamo;            
        }
        
        List<Prestamo> listaPrestamos= pni.listarPrestamos();		
        listaPrestamos.sort((p1, p2) -> Integer.compare(p1.getEstado(), p2.getEstado()));
		request.setAttribute("listaPrestamos", listaPrestamos);
		
		request.setAttribute("mensaje", mensaje);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/Admin/AdministrarPrestamos/AutorizarPrestamos.jsp");
		dispatcher.forward(request, response);
	}
	}
}
