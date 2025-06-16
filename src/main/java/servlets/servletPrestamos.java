package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/servletPrestamos")
public class servletPrestamos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public servletPrestamos() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("btnSimular")!=null) {
			simularPrestamo(request, response);
		}
		
		if(request.getParameter("btnConfirmar") != null) {
			String mensajeInformativo= "Su pedido de préstamo será evaluado.";
			request.setAttribute("mensajeInformativo", mensajeInformativo);
			request.getRequestDispatcher("/vistas/MensajesInformativos.jsp").forward(request, response);
		}		
	}

		
public void simularPrestamo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	boolean datosValidos = true;			
	double monto = 0.0;			
	int cantCuotas = 0;
	String montoParam;
	String cuotasParam;
	String mensajeError = "";

	
	    try {
	    	if (request.getParameter("monto") != null && !request.getParameter("monto").isEmpty()) {
	    	montoParam = request.getParameter("monto");		
	        montoParam = montoParam.replace(".", "").replace(",", ".");
			request.setAttribute("monto", montoParam);
	        monto = Double.parseDouble(montoParam);
	    	} else {
	    		mensajeError+= "Debe ingresar un monto. <br>";
	    		datosValidos=false;
	    	}
	    } catch (NumberFormatException e) {
	    	mensajeError += "Debe ingresar un monto válido. <br> ";
	    	datosValidos=false;
	    }
	

	
	        try {
	        	if (request.getParameter("cuotas") != null && !request.getParameter("cuotas").isEmpty()) {
	        	cuotasParam = request.getParameter("cuotas");
				request.setAttribute("cuotas", cuotasParam);
	            cantCuotas = Integer.parseInt(cuotasParam);
	        	} else {
	        		mensajeError += "Debe seleccionar una cantidad de cuotas. <br>";
	        		datosValidos=false;
	        	}
	        } catch (NumberFormatException e) {
	            e.printStackTrace();		            
	        }
	
	
	if (!datosValidos) {
	    request.setAttribute("mensajeError", mensajeError);
	    request.getRequestDispatcher("/vistas/Prestamos.jsp").forward(request, response);
	    return;
	}

ArrayList<String> fechas = new ArrayList<>();
LocalDate hoy = LocalDate.now();
DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

for (int i = 1; i <= cantCuotas; i++) {
    LocalDate fechaCuota = hoy.plusMonths(i);
    fechas.add("Cuota " + i + ": " + fechaCuota.format(formato));
}

request.setAttribute("fechasCuotas", fechas);
request.getRequestDispatcher("/vistas/Prestamos.jsp").forward(request, response);
}
	
}


