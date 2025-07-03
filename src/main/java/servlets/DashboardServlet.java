package servlets;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import entidades.Movimiento;
import negocio.CuentaNegocio;
import negocioImplementacion.CuentaNegocioImplementacion;
import negocio.MovimientoNegocio;
import negocioImplementacion.MovimientoNegocioImplementacion;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CuentaNegocio cuentaNegocio = new CuentaNegocioImplementacion();
	MovimientoNegocio movNegocio=new MovimientoNegocioImplementacion();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String fechaDesde = request.getParameter("fechaDesde");
		String fechaHasta = request.getParameter("fechaHasta");

		LocalDate desde;
		LocalDate hasta;

		// Si alguno es null → usar fecha actual
		if (fechaDesde == null || fechaHasta == null) {
		    LocalDate hoy = LocalDate.now();
		    desde = hoy;
		    hasta = hoy;
		}
		// Si llegan vacíos (""), mostrar mensaje de error
		else if (fechaDesde.trim().isEmpty() || fechaHasta.trim().isEmpty()) {
		    request.setAttribute("error", "Debe completar ambas fechas para generar el reporte.");
		    request.getRequestDispatcher("/vistas/Admin/Reportes/reportes.jsp").forward(request, response);
		    return;
		}
		// Si todo está OK, parsear normalmente
		else {
		    desde = LocalDate.parse(fechaDesde);
		    hasta = LocalDate.parse(fechaHasta);
		}
		 //Resumen promedio cuentas por cliente
		 int totalCuentas = cuentaNegocio.contarCuentas(desde, hasta);
		 int totalClientes= cuentaNegocio.cantidadClientesxPeriodo(desde, hasta);
		 double promedioCuentasXcliente=cuentaNegocio.promedioCuentasXCliente(desde, hasta);
		 
		 //Rentabilidad
		 double totalIngresos= movNegocio.obtenerIngresos(desde, hasta);
		 double totalEgresos = movNegocio.obtenerEgresos(desde, hasta);
		 double rentabilidad= calcularRentabilidad(totalIngresos,totalEgresos);
		
		request.setAttribute("totalCuentas", totalCuentas);
		request.setAttribute("totalClientes", totalClientes);
		request.setAttribute("promedioCuentasXcliente",promedioCuentasXcliente);
		
		//Rentabilidad
		request.setAttribute("totalIngresos",totalIngresos);
		request.setAttribute("totalEgresos",totalEgresos);
		request.setAttribute("rentabilidad",rentabilidad);
		
		request.getRequestDispatcher("/vistas/Admin/Reportes/reportes.jsp").forward(request, response);
	}
	
	private double calcularRentabilidad (double ingresos, double egresos) {
		
		double Rentabilidad=0.0;
		
		Rentabilidad=ingresos-egresos;
		
		return Rentabilidad;
		
	}			
}
