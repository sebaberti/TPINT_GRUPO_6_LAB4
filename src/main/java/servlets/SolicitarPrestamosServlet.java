package servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entidades.Plazo;
import negocioImplementacion.PlazoNegocioImplementacion;

@WebServlet("/SolicitarPrestamosServlet")
public class SolicitarPrestamosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SolicitarPrestamosServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  	
		if(request.getParameter("btnSimular")!=null) {
			simularPrestamo(request, response);
			return;
		}
		
		if(request.getParameter("btnConfirmar") != null) {
			String mensajeInformativo= "Su pedido de préstamo será evaluado.";
			request.setAttribute("mensajeInformativo", mensajeInformativo);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/vistas/MensajesInformativos.jsp");
			dispatcher.forward(request, response);
			return;
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
	        if (request.getParameter("OpcionesPlazo") != null && !request.getParameter("OpcionesPlazo").isEmpty()) {
	            cuotasParam = request.getParameter("OpcionesPlazo");
	            cantCuotas = Integer.parseInt(cuotasParam);
	            request.setAttribute("idPlazoSeleccionado", cantCuotas);
	            
	        } else {
	            mensajeError += "Debe seleccionar una cantidad de cuotas. <br>";
	            datosValidos = false;
	        }
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	    }
	
	
	if (!datosValidos) {
	    request.setAttribute("mensajeError", mensajeError);
	    request.getRequestDispatcher("/vistas/SolicitarPrestamos.jsp").forward(request, response);
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
request.getRequestDispatcher("/vistas/SolicitarPrestamos.jsp").forward(request, response);
}
	
}


