package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImplementacion.ClienteDaoImplementacion;
import entidades.ReporteDeCliente;
import negocioImplementacion.ReporteClienteNegocioImplementacion;

@WebServlet("/ReporteDeClienteServlet")
public class ReporteDeClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ReporteClienteNegocioImplementacion rep= new ReporteClienteNegocioImplementacion();
 
    public ReporteDeClienteServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); 
		ClienteDaoImplementacion cdi= new ClienteDaoImplementacion();
		
		String dni = request.getParameter("dniIngresado");

		if ((dni == null || dni.trim().isEmpty()) && session.getAttribute("dniBuscado") != null) {
		    dni = session.getAttribute("dniBuscado").toString();		   
		    session.removeAttribute("dniBuscado");  
		}
		
		request.setAttribute("dniIngresado", dni);
		ReporteDeCliente reporte;
		ReporteDeCliente reporteMovimientos;
		
		  if(request.getParameter("btnReporte")!=null) {
			  
		  if (dni != null && !dni.trim().isEmpty()) {
			    if(!validarDni(dni)) {
			    	request.setAttribute("verificacionDni", "Ingrese un formato vállido.");
					request.getRequestDispatcher("/vistas/Admin/Reportes/ReporteDeCliente.jsp").forward(request, response);
					return;
			    }
				if(!cdi.existeDNI(dni)) {
					request.setAttribute("verificacionDni", "EL dni ingresado no existe en nuestra base de datos. Intente nuevamente");
					request.getRequestDispatcher("/vistas/Admin/Reportes/ReporteDeCliente.jsp").forward(request, response);
					return;
				}
				
	            reporte = rep.obtenerReporteDeCliente(dni);
	            request.setAttribute("reporte", reporte); 		  
		 }		 
		  
		 }
		  
		  if(request.getParameter("btnMovimientos")!=null) {
			    reporte = rep.obtenerReporteDeCliente(dni);
	            request.setAttribute("reporte", reporte);         
	            String anioParam = request.getParameter("anio");	 
			    int anio = (anioParam != null && !anioParam.isEmpty()) ? Integer.parseInt(anioParam) : java.time.Year.now().getValue();
			    reporteMovimientos= rep.obtenerResumenIngresosEgresosPorDniYAño(dni, anio);		
			    request.setAttribute("reporteMovimientos", reporteMovimientos);
			    request.setAttribute("anioSeleccionado", anio);
	      }
		
		  request.getRequestDispatcher("/vistas/Admin/Reportes/ReporteDeCliente.jsp").forward(request, response);
		  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
	
	public boolean validarDni(String dni) {
	    try {
	        int dniValido = Integer.parseInt(dni);
	        return dniValido >= 1000000 && dniValido <= 99999999;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

}
